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

import com.google.datastore.v1.QueryResultBatch;

import java.util.List;

/**
 * Response to a query execution. When the {@link EntityManager} executes a {@link QueryRequest}, it
 * returns the {@link QueryResponse}.
 * 
 * @param <T>
 *          the expected type of results
 * @author Sai Pullabhotla
 *
 */
public interface QueryResponse<T> {

  /**
   * Returns the results from the query execution.
   * 
   * @return the results from the query execution.
   */
  List<T> getResults();

  /**
   * Returns the start cursor of the results.
   * 
   * @return the start cursor of the results.
   */
  DatastoreCursor getStartCursor();

  /**
   * Returns the end cursor of the results. This can be used to navigate to the next page of results
   * by setting this as the OFFSET for the next query request.
   * 
   * @return the end cursor of the results.
   */
  DatastoreCursor getEndCursor();

  /**
   * Returns the MoreResultsType enum value. This can be used to determine whether or not to fetch and
   * check the next page for emptiness (and thus safely null the current end cursor when paginating).
   *
   * @return the MoreResultsType enum value
   */
  QueryResultBatch.MoreResultsType getMoreResultsType();

}
