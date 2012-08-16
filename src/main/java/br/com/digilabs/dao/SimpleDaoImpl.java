/*
 *  Copyright 2010 inaiat.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package br.com.digilabs.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class SimpleDaoImpl extends HibernateDaoSupport implements SimpleDao {

    @Autowired
    protected void setupSessionFactory(SessionFactory sessionFactory) {
	setSessionFactory(sessionFactory);
    }

    public <T> T get(Class<T> entity, Serializable id) {
	return getHibernateTemplate().get(entity, id);
    }

    public <T> T load(Class<T> entity, Serializable id) {
	return getHibernateTemplate().load(entity, id);
    }

    public void update(Object object) {
	getHibernateTemplate().update(object);
    }

    public Serializable save(Object object) {
	return getHibernateTemplate().save(object);
    }

    public void saveOrUpdate(Object object) {
	getHibernateTemplate().saveOrUpdate(object);
    }

    public void delete(Object object) {
	getHibernateTemplate().delete(object);
    }

    @SuppressWarnings("unchecked")
    public <T> T getUniqueResultByCriteria(Class<T> entity,
	    DetachedCriteria criteria) {
	return (T) criteria.getExecutableCriteria(getSession()).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getList(Class<T> entity) {
	return getHibernateTemplate().find("from " + entity.getName() + " x");
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByCriteria(DetachedCriteria criteria) {
	return getHibernateTemplate().findByCriteria(criteria);
    }

}
