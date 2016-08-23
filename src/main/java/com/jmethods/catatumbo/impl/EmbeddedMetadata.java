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

import java.lang.reflect.Method;

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
	private EmbeddedField field;

	/**
	 * Read method for this embedded field
	 */
	private Method readMethod;

	/**
	 * Write method for this embedded field
	 */
	private Method writeMethod;

	/**
	 * Creates a new instance of <code>EmbeddedMetadata</code>.
	 * 
	 * @param field
	 *            the embedded field to which this metadata belongs
	 */
	public EmbeddedMetadata(EmbeddedField field) {
		super(field.getType());
		this.field = field;
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
	public Method getReadMethod() {
		return readMethod;
	}

	/**
	 * Sets the read method of the embedded field to which this metadata
	 * belongs.
	 * 
	 * @param readMethod
	 *            the read method of the embedded field to which this metadata
	 *            belongs.
	 */
	public void setReadMethod(Method readMethod) {
		this.readMethod = readMethod;
	}

	/**
	 * Returns the write method of the embedded field to which this metadata
	 * belongs.
	 * 
	 * @return the write method of the embedded field to which this metadata
	 *         belongs.
	 */
	public Method getWriteMethod() {
		return writeMethod;
	}

	/**
	 * Sets the write method of the embedded field to which this metadata
	 * belongs.
	 * 
	 * @param writeMethod
	 *            the write method of the embedded field to which this metadata
	 *            belongs.
	 */
	public void setWriteMethod(Method writeMethod) {
		this.writeMethod = writeMethod;
	}
}
