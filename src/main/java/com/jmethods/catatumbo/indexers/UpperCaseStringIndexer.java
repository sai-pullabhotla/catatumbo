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

package com.jmethods.catatumbo.indexers;

import java.util.Locale;

import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueType;
import com.jmethods.catatumbo.Indexer;
import com.jmethods.catatumbo.IndexingException;

/**
 * An implementation of {@link Indexer} interface for creating indexes in upper
 * case. This indexer assumes that the value being indexed is of type String.
 * Any other type will result in ClassCastException.
 * 
 * @author Sai Pullabhotla
 * 
 */
public class UpperCaseStringIndexer implements Indexer {

	@Override
	public Value<?> index(Value<?> input) {
		if (input.getType() == ValueType.NULL) {
			return NullValue.of();
		}
		try {
			String str = ((StringValue) input).get();
			return StringValue.of(str.toUpperCase(Locale.ENGLISH));
		} catch (Exception exp) {
			throw new IndexingException(exp);
		}
	}

}
