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

package com.jmethods.catatumbo.impl;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Sai Pullabhotla
 *
 */
public class LRUCacheTest {

	/**
	 * Test method for
	 * {@link com.jmethods.catatumbo.impl.LRUCache#getMaxCapacity()}.
	 */
	@Test
	public void testGetMaxCapacity() {
		LRUCache<Integer, Integer> cache = new LRUCache<>(5, 10);
		assertTrue(cache.getMaxCapacity() == 10);
	}

	/**
	 * Test method for
	 * {@link com.jmethods.catatumbo.impl.LRUCache#setMaxCapacity(int)}.
	 */
	@Test
	public void testSetMaxCapacity_Good() {
		LRUCache<String, String> cache = new LRUCache<>(2, 5);
		cache.setMaxCapacity(20);
		assertTrue(cache.getMaxCapacity() == 20);
	}

	/**
	 * Test method for
	 * {@link com.jmethods.catatumbo.impl.LRUCache#setMaxCapacity(int)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetMaxCapacity_Bad() {
		LRUCache<String, String> cache = new LRUCache<>(2, 5);
		cache.setMaxCapacity(0);
	}

	/**
	 * Test method for
	 * {@link com.jmethods.catatumbo.impl.LRUCache#get(java.lang.Object)}.
	 */
	@Test
	public void testGet() {
		LRUCache<Integer, Integer> squaresCache = new LRUCache<>(5, 10);
		for (int i = 1; i <= 5; i++) {
			squaresCache.put(i, i * i);
		}
		assertTrue(squaresCache.get(4) == 16);
	}

	/**
	 * Test method for
	 * {@link com.jmethods.catatumbo.impl.LRUCache#get(java.lang.Object)}.
	 */
	@Test
	public void testGet_2() {
		LRUCache<Integer, Integer> squaresCache = new LRUCache<>(5, 10);
		for (int i = 1; i <= 11; i++) {
			squaresCache.put(i, i * i);
		}
		assertTrue(squaresCache.size() == 10 && squaresCache.get(1) == null && squaresCache.get(11) != null);
	}

	/**
	 * Test method for
	 * {@link com.jmethods.catatumbo.impl.LRUCache#get(java.lang.Object)}.
	 */
	@Test
	public void testGet_3() {
		LRUCache<Integer, Integer> squaresCache = new LRUCache<>(5, 10);
		for (int i = 1; i <= 10; i++) {
			squaresCache.put(i, i * i);
		}
		squaresCache.get(1);
		squaresCache.put(11, 11 * 11);
		assertTrue(squaresCache.size() == 10 && squaresCache.get(1) != null && squaresCache.get(11) != null);
	}

	/**
	 * Test method for
	 * {@link com.jmethods.catatumbo.impl.LRUCache#get(java.lang.Object)}.
	 */
	@Test
	public void testGet_4() {
		LRUCache<Integer, Integer> squaresCache = new LRUCache<>(5, 10);
		for (int i = 1; i <= 10; i++) {
			squaresCache.put(i, i * i);
		}
		squaresCache.setMaxCapacity(20);
		squaresCache.get(1);
		squaresCache.put(11, 11 * 11);
		assertTrue(squaresCache.size() == 11 && squaresCache.get(1) == 1 && squaresCache.get(11) == 121);
	}

	/**
	 * Test method for
	 * {@link com.jmethods.catatumbo.impl.LRUCache#put(java.lang.Object, java.lang.Object)}
	 * .
	 */
	@Test
	public void testPut() {
		LRUCache<Integer, Integer> squaresCache = new LRUCache<>(5, 10);
		squaresCache.put(6, 6 * 6);
		assertTrue(squaresCache.get(6) == 36);
	}

	/**
	 * Test method for
	 * {@link com.jmethods.catatumbo.impl.LRUCache#containsKey(java.lang.Object)}
	 * .
	 */
	@Test
	public void testContainsKey() {
		LRUCache<Integer, Integer> squaresCache = new LRUCache<>(5, 10);
		squaresCache.put(6, 6 * 6);
		assertTrue(squaresCache.containsKey(6));
	}

	/**
	 * Test method for
	 * {@link com.jmethods.catatumbo.impl.LRUCache#containsKey(java.lang.Object)}
	 * .
	 */
	@Test
	public void testContainsKey_2() {
		LRUCache<Integer, Integer> squaresCache = new LRUCache<>(5, 10);
		squaresCache.put(6, 6 * 6);
		assertFalse(squaresCache.containsKey(5));
	}

}
