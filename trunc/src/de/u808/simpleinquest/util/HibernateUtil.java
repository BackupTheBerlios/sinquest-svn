package de.u808.simpleinquest.util;

import org.hibernate.*;
import org.hibernate.cfg.*;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new AnnotationConfiguration().buildSessionFactory();
        } catch (Throwable ex) {
            // Log exception!
            throw new ExceptionInInitializerError(ex);
        }
    }

    //TODO fix me
    public static Session getSession()
            throws HibernateException {
    	Session session = sessionFactory.getCurrentSession();
    	if(session == null){
    		session = sessionFactory.openSession();
    	}
        return session;
    }
}
