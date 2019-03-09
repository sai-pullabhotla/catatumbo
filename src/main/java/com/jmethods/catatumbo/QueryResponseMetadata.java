/*
 * Copyright 2019 Sai Pullabhotla.
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

/**
 * Contains metadata on the query execution result within the context of a {@link QueryResponse}.
 *
 * @author Matthew Tso
 */
public interface QueryResponseMetadata {

  /**
   * Returns the query execution state.
   *
   * @return the query execution state
   */
  QueryState getQueryState();

  /**
   * The result state value returned in a query execution.
   *
   * @author Matthew Tso
   */
  enum QueryState {
    /**
     * Possibly unspecified enum value.
     */
    MORE_RESULTS_TYPE_UNSPECIFIED,

    /**
     * Query execution is not finished.
     */
    NOT_FINISHED,

    /**
     * There *may* be more results after the specified limit (possibly a false positive).
     */
    MORE_RESULTS_AFTER_LIMIT,

    /**
     * There *may* be more results after the specified end cursor (possibly a false positive).
     */
    MORE_RESULTS_AFTER_CURSOR,

    /**
     * There are no more results left to query for.
     */
    NO_MORE_RESULTS,

    /**
     * Unrecognized state.
     */
    UNRECOGNIZED;

    /**
     * Initialize a QueryState from a Datastore QueryResultBatch.MoreResultsType enum value.
     *
     * @param moreResultsType
     *          original enum value.
     * @return query state enum
     */
    public static QueryState forMoreResultsType(QueryResultBatch.MoreResultsType moreResultsType) {
      if (null == moreResultsType) {
        return null;
      }
      switch (moreResultsType) {
        case MORE_RESULTS_TYPE_UNSPECIFIED:
          return MORE_RESULTS_TYPE_UNSPECIFIED;
        case NOT_FINISHED:
          return NOT_FINISHED;
        case MORE_RESULTS_AFTER_LIMIT:
          return MORE_RESULTS_AFTER_LIMIT;
        case MORE_RESULTS_AFTER_CURSOR:
          return MORE_RESULTS_AFTER_CURSOR;
        case NO_MORE_RESULTS:
          return NO_MORE_RESULTS;
        case UNRECOGNIZED:
          return UNRECOGNIZED;
        default:
          throw new IllegalArgumentException(
              "Unknown or unimplemented QueryResultBatch.MoreResultsType: " + moreResultsType);
      }
    }
  }
}
