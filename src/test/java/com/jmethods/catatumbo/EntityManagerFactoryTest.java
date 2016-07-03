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

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.cloud.datastore.Datastore;
import com.jmethods.catatumbo.impl.DefaultEntityManager;

/**
 * @author Sai Pullabhotla
 *
 */
public class EntityManagerFactoryTest {

	@Test
	public void testCreateDefaultEntityManager() {
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		EntityManager em = emf.createDefaultEntityManager();
		DefaultEntityManager dem = (DefaultEntityManager) em;
		Datastore ds = dem.getDatastore();
		assertTrue(ds.options().projectId() != null && ds.options().projectId().length() != 0
				&& ds.options().namespace().equals(""));
	}

	@Test
	public void testCreateDefaultEntityManager_Namespace() {
		EntityManagerFactory emf = EntityManagerFactory.getInstance();
		EntityManager em = emf.createDefaultEntityManager("junit");
		DefaultEntityManager dem = (DefaultEntityManager) em;
		Datastore ds = dem.getDatastore();
		assertTrue(ds.options().projectId() != null && ds.options().projectId().length() != 0
				&& ds.options().namespace().equals("junit"));
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
		String projectId = System.getenv("projectId");
		String jsonFile = System.getenv("jsonCredentialsFile");
		if (jsonFile == null) {
			System.out.println("Enviornment variable jsonCredentialsFile is not set, skipping the test case");
			return;
		}
		EntityManager em = emf.createEntityManager(projectId, jsonFile);
		DefaultEntityManager dem = (DefaultEntityManager) em;
		Datastore ds = dem.getDatastore();
		assertTrue(ds.options().projectId() != null && ds.options().projectId().length() != 0
				&& ds.options().namespace().equals(""));
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
			String projectId = System.getenv("projectId");
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
			String projectId = System.getenv("projectId");
			String jsonFile = System.getenv("jsonCredentialsFile");
			if (jsonFile == null) {
				System.out.println("Enviornment variable jsonCredentialsFile is not set, skipping the test case");
				return;
			}
			EntityManager em = emf.createEntityManager(projectId, new File(jsonFile), "junit");
			DefaultEntityManager dem = (DefaultEntityManager) em;
			Datastore ds = dem.getDatastore();
			assertTrue(ds.options().projectId() != null && ds.options().projectId().length() != 0
					&& ds.options().namespace().equals("junit"));
		} catch (Exception exp) {
			System.out.println(exp);
			throw exp;
		}
	}

}
