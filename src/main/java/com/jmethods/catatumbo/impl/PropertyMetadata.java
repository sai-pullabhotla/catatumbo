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
package com.jmethods.catatumbo.impl;

import java.lang.reflect.Field;

/**
 * Objects of this class contain metadata about a property of an entity.
 *
 * @author Sai Pullabhotla
 */
public class PropertyMetadata extends FieldMetadata {

	/**
	 * The property name, in the Cloud Datastore, to which a field is mapped
	 */
	private String mappedName;

	/**
	 * If the property is indexed or not
	 */
	private boolean indexed;

	/**
	 * Creates an instance of <code>PropertyMetadata</code>.
	 *
	 * @param field
	 *            the field
	 * @param mappedName
	 *            the property name in the Cloud Datastore
	 * @param indexed
	 *            whether or not to index
	 */
	public PropertyMetadata(Field field, String mappedName, boolean indexed) {
		super(field);
		this.mappedName = mappedName;
		this.indexed = indexed;
	}

	/**
	 * Returns the mapped name.
	 *
	 * @return the mapped name.
	 */
	public String getMappedName() {
		return mappedName;
	}

	/**
	 * Sets the mapped name.
	 *
	 * @param mappedName
	 *            the mapped name.
	 */
	public void setMappedName(String mappedName) {
		this.mappedName = mappedName;
	}

	/**
	 * Returns whether or not the property is indexed.
	 *
	 * @return true, if indexed; false, otherwise.
	 */
	public boolean isIndexed() {
		return indexed;
	}

	/**
	 * Sets whether or not the property is indexed.
	 *
	 * @param indexed
	 *            whether or not the property is indexed.
	 */
	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}
}
