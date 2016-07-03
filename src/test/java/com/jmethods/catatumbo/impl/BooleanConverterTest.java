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

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.cloud.datastore.BooleanValue;
import com.jmethods.catatumbo.impl.BooleanConverter;

/**
 * @author Sai Pullabhotla
 *
 */
public class BooleanConverterTest {

	@Test
	public void testToObject() {
		BooleanValue v = BooleanValue.of(true);
		Boolean o = (Boolean) BooleanConverter.getInstance().toObject(v);
		assertTrue(o == true);

	}

	@Test
	public void testToValue_Primitive_False() {
		BooleanValue v = (BooleanValue) BooleanConverter.getInstance().toValue(false);
		assertTrue(v.get() == false);
	}

	@Test
	public void testToValue_Primitive_True() {
		BooleanValue v = (BooleanValue) BooleanConverter.getInstance().toValue(true);
		assertTrue(v.get() == true);
	}

	@Test
	public void testToValue_Wrapper_False() {
		BooleanValue v = (BooleanValue) BooleanConverter.getInstance().toValue(Boolean.FALSE);
		assertTrue(v.get() == false);
	}

	@Test
	public void testToValue_Wrapper_True() {
		BooleanValue v = (BooleanValue) BooleanConverter.getInstance().toValue(Boolean.TRUE);
		assertTrue(v.get() == true);
	}

}
