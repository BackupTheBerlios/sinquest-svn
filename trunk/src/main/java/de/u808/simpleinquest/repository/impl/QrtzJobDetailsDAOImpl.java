package de.u808.simpleinquest.repository.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;

import de.u808.simpleinquest.domain.QrtzJobDetails;
import de.u808.simpleinquest.repository.QrtzJobDetailsDAO;

public class QrtzJobDetailsDAOImpl extends
		GenericHibernateDAO<QrtzJobDetails, Long> implements QrtzJobDetailsDAO {

	public List<String> getJobGroupNames() {

		return (List<String>) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session) {
						Criteria criteria = session
								.createCriteria(getPersistentClass());
						criteria
								.setProjection(Projections.property("jobGroup"));

						return criteria.list();
					}
				});
	}

	public List<String> getJobNames() {
		// TODO Auto-generated method stub
		return null;
	}
}
