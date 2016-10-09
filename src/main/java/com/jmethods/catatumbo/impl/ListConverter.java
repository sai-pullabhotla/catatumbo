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

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.cloud.datastore.BooleanValue;
import com.google.cloud.datastore.DoubleValue;
import com.google.cloud.datastore.KeyValue;
import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.LongValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.DefaultDatastoreKey;

/**
 * An implementation of {@link PropertyConverter} for handling List types.
 * 
 * @author Sai Pullabhotla
 *
 */
public class ListConverter extends AbstractConverter {

	/**
	 * Singleton instance
	 */
	private static final ListConverter INSTANCE = new ListConverter();

	/**
	 * Creates a new instance of <code>ListConverter</code>.
	 */
	private ListConverter() {
		// Hide the constructor
	}

	@Override
	public ValueBuilder<?, ?, ?> toValueBuilder(Object obj, PropertyMetadata metadata) {
		List<?> list = (List<?>) obj;
		Iterator<?> iterator = list.iterator();
		ListValue.Builder listValurBuilder = ListValue.builder();
		while (iterator.hasNext()) {
			Object item = iterator.next();
			Value<?> convertedItem = null;
			if (item instanceof String) {
				convertedItem = StringValue.builder((String) item).build();
			} else if (item instanceof Long) {
				convertedItem = LongValue.builder((long) item).build();
			} else if (item instanceof DatastoreKey) {
				DatastoreKey datastoreKey = (DatastoreKey) item;
				convertedItem = KeyValue.builder(datastoreKey.nativeKey()).build();
			} else if (item instanceof Boolean) {
				convertedItem = BooleanValue.builder((boolean) item).build();
			} else if (item instanceof Double) {
				convertedItem = DoubleValue.builder((double) item).build();
			} else {
				throw new RuntimeException("Unsupported type in List");
			}
			listValurBuilder.addValue(convertedItem);
		}
		return listValurBuilder;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Object toObject(Value<?> value, PropertyMetadata metadata) {
		ListValue listValue = (ListValue) value;
		List<? extends Value<?>> list = listValue.get();
		Iterator<? extends Value<?>> iterator = list.iterator();

		List<Object> output = null;
		Class<?> listType = metadata.getDeclaredType();
		if (Modifier.isAbstract(listType.getModifiers())) {
			output = new ArrayList<>();
		} else {
			output = (List<Object>) IntrospectionUtils.instantiateObject(listType);
		}

		// List<Object> output = new ArrayList<>(list.size());
		while (iterator.hasNext()) {
			Value<?> item = iterator.next();
			Object convertedItem = null;
			if (item instanceof StringValue) {
				convertedItem = item.get();
			} else if (item instanceof LongValue) {
				convertedItem = item.get();
			} else if (item instanceof KeyValue) {
				KeyValue keyValue = (KeyValue) item;
				convertedItem = new DefaultDatastoreKey(keyValue.get());
			} else if (item instanceof BooleanValue) {
				convertedItem = item.get();
			} else if (item instanceof DoubleValue) {
				convertedItem = item.get();
			} else {
				throw new RuntimeException("Unsupported type in list");
			}
			output.add(convertedItem);
		}
		return output;
	}

	/**
	 * Returns the singleton instance of <code>ListConverter</code>.
	 * 
	 * @return the singleton instance of <code>ListConverter</code>.
	 */
	public static ListConverter getInstance() {
		return INSTANCE;
	}

}
