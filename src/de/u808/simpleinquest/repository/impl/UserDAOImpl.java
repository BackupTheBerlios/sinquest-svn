package de.u808.simpleinquest.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import de.u808.simpleinquest.domain.User;
import de.u808.simpleinquest.repository.UserDAO;

public class UserDAOImpl extends GenericHibernateDAO<User, Long> implements UserDAO {

	public User findByUsername(final String username) {
		return (User) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(getPersistentClass());
                criteria.add(Restrictions.like("username", username));
                return criteria.uniqueResult();
            }
        });
	}

}
