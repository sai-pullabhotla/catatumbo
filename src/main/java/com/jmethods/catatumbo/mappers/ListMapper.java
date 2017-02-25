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

package com.jmethods.catatumbo.mappers;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.MapperFactory;
import com.jmethods.catatumbo.NoSuitableMapperException;
import com.jmethods.catatumbo.impl.IntrospectionUtils;

/**
 * An implementation of {@link Mapper} for mapping {@link List} types to/from
 * the Cloud Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public class ListMapper implements Mapper {

	/**
	 * List type - could be a Class or a Parameterized type
	 */
	private Type listType;

	/**
	 * List class
	 */
	private Class<?> listClass;

	/**
	 * Class of items in the list
	 */
	private Class<?> itemClass;

	/**
	 * Mapper for mapping items in the list
	 */
	private Mapper itemMapper;

	/**
	 * Whether or not the list property should be indexed. While this does not
	 * affect the ListProperty itself, it is applied on the items in the list.
	 */
	private boolean indexed;

	/**
	 * Creates a new instance of <code>ListMapper</code>.
	 * 
	 * @param type
	 *            the list type
	 * @param indexed
	 *            whether or not the property should be indexed
	 */
	public ListMapper(Type type, boolean indexed) {
		this.listType = type;
		this.indexed = indexed;
		Class<?>[] classArray = IntrospectionUtils.resolveCollectionType(listType);
		listClass = classArray[0];
		itemClass = classArray[1];
		initalizeMapper();
	}

	/**
	 * Initializes the mapper for items in the List.
	 */
	private void initalizeMapper() {
		if (itemClass == null) {
			itemMapper = CatchAllMapper.getInstance();
		} else {
			try {
				itemMapper = MapperFactory.getInstance().getMapper(itemClass);
			} catch (NoSuitableMapperException exp) {
				itemMapper = CatchAllMapper.getInstance();
			}
		}
	}

	@Override
	public ValueBuilder<?, ?, ?> toDatastore(Object input) {
		if (input == null) {
			return NullValue.newBuilder();
		}
		List<?> list = (List<?>) input;
		ListValue.Builder listValueBuilder = ListValue.newBuilder();
		for (Object item : list) {
			listValueBuilder.addValue(itemMapper.toDatastore(item).setExcludeFromIndexes(!indexed).build());
		}
		return listValueBuilder;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object toModel(Value<?> input) {
		if (input instanceof NullValue) {
			return null;
		}
		List<? extends Value<?>> list = ((ListValue) input).get();
		List<Object> output;
		if (Modifier.isAbstract(listClass.getModifiers())) {
			output = new ArrayList<>();
		} else {
			output = (List<Object>) IntrospectionUtils.instantiateObject(listClass);
		}
		for (Value<?> item : list) {
			output.add(itemMapper.toModel(item));
		}
		return output;
	}

}
