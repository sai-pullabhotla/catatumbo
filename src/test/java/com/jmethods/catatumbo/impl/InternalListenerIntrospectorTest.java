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

import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.entities.Animal;
import com.jmethods.catatumbo.entities.Dog;
import com.jmethods.catatumbo.entities.Pet;
import com.jmethods.catatumbo.entities.StringField;
import com.jmethods.catatumbo.listeners.BadInternalListener1;
import com.jmethods.catatumbo.listeners.BadInternalListener2;
import com.jmethods.catatumbo.listeners.BadInternalListener3;
import com.jmethods.catatumbo.listeners.BadInternalListener4;

/**
 * @author Sai Pullabhotla
 *
 */
public class InternalListenerIntrospectorTest {

	@Test
	public void testIntrospect1() {
		InternalListenerMetadata metadata = InternalListenerIntrospector.introspect(StringField.class);
		assertNull(metadata.getCallbacks());
	}

	@Test
	public void testIntrospect2() {
		InternalListenerMetadata metadata = InternalListenerIntrospector.introspect(Animal.class);
		assertEquals(1, metadata.getCallbacks().size());
		assertNotNull(metadata.getCallbacks().get(CallbackType.PRE_INSERT));
	}

	@Test
	public void testIntrospect3() {
		InternalListenerMetadata metadata = InternalListenerIntrospector.introspect(Pet.class);
		assertNotNull(metadata.getCallbacks().get(CallbackType.PRE_INSERT));
	}

	@Test
	public void testIntrospect4() {
		InternalListenerMetadata metadata = InternalListenerIntrospector.introspect(Dog.class);
		assertNotNull(metadata.getCallbacks().get(CallbackType.PRE_INSERT));
		assertNotNull(metadata.getCallbacks().get(CallbackType.POST_INSERT));
	}

	@Test(expected = EntityManagerException.class)
	public void testIntrospect5() {
		try {
			InternalListenerMetadata metadata = InternalListenerIntrospector.introspect(BadInternalListener1.class);
		} catch (EntityManagerException exp) {
			System.out.println(exp);
			throw exp;
		}
	}

	@Test(expected = EntityManagerException.class)
	public void testIntrospect6() {
		try {
			InternalListenerMetadata metadata = InternalListenerIntrospector.introspect(BadInternalListener2.class);
		} catch (EntityManagerException exp) {
			System.out.println(exp);
			throw exp;
		}
	}

	@Test(expected = EntityManagerException.class)
	public void testIntrospect7() {
		try {
			InternalListenerMetadata metadata = InternalListenerIntrospector.introspect(BadInternalListener3.class);
		} catch (EntityManagerException exp) {
			System.out.println(exp);
			throw exp;
		}
	}

	@Test(expected = EntityManagerException.class)
	public void testIntrospect8() {
		try {
			InternalListenerMetadata metadata = InternalListenerIntrospector.introspect(BadInternalListener4.class);
		} catch (EntityManagerException exp) {
			System.out.println(exp);
			throw exp;
		}
	}

}
