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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.Key;
import com.jmethods.catatumbo.TestUtils;
import com.jmethods.catatumbo.entities.Contact;
import com.jmethods.catatumbo.entities.Customer;
import com.jmethods.catatumbo.entities.WrappedLongIdEntity;
import com.jmethods.catatumbo.entities.WrappedLongObjectIdEntity;
import com.jmethods.catatumbo.entities.WrappedStringIdEntity;
import com.jmethods.catatumbo.impl.Marshaller.Intent;

/**
 * @author Sai Pullabhotla
 *
 */
public class MarshallerTest {

	private static DefaultEntityManager em;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		em = (DefaultEntityManager) TestUtils.getEntityManager();
	}

	@Test
	public void testMarshal_Embedded() {
		Customer customer = Customer.createSampleCustomer2();
		FullEntity<?> entity = (FullEntity<?>) Marshaller.marshal(em, customer, Intent.INSERT);
		assertNull(entity.getString("ba_line1"));
		assertNull(entity.getString("ba_line2"));
		assertNull(entity.getString("ba_zip"));
		assertNull(entity.getString("ba_zipx"));
		assertNull(entity.getString("ba_zip"));
	}

	@Test
	public void testMarshal_Embedded_Imploded() {
		Contact contact = Contact.createContact1();
		FullEntity<?> entity = (FullEntity<?>) Marshaller.marshal(em, contact, Intent.INSERT);
		assertNull(entity.getValue("cellNumber").get());
		assertNull(entity.getValue("homeAddress").get());
	}

	@Test
	public void testMarshal_Embedded_Imploded2() {
		Contact contact = Contact.createContact2();
		FullEntity<?> entity = (FullEntity<?>) Marshaller.marshal(em, contact, Intent.INSERT);
		assertEquals("55555", entity.getEntity("homeAddress").getEntity("postal_code").getString("zip"));
	}

	@Test
	public void testMarshal_WrappedLongId1() {
		WrappedLongIdEntity entity = WrappedLongIdEntity.getSample1();
		FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(em, entity, Intent.INSERT);
		IncompleteKey incompleteKey = nativeEntity.getKey();
		assertNotNull(incompleteKey);
	}

	@Test
	public void testMarshal_WrappedLongId2() {
		WrappedLongIdEntity entity = WrappedLongIdEntity.getSample2();
		FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(em, entity, Intent.INSERT);
		IncompleteKey incompleteKey = nativeEntity.getKey();
		assertNotNull(incompleteKey);
	}

	@Test
	public void testMarshal_WrappedLongId3() {
		WrappedLongIdEntity entity = WrappedLongIdEntity.getSample3();
		FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(em, entity, Intent.INSERT);
		Key key = (Key) nativeEntity.getKey();
		assertNotNull(key);
		assertEquals(50000L, (long) key.getId());
	}

	@Test
	public void testMarshal_WrappedLongObjectId1() {
		WrappedLongObjectIdEntity entity = WrappedLongObjectIdEntity.getSample1();
		FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(em, entity, Intent.INSERT);
		IncompleteKey incompleteKey = nativeEntity.getKey();
		assertNotNull(incompleteKey);
	}

	@Test
	public void testMarshal_WrappedLongObjectId2() {
		WrappedLongObjectIdEntity entity = WrappedLongObjectIdEntity.getSample2();
		FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(em, entity, Intent.INSERT);
		IncompleteKey incompleteKey = nativeEntity.getKey();
		assertNotNull(incompleteKey);
	}

	@Test
	public void testMarshal_WrappedLongObjectId3() {
		WrappedLongObjectIdEntity entity = WrappedLongObjectIdEntity.getSample3();
		FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(em, entity, Intent.INSERT);
		IncompleteKey incompleteKey = nativeEntity.getKey();
		assertNotNull(incompleteKey);
	}

	@Test
	public void testMarshal_WrappedLongObjectId4() {
		WrappedLongObjectIdEntity entity = WrappedLongObjectIdEntity.getSample4();
		Entity nativeEntity = (Entity) Marshaller.marshal(em, entity, Intent.INSERT);
		Key key = nativeEntity.getKey();
		assertNotNull(key);
		assertEquals(entity.getId().getValue(), key.getId());
	}

	@Test
	public void testMarshal_WrappedStringId1() {
		WrappedStringIdEntity entity = WrappedStringIdEntity.getSample1();
		Entity nativeEntity = (Entity) Marshaller.marshal(em, entity, Intent.INSERT);
		Key key = nativeEntity.getKey();
		assertNotNull(key);
		assertTrue(key.hasName());
	}

	@Test
	public void testMarshal_WrappedStringId2() {
		WrappedStringIdEntity entity = WrappedStringIdEntity.getSample2();
		Entity nativeEntity = (Entity) Marshaller.marshal(em, entity, Intent.INSERT);
		Key key = nativeEntity.getKey();
		assertNotNull(key);
		assertTrue(key.hasName());
	}

	@Test
	public void testMarshal_WrappedStringId3() {
		WrappedStringIdEntity entity = WrappedStringIdEntity.getSample3();
		Entity nativeEntity = (Entity) Marshaller.marshal(em, entity, Intent.INSERT);
		Key key = nativeEntity.getKey();
		assertNotNull(key);
		assertTrue(key.hasName());
	}

	@Test
	public void testMarshal_WrappedStringId4() {
		WrappedStringIdEntity entity = WrappedStringIdEntity.getSample4();
		Entity nativeEntity = (Entity) Marshaller.marshal(em, entity, Intent.INSERT);
		Key key = nativeEntity.getKey();
		assertNotNull(key);
		assertTrue(key.hasName());
		assertEquals(entity.getId().getValue(), key.getName());
	}

}
