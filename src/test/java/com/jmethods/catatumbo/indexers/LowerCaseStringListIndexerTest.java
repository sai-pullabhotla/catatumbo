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

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.cloud.datastore.ListValue;
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
public class LowerCaseStringListIndexerTest {

	private static final Logger LOGGER = Logger.getLogger(LowerCaseStringListIndexerTest.class.getName());
	private static LowerCaseStringListIndexer indexer;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		indexer = IndexerFactory.getInstance().getIndexer(LowerCaseStringListIndexer.class);
	}

	@Test
	public void testIndex_1() {
		Value<?> input = NullValue.of();
		Value<?> output = indexer.index(input);
		assertEquals(input, output);
	}

	@Test
	public void testIndex_2() {
		Value<?>[] inputArray = { StringValue.of("ONE"), StringValue.of("Two"), StringValue.of("thRee") };
		ListValue input = ListValue.of(Arrays.asList(inputArray));
		ListValue output = (ListValue) indexer.index(input);
		List<StringValue> inputList = (List<StringValue>) input.get();
		List<StringValue> outputList = (List<StringValue>) output.get();
		assertEquals(inputList.get(0).get().toLowerCase(Locale.ENGLISH), outputList.get(0).get());
		assertEquals(inputList.get(1).get().toLowerCase(Locale.ENGLISH), outputList.get(1).get());
		assertEquals(inputList.get(2).get().toLowerCase(Locale.ENGLISH), outputList.get(2).get());
	}

	@Test
	public void testIndex_3() {
		Value<?>[] inputArray = { StringValue.of("Hello"), NullValue.of() };
		ListValue input = ListValue.of(Arrays.asList(inputArray));
		ListValue output = (ListValue) indexer.index(input);
		List<? extends Value> inputList = input.get();
		List<? extends Value> outputList = output.get();
		assertEquals(((StringValue) inputList.get(0)).get().toLowerCase(Locale.ENGLISH), outputList.get(0).get());
		assertEquals(inputList.get(1).get(), outputList.get(1).get());
	}

	@Test(expected = IndexingException.class)
	public void testIndex_4() {
		StringValue input = StringValue.of("Hello World");
		try {
			Value<?> output = indexer.index(input);
		} catch (Exception exp) {
			LOGGER.log(Level.INFO, exp.toString());
			throw exp;
		}
	}

	@Test(expected = IndexingException.class)
	public void testIndex_5() {
		Value<?>[] inputArray = { StringValue.of("Hello"), NullValue.of(), LongValue.of(5L) };
		ListValue input = ListValue.of(Arrays.asList(inputArray));
		try {
			ListValue output = (ListValue) indexer.index(input);
		} catch (Exception exp) {
			LOGGER.log(Level.INFO, exp.toString());
			throw exp;
		}
	}

}
