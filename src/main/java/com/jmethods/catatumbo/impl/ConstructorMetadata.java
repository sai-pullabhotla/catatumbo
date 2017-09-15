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

/**
 * Metadata for persistence class construction/instantiation options.
 * 
 * @author Sai Pullabhotla
 *
 */
public class ConstructorMetadata {

	/**
	 * The strategy to use for constructing/instantiating/building a persistence
	 * object
	 * 
	 * @author Sai Pullabhotla
	 *
	 */
	public enum ConstructionStrategy {
		/**
		 * Classic design pattern - using the default constructor and
		 * accessor/mutator methods per standard Java Beans convention.
		 */
		CLASSIC,

		/**
		 * Builder design pattern - if the persistence class does not have a
		 * public no-arg constructor, but has a static newBuilder/builder
		 * method.
		 */
		BUILDER;
	}

	/**
	 * The class to which this metadata belongs
	 */
	private final Class<?> clazz;

	/**
	 * The construction strategy to use for creating/building instances of this
	 * persistence class
	 */
	private final ConstructionStrategy constructionStrategy;

	/**
	 * A method handle for getting an instance of the persistence class. This
	 * method handle may be a handle to the default constructor or to the static
	 * newBuilder method.
	 */
	private final MethodHandle constructorMethodHandle;

	/**
	 * Builder class, if construction strategy is BUILDER
	 */
	private final Class<?> builderClass;

	/**
	 * A method handle for building the final persistence object when using the
	 * BUILDER strategy.
	 */
	private final MethodHandle buildMethodHandle;

	/**
	 * Creates a new instance of {@code ConstructionMetadata}.
	 * 
	 * @param builder
	 *            the {@code Builder} from which to create this metadata
	 */
	private ConstructorMetadata(Builder builder) {
		this.clazz = builder.clazz;
		this.constructionStrategy = builder.constructionStrategy;
		this.constructorMethodHandle = builder.constructorMethodHandle;
		this.builderClass = builder.builderClass;
		this.buildMethodHandle = builder.buildMethodHandle;
	}

	/**
	 * Returns the class to which this metadata belongs.
	 * 
	 * @return the class to which this metadata belongs.
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * Returns the strategy used for constructing/building instances of the
	 * model class to which this metadata belongs.
	 * 
	 * @return the strategy used for constructing/building instances of the
	 *         model class to which this metadata belongs.
	 */
	public ConstructionStrategy getConstructionStrategy() {
		return constructionStrategy;
	}

	/**
	 * Returns the method handle for constructing a new instance of model class
	 * or its Builder class.
	 * 
	 * @return the methodHandle the method handle for constructing a new
	 *         instance of model class or its Builder class.
	 */
	public MethodHandle getConstructorMethodHandle() {
		return constructorMethodHandle;
	}

	/**
	 * Returns the Builder class for the class to which this metadata belongs.
	 * 
	 * @return the Builder class for the class to which this metadata belongs.
	 *         May be {@code null}, if not using the BUILDER strategy.
	 */
	public Class<?> getBuilderClass() {
		return builderClass;
	}

	/**
	 * Returns the method handle for building the final persistence object from
	 * its Builder.
	 * 
	 * @return the method handle for building the final persistence object from
	 *         its Builder. May be {@code null}, if not using the Builder
	 *         strategy.
	 */
	public MethodHandle getBuildMethodHandle() {
		return buildMethodHandle;
	}

	/**
	 * Tells whether or not the persistence class represented by this metadata
	 * using the {@link ConstructionStrategy#CLASSIC} strategy.
	 * 
	 * @return {@code true} if the persistence class using the
	 *         {@link ConstructionStrategy#CLASSIC} strategy; {@code false},
	 *         otherwise.
	 */
	public final boolean isClassicConstructionStrategy() {
		return constructionStrategy == ConstructionStrategy.CLASSIC;
	}

	/**
	 * Tells whether or not the persistence class represented by this metadata
	 * using the {@link ConstructionStrategy#BUILDER} strategy.
	 * 
	 * @return {@code true} if the persistence class using the
	 *         {@link ConstructionStrategy#BUILDER} strategy; {@code false},
	 *         otherwise.
	 */
	public final boolean isBuilderConstructionStrategy() {
		return constructionStrategy == ConstructionStrategy.BUILDER;
	}

	/**
	 * Creates and returns a new {@link Builder} for building a
	 * {@link ConstructorMetadata}.
	 * 
	 * @return a new {@link Builder} for building a {@link ConstructorMetadata}.
	 */
	public static Builder newBuilder() {
		return new Builder();
	}

	/**
	 * Builder for creating {@link ConstructorMetadata}.
	 * 
	 * @author Sai Pullabhotla
	 *
	 */
	public static class Builder {
		/**
		 * @see ConstructorMetadata#clazz
		 */
		private Class<?> clazz;

		/**
		 * @see ConstructorMetadata#constructionStrategy
		 */
		private ConstructionStrategy constructionStrategy;

		/**
		 * @see ConstructorMetadata#constructorMethodHandle
		 */
		private MethodHandle constructorMethodHandle;

		/**
		 * @see ConstructorMetadata#builderClass
		 */
		private Class<?> builderClass;

		/**
		 * @see ConstructorMetadata#buildMethodHandle
		 */
		private MethodHandle buildMethodHandle;

		/**
		 * Creates a new instance of {@code Builder}.
		 */
		private Builder() {
			// Do nothing
		}

		/**
		 * Sets the persistence class.
		 * 
		 * @param clazz
		 *            the persistence class.
		 * @return this {@link Builder}
		 */
		public Builder setClazz(Class<?> clazz) {
			this.clazz = clazz;
			return this;
		}

		/**
		 * Sets the constructor strategy.
		 * 
		 * @param constructionStrategy
		 *            the construction strategy.
		 * @return this {@code Builder} instance
		 */
		public Builder setConstructionStrategy(ConstructionStrategy constructionStrategy) {
			this.constructionStrategy = constructionStrategy;
			return this;
		}

		/**
		 * Sets the method handle for constructing the persistence object or its
		 * builder object.
		 * 
		 * @param constructorMethodHandle
		 *            the method handle for constructing the persistence object
		 *            or its builder object.
		 * @return this {@code Builder} instance
		 */
		public Builder setConstructorMethodHandle(MethodHandle constructorMethodHandle) {
			this.constructorMethodHandle = constructorMethodHandle;
			return this;
		}

		/**
		 * Sets the builder class.
		 * 
		 * @param builderClass
		 *            the builder class.
		 * @return this {@code Builder} instance
		 */
		public Builder setBuilderClass(Class<?> builderClass) {
			this.builderClass = builderClass;
			return this;
		}

		/**
		 * Sets the method handle for building the persistence object from its
		 * builder.
		 * 
		 * @param buildMethodHandle
		 *            the method handle for building the persistence object from
		 *            its builder.
		 * @return this {@code Builder} instance
		 */
		public Builder setBuildMethodHandle(MethodHandle buildMethodHandle) {
			this.buildMethodHandle = buildMethodHandle;
			return this;
		}

		/**
		 * Creates and returns a new {@link ConstructorMetadata} from this
		 * {@code Builder}'s state.
		 * 
		 * @return a new {@link ConstructorMetadata} from this {@code Builder}'s
		 *         state.
		 */
		public ConstructorMetadata build() {
			return new ConstructorMetadata(this);
		}

	}

}
