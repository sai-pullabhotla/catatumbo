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

import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;

/**
 * An implementation of {@link PropertyConverter} interface for dealing with
 * enumerated types (enum).
 * 
 * @author Sai Pullabhotla
 *
 */
public class EnumConverter extends AbstractConverter {

	/**
	 * Singleton instance
	 */
	private static final EnumConverter INSTANCE = new EnumConverter();

	/**
	 * Creates a new instance of <code>EnumConverter</code>.
	 */
	private EnumConverter() {
		// Do nothing
	}

	@Override
	public ValueBuilder<?, ?, ?> toValueBuilder(Object input, PropertyMetadata metadata) {
		return StringValue.builder(input.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object toObject(Value<?> input, PropertyMetadata metadata) {
		String value = ((StringValue) input).get();
		return Enum.valueOf(metadata.getDeclaredType(), value);
	}

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static EnumConverter getInstance() {
		return INSTANCE;
	}

}
