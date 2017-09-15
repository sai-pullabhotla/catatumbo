/*
 * Copyright 2017 Sai Pullabhotla.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityValue;
import com.jmethods.catatumbo.entities.ImmutablePerson;
import com.jmethods.catatumbo.entities.OptionalCreatedTimestamp;
import com.jmethods.catatumbo.entities.OptionalFieldsEntity;
import com.jmethods.catatumbo.entities.OptionalFieldsEntity2;
import com.jmethods.catatumbo.entities.OptionalFieldsEntity3;
import com.jmethods.catatumbo.entities.OptionalSubClass;
import com.jmethods.catatumbo.entities.OptionalUpdatedTimestamp;
import com.jmethods.catatumbo.entities.OptionalVersion;
import com.jmethods.catatumbo.impl.DefaultEntityManager;

/**
 * @author Sai Pullabhotla
 *
 */

public class OptionalFieldsTest {

	private static EntityManager em = null;
	private static Datastore datastore;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		em = TestUtils.getEntityManager();
		datastore = ((DefaultEntityManager) em).getDatastore();
	}

	@Test
	public void testInsert1() {
		OptionalFieldsEntity entity = OptionalFieldsEntity.getSample1();
		OptionalFieldsEntity entity2 = em.insert(entity);
		com.google.cloud.datastore.Entity nativeEntity = datastore.get(entity2.getKey().nativeKey());
		OptionalFieldsEntity entity3 = em.load(OptionalFieldsEntity.class, entity2.getId());
		assertTrue(nativeEntity.contains("mandatoryString"));
		assertTrue(nativeEntity.contains("$city"));
		assertTrue(nativeEntity.contains("FIELD2"));
		assertTrue(nativeEntity.contains("city"));
		assertTrue(nativeEntity.contains("field1"));
		assertTrue(nativeEntity.contains("line1"));
		assertTrue(nativeEntity.contains("line2"));
		assertFalse(nativeEntity.contains("optionalBlob"));
		assertFalse(nativeEntity.contains("optionalDate"));
		assertFalse(nativeEntity.contains("optionalInteger"));
		assertFalse(nativeEntity.contains("optionalList"));
		assertFalse(nativeEntity.contains("optionalLong"));
		assertFalse(nativeEntity.contains("optionalMap"));
		assertFalse(nativeEntity.contains("optionalPhone"));
		assertFalse(nativeEntity.contains("optionalSet"));
		assertFalse(nativeEntity.contains("optionalShort"));
		assertFalse(nativeEntity.contains("optionalString"));
		assertTrue(nativeEntity.contains("state"));
		assertTrue(nativeEntity.contains("zip"));
		// Zipx is optional in the embeddable
		assertFalse(nativeEntity.contains("zipx"));

		assertTrue(nativeEntity.contains("faxno"));

		assertTrue(entity.equalsExceptId(entity2));
		assertEquals(entity2, entity3);
	}

	@Test
	public void testInsert2() {
		OptionalFieldsEntity entity = OptionalFieldsEntity.getSample2();
		OptionalFieldsEntity entity2 = em.insert(entity);
		OptionalFieldsEntity entity3 = em.load(OptionalFieldsEntity.class, entity2.getId());
		assertTrue(entity.equalsExceptId(entity2));
		assertEquals(entity2, entity3);
	}

	@Test
	public void testInsert_PhoneList() {
		OptionalFieldsEntity2 entity = OptionalFieldsEntity2.getSample1();
		OptionalFieldsEntity2 entity2 = em.insert(entity);
		OptionalFieldsEntity2 entity3 = em.load(OptionalFieldsEntity2.class, entity2.getId());
		com.google.cloud.datastore.Entity nativeEntity = datastore.get(entity2.getKey().nativeKey());

		List<EntityValue> entityValues = nativeEntity.getList("phoneNumbers");
		assertTrue(entityValues.get(0).get().contains("countryCode"));
		assertTrue(entityValues.get(1).get().contains("countryCode"));
		assertFalse(entityValues.get(2).get().contains("countryCode"));

		assertEquals(entity.getPhoneNumbers(), entity2.getPhoneNumbers());
		assertEquals(entity.getPhoneNumbers(), entity3.getPhoneNumbers());
	}

	@Test
	public void testInsert_SubClass() {
		OptionalSubClass entity = new OptionalSubClass();
		OptionalSubClass entity2 = em.insert(entity);
		OptionalSubClass entity3 = em.load(OptionalSubClass.class, entity2.getId());

		Entity nativeEntity = datastore.get(entity2.getKey().nativeKey());
		assertFalse(nativeEntity.contains("createdBy"));
	}

	@Test
	public void testInsert_OptionalFieldsEntity3() {
		OptionalFieldsEntity3 entity = new OptionalFieldsEntity3();
		entity = em.insert(entity);
		Entity nativeEntity = datastore.get(entity.getKey().nativeKey());
		assertTrue(nativeEntity.contains("ccode"));
		assertFalse(nativeEntity.contains("acode"));
	}

	@Test
	public void testInsert_OptionalVersion() {
		OptionalVersion entity = new OptionalVersion();
		entity = em.insert(entity);
		entity = em.update(entity);
		entity = em.load(OptionalVersion.class, entity.getId());
		assertEquals(1L, entity.getVersion());
	}

	@Test
	public void testInsert_OptionalCreationTimestamp() {
		long now = System.currentTimeMillis();
		OptionalCreatedTimestamp entity = new OptionalCreatedTimestamp();
		entity = em.insert(entity);
		entity = em.load(OptionalCreatedTimestamp.class, entity.getId());
		assertTrue(entity.getCreationTimestamp() >= now);
	}

	@Test
	public void testInsert_OptionalUpdatedTimestamp() {
		long now = System.currentTimeMillis();
		OptionalUpdatedTimestamp entity = new OptionalUpdatedTimestamp();
		entity = em.insert(entity);
		entity = em.load(OptionalUpdatedTimestamp.class, entity.getId());
		assertTrue(entity.getModificationTimestamp().getTime() >= now);
		now = System.currentTimeMillis();
		entity = em.update(entity);
		assertTrue(entity.getModificationTimestamp().getTime() >= now);
	}

	@Test
	public void testInsert_Immutable() {
		ImmutablePerson entity = ImmutablePerson.getSample3();
		ImmutablePerson entity2 = em.insert(entity);
		ImmutablePerson entity3 = em.load(ImmutablePerson.class, entity2.getId());
		com.google.cloud.datastore.Entity nativeEntity = datastore.get(entity2.getKey().nativeKey());
		assertFalse(nativeEntity.contains("fourDigits"));
		assertTrue(entity.equalsExceptAutoGeneratedFields(entity2));
		assertEquals(entity2, entity3);

	}

	@Test
	public void testInsert_Immutable2() {
		ImmutablePerson entity = ImmutablePerson.getSample2();
		ImmutablePerson entity2 = em.insert(entity);
		ImmutablePerson entity3 = em.load(ImmutablePerson.class, entity2.getId());
		com.google.cloud.datastore.Entity nativeEntity = datastore.get(entity2.getKey().nativeKey());
		assertFalse(nativeEntity.contains("fourDigits"));
		assertFalse(nativeEntity.contains("phoneNumber"));
		assertFalse(nativeEntity.contains("otherNumbers"));
		assertEquals(entity2, entity3);

	}

}
