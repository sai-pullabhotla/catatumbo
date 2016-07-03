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

import java.util.List;
import java.util.Map;

/**
 * QueryRequest objects hold the necessary information about a query to execute.
 * The {@link EntityManager} executes the QueryRquests and returns the
 * {@link QueryResponse}.
 * 
 * @author Sai Pullabhotla
 *
 */
public interface QueryRequest {

	/**
	 * Returns the GQL query
	 * 
	 * @return the GQL query
	 */
	public String getQuery();

	/**
	 * Returns the named bindings for this QueryRequest, if any. Implementations
	 * should never return <code>null</code>.
	 * 
	 * @return the named bindings
	 */
	public Map<String, Object> getNamedBindings();

	/**
	 * Returns the positional bindings of this QueryRequest, if any,
	 * Implementations should never return a <code>null</code>.
	 * 
	 * @return the positional bindings
	 */
	public List<Object> getPositionalBindings();

}
