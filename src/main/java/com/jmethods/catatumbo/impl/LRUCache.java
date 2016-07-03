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

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A simple LRU (Least Recently Used) cache that limits the maximum size of
 * cache to the specified capacity. When new entries are placed in the cache,
 * the oldest accessed entry is removed.
 *
 * @author Sai Pullabhotla
 * @param <K>
 *            Key
 * @param <V>
 *            Value
 */
public class LRUCache<K, V> {

	/**
	 * Cache store
	 */
	private LinkedHashMap<K, V> map;

	/**
	 * Maximum capacity
	 */
	private int maxCapacity;

	/**
	 * Creates a new instance of <code>LRUCache</code>.
	 *
	 * @param initialCapacity
	 *            initial capacity
	 * @param maxCapacity
	 *            maximum capacity
	 */
	public LRUCache(final int initialCapacity, int maxCapacity) {
		setMaxCapacity(maxCapacity);
		map = new LinkedHashMap<K, V>(initialCapacity, 0.75f, true) {
			/**
			 * Serial version UID
			 */
			private static final long serialVersionUID = 7959078771128286844L;

			@Override
			protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
				return size() > LRUCache.this.maxCapacity;
			}
		};

	}

	/**
	 * Returns the maximum capacity.
	 *
	 * @return the maximum capacity.
	 */
	public int getMaxCapacity() {
		return maxCapacity;
	}

	/**
	 * Sets the maximum capacity.
	 *
	 * @param maxCapacity
	 *            the maximum capacity
	 */
	public void setMaxCapacity(int maxCapacity) {
		if (maxCapacity < 1) {
			throw new IllegalArgumentException("maxCapacity must not be less than 1");
		}
		this.maxCapacity = maxCapacity;
	}

	/**
	 * Returns the cached object associated the given key.
	 *
	 * @param key
	 *            the key
	 * @return the cached object associated the given key.
	 */
	public synchronized V get(K key) {
		return map.get(key);
	}

	/**
	 * Adds the given key and value to this cache.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return old value associated with the given key, if any.
	 */
	public synchronized V put(K key, V value) {
		return map.put(key, value);
	}

	/**
	 * Checks to see if the given key exists in this cache.
	 *
	 * @param key
	 *            the key
	 * @return true, if the given key exists in this cache; false, otherwise.
	 */
	public synchronized boolean containsKey(K key) {
		return map.containsKey(key);
	}

	/**
	 * Returns the current size of this cache.
	 * 
	 * @return the current size of this cache.
	 */
	public int size() {
		return map.size();
	}

	@Override
	public String toString() {
		return map.toString();
	}
}
