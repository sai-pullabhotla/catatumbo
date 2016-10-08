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

import java.lang.reflect.Method;
import java.util.Map;

import org.junit.Test;

import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.listeners.BadExternalListener1;
import com.jmethods.catatumbo.listeners.BadExternalListener2;
import com.jmethods.catatumbo.listeners.BadExternalListener3;
import com.jmethods.catatumbo.listeners.GoodExternalListener1;

/**
 * @author Sai Pullabhotla
 *
 */
public class ExternalListenerIntrospectorTest {

	@Test(expected = EntityManagerException.class)
	public void testIntrospect_NotEntityListener() {
		try {
			ExternalListenerIntrospector.introspect(BadExternalListener1.class);
		} catch (EntityManagerException exp) {
			System.out.println(exp.getMessage());
			throw exp;
		}
	}

	@Test(expected = EntityManagerException.class)
	public void testIntrospect_MultipleCallbacks() {
		try {
			ExternalListenerIntrospector.introspect(BadExternalListener2.class);
		} catch (EntityManagerException exp) {
			System.out.println(exp.getMessage());
			throw exp;
		}
	}

	@Test(expected = EntityManagerException.class)
	public void testIntrospect_InvalidMethod() {
		try {
			ExternalListenerIntrospector.introspect(BadExternalListener3.class);
		} catch (EntityManagerException exp) {
			System.out.println(exp.getMessage());
			throw exp;
		}
	}

	@Test
	public void testIntroSpect1() {
		ExternalListenerMetadata metadata = ExternalListenerIntrospector.introspect(GoodExternalListener1.class);
		Map<CallbackType, Method> callbacks = metadata.getCallbacks();
		assertTrue(metadata.getCallbacks().size() == 3);
		assertTrue(callbacks.containsKey(CallbackType.PRE_INSERT));
		assertTrue(callbacks.containsKey(CallbackType.PRE_UPDATE));
		assertTrue(callbacks.containsKey(CallbackType.POST_LOAD));
	}

}
