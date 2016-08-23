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
import java.lang.reflect.Method;

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
	protected Field field;

	/**
	 * Data type
	 */
	protected DataType dataType;

	/**
	 * Read method (or getter method) for this field
	 */
	protected Method readMethod;

	/**
	 * Write method (or setter method) for this field
	 */
	protected Method writeMethod;

	/**
	 * Creates a new instance of <code>FieldMetadata</code>.
	 * 
	 * @param field
	 *            the field
	 *
	 * @param dataType
	 *            the data type
	 */
	public FieldMetadata(Field field, DataType dataType) {
		this.field = field;
		this.dataType = dataType;
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
	 * Returns the data type of the field.
	 *
	 * @return the data type of the field.
	 */
	public DataType getDataType() {
		return dataType;
	}

	/**
	 * Sets the feild's data type.
	 *
	 * @param dataType
	 *            the data type.
	 */
	public void setDataType(DataType dataType) {
		this.dataType = dataType;
	}

	/**
	 * Returns the read method (or getter method) for this field.
	 *
	 * @return the read method (or getter method) for this field.
	 */
	public Method getReadMethod() {
		return readMethod;
	}

	/**
	 * Sets the read method (or getter method).
	 *
	 * @param readMethod
	 *            the read method (or getter method).
	 */
	public void setReadMethod(Method readMethod) {
		this.readMethod = readMethod;
	}

	/**
	 * Returns the write method (or setter method).
	 *
	 * @return the write method (or setter method).
	 */
	public Method getWriteMethod() {
		return writeMethod;
	}

	/**
	 * Sets the write method (or setter method).
	 *
	 * @param writeMethod
	 *            the write method (or setter method).
	 */
	public void setWriteMethod(Method writeMethod) {
		this.writeMethod = writeMethod;
	}

	/**
	 * Returns the field's class (or type).
	 *
	 * @return the field's class (or type).
	 */
	public Class<?> getDataClass() {
		return dataType.getDataClass();
	}

}
