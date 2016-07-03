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

import com.google.cloud.datastore.KeyValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.DefaultDatastoreKey;

/**
 * An implementation of {@link PropertyConverter} for handling Keys.
 * 
 * @author Sai Pullabhotla
 *
 */
public class KeyConverter extends AbstractConverter {

	/**
	 * Singleton instance
	 */
	private static final KeyConverter INSTANCE = new KeyConverter();

	/**
	 * Creates a new instance of <code>KeyConverter</code>.
	 */
	private KeyConverter() {
		// Do nothing
	}

	@Override
	public ValueBuilder<?, ?, ?> toValueBuilder(Object obj) {
		DatastoreKey datastoreKey = (DatastoreKey) obj;
		return KeyValue.builder(datastoreKey.nativeKey());
	}

	@Override
	public Object toObject(Value<?> value) {
		KeyValue keyValue = (KeyValue) value;
		return new DefaultDatastoreKey(keyValue.get());
	}

	/**
	 * Returns the singleton instance of <code>KeyConverter</code>.
	 * 
	 * @return the singleton instance of <code>KeyConverter</code>.
	 */
	public static KeyConverter getInstance() {
		return INSTANCE;
	}
}
