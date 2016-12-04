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

package com.jmethods.catatumbo;

import com.google.cloud.datastore.Value;

/**
 * Contract for building secondary indexes for entity properties. An
 * implementation of Indexer must have a public default (a.k.a no-argument)
 * constructor.
 * 
 * @author Sai Pullabhotla
 *
 */
public interface Indexer {

	/**
	 * Returns indexed value for the given input.
	 * 
	 * @param input
	 *            the input, this is the output from the {@link Mapper} that is
	 *            associated with the entity's field.
	 * @return the indexed value
	 */
	Value<?> index(Value<?> input);

}
