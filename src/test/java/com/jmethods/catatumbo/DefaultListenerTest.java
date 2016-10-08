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

import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jmethods.catatumbo.entities.GlobalCalculatorEntity;
import com.jmethods.catatumbo.listeners.Adder;

/**
 * @author Sai Pullabhotla
 *
 */
public class DefaultListenerTest {

	private static EntityManager em;
	private static Random random = new Random();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		em = TestUtils.setupEntityManager();
		em.setDefaultListeners(Adder.class);
		em.deleteAll(GlobalCalculatorEntity.class);
	}

	@Test
	public void testPreAndPostInsert() {
		GlobalCalculatorEntity entity = new GlobalCalculatorEntity(7, 5);
		entity = em.insert(entity);
		assertTrue(entity.getSum() == 12);
		assertTrue(entity.getSum2() == 24);
	}

	@Test
	public void testPreAndPostUpdate() {
		GlobalCalculatorEntity entity = new GlobalCalculatorEntity();
		entity = em.insert(entity);
		entity.setOperand1(9);
		entity.setOperand2(2);
		entity = em.update(entity);
		assertTrue(entity.getSum() == 11);
		assertTrue(entity.getSum2() == 33);
	}

	@Test
	public void testPreAndPostUpsert() {
		GlobalCalculatorEntity entity = new GlobalCalculatorEntity(8, 8);
		entity = em.upsert(entity);
		assertTrue(entity.getSum() == 16);
		assertTrue(entity.getSum2() == 64);
	}

	@Test
	public void testPreAndPostDelete() {
		GlobalCalculatorEntity entity = new GlobalCalculatorEntity();
		entity = em.insert(entity);
		entity.setOperand1(1);
		entity.setOperand2(2);
		em.delete(entity);
		assertTrue(entity.getSum() == 3);
		assertTrue(entity.getSum2() == 15);
	}

	@Test
	public void testPostLoad() {
		GlobalCalculatorEntity entity = new GlobalCalculatorEntity(6, 9);
		entity = em.insert(entity);
		entity = em.load(GlobalCalculatorEntity.class, entity.getId());
		assertTrue(entity.getSum() == 15);
		assertTrue(entity.getSum2() == 90);
	}

}
