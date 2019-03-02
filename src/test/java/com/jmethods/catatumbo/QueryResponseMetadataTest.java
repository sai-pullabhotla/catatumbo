/*
 * Copyright 2019 Sai Pullabhotla, Matthew Tso.
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

import java.util.Arrays;

import com.google.datastore.v1.QueryResultBatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static junit.framework.TestCase.assertEquals;

/**
 * @author Matthew Tso
 */
@RunWith(Parameterized.class)
public class QueryResponseMetadataTest {
  private final QueryResultBatch.MoreResultsType input;
  private final QueryResponseMetadata.QueryState expected;

  public QueryResponseMetadataTest(QueryResultBatch.MoreResultsType input,
    QueryResponseMetadata.QueryState expected) {
    this.input = input;
    this.expected = expected;
  }

  @Parameterized.Parameters
  public static Iterable<Object[]> testCases() {
    return Arrays.asList(new Object[][] {
      {
        QueryResultBatch.MoreResultsType.MORE_RESULTS_TYPE_UNSPECIFIED,
        QueryResponseMetadata.QueryState.MORE_RESULTS_TYPE_UNSPECIFIED
      },
      {
        QueryResultBatch.MoreResultsType.NOT_FINISHED,
        QueryResponseMetadata.QueryState.NOT_FINISHED,
      },
      {
        QueryResultBatch.MoreResultsType.MORE_RESULTS_AFTER_LIMIT,
        QueryResponseMetadata.QueryState.MORE_RESULTS_AFTER_LIMIT,
      },
      {
        QueryResultBatch.MoreResultsType.MORE_RESULTS_AFTER_CURSOR,
        QueryResponseMetadata.QueryState.MORE_RESULTS_AFTER_CURSOR,
      },
      {
        QueryResultBatch.MoreResultsType.NO_MORE_RESULTS,
        QueryResponseMetadata.QueryState.NO_MORE_RESULTS,
      },
      {
        QueryResultBatch.MoreResultsType.UNRECOGNIZED,
        QueryResponseMetadata.QueryState.UNRECOGNIZED,
      },
      {
        null,
        null,
      },
    });
  }

  @Test
  public void testQueryStateMapping() {
    QueryResponseMetadata.QueryState actual = QueryResponseMetadata.QueryState.forMoreResultsType(input);
    assertEquals(expected, actual);
  }
}
