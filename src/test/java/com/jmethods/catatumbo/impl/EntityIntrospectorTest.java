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

import java.util.Objects;

import org.junit.Test;

import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.entities.Customer;
import com.jmethods.catatumbo.entities.OptimisticLock1;
import com.jmethods.catatumbo.entities.OptimisticLockBad1;
import com.jmethods.catatumbo.entities.OptimisticLockBad2;

/**
 * @author Sai Pullabhotla
 *
 */
public class EntityIntrospectorTest {

	@Test
	public void testIntrospect_Embedded() {
		EntityMetadata metadata = EntityIntrospector.introspect(Customer.class);
		System.out.println(metadata);
		System.out.println("************");
		metadata = EntityIntrospector.introspect(Customer.class);
		System.out.println(metadata);

	}

	@Test
	public void testIntrospect_OptimisticLock1() {
		EntityMetadata metadata = EntityIntrospector.introspect(OptimisticLock1.class);
		assertTrue(Objects.equals(metadata.getVersionMetadata(), metadata.getPropertyMetadata("version")));
	}

	@Test(expected = EntityManagerException.class)
	public void testIntrospect_OptimisticLock_StringField() {
		EntityMetadata metadata = EntityIntrospector.introspect(OptimisticLockBad1.class);
	}

	@Test(expected = EntityManagerException.class)
	public void testIntrospect_OptimisticLock_TwoVersionFields() {
		EntityMetadata metadata = EntityIntrospector.introspect(OptimisticLockBad2.class);
	}

}
