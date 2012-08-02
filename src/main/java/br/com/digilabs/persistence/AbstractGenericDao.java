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

import java.util.List;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Inaiat 
 */
@Transactional
public interface AbstractGenericDao<DomainObject, KeyType> {

    DomainObject load(KeyType id);    
    
    List<DomainObject> find(String query, final Object... values);

    void update(DomainObject object);

    void save(DomainObject object);

    void saveOrUpdate(DomainObject object);

    void delete(DomainObject object);

    void deleteById(KeyType id);

    List<DomainObject> getList();

    DomainObject get(KeyType id);

    List<DomainObject> findByCriteria(DetachedCriteria criteria);

    DomainObject getUniqueResultByCriteria(DetachedCriteria criteria);
    
    Query createQuery(String hql);
}