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

package com.jmethods.catatumbo;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jmethods.catatumbo.entities.ChildEntity;
import com.jmethods.catatumbo.entities.LongId;
import com.jmethods.catatumbo.entities.OptimisticLock1;
import com.jmethods.catatumbo.entities.ParentEntity;
import com.jmethods.catatumbo.entities.StringId;

/**
 * @author Sai Pullabhotla
 *
 */
public class DatastoreBatchTest {

	private static EntityManager em = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		em = TestUtils.getEntityManager();
		em.deleteAll(LongId.class);
		em.deleteAll(StringId.class);
		em.deleteAll(ParentEntity.class);
		em.deleteAll(ChildEntity.class);

	}

	@Test
	public void testInsert_LongId() {
		DatastoreBatch batch = em.newBatch();
		LongId entity = new LongId();
		entity.setField1("Batch Insert Test");
		LongId entity2 = batch.insert(entity);
		batch.submit();
		LongId entity3 = em.load(LongId.class, entity2.getId());
		assertTrue(entity2.equals(entity3));
	}

	@Test
	public void testInsert_LongId_List() {
		DatastoreBatch batch = em.newBatch();
		List<LongId> entities = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			LongId entity = new LongId();
			entity.setField1("Batch Insert Test " + i);
			entities.add(entity);
		}
		List<LongId> insertedEntities = batch.insert(entities);
		batch.submit();
		List<Long> identifiers = new ArrayList<>();
		for (LongId entity : insertedEntities) {
			identifiers.add(entity.getId());
		}
		List<LongId> retrievedEntities = em.loadById(LongId.class, identifiers);
		assertTrue(insertedEntities.equals(retrievedEntities));
	}

	@Test
	public void testInsertWithDeferredIdAllocation_LongId() {
		DatastoreBatch batch = em.newBatch();
		LongId entity = new LongId();
		entity.setField1("Batch Insert Test with Deferred ID Allocation");
		batch.insertWithDeferredIdAllocation(entity);
		DatastoreBatch.Response response = batch.submit();
		// Leaving this call to the deprecated method
		assertTrue(response.getGeneratedKeys().size() == 1);
	}

	@Test
	public void testInsertWithDeferredIdAllocation_LongId_List() {
		DatastoreBatch batch = em.newBatch();
		List<LongId> entities = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			LongId entity = new LongId();
			entity.setField1("Batch Insert Test with Deferred ID Allocation " + i);
			entities.add(entity);
		}
		batch.insertWithDeferredIdAllocation(entities);
		DatastoreBatch.Response response = batch.submit();
		assertTrue(response.getGeneratedKeys().size() == 5);
	}

	@Test(expected = EntityManagerException.class)
	public void testInsertWithDeferredIdAllocation_StringId() {
		DatastoreBatch batch = em.newBatch();
		StringId entity = new StringId();
		entity.setGreetings("Batch Insert Test with Deferred String ID Allocation");
		batch.insertWithDeferredIdAllocation(entity);
	}

	@Test(expected = EntityManagerException.class)
	public void testInsertWithDeferredIdAllocation_StringId_List() {
		DatastoreBatch batch = em.newBatch();
		List<StringId> entities = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			StringId entity = new StringId();
			entities.add(entity);
		}
		batch.insertWithDeferredIdAllocation(entities);
	}

	@Test
	public void testUpdate() {
		LongId entity = new LongId();
		entity.setField1("Test for Batch Update");
		entity = em.insert(entity);
		entity = em.load(LongId.class, entity.getId());
		entity.setField1("Updated");
		DatastoreBatch batch = em.newBatch();
		entity = batch.update(entity);
		batch.submit();
		LongId entity2 = em.load(LongId.class, entity.getId());
		assertTrue("Updated".equals(entity2.getField1()));
	}

	@Test
	public void testUpdate_List() {
		List<LongId> entities = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			entities.add(new LongId());
		}
		entities = em.insert(entities);
		for (LongId entity : entities) {
			entity.setField1("Updated");
		}
		DatastoreBatch batch = em.newBatch();
		entities = batch.update(entities);
		batch.submit();
		List<Long> identifiers = new ArrayList<>();
		for (LongId entity : entities) {
			identifiers.add(entity.getId());
		}
		List<LongId> entities2 = em.loadById(LongId.class, identifiers);
		assertTrue(entities.equals(entities2));
	}

	@Test
	public void testUpdate_OptimisticLock() {
		DatastoreBatch batch = em.newBatch();
		OptimisticLock1 entity = new OptimisticLock1();
		entity.setName("Batch Update Test");
		entity = em.insert(entity);
		entity = em.load(OptimisticLock1.class, entity.getId());
		entity.setName("Batch Update Test - Updated");
		entity = batch.update(entity);
		batch.submit();
		entity = em.load(OptimisticLock1.class, entity.getId());
		assertTrue(entity.getVersion() == 1);
	}

	@Test
	public void testUpsert_LongId() {
		DatastoreBatch batch = em.newBatch();
		LongId entity = new LongId();
		entity.setField1("Batch Upsert Test");
		LongId entity2 = batch.upsert(entity);
		batch.submit();
		LongId entity3 = em.load(LongId.class, entity2.getId());
		assertTrue(entity2.equals(entity3));
	}

	@Test
	public void testUpsert_LongId_List() {
		DatastoreBatch batch = em.newBatch();
		List<LongId> entities = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			LongId entity = new LongId();
			entity.setField1("Batch Upsert Test " + i);
			entities.add(entity);
		}
		List<LongId> insertedEntities = batch.upsert(entities);
		batch.submit();
		List<Long> identifiers = new ArrayList<>();
		for (LongId entity : insertedEntities) {
			identifiers.add(entity.getId());
		}
		List<LongId> retrievedEntities = em.loadById(LongId.class, identifiers);
		assertTrue(insertedEntities.equals(retrievedEntities));
	}

	@Test
	public void testUpsertWithDeferredIdAllocation_LongId() {
		DatastoreBatch batch = em.newBatch();
		LongId entity = new LongId();
		entity.setField1("Batch Upsert Test with Deferred ID Allocation");
		batch.upsertWithDeferredIdAllocation(entity);
		DatastoreBatch.Response response = batch.submit();
		assertTrue(response.getGeneratedKeys().size() == 1);
	}

	@Test
	public void testUpsertWithDeferredIdAllocation_LongId_List() {
		DatastoreBatch batch = em.newBatch();
		List<LongId> entities = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			LongId entity = new LongId();
			entity.setField1("Batch Upsert Test with Deferred ID Allocation " + i);
			entities.add(entity);
		}
		batch.upsertWithDeferredIdAllocation(entities);
		DatastoreBatch.Response response = batch.submit();
		assertTrue(response.getGeneratedKeys().size() == 5);
	}

	@Test(expected = EntityManagerException.class)
	public void testUpsertWithDeferredIdAllocation_StringId() {
		DatastoreBatch batch = em.newBatch();
		StringId entity = new StringId();
		entity.setGreetings("Batch Insert Test with Deferred String ID Allocation");
		batch.upsertWithDeferredIdAllocation(entity);
	}

	@Test(expected = EntityManagerException.class)
	public void testUpsertWithDeferredIdAllocation_StringId_List() {
		DatastoreBatch batch = em.newBatch();
		List<StringId> entities = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			StringId entity = new StringId();
			entities.add(entity);
		}
		batch.upsertWithDeferredIdAllocation(entities);
	}

	@Test
	public void testDelete_Object() {
		LongId entity = em.insert(new LongId());
		DatastoreBatch batch = em.newBatch();
		batch.delete(entity);
		batch.submit();
		LongId retrievedEntity = em.load(LongId.class, entity.getId());
		assertNull(retrievedEntity);
	}

	@Test
	public void testDelete_ListOfObjects() {
		List<LongId> entities = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			entities.add(new LongId());
		}
		entities = em.insert(entities);
		List<Long> identifiers = new ArrayList<>();
		for (LongId entity : entities) {
			identifiers.add(entity.getId());
		}
		DatastoreBatch batch = em.newBatch();
		batch.delete(entities);
		batch.submit();
		List<LongId> retrievedEntities = em.loadById(LongId.class, identifiers);
		assertTrue(
				retrievedEntities.get(0) == null && retrievedEntities.get(1) == null && retrievedEntities.get(2) == null
						&& retrievedEntities.get(3) == null && retrievedEntities.get(4) == null);
	}

	@Test
	public void testDeleteByKey() {
		ParentEntity entity = new ParentEntity();
		entity = em.insert(entity);
		DatastoreBatch batch = em.newBatch();
		DatastoreKey key = entity.getKey();
		batch.deleteByKey(key);
		batch.submit();
		ParentEntity retrievedEntity = em.load(ParentEntity.class, entity.getId());
		assertNull(retrievedEntity);
	}

	@Test
	public void testDeleteByKey_List() {
		List<ParentEntity> entities = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			ParentEntity entity = new ParentEntity();
			entities.add(entity);
		}
		entities = em.insert(entities);
		List<DatastoreKey> keys = new ArrayList<>();
		List<Long> identifiers = new ArrayList<>();
		for (ParentEntity entity : entities) {
			keys.add(entity.getKey());
			identifiers.add(entity.getId());
		}
		DatastoreBatch batch = em.newBatch();
		batch.deleteByKey(keys);
		batch.submit();
		List<ParentEntity> retrievedEntities = em.loadById(ParentEntity.class, identifiers);
		assertTrue(retrievedEntities.get(0) == null && retrievedEntities.get(1) == null
				&& retrievedEntities.get(2) == null);
	}

	@Test
	public void testInsertParentChild() {
		DatastoreBatch batch = em.newBatch();
		ParentEntity parent = new ParentEntity();
		parent = batch.insert(parent);
		ChildEntity child = new ChildEntity();
		child.setParentKey(parent.getKey());
		child = batch.insert(child);
		batch.submit();
		ChildEntity child2 = em.load(ChildEntity.class, parent.getKey(), child.getId());
		System.out.println(child2.getKey().getEncoded());
		assertTrue(child2.getId() == child.getId() && child2.getParentKey().equals(parent.getKey()));
	}

	@Test
	public void testInsert_Upsert() {
		LongId entity = new LongId();
		entity.setId(100);
		em.insert(entity);
		DatastoreBatch batch = em.newBatch();
		batch.delete(entity);
		entity.setField1("Updated");
		batch.insert(entity);
		batch.submit();
		entity = em.load(LongId.class, entity.getId());
		assertTrue(entity != null);
	}

	@Test(expected = EntityManagerException.class)
	public void testInactiveBatch() {
		DatastoreBatch batch = em.newBatch();
		LongId entity = new LongId();
		entity.setField1("Batch Insert Test");
		LongId entity2 = batch.insert(entity);
		batch.submit();
		batch.delete(entity2);
	}

}
