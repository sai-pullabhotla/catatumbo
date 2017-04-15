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

import java.lang.invoke.MethodHandle;

/**
 * Objects of this class hold the metadata of an embedded field.
 * 
 * @author Sai Pullabhotla
 *
 */
public class EmbeddedMetadata extends MetadataBase {

	/**
	 * The embedded field to which this metadata belongs.
	 */
	private final EmbeddedField field;

	/**
	 * Read method for this embedded field
	 */
	private final MethodHandle readMethod;

	/**
	 * Write method for this embedded field
	 */
	private final MethodHandle writeMethod;

	/**
	 * Storage strategy to use for the embedded field
	 */
	private StorageStrategy storageStrategy;

	/**
	 * The property name under which to store this embedded field
	 */
	private String mappedName;

	/**
	 * Whether or not to index this embedded field
	 */
	private boolean indexed;

	/**
	 * Creates a new instance of <code>EmbeddedMetadata</code>.
	 * 
	 * @param field
	 *            the embedded field to which this metadata belongs
	 */
	public EmbeddedMetadata(EmbeddedField field) {
		super(field.getType());
		this.field = field;
		// Default storage strategy is EXPLODED
		this.storageStrategy = StorageStrategy.EXPLODED;
		this.readMethod = findReadMethod();
		this.writeMethod = findWriteMethod();
	}

	/**
	 * Finds and returns the read method for the embedded field.
	 * 
	 * @return the read method for the embedded field.
	 */
	private MethodHandle findReadMethod() {
		return IntrospectionUtils.findReadMethodHandle(field.getDeclaringClass(),
				IntrospectionUtils.getReadMethodName(field.getField()), field.getType());
	}

	/**
	 * Finds and returns the write method for the embedded field.
	 * 
	 * @return the write method for the embedded field.
	 */
	private MethodHandle findWriteMethod() {
		return IntrospectionUtils.findWriteMethodHandle(field.getDeclaringClass(),
				IntrospectionUtils.getWriteMethodName(field.getField()), field.getType());
	}

	/**
	 * Returns the embedded field to which this metadata belongs.
	 * 
	 * @return the embedded field to which this metadata belongs.
	 */
	public EmbeddedField getField() {
		return field;
	}

	/**
	 * Returns the read method of the embedded field to which this metadata
	 * belongs.
	 * 
	 * @return the read method of the embedded field to which this metadata
	 *         belongs.
	 */
	public MethodHandle getReadMethod() {
		return readMethod;
	}

	/**
	 * Returns the storage strategy.
	 * 
	 * @return the storage strategy.
	 */
	public StorageStrategy getStorageStrategy() {
		return storageStrategy;
	}

	/**
	 * Sets the storage strategy to the given value.
	 * 
	 * @param storageStrategy
	 *            the storage strategy
	 */
	public void setStorageStrategy(StorageStrategy storageStrategy) {
		this.storageStrategy = storageStrategy;
	}

	/**
	 * Returns the write method of the embedded field to which this metadata
	 * belongs.
	 * 
	 * @return the write method of the embedded field to which this metadata
	 *         belongs.
	 */
	public MethodHandle getWriteMethod() {
		return writeMethod;
	}

	/**
	 * Returns the property name to which this embedded field is mapped. Only
	 * used when the storage strategy is {@link StorageStrategy#IMPLODED}.
	 * 
	 * @return the property name to which this embedded field is mapped.
	 */
	public String getMappedName() {
		return mappedName;
	}

	/**
	 * Sets the property name to which this embedded field is mapped.
	 * 
	 * @param mappedName
	 *            the property name to which this embedded field is mapped.
	 */
	public void setMappedName(String mappedName) {
		this.mappedName = mappedName;
	}

	/**
	 * Returns whether or not this embedded object should be indexed. Only used
	 * when the storage strategy is {@link StorageStrategy#IMPLODED}.
	 * 
	 * @return the indexed <code>true</code>, if this embedded object should be
	 *         indexed; <code>false</code>, otherwise.
	 */
	public boolean isIndexed() {
		return indexed;
	}

	/**
	 * Sets whether or not to index this embedded field.
	 * 
	 * @param indexed
	 *            whether or not to index this embedded field.
	 */
	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}
}
