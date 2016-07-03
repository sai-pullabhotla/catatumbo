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

/**
 * A base implementation of {@link QueryResponse} interface.
 * 
 * @param <T>
 *            the expected result type
 * @author Sai Pullabhotla
 *
 */
public class BaseQueryResponse<T> implements QueryResponse<T> {

	/**
	 * A list to hold the results of the query
	 */
	protected List<T> results = null;

	/**
	 * Start cursor
	 */
	protected DatastoreCursor startCursor = null;

	/**
	 * End cursor
	 */
	protected DatastoreCursor endCursor = null;

	/**
	 * Creates a new instance of <code>BaseQueryResponse</code>.
	 * 
	 */
	public BaseQueryResponse() {
		// Do nothing
	}

	@Override
	public List<T> getResults() {
		return results;
	}

	/**
	 * Sets the results to the given <code>results</code>.
	 * 
	 * @param results
	 *            the results
	 */
	public void setResults(List<T> results) {
		this.results = results;
	}

	@Override
	public DatastoreCursor getStartCursor() {
		return startCursor;
	}

	/**
	 * Sets the starting cursor to the given value.
	 * 
	 * @param startCursor
	 *            the start cursor
	 */
	public void setStartCursor(DatastoreCursor startCursor) {
		this.startCursor = startCursor;
	}

	@Override
	public DatastoreCursor getEndCursor() {
		return endCursor;
	}

	/**
	 * Sets the end cursor to the given value.
	 * 
	 * @param endCursor
	 *            the end cursor
	 */
	public void setEndCursor(DatastoreCursor endCursor) {
		this.endCursor = endCursor;
	}

}
