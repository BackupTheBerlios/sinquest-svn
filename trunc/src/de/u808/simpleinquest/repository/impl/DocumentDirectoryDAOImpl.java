package de.u808.simpleinquest.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import de.u808.simpleinquest.domain.DocumentDirectory;
import de.u808.simpleinquest.repository.DocumentDirectoryDAO;

public class DocumentDirectoryDAOImpl extends GenericHibernateDAO<DocumentDirectory, Long> implements DocumentDirectoryDAO {

	@Override
	public DocumentDirectory findByPath(final String path) {
		return (DocumentDirectory) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(getPersistentClass());
                criteria.add(Restrictions.like("path", path));
                return criteria.uniqueResult();
            }
        });
	}

}
