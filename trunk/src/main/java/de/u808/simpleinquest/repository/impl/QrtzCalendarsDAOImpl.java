package de.u808.simpleinquest.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.quartz.JobPersistenceException;
import org.springframework.orm.hibernate3.HibernateCallback;

import de.u808.simpleinquest.domain.QrtzCalendars;
import de.u808.simpleinquest.repository.QrtzCalendarsDAO;

public class QrtzCalendarsDAOImpl extends GenericHibernateDAO<QrtzCalendars, Long> implements QrtzCalendarsDAO{

	public List<String> getCalendarNames()
			throws JobPersistenceException {
		
		return (List<String>) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(getPersistentClass());
                criteria.setProjection(Projections.property("calendarName"));
                
                return criteria.list();
            }
        });
		
	}

}
