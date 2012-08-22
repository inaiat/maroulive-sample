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
package br.com.digilabs.wicket.crud;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CrudDao {

	<T> T load(Class<T> entity, Serializable id);

	<T> T get(Class<T> entity, Serializable id);

	void update(Object object);

	Serializable save(Object object);

	void saveOrUpdate(Object object);

	void delete(Object object);

	<T> List<T> getList(Class<T> entity);

	<T> List<T> findByCriteria(DetachedCriteria criteria);

	<T> T getUniqueResultByCriteria(Class<T> entity, DetachedCriteria criteria);

}
