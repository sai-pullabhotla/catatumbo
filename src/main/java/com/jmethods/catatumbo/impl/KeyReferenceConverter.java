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

import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;

/**
 * @author Sai Pullabhotla
 *
 */
public class KeyReferenceConverter extends AbstractConverter {

	private static final KeyReferenceConverter INSTANCE = new KeyReferenceConverter();

	/**
	 * 
	 */
	private KeyReferenceConverter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ValueBuilder<?, ?, ?> toValueBuilder(Object obj) {
		return KeyValue.builder(Key.fromUrlSafe((String) obj));
	}

	@Override
	public Object toObject(Value<?> value) {
		return ((Key) value.get()).toUrlSafe();
	}

	/**
	 * @return the instance
	 */
	public static KeyReferenceConverter getInstance() {
		return INSTANCE;
	}

}
