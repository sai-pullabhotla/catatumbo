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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jmethods.catatumbo.DatastoreKey;

/**
 * Various data types supported by the Cloud Datastore and/or framework.
 *
 * @author Sai Pullabhotla
 */
public enum DataType {
	/**
	 * Primitive boolean (Stored as Boolean in the Cloud Datastore).
	 */
	BOOLEAN(boolean.class, BooleanConverter.getInstance()),
	/**
	 * Primitive char (Stored as String int he Cloud Datastore)
	 */
	CHARACTER(char.class, CharConverter.getInstance()),
	/**
	 * Primitive short (Stored as Integer in the Cloud Datastore).
	 */
	SHORT(short.class, ShortConverter.getInstance()),
	/**
	 * Primitive int (stored as Integer in the Cloud Datastore).
	 */
	INTEGER(int.class, IntegerConverter.getInstance()),
	/**
	 * Primitive long (Stored as Integer in the Cloud Datastore).
	 */
	LONG(long.class, LongConverter.getInstance()),
	/**
	 * Primitive float (Stored as Floating point number in the Cloud Datastore).
	 */
	FLOAT(float.class, FloatConverter.getInstance()),
	/**
	 * Primitive double (stored as Floating point number in the Cloud
	 * Datastore).
	 */
	DOUBLE(double.class, DoubleConverter.getInstance()),
	/**
	 * Boolean wrapper (Stored as Boolean in the Cloud Datastore).
	 */
	BOOLEAN_OBJECT(Boolean.class, BooleanConverter.getInstance()),
	/**
	 * Character wrapper (Stored as String in the Cloud Datastore).
	 */
	CHARACTER_OBJECT(Character.class, CharConverter.getInstance()),
	/**
	 * Short wrapper (stored as Integer in the Cloud Datastore).
	 */
	SHORT_OBJECT(Short.class, ShortConverter.getInstance()),
	/**
	 * Integer wrapper (stored as Integer in the Cloud Datastore).
	 */
	INTEGER_OBJECT(Integer.class, IntegerConverter.getInstance()),
	/**
	 * Long wrapper (stored as Integer in the Cloud Datastore).
	 */
	LONG_OBJECT(Long.class, LongConverter.getInstance()),
	/**
	 * Float wrapper (stored as Floating point number in the Cloud Datastore).
	 */
	FLOAT_OBJECT(Float.class, FloatConverter.getInstance()),
	/**
	 * Double wrapper (stored as Floating point number in the Cloud Datastore).
	 */
	DOUBLE_OBJECT(Double.class, DoubleConverter.getInstance()),
	/**
	 * java.lang.String (stored as String in the Cloud Datastore).
	 */
	STRING(String.class, StringConverter.getInstance()),
	/**
	 * java.util.Date (stored as Date and Time in the Cloud Datastore).
	 */
	DATE(Date.class, DateConverter.getInstance()),
	/**
	 * java.util.Calendar (stored as Date and Time in the Cloud Datastore).
	 */
	CALENDAR(Calendar.class, CalendarConverter.getInstance()),
	/**
	 * Array of bytes (stored as Binary or Blob in the Cloud Datastore).
	 */
	BYTE_ARRAY(byte[].class, ByteArrayConverter.getInstance()),
	/**
	 * Array of characters (stored as String in the Cloud Datastore).
	 */
	CHAR_ARRAY(char[].class, CharArrayConverter.getInstance()),

	/**
	 * List object
	 */
	LIST(List.class, ListConverter.getInstance()),

	/**
	 * Key (for an entity's full key, parent key, key references to other
	 * entities).
	 */
	KEY(DatastoreKey.class, KeyConverter.getInstance());

	/**
	 * The Java class type
	 */
	private final Class<?> dataClass;

	/**
	 * A converter to convert POJOs to Datastore properties and vice versa.
	 */
	private final PropertyConverter converter;

	/**
	 * Creates a new instance of <code>DataType</code>.
	 *
	 * @param dataClass
	 *            the Java class type.
	 * @param converter
	 *            the converter associated with this <code>DataType<code>.
	 */
	private DataType(Class<?> dataClass, PropertyConverter converter) {
		this.dataClass = dataClass;
		this.converter = converter;
	}

	/**
	 * Returns the Java class type.
	 *
	 * @return the Java class type.
	 */
	public Class<?> getDataClass() {
		return dataClass;
	}

	/**
	 * Returns the converter associated with this <code>DataType</code>.
	 * 
	 * @return the converter associated with this <code>DataType</code>.
	 */
	public PropertyConverter getConverter() {
		return converter;
	}

	/**
	 * Returns the DataType for the given Java class type.
	 *
	 * @param dataClass
	 *            the Java class type
	 * @return the corresponding DataType object
	 */
	public static DataType forClass(Class<?> dataClass) {
		for (DataType propertyType : values()) {
			if (propertyType.getDataClass().isAssignableFrom(dataClass)) {
				return propertyType;
			}
		}
		return null;
	}

}
