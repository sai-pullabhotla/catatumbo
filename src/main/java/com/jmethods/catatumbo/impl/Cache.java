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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A simple cache backed by a {@link ConcurrentHashMap}.
 * 
 * @author Sai Pullabhotla
 * @param <K>
 *          Type of keys in the cache
 * @param <V>
 *          Type of values in the cache
 *
 */
public class Cache<K, V> {

  /**
   * A map to store the entries in the cache
   */
  private Map<K, V> map;

  /**
   * Creates a new instance of <code>Cache</code>.
   */
  public Cache() {
    this(16, 0.75f);
  }

  /**
   * Creates a new instance of <code>Cache</code>.
   * 
   * @param initialSize
   *          initial size
   */
  public Cache(int initialSize) {
    this(initialSize, 0.75f);
  }

  /**
   * Creates a new instance of <code>Cache</code>.
   * 
   * @param initialSize
   *          initial size
   * @param loadFactor
   *          load factor
   */
  public Cache(int initialSize, float loadFactor) {
    map = new ConcurrentHashMap<>(initialSize, loadFactor);
  }

  /**
   * Returns the cached value for the given key.
   * 
   * @param key
   *          the key
   * @return the cached value of the given key. Returns <code>null</code>, if the given key does not
   *         exist in this cache.
   */
  public V get(K key) {
    return map.get(key);
  }

  /**
   * Puts the given entry into this cache.
   * 
   * @param key
   *          the key
   * @param value
   *          the value
   * @return previous value, if any, for the given key.
   */
  public V put(K key, V value) {
    return map.put(key, value);
  }

  /**
   * Checks if the give key is in this cache.
   * 
   * @param key
   *          the key
   * @return <code>true</code>, if the given key exists in the cache; <code>false</code>, otherwise.
   */
  public boolean containsKey(K key) {
    return map.containsKey(key);
  }

  /**
   * Returns the size of this cache.
   * 
   * @return the size of this cache.
   */
  public int size() {
    return map.size();
  }

}
