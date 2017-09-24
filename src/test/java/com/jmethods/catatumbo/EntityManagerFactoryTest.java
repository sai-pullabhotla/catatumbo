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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.jmethods.catatumbo.impl.DefaultEntityManager;

/**
 * @author Sai Pullabhotla
 *
 */
public class EntityManagerFactoryTest {

	/**
	 * This Test requires default project/auth set up using gcloud.
	 */
	@Test
	public void testCreateDefaultEntityManager() {
		if (TestUtils.isCI()) {
			// TODO
			return;
		}
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		EntityManager em = emf.createDefaultEntityManager();
		DefaultEntityManager dem = (DefaultEntityManager) em;
		Datastore ds = dem.getDatastore();
		assertTrue(ds.getOptions().getProjectId() != null && ds.getOptions().getProjectId().length() != 0
				&& ds.getOptions().getNamespace().equals(""));
	}

	/**
	 * This Test requires default project/auth set up using gcloud.
	 */
	@Test
	public void testCreateDefaultEntityManager_Namespace() {
		if (TestUtils.isCI()) {
			// TODO
			return;
		}
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		EntityManager em = emf.createDefaultEntityManager("junit");
		DefaultEntityManager dem = (DefaultEntityManager) em;
		Datastore ds = dem.getDatastore();
		assertTrue(ds.getOptions().getProjectId() != null && ds.getOptions().getProjectId().length() != 0
				&& ds.getOptions().getNamespace().equals("junit"));
	}

	@Test(expected = EntityManagerFactoryException.class)
	public void testCreateEntityManager_BadFilePath() {
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		try {
			emf.createEntityManager("my-project", "temp.json");
		} catch (Exception exp) {
			System.out.println(exp);
			throw exp;
		}
	}

	@Test
	public void testCreateEntityManager_GoodFilePath() {
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		String projectId = System.getenv(TestUtils.ENV_PROJECT_ID);
		String jsonFile = System.getenv(TestUtils.ENV_CREDENTIALS);
		if (jsonFile == null) {
			System.out.printf("Enviornment variable %s is not set, skipping the test case%n",
					TestUtils.ENV_CREDENTIALS);
			return;
		}
		EntityManager em = emf.createEntityManager(projectId, jsonFile);
		DefaultEntityManager dem = (DefaultEntityManager) em;
		Datastore ds = dem.getDatastore();
		assertTrue(ds.getOptions().getProjectId() != null && ds.getOptions().getProjectId().length() != 0
				&& ds.getOptions().getNamespace().equals(""));
	}

	@Test(expected = EntityManagerFactoryException.class)
	public void testCreateEntityManager_CorruptFilePath() {
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		File tempFile = null;
		try {
			tempFile = File.createTempFile("credentials", ".json");
		} catch (IOException exp) {
			exp.printStackTrace();
			System.out.println("Skipping test because temp file creation filed");
			return;
		}
		try {
			String projectId = System.getenv(TestUtils.ENV_PROJECT_ID);
			EntityManager em = emf.createEntityManager(projectId, tempFile);
			DefaultEntityManager dem = (DefaultEntityManager) em;
		} catch (Exception exp) {
			System.out.println(exp);
			exp.printStackTrace();
			throw exp;
		} finally {
			if (tempFile != null) {
				tempFile.delete();
			}
		}
	}

	@Test
	public void testCreateEntityManager_Namespace() {
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		try {
			String projectId = System.getenv(TestUtils.ENV_PROJECT_ID);
			String jsonFile = System.getenv(TestUtils.ENV_CREDENTIALS);
			if (jsonFile == null) {
				System.out.printf("Enviornment variable %s is not set, skipping the test case%n",
						TestUtils.ENV_CREDENTIALS);
				return;
			}
			EntityManager em = emf.createEntityManager(projectId, new File(jsonFile), "junit");
			DefaultEntityManager dem = (DefaultEntityManager) em;
			Datastore ds = dem.getDatastore();
			assertTrue(ds.getOptions().getProjectId() != null && ds.getOptions().getProjectId().length() != 0
					&& ds.getOptions().getNamespace().equals("junit"));
		} catch (Exception exp) {
			System.out.println(exp);
			throw exp;
		}
	}

	@Test
	public void testCreateLocalEntityManager1() {
		try {
			EntityManagerFactory emf = EntityManagerFactory.getInstance();
			EntityManager em = emf.createLocalEntityManager("localhost:9999");
			DefaultEntityManager dem = (DefaultEntityManager) em;
			DatastoreOptions options = dem.getDatastore().getOptions();
			assertEquals("localhost:9999", options.getHost());
			assertNotNull(options.getProjectId());
			assertEquals("", options.getNamespace());
		} catch (EntityManagerFactoryException exp) {
			if (!TestUtils.isCI()) {
				throw exp;
			}
		}
	}

	@Test
	public void testCreateLocalEntityManager2() {
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		EntityManager em = emf.createLocalEntityManager("localhost:9999", "cool-project");
		DefaultEntityManager dem = (DefaultEntityManager) em;
		DatastoreOptions options = dem.getDatastore().getOptions();
		assertEquals("localhost:9999", options.getHost());
		assertEquals("cool-project", options.getProjectId());
		assertEquals("", options.getNamespace());
	}

	@Test
	public void testCreateLocalEntityManager3() {
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		EntityManager em = emf.createLocalEntityManager("localhost:9999", "cool-project", "scret-namespace");
		DefaultEntityManager dem = (DefaultEntityManager) em;
		DatastoreOptions options = dem.getDatastore().getOptions();
		assertEquals("localhost:9999", options.getHost());
		assertEquals("cool-project", options.getProjectId());
		assertEquals("scret-namespace", options.getNamespace());
	}

	@Test
	public void testCreateEntityManager_ConnectionParameters1() {
		if (TestUtils.isCI()) {
			// TODO
			return;
		}
		ConnectionParameters parameters = new ConnectionParameters();
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		DefaultEntityManager em = (DefaultEntityManager) emf.createEntityManager(parameters);
		DatastoreOptions options = em.getDatastore().getOptions();
		assertEquals(ConnectionParameters.DEFAULT_SERVICE_URL, options.getHost());
		assertNotNull(options.getProjectId());
		assertTrue(options.getProjectId().length() > 0);

	}

	@Test
	public void testCreateEntityManager_ConnectionParameters2() {
		ConnectionParameters parameters = new ConnectionParameters();
		final String serviceURL = "http://localhost:9999";
		parameters.setServiceURL(serviceURL);
		parameters.setProjectId("my-project");
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		DefaultEntityManager em = (DefaultEntityManager) emf.createEntityManager(parameters);
		DatastoreOptions options = em.getDatastore().getOptions();
		assertEquals(serviceURL, options.getHost());
		assertNotNull(options.getProjectId());
		assertTrue(options.getProjectId().length() > 0);
		assertNotNull(options.getCredentials());
	}

	@Test
	public void testCreateEntityManager_ConnectionParameters3() {
		ConnectionParameters parameters = new ConnectionParameters();
		final String serviceURL = "http://localhost:9999";
		final String projectId = "my-project";
		final String namespace = "my-namespace";
		parameters.setServiceURL(serviceURL);
		parameters.setProjectId(projectId);
		parameters.setNamespace(namespace);
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		DefaultEntityManager em = (DefaultEntityManager) emf.createEntityManager(parameters);
		DatastoreOptions options = em.getDatastore().getOptions();
		assertEquals(serviceURL, options.getHost());
		assertNotNull(options.getProjectId());
		assertTrue(options.getProjectId().length() > 0);
		assertNotNull(options.getCredentials());
		assertEquals(projectId, options.getProjectId());
		assertEquals(namespace, options.getNamespace());
	}

	@Test
	public void testCreateEntityManager_ConnectionParameters4() {
		if (TestUtils.isCI()) {
			// TODO
			return;
		}
		ConnectionParameters parameters = new ConnectionParameters();
		final String projectId = "my-project";
		final String namespace = "my-namespace";
		parameters.setProjectId(projectId);
		parameters.setNamespace(namespace);
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		DefaultEntityManager em = (DefaultEntityManager) emf.createEntityManager(parameters);
		DatastoreOptions options = em.getDatastore().getOptions();
		assertEquals(ConnectionParameters.DEFAULT_SERVICE_URL, options.getHost());
		assertNotNull(options.getProjectId());
		assertTrue(options.getProjectId().length() > 0);
		assertNotNull(options.getCredentials());
		assertEquals(projectId, options.getProjectId());
		assertEquals(namespace, options.getNamespace());
	}

	@Test
	public void testCreateEntityManager_ConnectionParameters5() {
		ConnectionParameters parameters = new ConnectionParameters();
		final String projectId = "my-project";
		final String namespace = "my-namespace";
		final String credentialsFile = System.getenv(TestUtils.ENV_CREDENTIALS);
		if (Utility.isNullOrEmpty(credentialsFile)) {
			System.out.printf("Enviornment variable %s is not set, skipping the test case%n",
					TestUtils.ENV_CREDENTIALS);
			return;
		}
		parameters.setProjectId(projectId);
		parameters.setNamespace(namespace);
		parameters.setJsonCredentialsFile(credentialsFile);
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		DefaultEntityManager em = (DefaultEntityManager) emf.createEntityManager(parameters);
		DatastoreOptions options = em.getDatastore().getOptions();
		assertEquals(ConnectionParameters.DEFAULT_SERVICE_URL, options.getHost());
		assertNotNull(options.getProjectId());
		assertTrue(options.getProjectId().length() > 0);
		assertEquals(ServiceAccountCredentials.class, options.getCredentials().getClass());
		assertEquals(projectId, options.getProjectId());
		assertEquals(namespace, options.getNamespace());
	}

	@Test
	public void testCreateEntityManager_ConnectionParameters6() throws FileNotFoundException {
		ConnectionParameters parameters = new ConnectionParameters();
		final String projectId = "my-project";
		final String namespace = "my-namespace";
		final String credentialsFile = System.getenv(TestUtils.ENV_CREDENTIALS);
		if (Utility.isNullOrEmpty(credentialsFile)) {
			System.out.printf("Enviornment variable %s is not set, skipping the test case%n",
					TestUtils.ENV_CREDENTIALS);
			return;
		}
		parameters.setProjectId(projectId);
		parameters.setNamespace(namespace);
		parameters.setJsonCredentialsStream(new FileInputStream(credentialsFile));
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		DefaultEntityManager em = (DefaultEntityManager) emf.createEntityManager(parameters);
		DatastoreOptions options = em.getDatastore().getOptions();
		assertEquals(ConnectionParameters.DEFAULT_SERVICE_URL, options.getHost());
		assertNotNull(options.getProjectId());
		assertTrue(options.getProjectId().length() > 0);
		assertEquals(ServiceAccountCredentials.class, options.getCredentials().getClass());
		assertEquals(projectId, options.getProjectId());
		assertEquals(namespace, options.getNamespace());
	}

	@Test
	public void testCreateEntityManager_ConnectionParameters7() throws FileNotFoundException {
		ConnectionParameters parameters = new ConnectionParameters();
		final String projectId = "my-project";
		final String namespace = "my-namespace";
		final String credentialsFile = System.getenv(TestUtils.ENV_CREDENTIALS);
		if (Utility.isNullOrEmpty(credentialsFile)) {
			System.out.printf("Enviornment variable %s is not set, skipping the test case%n",
					TestUtils.ENV_CREDENTIALS);
			return;
		}
		parameters.setProjectId(projectId);
		parameters.setNamespace(namespace);
		parameters.setJsonCredentialsStream(new FileInputStream(credentialsFile));
		parameters.setJsonCredentialsFile("nonexistentfile.json");
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		DefaultEntityManager em = (DefaultEntityManager) emf.createEntityManager(parameters);
		DatastoreOptions options = em.getDatastore().getOptions();
		assertEquals(ConnectionParameters.DEFAULT_SERVICE_URL, options.getHost());
		assertNotNull(options.getProjectId());
		assertTrue(options.getProjectId().length() > 0);
		assertEquals(ServiceAccountCredentials.class, options.getCredentials().getClass());
		assertEquals(projectId, options.getProjectId());
		assertEquals(namespace, options.getNamespace());
	}

}
