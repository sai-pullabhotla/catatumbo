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
import java.lang.reflect.Field;

/**
 * Base class for holding the metadata about an entity's or embedded object's
 * field (e.g. identifier, key or property).
 *
 * @author Sai Pullabhotla
 */
public abstract class FieldMetadata {

	/**
	 * Reference to the field
	 */
	protected final Field field;

	/**
	 * Read method (or getter method) for this field
	 */
	protected final MethodHandle readMethod;

	/**
	 * Write method (or setter method) for this field
	 */
	protected final MethodHandle writeMethod;

	/**
	 * Creates a new instance of <code>FieldMetadata</code>.
	 * 
	 * @param field
	 *            the field
	 *
	 */
	public FieldMetadata(Field field) {
		this.field = field;
		this.readMethod = IntrospectionUtils.findReadMethodHandle(this);
		this.writeMethod = IntrospectionUtils.findWriteMethodHandle(this);
	}

	/**
	 * Returns the field.
	 * 
	 * @return the field.
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Returns the field name.
	 *
	 * @return the field name.
	 */
	public String getName() {
		return field.getName();
	}

	/**
	 * Returns the read method (or getter method) for this field.
	 *
	 * @return the read method (or getter method) for this field.
	 */
	public MethodHandle getReadMethod() {
		return readMethod;
	}

	/**
	 * Returns the write method (or setter method).
	 *
	 * @return the write method (or setter method).
	 */
	public MethodHandle getWriteMethod() {
		return writeMethod;
	}

	/**
	 * Returns the declared type of the field to which this metadata belongs.
	 * 
	 * @return the declared type of the field to which this metadata belongs.
	 */
	@SuppressWarnings("rawtypes")
	public Class getDeclaredType() {
		return field.getType();
	}

}
