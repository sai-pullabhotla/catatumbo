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
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.cloud.datastore.EntityValue;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.MapperFactory;
import com.jmethods.catatumbo.MappingException;
import com.jmethods.catatumbo.NoSuitableMapperException;
import com.jmethods.catatumbo.impl.IntrospectionUtils;

/**
 * An implementation of {@link Mapper} interface for mapping {@link Map} types
 * to/from Cloud Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public class MapMapper implements Mapper {

	/**
	 * Map Type
	 */
	private Type mapType;

	/**
	 * Class of Map
	 */
	private Class<?> mapClass;

	/**
	 * Type of Keys in the map
	 */
	private Class<?> keyClass;

	/**
	 * Type of Values in the Map
	 */
	private Class<?> valueClass;

	/**
	 * A Mapper for mapping the Values of the map
	 */
	private Mapper valueMapper;

	/**
	 * Creates a new instance of <code>MapMapper</code>.
	 * 
	 * @param type
	 *            the type of Map
	 */
	public MapMapper(Type type) {
		this.mapType = type;
		Class<?>[] classArray = IntrospectionUtils.resolveMapType(mapType);
		mapClass = classArray[0];
		keyClass = classArray[1] == null ? String.class : classArray[1];
		if (!(keyClass.equals(String.class))) {
			throw new MappingException(String.format("Unsupported type %s for Map's key. Keys must be of type %s",
					keyClass.getName(), String.class.getName()));
		}
		valueClass = classArray[2];
		initializeMapper();

	}

	/**
	 * Initializes the mapper for values in the Map.
	 */
	private void initializeMapper() {
		if (valueClass == null) {
			valueMapper = CatchAllMapper.getInstance();
		} else {
			try {
				valueMapper = MapperFactory.getInstance().getMapper(valueClass);
			} catch (NoSuitableMapperException exp) {
				valueMapper = CatchAllMapper.getInstance();
			}

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ValueBuilder<?, ?, ?> toDatastore(Object input) {
		if (input == null) {
			return NullValue.newBuilder();
		}
		Map<String, ?> map = (Map<String, ?>) input;
		FullEntity.Builder<IncompleteKey> entityBuilder = FullEntity.newBuilder();
		for (Map.Entry<String, ?> entry : map.entrySet()) {
			String key = entry.getKey();
			entityBuilder.set(key, valueMapper.toDatastore(entry.getValue()).build());
		}
		return EntityValue.newBuilder(entityBuilder.build());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object toModel(Value<?> input) {
		if (input instanceof NullValue) {
			return null;
		}
		EntityValue entityValue = (EntityValue) input;
		FullEntity<?> entity = entityValue.get();
		Map<String, Object> map;
		if (Modifier.isAbstract(mapClass.getModifiers())) {
			if (SortedMap.class.equals(mapClass)) {
				map = new TreeMap<>();
			} else {
				map = new HashMap<>();
			}
		} else {
			map = (Map<String, Object>) IntrospectionUtils.instantiateObject(mapClass);
		}
		for (String property : entity.getNames()) {
			map.put(property, valueMapper.toModel(entity.getValue(property)));
		}
		return map;
	}

}
