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

import com.google.cloud.datastore.LongValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;

/**
 * An implementation of {@link PropertyConverter} interface for dealing with
 * primitive short and wrapper Short types.
 * 
 * @author Sai Pullabhotla
 *
 */
public class ShortConverter extends AbstractConverter {

	/**
	 * Singleton instance
	 */
	private static final ShortConverter INSTANCE = new ShortConverter();

	/**
	 * Creates a new instance of <code>ShortConverter</code>.
	 */
	private ShortConverter() {
		// Do nothing
	}

	@Override
	public Object toObject(Value<?> input) {
		// @ToDo check the value to make sure it is in the range of short.
		return ((LongValue) input).get().shortValue();
	}

	@Override
	public ValueBuilder<?, ?, ?> toValueBuilder(Object input) {
		return LongValue.builder((short) input);
	}

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static ShortConverter getInstance() {
		return INSTANCE;
	}
}
