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

package com.jmethods.catatumbo;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.jmethods.catatumbo.impl.Cache;
import com.jmethods.catatumbo.mappers.BigDecimalMapper;
import com.jmethods.catatumbo.mappers.BooleanMapper;
import com.jmethods.catatumbo.mappers.ByteArrayMapper;
import com.jmethods.catatumbo.mappers.CalendarMapper;
import com.jmethods.catatumbo.mappers.CharArrayMapper;
import com.jmethods.catatumbo.mappers.CharMapper;
import com.jmethods.catatumbo.mappers.DateMapper;
import com.jmethods.catatumbo.mappers.DoubleMapper;
import com.jmethods.catatumbo.mappers.EnumMapper;
import com.jmethods.catatumbo.mappers.FloatMapper;
import com.jmethods.catatumbo.mappers.GeoLocationMapper;
import com.jmethods.catatumbo.mappers.IntegerMapper;
import com.jmethods.catatumbo.mappers.KeyMapper;
import com.jmethods.catatumbo.mappers.ListMapper;
import com.jmethods.catatumbo.mappers.LongMapper;
import com.jmethods.catatumbo.mappers.MapMapper;
import com.jmethods.catatumbo.mappers.SetMapper;
import com.jmethods.catatumbo.mappers.ShortMapper;
import com.jmethods.catatumbo.mappers.StringMapper;

/**
 * A factory for producing data mappers that are used for mapping fields of
 * model class to/from the Cloud Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public class MapperFactory {

	/**
	 * Singleton instance
	 */
	private static final MapperFactory INSTANCE = new MapperFactory();

	/**
	 * Cache of mappers by Type
	 */
	private Cache<Type, Mapper> cache = null;

	/**
	 * A lock for preventing multiple threads creating Mappers simultaneously
	 */
	private Lock lock;

	/**
	 * Creates a new instance of <code>MapperFactory</code>.
	 */
	private MapperFactory() {
		cache = new Cache<>();
		lock = new ReentrantLock();
		createDefaultMappers();
	}

	/**
	 * Returns the singleton instance of this <code>MapperFactory</code>.
	 * 
	 * @return the singleton instance of this <code>MapperFactory</code>.
	 */
	public static MapperFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * Returns a mapper for the given type. If a mapper that can handle given
	 * type exists in the cache, it will be returned. Otherwise, a new mapper
	 * will be created.
	 * 
	 * @param type
	 *            the type of field in the model class
	 * @return a {@link Mapper} that is capable of mapping the given type.
	 */
	public Mapper getMapper(Type type) {
		Mapper mapper = cache.get(type);
		if (mapper == null) {
			mapper = createMapper(type);
		}
		return mapper;
	}

	/**
	 * Creates a new mapper for the given type.
	 * 
	 * @param type
	 *            the type for which a mapper is to be created
	 * @return a mapper that can handle the mapping of given type to/from the
	 *         Cloud Datastore.
	 */
	private Mapper createMapper(Type type) {
		lock.lock();
		try {
			Mapper mapper = cache.get(type);
			if (mapper != null) {
				return mapper;
			}
			if (type instanceof Class) {
				mapper = createMapper((Class<?>) type);
			} else if (type instanceof ParameterizedType) {
				mapper = createMapper((ParameterizedType) type);
			} else {
				throw new IllegalArgumentException(
						String.format("Type %s is neither a Class nor ParameterizedType", type));
			}
			cache.put(type, mapper);
			return mapper;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * Creates a mapper for the given class.
	 * 
	 * @param clazz
	 *            the class
	 * @return the mapper for the given class.
	 */
	private Mapper createMapper(Class<?> clazz) {
		Mapper mapper;
		if (Date.class.isAssignableFrom(clazz)) {
			mapper = new DateMapper();
		} else if (Calendar.class.isAssignableFrom(clazz)) {
			mapper = new CalendarMapper();
		} else if (Enum.class.isAssignableFrom(clazz)) {
			mapper = new EnumMapper(clazz);
		} else if (List.class.isAssignableFrom(clazz)) {
			mapper = new ListMapper(clazz);
		} else if (Set.class.isAssignableFrom(clazz)) {
			mapper = new SetMapper(clazz);
		} else if (Map.class.isAssignableFrom(clazz)) {
			mapper = new MapMapper(clazz);
		} else {
			throw new NoSuitableMapperException(String.format("No mapper found for class %s", clazz.getName()));
		}
		return mapper;
	}

	/**
	 * Creates a {@link Mapper} for the given class/type.
	 * 
	 * @param type
	 *            the type
	 * @return a {@link Mapper} for the given class/type.
	 */
	private Mapper createMapper(ParameterizedType type) {
		Type rawType = type.getRawType();
		if (!(rawType instanceof Class)) {
			// Don't see how this could ever happen, but just in case...
			throw new IllegalArgumentException(String.format("Raw type of ParameterizedType is not a class: %s", type));
		}
		Class<?> rawClass = (Class<?>) rawType;
		Mapper mapper;
		if (List.class.isAssignableFrom(rawClass)) {
			mapper = new ListMapper(type);
		} else if (Set.class.isAssignableFrom(rawClass)) {
			mapper = new SetMapper(type);
		} else if (Map.class.isAssignableFrom(rawClass)) {
			mapper = new MapMapper(type);
		} else {
			throw new NoSuitableMapperException(String.format("Unsupported type: %s", type));
		}
		return mapper;
	}

	private void createDefaultMappers() {
		BooleanMapper booleanMapper = new BooleanMapper();
		CharMapper charMapper = new CharMapper();
		ShortMapper shortMapper = new ShortMapper();
		IntegerMapper integerMapper = new IntegerMapper();
		LongMapper longMapper = new LongMapper();
		FloatMapper floatMapper = new FloatMapper();
		DoubleMapper doubleMapper = new DoubleMapper();

		cache.put(boolean.class, booleanMapper);
		cache.put(Boolean.class, booleanMapper);
		cache.put(char.class, charMapper);
		cache.put(Character.class, charMapper);
		cache.put(short.class, shortMapper);
		cache.put(Short.class, shortMapper);
		cache.put(int.class, integerMapper);
		cache.put(Integer.class, integerMapper);
		cache.put(long.class, longMapper);
		cache.put(Long.class, longMapper);
		cache.put(float.class, floatMapper);
		cache.put(Float.class, floatMapper);
		cache.put(double.class, doubleMapper);
		cache.put(Double.class, doubleMapper);
		cache.put(String.class, new StringMapper());
		cache.put(BigDecimal.class, new BigDecimalMapper());
		cache.put(byte[].class, new ByteArrayMapper());
		cache.put(char[].class, new CharArrayMapper());
		cache.put(GeoLocation.class, new GeoLocationMapper());
		cache.put(DatastoreKey.class, new KeyMapper());
	}

}
