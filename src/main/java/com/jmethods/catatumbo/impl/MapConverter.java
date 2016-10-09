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
import java.util.HashMap;
import java.util.Map;

import com.google.cloud.datastore.EntityValue;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;

/**
 * An implementation of {@link PropertyConverter} for handling {@link Map}
 * types. Map objects are mapped to an EmbeddedEntity field in the datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public class MapConverter extends AbstractConverter {

	/**
	 * Singleton instance
	 */
	private static final MapConverter INSTANCE = new MapConverter();

	/**
	 * Creates a new instance of <code>ListConverter</code>.
	 */
	private MapConverter() {
		// Hide the constructor
	}

	@Override
	public ValueBuilder<?, ?, ?> toValueBuilder(Object input, PropertyMetadata metadata) {
		@SuppressWarnings("unchecked")
		Map<String, ?> map = (Map<String, ?>) input;
		FullEntity.Builder<IncompleteKey> entityBuilder = FullEntity.builder();
		// For now we expect the keys in the map to be Strings.
		for (String mapKey : map.keySet()) {
			Object javaValue = map.get(mapKey);
			entityBuilder.set(mapKey, javaToNative(javaValue));
		}
		return EntityValue.builder(entityBuilder.build());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object toObject(Value<?> input, PropertyMetadata metadata) {
		EntityValue entityValue = (EntityValue) input;
		FullEntity<?> entity = entityValue.get();
		Class<?> mapType = metadata.getDeclaredType();
		Map<String, Object> map = null;
		if (Modifier.isAbstract(mapType.getModifiers())) {
			map = new HashMap<>();
		} else {
			map = (Map<String, Object>) IntrospectionUtils.instantiateObject(mapType);
		}
		for (String property : entity.names()) {
			map.put(property, nativeToJava(entity.getValue(property)));
		}
		return map;
	}

	/**
	 * Returns the singleton instance of <code>ListConverter</code>.
	 * 
	 * @return the singleton instance of <code>ListConverter</code>.
	 */
	public static MapConverter getInstance() {
		return INSTANCE;
	}

}
