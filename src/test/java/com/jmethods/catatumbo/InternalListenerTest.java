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

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jmethods.catatumbo.entities.InternalCalculatorEntity;

/**
 * @author Sai Pullabhotla
 *
 */
public class InternalListenerTest {

	private static EntityManager em;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		em = TestUtils.getEntityManager();
		em.deleteAll(InternalCalculatorEntity.class);
	}

	@Test
	public void testPreInsert() {
		InternalCalculatorEntity entity = new InternalCalculatorEntity(7, 5);
		entity = em.insert(entity);
		assertTrue(entity.getSum() == 12);
		assertTrue(entity.getProduct() == 35);
		assertTrue(entity.getDifference() == 2);
	}

	@Test
	public void testPreInsert_List() {
		List<InternalCalculatorEntity> entities = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			InternalCalculatorEntity entity = new InternalCalculatorEntity(i + 2, i + 4);
			entities.add(entity);
		}
		entities = em.insert(entities);
		assertTrue(entities.get(0).getSum() == 6);
		assertTrue(entities.get(0).getProduct() == 8);
		assertTrue(entities.get(1).getSum() == 8);
		assertTrue(entities.get(1).getProduct() == 15);
		assertTrue(entities.get(0).getDifference() == -2);
		assertTrue(entities.get(1).getDifference() == -2);
	}

	@Test
	public void testPreUpdate() {
		InternalCalculatorEntity entity = new InternalCalculatorEntity();
		entity = em.insert(entity);
		entity.setOperand1(9);
		entity.setOperand2(2);
		entity = em.update(entity);
		assertTrue(entity.getSum() == 11);
		assertTrue(entity.getProduct() == 18);
		assertTrue(entity.getDifference() == 7);
	}

	@Test
	public void testPreUpdate_List() {
		List<InternalCalculatorEntity> entities = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			InternalCalculatorEntity entity = new InternalCalculatorEntity(i, i + 1);
			entities.add(entity);
		}
		entities = em.insert(entities);
		entities = em.update(entities);
		assertTrue(entities.get(0).getSum() == 1);
		assertTrue(entities.get(0).getProduct() == 0);
		assertTrue(entities.get(1).getSum() == 3);
		assertTrue(entities.get(1).getProduct() == 2);
		assertTrue(entities.get(0).getDifference() == -1);
		assertTrue(entities.get(1).getDifference() == -1);
	}

	@Test
	public void testPreUpsert() {
		InternalCalculatorEntity entity = new InternalCalculatorEntity(8, 8);
		entity = em.upsert(entity);
		assertTrue(entity.getSum() == 16);
		assertTrue(entity.getProduct() == 64);
		assertTrue(entity.getDifference() == 0);
	}

	@Test
	public void testPreUpsert_List() {
		List<InternalCalculatorEntity> entities = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			InternalCalculatorEntity entity = new InternalCalculatorEntity(i + 6, i + 2);
			entities.add(entity);
		}
		entities = em.upsert(entities);
		assertTrue(entities.get(0).getSum() == 8);
		assertTrue(entities.get(0).getProduct() == 12);
		assertTrue(entities.get(1).getSum() == 10);
		assertTrue(entities.get(1).getProduct() == 21);
		assertTrue(entities.get(0).getDifference() == 4);
		assertTrue(entities.get(1).getDifference() == 4);
	}

	@Test
	public void testPreDelete() {
		InternalCalculatorEntity entity = new InternalCalculatorEntity();
		entity = em.insert(entity);
		entity.setOperand1(1);
		entity.setOperand2(2);
		em.delete(entity);
		assertTrue(entity.getSum() == 3);
		assertTrue(entity.getProduct() == 2);
		assertTrue(entity.getDifference() == -1);
	}

	@Test
	public void testPreDelete_List() {
		List<InternalCalculatorEntity> entities = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			InternalCalculatorEntity entity = new InternalCalculatorEntity(i + 1, i + 1);
			entities.add(entity);
		}
		entities = em.insert(entities);
		em.delete(entities);
		assertTrue(entities.get(0).getSum() == 2);
		assertTrue(entities.get(0).getProduct() == 1);
		assertTrue(entities.get(1).getSum() == 4);
		assertTrue(entities.get(1).getProduct() == 4);
		assertTrue(entities.get(0).getDifference() == 0);
		assertTrue(entities.get(1).getDifference() == 0);
	}

	@Test
	public void testPostLoad() {
		InternalCalculatorEntity entity = new InternalCalculatorEntity(6, 9);
		entity = em.insert(entity);
		entity = em.load(InternalCalculatorEntity.class, entity.getId());
		assertTrue(entity.getSum() == 15);
		assertTrue(entity.getProduct() == 54);
		assertTrue(entity.getDifference() == -3);
	}

	@Test
	public void testPostLoad_List() {
		List<InternalCalculatorEntity> entities = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			InternalCalculatorEntity entity = new InternalCalculatorEntity(i + 5, i + 6);
			entities.add(entity);
		}
		entities = em.insert(entities);
		List<Long> ids = new ArrayList<>();
		for (InternalCalculatorEntity entity : entities) {
			ids.add(entity.getId());
		}
		entities = em.loadById(InternalCalculatorEntity.class, ids);
		assertTrue(entities.get(0).getSum() == 11);
		assertTrue(entities.get(0).getProduct() == 30);
		assertTrue(entities.get(1).getSum() == 13);
		assertTrue(entities.get(1).getProduct() == 42);
		assertTrue(entities.get(0).getDifference() == -1);
		assertTrue(entities.get(1).getDifference() == -1);
	}

	@Test
	public void testPostLoad_Query() {
		em.deleteAll(InternalCalculatorEntity.class);
		InternalCalculatorEntity entity = new InternalCalculatorEntity(8, 9);
		entity = em.insert(entity);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		EntityQueryRequest request = em.createEntityQueryRequest(
				String.format("SELECT * FROM %s LIMIT 1", InternalCalculatorEntity.class.getSimpleName()));
		request.setAllowLiterals(true);
		QueryResponse<InternalCalculatorEntity> response = em.executeEntityQueryRequest(InternalCalculatorEntity.class,
				request);
		List<InternalCalculatorEntity> entities = response.getResults();
		assertTrue(entities.get(0).getSum() == 17);
		assertTrue(entities.get(0).getProduct() == 72);
		assertTrue(entities.get(0).getDifference() == -1);
	}

}
