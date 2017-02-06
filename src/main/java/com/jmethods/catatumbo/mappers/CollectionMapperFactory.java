/*
 * Copyright 2017 Sai Pullabhotla.
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

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.impl.Cache;

/**
 * A factory for producing {@link Mapper} for Collections - {@link List} and
 * {@link Set}.
 * 
 * @author Sai Pullabhotla
 *
 */
public class CollectionMapperFactory {

	/**
	 * Singleton instance
	 */
	private static final CollectionMapperFactory INSTANCE = new CollectionMapperFactory();

	/**
	 * Cache of previously produced Mappers, so they can be reused for similar
	 * field declarations of an entity
	 */
	private Cache<String, Mapper> cache;

	/**
	 * A lock for preventing multiple threads creating Mappers simultaneously
	 */
	private Lock lock;

	/**
	 * Creates a new instance of <code>CollectionMapperFactory</code>.
	 */
	private CollectionMapperFactory() {
		cache = new Cache<>();
		lock = new ReentrantLock();
	}

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static CollectionMapperFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * Returns the Mapper for the given field. If a Mapper exists in the cache
	 * that can map the given field, the cached Mapper will be returned.
	 * Otherwise, a new Mapper is created and returned.
	 * 
	 * @param field
	 *            the field of an entity for which a Mapper is to be produced.
	 * @return A Mapper to handle the mapping of the field.
	 */
	public Mapper getMapper(Field field) {
		Type genericType = field.getGenericType();
		Property propertyAnnotation = field.getAnnotation(Property.class);
		boolean indexed = true;
		if (propertyAnnotation != null) {
			indexed = propertyAnnotation.indexed();
		}
		String cacheKey = computeCacheKey(genericType, indexed);
		Mapper mapper = cache.get(cacheKey);
		if (mapper == null) {
			mapper = createMapper(field, indexed);
		}
		return mapper;

	}

	/**
	 * Creates a new Mapper for the given field.
	 * 
	 * @param field
	 *            the field
	 * @param indexed
	 *            whether or not the field is to be indexed
	 * @return the Mapper
	 */
	private Mapper createMapper(Field field, boolean indexed) {
		lock.lock();
		try {
			Mapper mapper;
			Class<?> fieldType = field.getType();
			Type genericType = field.getGenericType();
			String cacheKey = computeCacheKey(genericType, indexed);
			mapper = cache.get(cacheKey);
			if (mapper != null) {
				return mapper;
			}
			if (List.class.isAssignableFrom(fieldType)) {
				mapper = new ListMapper(genericType, indexed);
			} else if (Set.class.isAssignableFrom(fieldType)) {
				mapper = new SetMapper(genericType, indexed);
			} else {
				// we shouldn't be getting here
				throw new IllegalArgumentException(
						String.format("Field type must be List or Set, found %s", fieldType));
			}
			cache.put(cacheKey, mapper);
			return mapper;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Returns the cache key.
	 * 
	 * @param type
	 *            the type
	 * @param indexed
	 *            whether or not the property is indexed
	 * @return the cache key
	 */
	private String computeCacheKey(Type type, boolean indexed) {
		return type.toString() + "-" + indexed;
	}

}
