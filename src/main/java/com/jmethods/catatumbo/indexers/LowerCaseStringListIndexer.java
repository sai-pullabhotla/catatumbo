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

import java.util.List;

import com.google.cloud.datastore.ListValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueType;
import com.jmethods.catatumbo.Indexer;
import com.jmethods.catatumbo.IndexerFactory;
import com.jmethods.catatumbo.IndexingException;

/**
 * An implementation of {@link Indexer} interface to index a List of Strings.
 * Each item in the list will be converted to lower case and stored as an Array
 * property in the Cloud Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public class LowerCaseStringListIndexer implements Indexer {

	/**
	 * Indexer for items in the list.
	 */
	private static final LowerCaseStringIndexer ITEM_INDEXER = IndexerFactory.getInstance()
			.getIndexer(LowerCaseStringIndexer.class);

	@Override
	public Value<?> index(Value<?> input) {
		if (input.getType() == ValueType.NULL) {
			return NullValue.of();
		}
		try {
			List<? extends Value<?>> list = ((ListValue) input).get();
			ListValue.Builder builder = ListValue.newBuilder();
			for (Value<?> item : list) {
				builder.addValue(ITEM_INDEXER.index(item));
			}
			return builder.build();
		} catch (Exception exp) {
			throw new IndexingException(exp);
		}
	}

}
