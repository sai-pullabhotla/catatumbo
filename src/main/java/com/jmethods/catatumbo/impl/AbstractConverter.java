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

import com.google.cloud.datastore.BooleanValue;
import com.google.cloud.datastore.DoubleValue;
import com.google.cloud.datastore.KeyValue;
import com.google.cloud.datastore.LongValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.DefaultDatastoreKey;

/**
 * Base class for various converters.
 * 
 * @author Sai Pullabhotla
 *
 */
public abstract class AbstractConverter implements PropertyConverter {

	@Override
	public Value<?> toValue(Object input, PropertyMetadata metadata) {
		return toValueBuilder(input, metadata).build();
	}

	/**
	 * Utility method used by List/Set/Map converters. Converts the given Java
	 * object to equivalent Native value.
	 * 
	 * @param javaValue
	 *            the Java object
	 * @return the equivalent native value
	 */
	public static Value<?> javaToNative(Object javaValue) {
		Value<?> nativeValue = null;
		if (javaValue instanceof String) {
			nativeValue = StringValue.of((String) javaValue);
		} else if (javaValue instanceof Long) {
			nativeValue = LongValue.of((long) javaValue);
		} else if (javaValue instanceof Double) {
			nativeValue = DoubleValue.of((double) javaValue);
		} else if (javaValue instanceof Boolean) {
			nativeValue = BooleanValue.of((boolean) javaValue);
		} else if (javaValue instanceof DatastoreKey) {
			nativeValue = KeyValue.of(((DatastoreKey) javaValue).nativeKey());
		} else {
			throw new RuntimeException(String.format("Unsupported type %s", javaValue.getClass()));
		}
		return nativeValue;

	}

	/**
	 * Utility method used by List/Set/Map converters. Converts the given native
	 * value to equivalent Java object.
	 * 
	 * @param nativeValue
	 *            the native value
	 * @return the equivalent Java object
	 */
	public static Object nativeToJava(Value<?> nativeValue) {
		Object javaValue = null;
		if (nativeValue instanceof StringValue) {
			javaValue = nativeValue.get();
		} else if (nativeValue instanceof LongValue) {
			javaValue = nativeValue.get();
		} else if (nativeValue instanceof DoubleValue) {
			javaValue = nativeValue.get();
		} else if (nativeValue instanceof BooleanValue) {
			javaValue = nativeValue.get();
		} else if (nativeValue instanceof KeyValue) {
			javaValue = new DefaultDatastoreKey(((KeyValue) nativeValue).get());
		} else {
			throw new RuntimeException(String.format("Unsupported type %s", nativeValue.getClass()));
		}
		return javaValue;
	}

}
