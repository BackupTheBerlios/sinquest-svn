/*
 * Copyright 2008-2009 Andreas Friedel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.u808.simpleinquest.repository.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import de.u808.simpleinquest.repository.GenericDAO;

public abstract class GenericHibernateDAO<T, ID extends Serializable> 
		implements GenericDAO<T, ID> {

	private Class<T> persistentClass;
	
    private HibernateTemplate hibernateTemplate;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}
	
	protected void setFetchModJoin(final String[] fetchList, Criteria criteria) {
        if (fetchList != null) {
            for (String aFetchList : fetchList) {
                criteria.setFetchMode(aFetchList, FetchMode.JOIN);
            }
        }
    }

	@SuppressWarnings("unchecked")
	public T findById(final ID id) {
		T entity;
		entity = (T) this.getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(getPersistentClass());
                criteria.add( Restrictions.naturalId()
                        .set("id", id)                        
                    ).setCacheable(true)
                    .uniqueResult();
                return criteria.uniqueResult();
            }
        });
		return entity;
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return findByCriteria();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance) {
		return hibernateTemplate.findByExample(exampleInstance);
	}

	@SuppressWarnings("unchecked")
	public T makePersistent(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
		return entity;
	}

	public void makeTransient(T entity) {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(final Criterion... criterion) {
		return (List<T>) this.hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(getPersistentClass());
                for (Criterion c : criterion) {
                	criteria.add(c);
        		}
                return criteria.list();
            }
        });
	}

}
