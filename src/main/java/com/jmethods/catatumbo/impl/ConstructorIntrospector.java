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

package com.jmethods.catatumbo.impl;

import java.lang.invoke.MethodHandle;

import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.UnsupportedConstructionStrategyException;

/**
 * Introspector for persistence class construction/instantiation/building
 * mechanism. Metadata of the introspected classes will be cached for future
 * use.
 * 
 * @author Sai Pullabhotla
 *
 */
public class ConstructorIntrospector {

	/**
	 * Cache of previously introspected classes
	 */
	private static final Cache<Class<?>, ConstructorMetadata> cache = new Cache<>(64);

	/**
	 * Method name for producing an instance of Persistence class from the
	 * corresponding Builder
	 */
	public static final String METHOD_NAME_BUILD = "build";

	/**
	 * First of the supported method names to produce a new Builder for a
	 * persistence class
	 */
	public static final String METHOD_NAME_NEW_BUILDER = "newBuilder";

	/**
	 * Second/Last of the supported method names to produce a new Builder for a
	 * persistence class
	 */
	public static final String METHOD_NAME_BUILDER = "builder";

	/**
	 * Valid/supported method names for getting a new Builder for a Persistence
	 * class
	 */
	private static final String[] NEW_BUILDER_METHOD_NAMES = { METHOD_NAME_NEW_BUILDER, METHOD_NAME_BUILDER };

	/**
	 * Builder for the {@code ConstructorMetadata}
	 */
	private ConstructorMetadata.Builder metadataBuilder;

	/**
	 * Class being introspected
	 */
	private Class<?> clazz;

	/**
	 * Creates a new instance of {@code ConstructorIntrospector}.
	 */
	private ConstructorIntrospector(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Introspects the given Persistence class and returns the
	 * {@link ConstructorMetadata}.
	 * 
	 * @param clazz
	 *            the class to introspect
	 * @return the {@link ConstructorMetadata}.
	 */
	public static ConstructorMetadata introspect(Class<?> clazz) {
		ConstructorMetadata metadata = cache.get(clazz);
		if (metadata != null) {
			return metadata;
		}
		return loadMetadata(clazz);
	}

	/**
	 * Loads the metadata, if it does not already exist in the cache.
	 * 
	 * @param clazz
	 *            the class to introspect
	 * @return the metadata
	 */
	private static ConstructorMetadata loadMetadata(Class<?> clazz) {
		synchronized (clazz) {
			ConstructorMetadata metadata = cache.get(clazz);
			if (metadata == null) {
				ConstructorIntrospector introspector = new ConstructorIntrospector(clazz);
				introspector.process();
				metadata = introspector.metadataBuilder.build();
				cache.put(clazz, metadata);
			}
			return metadata;
		}
	}

	/**
	 * Introspects the class and builds the metadata.
	 */
	private void process() {
		metadataBuilder = ConstructorMetadata.newBuilder().setClazz(clazz);
		MethodHandle mh = IntrospectionUtils.findDefaultConstructor(clazz);
		if (mh != null) {
			metadataBuilder.setConstructionStrategy(ConstructorMetadata.ConstructionStrategy.CLASSIC)
					.setConstructorMethodHandle(mh);
			// We have everything we need for the classic pattern with a default
			// constructor and accessor/mutator methods, more validations will
			// follow.
			return;
		}
		// Now we check for Builder pattern usage
		mh = findNewBuilderMethod(clazz);
		if (mh == null) {
			final String pattern = "Class %s requires a public no-arg constructor or a public static method that "
					+ "returns a corresponding Builder instance. The name of the static method can either be %s or %s";
			String error = String.format(pattern, clazz.getName(), METHOD_NAME_NEW_BUILDER, METHOD_NAME_BUILDER);
			throw new UnsupportedConstructionStrategyException(error);
		}
		Class<?> builderClass = mh.type().returnType();
		metadataBuilder.setConstructionStrategy(ConstructorMetadata.ConstructionStrategy.BUILDER)
				.setConstructorMethodHandle(mh).setBuilderClass(builderClass);
		MethodHandle buildMethodHandle = IntrospectionUtils.findInstanceMethod(builderClass, METHOD_NAME_BUILD, clazz);
		if (buildMethodHandle == null) {
			String pattern = "Class %s requires a public instance method, %s, with a return type of %s";
			throw new EntityManagerException(String.format(pattern, builderClass.getName(), METHOD_NAME_BUILD, clazz));
		}
		metadataBuilder.setBuildMethodHandle(buildMethodHandle);
	}

	/**
	 * Finds and returns a method handle for new instance of a Builder class.
	 * 
	 * @param clazz
	 *            the persistence class
	 * @return the method handle for the newBuilder/builder method
	 */
	public static MethodHandle findNewBuilderMethod(Class<?> clazz) {
		MethodHandle mh = null;
		for (String methodName : NEW_BUILDER_METHOD_NAMES) {
			mh = IntrospectionUtils.findStaticMethod(clazz, methodName, Object.class);
			if (mh != null) {
				break;
			}
		}
		return mh;
	}

}
