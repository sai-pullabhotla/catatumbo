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

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.ParentKey;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class GrandchildEntity {

	public static final GrandchildEntity grandchild1 = new GrandchildEntity(9200, "Grandchild 9200");

	@Identifier
	private long id;
	private String field1;
	@Key
	private DatastoreKey key;
	@ParentKey
	private DatastoreKey parentKey;

	/**
	 * 
	 */
	public GrandchildEntity() {
	}

	public GrandchildEntity(long id, String field1) {
		super();
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

	/**
	 * @return the parentKey
	 */
	public DatastoreKey getParentKey() {
		return parentKey;
	}

	/**
	 * @param parentKey
	 *            the parentKey to set
	 */
	public void setParentKey(DatastoreKey parentKey) {
		this.parentKey = parentKey;
	}

}
