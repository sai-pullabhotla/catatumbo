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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.jmethods.catatumbo.entities.Account;

/**
 * @author Sai Pullabhotla
 *
 */
public class DatastoreTransactionTest {

	private static EntityManager em = null;

	private static Datastore datastore = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String jsonCredentialsFile = System.getenv("jsonCredentialsFile");
		String projectId = System.getenv("projectId");
		String namespace = System.getenv("namespace");
		if (jsonCredentialsFile == null) {
			em = EntityManagerFactory.getInstance().createDefaultEntityManager(namespace);
		} else {
			em = EntityManagerFactory.getInstance().createEntityManager(projectId, jsonCredentialsFile, namespace);
		}
		System.out.println(
				String.format("Running tests against project %s with credentials from file %s using namespace %s",
						projectId, jsonCredentialsFile, namespace));

		datastore = DatastoreOptions.defaultInstance().service();
		em.deleteAll(Account.class);

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

}
