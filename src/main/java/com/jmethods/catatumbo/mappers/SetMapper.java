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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.MapperFactory;
import com.jmethods.catatumbo.NoSuitableMapperException;
import com.jmethods.catatumbo.impl.IntrospectionUtils;

/**
 * An implementation of {@link Mapper} for mapping {@link Set} types to/from
 * Cloud Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public class SetMapper implements Mapper {

	/**
	 * Set type - could be a Class or a Parameterized type
	 */
	private Type setType;

	/**
	 * Set class
	 */
	private Class<?> setClass;

	/**
	 * Class of items in the Set
	 */
	private Class<?> itemClass;

	/**
	 * Mapper for mapping items in the Set
	 */
	Mapper itemMapper;

	/**
	 * Whether or not the list property should be indexed. While this does not
	 * affect the ListProperty itself, it is applied on the items in the list.
	 */
	private boolean indexed;

	/**
	 * Creates a new instance of <code>SetMapper</code>.
	 * 
	 * @param type
	 *            the type of Set
	 */
	public SetMapper(Type type, boolean indexed) {
		this.setType = type;
		this.indexed = indexed;
		Class<?>[] classArray = IntrospectionUtils.resolveCollectionType(setType);
		setClass = classArray[0];
		itemClass = classArray[1];
		initalizeMapper();
	}

	/**
	 * Initializes the mapper for the items in the Set.
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
		Set<?> set = (Set<?>) input;
		ListValue.Builder listValueBuilder = ListValue.newBuilder();
		for (Object item : set) {
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
		Set<Object> output;
		if (Modifier.isAbstract(setClass.getModifiers())) {
			if (SortedSet.class.isAssignableFrom(setClass)) {
				output = new TreeSet<>();
			} else {
				output = new HashSet<>();
			}
		} else {
			output = (Set<Object>) IntrospectionUtils.instantiateObject(setClass);
		}
		for (Value<?> item : list) {
			output.add(itemMapper.toModel(item));
		}
		return output;
	}

}
