/*
 *  Copyright 2010 henrique.
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
package br.com.digilabs.persistence;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Inaiat 
 */
public abstract class AbstractHibernateDaoImpl<T extends Serializable, KeyType extends Serializable> implements AbstractGenericDao<T, KeyType> {

    protected Class<T> domainClass = getDomainClass();
    protected SessionFactory sessionFactory;

    /**
     * Method to return the class of the domain object
     */
    protected abstract Class<T> getDomainClass();

    @Autowired
    protected void setupSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @SuppressWarnings("unchecked")
    public T load(KeyType id) {
        return (T) getSession().load(domainClass, id);
    }

    public void update(T t) {
        getSession().update(t);
    }

    public void save(T t) {
        getSession().save(t);
    }

    public void saveOrUpdate(T t) {
        getSession().saveOrUpdate(t);
    }

    public void delete(T t) {
        getSession().delete(t);
    }

    public List<T> find(String query, Object... values) {
        Query queryObject = getSession().createQuery(query);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        return queryObject.list();
    }

    @SuppressWarnings("unchecked")
    public List<T> getList() {
        return (getSession().createQuery("from " + domainClass.getName() + " x").list());
    }

    public void deleteById(KeyType id) {
        Object obj = load(id);
        getSession().delete(obj);
    }

    @SuppressWarnings("unchecked")
    public T get(KeyType id) {
        return (T) getSession().get(domainClass, id);
    }

    public int count() {
        @SuppressWarnings("unchecked")
        List<T> list = getSession().createQuery(
                "select count(*) from " + domainClass.getName() + " x").list();
        Integer count = (Integer) list.get(0);
        return count;
    }

    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(DetachedCriteria criteria) {
        return criteria.getExecutableCriteria(getSession()).list();

    }

    @SuppressWarnings("unchecked")
    public T getUniqueResultByCriteria(DetachedCriteria criteria) {
        return (T) criteria.getExecutableCriteria(getSession()).uniqueResult();
    }

    public Query createQuery(String hql) {
        return getSession().createQuery(hql);
    }
}
