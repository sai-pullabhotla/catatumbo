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
 * primitive long and wrapper Long types.
 * 
 * @author Sai Pullabhotla
 *
 */
public class LongConverter extends AbstractConverter {

	/**
	 * Singleton instance
	 */
	private static final LongConverter INSTANCE = new LongConverter();

	/**
	 * Creates a new instance of <code>LongConverter</code>.
	 */
	private LongConverter() {
		// Do nothing
	}

	@Override
	public ValueBuilder<?, ?, ?> toValueBuilder(Object input) {
		return LongValue.builder((long) input);
	}

	@Override
	public Object toObject(Value<?> input) {
		return input.get();
	}

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static LongConverter getInstance() {
		return INSTANCE;
	}

}
