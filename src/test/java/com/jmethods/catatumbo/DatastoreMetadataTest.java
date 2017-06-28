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
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jmethods.catatumbo.entities.StringField;
import com.jmethods.catatumbo.impl.EntityIntrospector;

/**
 * @author Sai Pullabhotla
 *
 */
public class DatastoreMetadataTest {

	private static EntityManager em = null;

	private List<String> createdNamespaces;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		em = TestUtils.getEntityManager();
	}

	@Before
	public void beforeEachTest() {
		createdNamespaces = new ArrayList<>();
	}

	@After
	public void afterEachTest() {
		String backupNamespace = Tenant.getNamespace();
		try {
			for (String namespace : createdNamespaces) {
				Tenant.setNamespace(namespace);
				em.deleteAll(StringField.class);
			}
		} finally {
			Tenant.setNamespace(backupNamespace);
		}
	}

	@Test
	public void testGetNamespaces() {
		long millis = System.currentTimeMillis();
		List<StringField> entities = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			String namespace = String.valueOf(millis + i);
			StringField entity = new StringField();
			entity.setName("I'm in namespace " + namespace);
			entity = createEntity(entity, namespace);
			entities.add(entity);
			createdNamespaces.add(namespace);
		}
		DatastoreMetadata dmd = em.getDatastoreMetadata();
		List<String> namespaces = dmd.getNamespaces();
		assertTrue(namespaces.contains(String.valueOf(millis)));
		assertTrue(namespaces.contains(String.valueOf(millis + 1)));
	}

	@Test
	public void testGetNamespaces_Limit() {
		long millis = System.currentTimeMillis();
		List<StringField> entities = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			String namespace = String.valueOf(millis + i);
			StringField entity = new StringField();
			entity.setName("I'm in namespace " + namespace);
			entity = createEntity(entity, namespace);
			entities.add(entity);
			createdNamespaces.add(namespace);
		}
		DatastoreMetadata dmd = em.getDatastoreMetadata();
		List<String> namespaces = dmd.getNamespaces(2).getResults();
		assertEquals(2, namespaces.size());
	}

	@Test
	public void testGetNamespaces_FromCursor_Limit() {
		long millis = System.currentTimeMillis();
		List<StringField> entities = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			String namespace = String.valueOf(millis + i);
			StringField entity = new StringField();
			entity.setName("I'm in namespace " + namespace);
			entity = createEntity(entity, namespace);
			entities.add(entity);
			createdNamespaces.add(namespace);
		}
		DatastoreMetadata dmd = em.getDatastoreMetadata();
		QueryResponse<String> response = dmd.getNamespaces(2);
		DatastoreCursor nextPageCursor = response.getEndCursor();
		List<String> namespaces = dmd.getNamespaces(nextPageCursor, 2).getResults();
		assertEquals(2, namespaces.size());
	}

	@Test
	public void testGetKinds() {
		StringField entity = new StringField();
		entity.setName("Test for getKinds()");
		entity = em.insert(entity);
		DatastoreMetadata dmd = em.getDatastoreMetadata();
		List<String> kinds = dmd.getKinds();
		assertTrue(kinds.contains(EntityIntrospector.introspect(StringField.class).getKind()));
	}

	@Test
	public void testGetKinds_ExcludeSystemKinds() {
		StringField entity = new StringField();
		entity.setName("Test for getKinds()");
		entity = em.insert(entity);
		DatastoreMetadata dmd = em.getDatastoreMetadata();
		List<String> kinds = dmd.getKinds(true);
		assertTrue(kinds.contains(EntityIntrospector.introspect(StringField.class).getKind()));
		// This may give false positive (e.g. no Stat entities are yet available
		// or testing with Emulator.
		for (String kind : kinds) {
			assertTrue(!kind.startsWith("__"));
		}
	}

	@Test
	public void testGetProperties() {
		StringField entity = new StringField();
		entity.setName("Test for getProperties()");
		em.insert(entity);
		List<DatastoreProperty> properties = em.getDatastoreMetadata()
				.getProperties(EntityIntrospector.introspect(StringField.class).getKind());
		assertEquals("name", properties.get(0).getName());
	}

	private <E> E createEntity(E entity, String namespace) {
		String backupNamespace = Tenant.getNamespace();
		try {
			Tenant.setNamespace(namespace);
			return em.insert(entity);
		} finally {
			Tenant.setNamespace(backupNamespace);
		}

	}

}
