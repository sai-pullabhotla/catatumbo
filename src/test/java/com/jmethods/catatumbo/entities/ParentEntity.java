/*
 * Copyright 2016 Sai Pullabhotla.
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

package com.jmethods.catatumbo.entities;

import java.io.Serializable;
import java.util.Objects;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Key;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class ParentEntity implements Serializable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 8794953055891942172L;

	public static ParentEntity parent1 = new ParentEntity(9000, "Parent 9000");

	@Identifier
	private long id;
	private String field1;
	@Key
	private DatastoreKey key;

	public ParentEntity() {

	}

	public ParentEntity(long id, String field1) {
		this.id = id;
		this.field1 = field1;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the field1
	 */
	public String getField1() {
		return field1;
	}

	/**
	 * @param field1
	 *            the field1 to set
	 */
	public void setField1(String field1) {
		this.field1 = field1;
	}

	/**
	 * @return the key
	 */
	public DatastoreKey getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(DatastoreKey key) {
		this.key = key;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		ParentEntity that = (ParentEntity) obj;
		return this.id == that.id && Objects.equals(this.field1, that.field1) && Objects.equals(this.key, that.key);
	}

}
