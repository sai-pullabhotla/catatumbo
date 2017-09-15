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

package com.jmethods.catatumbo.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.invoke.MethodHandle;
import java.util.logging.Logger;

import org.junit.Test;

import com.jmethods.catatumbo.entities.ImmutablePhone;

/**
 * @author Sai Pullabhotla
 *
 */
public class IntrospectionUtilsTest {

	private static Logger LOGGER = Logger.getLogger(IntrospectionUtilsTest.class.getName());

	@Test
	public void testGetCapitalizedName1() {
		String input = "name";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("Name", output);
	}

	@Test
	public void testGetCapitalizedName2() {
		String input = "x";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("X", output);
	}

	@Test
	public void testGetCapitalizedName3() {
		String input = "firstName";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("FirstName", output);
	}

	@Test
	public void testGetCapitalizedName4() {
		String input = "X";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("X", output);
	}

	@Test
	public void testGetCapitalizedName5() {
		String input = "xY";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("xY", output);
	}

	@Test
	public void testGetCapitalizedName6() {
		String input = "xYz";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("xYz", output);
	}

	@Test
	public void testGetCapitalizedName7() {
		String input = "URL";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("URL", output);
	}

	@Test
	public void testGetCapitalizedName8() {
		String input = "uRL";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("uRL", output);
	}

	@Test
	public void testGetCapitalizedName9() {
		String input = "aURL";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("aURL", output);
	}

	@Test
	public void testGetCapitalizedName10() {
		String input = "salesRepCommission";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("SalesRepCommission", output);
	}

	@Test
	public void testGetCapitalizedName11() {
		String input = "xSameAsY";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("xSameAsY", output);
	}

	@Test
	public void testGetCapitalizedName12() {
		String input = "xindex";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("Xindex", output);
	}

	@Test
	public void testGetCapitalizedName13() {
		String input = "xIndex";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("xIndex", output);
	}

	@Test
	public void testGetCapitalizedName14() {
		String input = "_name";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("_name", output);
	}

	@Test
	public void testGetCapitalizedName15() {
		String input = "$abc";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("$abc", output);
	}

	@Test
	public void testGetCapitalizedName16() {
		String input = "$Ab";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("$Ab", output);
	}

	@Test
	public void testGetCapitalizedName17() {
		String input = "a1";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("A1", output);
	}

	@Test
	public void testGetCapitalizedName18() {
		String input = "aB1";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("aB1", output);
	}

	@Test
	public void testGetCapitalizedName19() {
		String input = "ab1";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("Ab1", output);
	}

	@Test
	public void testGetCapitalizedName20() {
		String input = "iPhoneName";
		String output = IntrospectionUtils.getCapitalizedName(input);
		LOGGER.info(input + "-->" + output);
		assertEquals("iPhoneName", output);
	}

	@Test
	public void testFindStaticMethod() {
		MethodHandle mh = IntrospectionUtils.findStaticMethod(ImmutablePhone.class, "newBuilder", Object.class);
		assertNotNull(mh);
	}

	@Test
	public void testFindInstanceStaticMethod() {
		MethodHandle mh = IntrospectionUtils.findInstanceMethod(ImmutablePhone.class, "getCountryCode", String.class);
		assertNotNull(mh);
	}

	@Test
	public void testFindInstanceStaticMethod2() {
		MethodHandle mh = IntrospectionUtils.findInstanceMethod(ImmutablePhone.Builder.class, "setCountryCode", null,
				String.class);
		assertNotNull(mh);
	}

}
