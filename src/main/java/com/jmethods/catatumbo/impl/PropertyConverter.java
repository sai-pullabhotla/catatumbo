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

import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;

/**
 * An interface for converting Java types from/to the Cloud Datastore properties
 * (types expected by the Datastore API).
 * 
 * @author Sai Pullabhotla
 *
 */
public interface PropertyConverter {

	/**
	 * Converts the given input to a {@link Value}. Used when marshaling a POJO
	 * to Entity.
	 * 
	 * @param input
	 *            the object to convert - can be any of the supported types by
	 *            this API.
	 * @param metadata
	 *            the metadata of the property
	 * @return a Value that is equivalent to the given object.
	 */
	Value<?> toValue(Object input, PropertyMetadata metadata);

	/**
	 * Converts the given input to a ValueBuilder. Used when marshaling a POJO
	 * to Entity.
	 * 
	 * @param input
	 *            the object to convert.
	 * @param metadata
	 *            the metadata of the property
	 * @return A ValueBuilder that is equivalent to the given object.
	 */
	ValueBuilder<?, ?, ?> toValueBuilder(Object input, PropertyMetadata metadata);

	/**
	 * Converts the given input to an object. Used when unmarshaling an Entity
	 * to a POJO.
	 * 
	 * @param input
	 *            the Value to convert.
	 * @param metadata
	 *            the metadata of the property
	 * @return An object that is equivalent to the given Value.
	 */
	Object toObject(Value<?> input, PropertyMetadata metadata);

}
