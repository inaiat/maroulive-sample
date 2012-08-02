/*
 * Copyright 2012 inaiat.
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
package br.com.digilabs.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class BasicEntity implements Serializable {

    private static final long serialVersionUID = 1419739314147011332L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BasicEntity() {
    }


    @Override
    public boolean equals(Object arg0) {
        if (arg0 == null) {
            return false;
        }
        if (arg0 instanceof BasicEntity) {
            BasicEntity basicEntity = (BasicEntity) arg0;
            if (basicEntity.getId() != null && this.id.equals(basicEntity.getId()) && this.id != 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    @Override
    public int hashCode() {
        return id.intValue();
    }
}