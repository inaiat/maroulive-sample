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

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class GenericDaoImpl extends HibernateDaoSupport implements GenericDao {

    @Autowired
    protected void setupSessionFactory(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    public Object load(Class<?> entity, Serializable id) {
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

    public Object get(Class<?> entity, Serializable id) {
        return getHibernateTemplate().get(entity, id);
    }

    public List<?> getList(Class<?> entity) {
        return (getHibernateTemplate().find("from " + entity.getName() + " x"));
    }

    public void initialize(Object proxy) {
        getHibernateTemplate().initialize(proxy);
    }

    public List<?> findByCriteria(DetachedCriteria criteria) {
        return getHibernateTemplate().findByCriteria(criteria);
        
    }

    public Object getUniqueResultByCriteria(DetachedCriteria criteria) {
        return criteria.getExecutableCriteria(getSession()).uniqueResult();
    }
    
}
