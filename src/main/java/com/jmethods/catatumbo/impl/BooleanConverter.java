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

import com.google.cloud.datastore.BooleanValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;

/**
 * An implementation of {@link PropertyConverter} interface to handle the
 * conversion of primitive boolean and wrapper Boolean.
 * 
 * @author Sai Pullabhotla
 *
 */
public class BooleanConverter extends AbstractConverter {

	/**
	 * Singleton instance
	 */
	private static final BooleanConverter INSTANCE = new BooleanConverter();

	/**
	 * Creates a new instance of <code>BooleanConverter</code>.
	 */
	private BooleanConverter() {
		// Do nothing
	}

	@Override
	public ValueBuilder<?, ?, ?> toValueBuilder(Object input) {
		return BooleanValue.builder((boolean) input);
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
	public static BooleanConverter getInstance() {
		return INSTANCE;
	}

}
