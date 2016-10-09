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

package com.jmethods.catatumbo.entities;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class MapFields {
	@Identifier
	private long id;

	private HashMap<String, Object> hashMap;

	private LinkedHashMap<String, Object> linkedHashMap;

	private TreeMap<String, String> treeMap;

	private Map<String, Object> genericMap;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the hashMap
	 */
	public HashMap<String, Object> getHashMap() {
		return hashMap;
	}

	/**
	 * @param hashMap
	 *            the hashMap to set
	 */
	public void setHashMap(HashMap<String, Object> hashMap) {
		this.hashMap = hashMap;
	}

	/**
	 * @return the linkedHashMap
	 */
	public LinkedHashMap<String, Object> getLinkedHashMap() {
		return linkedHashMap;
	}

	/**
	 * @param linkedHashMap
	 *            the linkedHashMap to set
	 */
	public void setLinkedHashMap(LinkedHashMap<String, Object> linkedHashMap) {
		this.linkedHashMap = linkedHashMap;
	}

	/**
	 * @return the treeMap
	 */
	public TreeMap<String, String> getTreeMap() {
		return treeMap;
	}

	/**
	 * @param treeMap
	 *            the treeMap to set
	 */
	public void setTreeMap(TreeMap<String, String> treeMap) {
		this.treeMap = treeMap;
	}

	/**
	 * @return the genericMap
	 */
	public Map<String, Object> getGenericMap() {
		return genericMap;
	}

	/**
	 * @param genericMap
	 *            the genericMap to set
	 */
	public void setGenericMap(Map<String, Object> genericMap) {
		this.genericMap = genericMap;
	}

}
