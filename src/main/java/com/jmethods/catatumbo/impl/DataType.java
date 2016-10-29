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

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.GeoLocation;

/**
 * Various data types supported by the Cloud Datastore and/or framework.
 *
 * @author Sai Pullabhotla
 */
public enum DataType {
	/**
	 * Primitive boolean (Stored as Boolean in the Cloud Datastore).
	 */
	BOOLEAN(boolean.class),
	/**
	 * Primitive char (Stored as String int he Cloud Datastore)
	 */
	CHARACTER(char.class),
	/**
	 * Java Enum (Stored as String in the Cloud Datastore, by calling the
	 * toString() method.
	 */
	ENUM(Enum.class),
	/**
	 * Primitive short (Stored as Integer in the Cloud Datastore).
	 */
	SHORT(short.class),
	/**
	 * Primitive int (stored as Integer in the Cloud Datastore).
	 */
	INTEGER(int.class),
	/**
	 * Primitive long (Stored as Integer in the Cloud Datastore).
	 */
	LONG(long.class),
	/**
	 * Primitive float (Stored as Floating point number in the Cloud Datastore).
	 */
	FLOAT(float.class),
	/**
	 * Primitive double (stored as Floating point number in the Cloud
	 * Datastore).
	 */
	DOUBLE(double.class),
	/**
	 * Boolean wrapper (Stored as Boolean in the Cloud Datastore).
	 */
	BOOLEAN_OBJECT(Boolean.class),
	/**
	 * Character wrapper (Stored as String in the Cloud Datastore).
	 */
	CHARACTER_OBJECT(Character.class),
	/**
	 * Short wrapper (stored as Integer in the Cloud Datastore).
	 */
	SHORT_OBJECT(Short.class),
	/**
	 * Integer wrapper (stored as Integer in the Cloud Datastore).
	 */
	INTEGER_OBJECT(Integer.class),
	/**
	 * Long wrapper (stored as Integer in the Cloud Datastore).
	 */
	LONG_OBJECT(Long.class),
	/**
	 * Float wrapper (stored as Floating point number in the Cloud Datastore).
	 */
	FLOAT_OBJECT(Float.class),
	/**
	 * Double wrapper (stored as Floating point number in the Cloud Datastore).
	 */
	DOUBLE_OBJECT(Double.class),
	/**
	 * java.lang.String (stored as String in the Cloud Datastore).
	 */
	STRING(String.class),
	/**
	 * java.util.Date (stored as Date and Time in the Cloud Datastore).
	 */
	DATE(Date.class),
	/**
	 * java.util.Calendar (stored as Date and Time in the Cloud Datastore).
	 */
	CALENDAR(Calendar.class),
	/**
	 * Array of bytes (stored as Binary or Blob in the Cloud Datastore).
	 */
	BYTE_ARRAY(byte[].class),
	/**
	 * Array of characters (stored as String in the Cloud Datastore).
	 */
	CHAR_ARRAY(char[].class),

	/**
	 * List object
	 */
	LIST(List.class),

	/**
	 * Set object
	 */
	SET(Set.class),

	/**
	 * Map object
	 */
	MAP(Map.class),

	/**
	 * Key (for an entity's full key, parent key, key references to other
	 * entities).
	 */
	KEY(DatastoreKey.class),

	/**
	 * Geo Location
	 */
	GEO_LOCATION(GeoLocation.class),

	/**
	 * BigDecimal - Maps to a Floating Point number in the Cloud Datastore.
	 */
	BIG_DECIMAL(BigDecimal.class);

	/**
	 * The Java class type
	 */
	private final Class<?> dataClass;

	/**
	 * Creates a new instance of <code>DataType</code>.
	 *
	 * @param dataClass
	 *            the Java class type.
	 */
	private DataType(Class<?> dataClass) {
		this.dataClass = dataClass;
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
