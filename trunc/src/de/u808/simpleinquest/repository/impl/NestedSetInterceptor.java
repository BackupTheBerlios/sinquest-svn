package de.u808.simpleinquest.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.Type;

import de.u808.simpleinquest.domain.File;

// mostly coming from new version of Hibernate "CaveatEmptor" sample
// http://anonsvn.jboss.org/repos/hibernate/trunk/CaveatEmptor/HiA-SE
@SuppressWarnings({"unchecked", "deprecation"})
public class NestedSetInterceptor extends EmptyInterceptor {
	private static final long serialVersionUID = 4073508315368385925L;

	private static Log log = LogFactory.getLog(NestedSetInterceptor.class);

        private Collection newNodes = new ArrayList();
        private Collection deletedNodes = new ArrayList();

        private SessionFactory sessionFactory;

        public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof File && ((File) entity).getThread() != 0) {
            File node = (File) entity;
            File oldParent = null;
            File newParent = null;

            // Find index of "parent" property
            int parentPropertyIndex = -1;
            for (int it = 0; it < propertyNames.length; it++) {
                String propertyName = propertyNames[it];
                if (propertyName.equals("parent")) parentPropertyIndex = it;
            }
            // Get old and current state for the "parent" property
            if (previousState != null) oldParent = (File) previousState[parentPropertyIndex];
            if (currentState != null) newParent = (File) currentState[parentPropertyIndex];

            // Move the node if parent changed
            if ( oldParent != null && !oldParent.equals(newParent) ) {
                // Delete the node from its current position (possibly also its children)
                log.debug("Node will be deleted: " + node);
                deletedNodes.add(node);
                if (newParent != null) {
                    // Place it in new position
                    log.debug("Node will be inserted: " + node);
                    newNodes.add(node);
                }
            }
        }
        return null;
     }

    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof File && ((File) entity).getThread() != 0) {
            log.debug("Node will be deleted: " + entity);
            deletedNodes.add(entity);
        }
        super.onDelete(entity, id, state, propertyNames, types);
    }

    public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        if (entity instanceof File && ((File) entity).getThread() == 0) {
            log.debug("Node will be inserted: " + entity);
            newNodes.add(entity);
        }
        return false;
    }

    public void postFlush(Iterator entities) throws CallbackException {
        // Get a collection of all nodes in memory for synchronization
        Collection nodesInContext = new HashSet();
        while (entities.hasNext()) {
            Object o = entities.next();
            if (o instanceof File) {
                nodesInContext.add(o);
            }
        }
        
        // Session.connection() is deprecated - see
        // http://forum.hibernate.org/viewtopic.php?t=974518 
        // for more info.
        Session tmpSession = sessionFactory.openSession( sessionFactory.getCurrentSession().connection() );
        try {
            // Handle delete nodes in tree
            Collection alreadyDeleted = new ArrayList();
            for (Iterator it = deletedNodes.iterator(); it.hasNext();) {
                File node = (File) it.next();
                String entityName = tmpSession.getSessionFactory()
                        .getClassMetadata( node.getClass() ).getEntityName();

                if (alreadyDeleted.contains(node)) continue;

                // Node with children, deleting subtree
                log.debug("Deleting node: " + node);

                long moveOffset = 2;

                if (node.getRight() != (node.getLeft() + 1) ) {
                    // Node with children, deleting subtree
                    log.debug("Deleting subtree of node: " + node);

                    // Calculate update offset for other nodes
                    moveOffset = (int)Math.floor( (node.getRight() -
                                                       node.getLeft()) / 2 );
                    moveOffset = 2 * (1 + moveOffset);

                    // Add subtree nodes to already deleted collection, avoid duplicate updates
                    for (Iterator subit = deletedNodes.iterator(); subit.hasNext();) {
                        File n = (File) subit.next();
                        if (n.getThread() == node.getThread()
                            && n.getLeft() > node.getLeft()
                            && n.getLeft() < node.getRight() ) {
                            log.debug("Subtree node, mark as already deleted: " + n);
                            alreadyDeleted.add(n);
                        }
                    }
                }

                Query updateLeft = tmpSession
                        .createQuery("update " + entityName + " n set n.left = n.left - :offset " +
                                     "where n.thread = :thread and n.left > :right");
                updateLeft.setParameter("offset", moveOffset);
                updateLeft.setParameter("thread", node.getThread());
                updateLeft.setParameter("right", node.getRight());
                updateLeft.executeUpdate();
                for (Iterator itContext = nodesInContext.iterator(); itContext.hasNext();) {
                    File n = (File) itContext.next();
                    if (n.getThread() == node.getThread()
                        && n.getLeft() > node.getRight()) {
                        n.setLeft(n.getLeft() - moveOffset);
                        log.debug("Updated node in memory: " + n);
                    }
                }

                Query updateRight = tmpSession
                        .createQuery("update " + entityName + " n set n.right = n.right - :offset " +
                                     "where n.thread = :thread and n.right > :right");
                updateRight.setParameter("offset", moveOffset);
                updateRight.setParameter("thread", node.getThread());
                updateRight.setParameter("right", node.getRight());
                updateRight.executeUpdate();
                for (Iterator itContext = nodesInContext.iterator(); itContext.hasNext();) {
                    File n = (File) itContext.next();
                    if (n.getThread() == node.getThread()
                        && n.getRight() > node.getRight()) {
                        n.setRight(n.getRight() - moveOffset);
                        log.debug("Updated node in memory: " + n);
                    }
                }
            }

            // Handle new nodes in tree
            for (Iterator it = newNodes.iterator(); it.hasNext();) {
                File node = (File) it.next();
                String entityName = tmpSession.getSessionFactory()
                        .getClassMetadata( node.getClass() ).getEntityName();

                if (node.getParent() != null) {
                    // New child node
                    log.debug("New child node: " + node);
                    long parentThread = node.getParent().getThread();
                    long parentRight = node.getParent().getRight();

                    Query updateLeft = tmpSession
                            .createQuery("update " + entityName + " n set n.left = n.left + 2 " +
                                         "where n.thread = :thread and n.left > :right");
                    updateLeft.setParameter("thread", parentThread);
                    updateLeft.setParameter("right", parentRight);
                    updateLeft.executeUpdate();
                    for (Iterator itContext = nodesInContext.iterator(); itContext.hasNext();) {
                        File n = (File) itContext.next();
                        if (n.getThread() == parentThread && n.getLeft() > parentRight) {
                            n.setLeft(n.getLeft() + 2);
                            log.debug("Updated node in memory: " + n);
                        }
                    }

                    Query updateRight = tmpSession
                            .createQuery("update " + entityName + " n set n.right = n.right + 2 " +
                                         "where n.thread = :thread and n.right >= :right");
                    updateRight.setParameter("thread", parentThread);
                    updateRight.setParameter("right", parentRight);
                    updateRight.executeUpdate();
                    for (Iterator itContext = nodesInContext.iterator(); itContext.hasNext();) {
                        File n = (File) itContext.next();
                        if (n.getThread() == parentThread && n.getRight() >= parentRight) {
                            n.setRight(n.getRight() + 2);
                            log.debug("Updated node in memory: " + n);
                        }
                    }

                    Query updateNode = tmpSession
                            .createQuery("update " + entityName + " n set n.thread = :thread, " +
                                         "n.left = :left, " +
                                         "n.right = :right " +
                                         "where n.id = :nid");
                    updateNode.setParameter("thread", parentThread);
                    updateNode.setParameter("left", parentRight);
                    updateNode.setParameter("right", parentRight + 1);
                    updateNode.setParameter("nid", node.getId());
                    updateNode.executeUpdate();

                    node.setThread(parentThread);
                    node.setLeft(parentRight);
                    node.setRight(parentRight + 1);
                    log.debug("Inserted node: " + node);
                } else {
                    // New root node, hence new thread (thread identifier is root node identifier)
                    log.debug("New root node: " + node);
                    node.setThread(node.getId());

                    // Set thread in database
                    Query updateInDB =
                            tmpSession.createQuery("update " + entityName + " n set n.thread = :thread " +
                                                   "where n.id = :nid");
                    updateInDB.setParameter("thread", node.getId());
                    updateInDB.setParameter("nid", node.getId());
                    updateInDB.executeUpdate();
                    log.debug("Inserted node: " + node);
                }
            }

        } finally {
            tmpSession.close();
            newNodes.clear();
            deletedNodes.clear();
        }
    }
}

