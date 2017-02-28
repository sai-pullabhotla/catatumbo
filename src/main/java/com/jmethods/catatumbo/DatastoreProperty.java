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

package com.jmethods.catatumbo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Objects of this class represent the metadata of a Datastore property.
 * 
 * @author Sai Pullabhotla
 *
 */

@Entity(kind = "__property__")
public class DatastoreProperty implements Serializable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 6647639868379397875L;

	/**
	 * Property name
	 */
	@Identifier
	private String name;

	/**
	 * The full key of this property
	 */
	@Key
	private DatastoreKey key;

	/**
	 * Entity Key
	 */
	@ParentKey
	private DatastoreKey entityKey;

	/**
	 * The data types
	 */
	@Property(name = "property_representation")
	private List<String> dataTypes;

	/**
	 * Returns the name of this Property.
	 * 
	 * @return the name of this Property.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this property.
	 * 
	 * @param name
	 *            the property name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the full key of this property.
	 * 
	 * @return the full key of this property.
	 */
	public DatastoreKey getKey() {
		return key;
	}

	/**
	 * Sets the full key of this property.
	 * 
	 * @param key
	 *            the full key of this property.
	 */
	public void setKey(DatastoreKey key) {
		this.key = key;
	}

	/**
	 * Returns the key of the owning entity.
	 * 
	 * @return the key of the owning entity.
	 */
	public DatastoreKey getEntityKey() {
		return entityKey;
	}

	/**
	 * Sets the owning entity's key.
	 * 
	 * @param entityKey
	 *            the owning entity's key.
	 */
	public void setEntityKey(DatastoreKey entityKey) {
		this.entityKey = entityKey;
	}

	/**
	 * Returns the data types.
	 * 
	 * @return the data types
	 */
	public List<String> getDataTypes() {
		return dataTypes;
	}

	/**
	 * Sets the data types.
	 * 
	 * @param dataTypes
	 *            the data types
	 */
	public void setDataTypes(List<String> dataTypes) {
		this.dataTypes = dataTypes;
	}

	/**
	 * Returns the name of the owning entity.
	 * 
	 * @return the name of the owning entity.
	 */
	public String getEntityName() {
		return entityKey.name();
	}

	/**
	 * Returns a String representation of this DatastoreProperty. The returned
	 * value will be of the form - <code>EntityName.PropertyName</code>.
	 */
	@Override
	public String toString() {
		return getEntityName() + "." + name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		DatastoreProperty that = (DatastoreProperty) obj;
		return Objects.equals(this.key, that.key);
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

}
