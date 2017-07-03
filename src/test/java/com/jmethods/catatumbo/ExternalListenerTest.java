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

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jmethods.catatumbo.entities.ExternalCalculatorEntity;

/**
 * @author Sai Pullabhotla
 *
 */
public class ExternalListenerTest {

	private static EntityManager em;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		em = TestUtils.getEntityManager();
	}

	@Test
	public void testPreAndPostInsert() {
		ExternalCalculatorEntity entity = new ExternalCalculatorEntity(7, 5);
		entity = em.insert(entity);
		assertTrue(entity.getSum() == 12);
		assertTrue(entity.getSum2() == 24);
	}

	@Test
	public void testPreAndPostUpdate() {
		ExternalCalculatorEntity entity = new ExternalCalculatorEntity();
		entity = em.insert(entity);
		entity.setOperand1(9);
		entity.setOperand2(2);
		entity = em.update(entity);
		assertTrue(entity.getSum() == 11);
		assertTrue(entity.getSum2() == 33);
	}

	@Test
	public void testPreAndPostUpsert() {
		ExternalCalculatorEntity entity = new ExternalCalculatorEntity(8, 8);
		entity = em.upsert(entity);
		assertTrue(entity.getSum() == 16);
		assertTrue(entity.getSum2() == 64);
	}

	@Test
	public void testPreAndPostDelete() {
		ExternalCalculatorEntity entity = new ExternalCalculatorEntity();
		entity = em.insert(entity);
		entity.setOperand1(1);
		entity.setOperand2(2);
		em.delete(entity);
		assertTrue(entity.getSum() == 3);
		assertTrue(entity.getSum2() == 15);
	}

	@Test
	public void testPostLoad() {
		ExternalCalculatorEntity entity = new ExternalCalculatorEntity(6, 9);
		entity = em.insert(entity);
		entity = em.load(ExternalCalculatorEntity.class, entity.getId());
		assertTrue(entity.getSum() == 15);
		assertTrue(entity.getSum2() == 90);
	}

}
