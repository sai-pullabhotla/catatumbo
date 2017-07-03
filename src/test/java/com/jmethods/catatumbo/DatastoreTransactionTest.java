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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jmethods.catatumbo.entities.Account;
import com.jmethods.catatumbo.entities.LongId;
import com.jmethods.catatumbo.entities.StringId;

/**
 * @author Sai Pullabhotla
 *
 */
public class DatastoreTransactionTest {

	private static EntityManager em = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		em = TestUtils.getEntityManager();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test(expected = EntityManagerException.class)
	public void testUpdate() {
		Account a1 = new Account();
		a1.setName("Transaction Test");
		a1.setEmail("user@example.com");
		a1 = em.insert(a1);

		DatastoreTransaction t1 = em.newTransaction();
		DatastoreTransaction t2 = em.newTransaction();

		try {
			Account account1 = t1.load(Account.class, a1.getId());
			Account account2 = t2.load(Account.class, a1.getId());

			account1.setName("Transaction Test from 1st Transaction");
			account2.setName("Transaction Test from 2nd Transaction");

			t1.update(account1);
			t2.update(account2);
			t1.commit();
			System.out.println("Transaction 1 Committed successfully");
			t2.commit();
			// This should never succeed
			System.out.println("Transaction 2 Committed successfully");
		} catch (Exception exp) {
			System.out.println(exp.getMessage());
			throw exp;
		} finally {
			if (t1.isActive()) {
				t1.rollback();
			}
			if (t2.isActive()) {
				t2.rollback();
			}
		}

	}

	@Test
	public void testInsert_OptimisticLock1() {
		DatastoreTransaction transaction = em.newTransaction();
		try {
			Account entity = new Account();
			entity.setName("Version Test");
			entity.setEmail("user@example.com");
			Account entity2 = transaction.insert(entity);
			transaction.commit();
			assertTrue(entity2.getId() != 0 && entity2.getVersion() == 1);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	@Test
	public void testUpdate_OptimisticLock1() {
		DatastoreTransaction transaction = em.newTransaction();
		try {
			Account entity = new Account();
			entity.setName("Hello World!");
			entity = em.insert(entity);
			entity = transaction.load(Account.class, entity.getId());
			entity.setName("Hello World! After Update!!");
			entity = transaction.update(entity);
			transaction.commit();
			assertTrue(entity.getId() != 0 && entity.getVersion() == 2);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}

	}

	@Test(expected = EntityManagerException.class)
	public void testUpdate_OptimisticLock2() {
		DatastoreTransaction transaction = em.newTransaction();
		try {
			Account entity = new Account();
			entity.setName("Hello World!");
			entity = em.insert(entity);
			Account entity2 = em.load(Account.class, entity.getId());
			entity2.setName("Hello World! After Update!!");
			entity2 = transaction.update(entity2);
			entity2 = transaction.update(entity2);
			entity2 = transaction.update(entity2);
			entity = transaction.update(entity);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	@Test
	public void testUpdate_OptimisticLock3() {
		DatastoreTransaction transaction = em.newTransaction();
		try {
			List<Account> entities = new ArrayList<>();
			for (int i = 0; i < 2; i++) {
				Account entity = new Account();
				entity.setName("Test Insert from Transaction " + i);
				entities.add(entity);
			}
			List<Account> entities2 = transaction.insert(entities);
			transaction.commit();
			assertEquals(1, entities2.get(0).getVersion());
			assertEquals(1, entities2.get(1).getVersion());
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	@Test
	public void testUpdate_OptimisticLock4() {
		List<Account> entities = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			Account entity = new Account();
			entity.setName("Test Update from Transaction " + i);
			entities.add(entity);
		}
		List<Account> entities2 = em.insert(entities);
		DatastoreTransaction transaction = em.newTransaction();
		try {
			List<Account> entities3 = transaction.update(entities2);
			transaction.commit();
			assertEquals(1, entities2.get(0).getVersion());
			assertEquals(1, entities2.get(1).getVersion());
			assertEquals(2, entities3.get(0).getVersion());
			assertEquals(2, entities3.get(1).getVersion());
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	@Test
	public void testInsertWithDeferredIdAllocation_LongId() {
		DatastoreTransaction transaction = em.newTransaction();
		try {
			LongId entity = new LongId();
			entity.setField1("Transaction Insert Test with Deferred ID Allocation");
			transaction.insertWithDeferredIdAllocation(entity);
			DatastoreTransaction.Response response = transaction.commit();
			assertTrue(response.getGeneratedKeys().size() == 1);

		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	@Test
	public void testInsertWithDeferredIdAllocation_LongId_List() {
		DatastoreTransaction transaction = em.newTransaction();
		try {
			List<LongId> entities = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				LongId entity = new LongId();
				entity.setField1("Transaction Insert Test with Deferred ID Allocation " + i);
				entities.add(entity);
			}
			transaction.insertWithDeferredIdAllocation(entities);
			DatastoreTransaction.Response response = transaction.commit();
			// Leaving this call to the deprecated method
			assertTrue(response.getGeneratedKeys().size() == 5);
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

	@Test(expected = EntityManagerException.class)
	public void testInsertWithDeferredIdAllocation_StringId() {
		DatastoreTransaction transaction = em.newTransaction();
		try {
			StringId entity = new StringId();
			transaction.insertWithDeferredIdAllocation(entity);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}

	}

	@Test(expected = EntityManagerException.class)
	public void testInsertWithDeferredIdAllocation_StringId_List() {
		DatastoreTransaction transaction = em.newTransaction();
		try {
			List<StringId> entities = new ArrayList<>();
			for (int i = 0; i < 5; i++) {
				StringId entity = new StringId();
				entities.add(entity);
			}
			transaction.insertWithDeferredIdAllocation(entities);
			transaction.commit();
		} finally {
			if (transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

}
