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
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jmethods.catatumbo.entities.Address;
import com.jmethods.catatumbo.entities.EmbeddedListIndex;
import com.jmethods.catatumbo.entities.ParentEntity;
import com.jmethods.catatumbo.entities.StringIndex;
import com.jmethods.catatumbo.entities.StringIndex2;
import com.jmethods.catatumbo.entities.StringListIndex;
import com.jmethods.catatumbo.entities.StringListIndex2;
import com.jmethods.catatumbo.entities.StringSetIndex;
import com.jmethods.catatumbo.entities.StringSetIndex2;

/**
 * @author Sai Pullabhotla
 *
 */
public class SecondaryIndexTest {

	private static EntityManager em;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		em = TestUtils.getEntityManager();
		em.deleteAll(StringIndex.class);
		em.deleteAll(StringListIndex.class);
		em.deleteAll(StringSetIndex.class);
		em.deleteAll(EmbeddedListIndex.class);
	}

	@Test
	public void testInsertStringIndex() {
		StringIndex entity = StringIndex.getSample1();
		entity = em.insert(entity);
		StringIndex loadedEntity = em.load(StringIndex.class, entity.getId());
		StringIndex2 entity2 = em.load(StringIndex2.class, entity.getId());
		assertEquals(entity, loadedEntity);
		assertEquals(entity.getFirstName(), entity2.getFirstName());
		assertEquals(entity.getLastName(), entity2.getLastName());
		assertEquals(entity.getEmail(), entity2.getEmail());
		assertEquals(entity2.getFirstNameIndex(), entity.getFirstName().toLowerCase(Locale.ENGLISH));
		assertEquals(entity2.getLastNameIndex(), entity.getLastName().toUpperCase(Locale.ENGLISH));
		assertEquals(entity2.getEmailIndex(), entity.getEmail().toLowerCase(Locale.ENGLISH));
	}

	@Test
	public void testInsertStringIndex_Null() {
		StringIndex entity = new StringIndex();
		entity = em.insert(entity);
		StringIndex loadedEntity = em.load(StringIndex.class, entity.getId());
		StringIndex2 entity2 = em.load(StringIndex2.class, entity.getId());
		assertEquals(entity, loadedEntity);
		assertEquals(entity.getFirstName(), entity2.getFirstName());
		assertEquals(entity.getLastName(), entity2.getLastName());
		assertEquals(entity.getEmail(), entity2.getEmail());
		assertNull(entity2.getFirstNameIndex());
		assertNull(entity2.getLastNameIndex());
		assertNull(entity2.getEmailIndex());
	}

	@Test
	public void testInsertStringListIndex() {
		StringListIndex entity = StringListIndex.getSample1();
		entity = em.insert(entity);
		StringListIndex loadedEntity = em.load(StringListIndex.class, entity.getId());
		StringListIndex2 entity2 = em.load(StringListIndex2.class, entity.getId());
		assertEquals(entity, loadedEntity);
		assertEquals(entity.getColors().get(0).toLowerCase(Locale.ENGLISH), entity2.getColorsIndex().get(0));
		assertEquals(entity.getColors().get(1).toLowerCase(Locale.ENGLISH), entity2.getColorsIndex().get(1));
		assertEquals(entity.getColors().get(2).toLowerCase(Locale.ENGLISH), entity2.getColorsIndex().get(2));
		assertEquals(entity.getSizes().get(0).toUpperCase(Locale.ENGLISH), entity2.getSizesIndex().get(0));
		assertEquals(entity.getSizes().get(1).toUpperCase(Locale.ENGLISH), entity2.getSizesIndex().get(1));
		assertEquals(entity.getSizes().get(2).toUpperCase(Locale.ENGLISH), entity2.getSizesIndex().get(2));
		assertEquals(entity.getSizes().get(3), entity2.getSizesIndex().get(3));
	}

	@Test
	public void testInsertStringSetIndex() {
		StringSetIndex entity = StringSetIndex.getSample1();
		entity = em.insert(entity);
		StringSetIndex loadedEntity = em.load(StringSetIndex.class, entity.getId());
		StringSetIndex2 entity2 = em.load(StringSetIndex2.class, entity.getId());
		assertEquals(entity, loadedEntity);
		Iterator<String> colors1 = entity.getColors().iterator();
		Iterator<String> colors2 = entity2.getColorsIndex().iterator();
		Iterator<String> sizes1 = entity.getSizes().iterator();
		Iterator<String> sizes2 = entity2.getSizesIndex().iterator();
		assertEquals(colors1.next().toLowerCase(Locale.ENGLISH), colors2.next());
		assertEquals(colors1.next().toLowerCase(Locale.ENGLISH), colors2.next());
		assertEquals(colors1.next().toLowerCase(Locale.ENGLISH), colors2.next());
		assertEquals(sizes1.next().toUpperCase(Locale.ENGLISH), sizes2.next());
		assertEquals(sizes1.next().toUpperCase(Locale.ENGLISH), sizes2.next());
		assertEquals(sizes1.next().toUpperCase(Locale.ENGLISH), sizes2.next());
		assertEquals(sizes1.next(), sizes2.next());
	}

	@Test
	public void testInsertEmbeddedListIndex() {
		ParentEntity parent = new ParentEntity();
		parent.setField1("SecondaryIndex Test");
		parent = em.insert(parent);
		EmbeddedListIndex entity = new EmbeddedListIndex();
		entity.setParentKey(parent.getKey());
		List<Address> addresses = new ArrayList<>();
		addresses.add(Address.getSample1());
		addresses.add(Address.getSample2());
		addresses.add(Address.getSample3());
		entity.setAddresses(addresses);
		entity = em.insert(entity);
		String query = "SELECT * FROM EmbeddedListIndex WHERE __key__ HAS ANCESTOR @1 AND addresses.$city=@2";
		EntityQueryRequest request = em.createEntityQueryRequest(query);
		request.addPositionalBinding(parent.getKey());
		request.addPositionalBinding("lincoln");
		QueryResponse<EmbeddedListIndex> response = em.executeEntityQueryRequest(EmbeddedListIndex.class, request);
		List<EmbeddedListIndex> entities = response.getResults();
		assertEquals(1, entities.size());
	}

}
