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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.cloud.datastore.LongValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.jmethods.catatumbo.IndexerFactory;
import com.jmethods.catatumbo.IndexingException;

/**
 * @author Sai Pullabhotla
 *
 */
public class LowerCaseStringIndexerTest {

  private static final Logger LOGGER = Logger.getLogger(LowerCaseStringIndexerTest.class.getName());

  private static LowerCaseStringIndexer indexer;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    indexer = IndexerFactory.getInstance().getIndexer(LowerCaseStringIndexer.class);
  }

  @Test
  public void testIndex_1() {
    Value<?> input = NullValue.of();
    Value<?> output = indexer.index(input);
    assertEquals(input, output);
  }

  @Test
  public void testIndex_2() {
    StringValue input = StringValue.of("Hello World");
    StringValue output = (StringValue) indexer.index(input);
    assertEquals(input.get().toLowerCase(Locale.ENGLISH), output.get());
    assertNotEquals(input.get(), output.get());
  }

  @Test(expected = IndexingException.class)
  public void testIndex_3() {
    LongValue input = LongValue.of(5L);
    try {
      Value<?> output = indexer.index(input);
    } catch (Exception exp) {
      LOGGER.log(Level.INFO, exp.toString());
      throw exp;
    }
  }

}
