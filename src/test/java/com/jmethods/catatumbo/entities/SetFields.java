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

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class SetFields {

	@Identifier
	private long id;

	private Set<?> set;

	private HashSet<?> hashSet;

	private TreeSet<String> treeSet;

	private LinkedHashSet<?> linkedHashSet;

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
	 * @return the set
	 */
	public Set<?> getSet() {
		return set;
	}

	/**
	 * @param set
	 *            the set to set
	 */
	public void setSet(Set<?> set) {
		this.set = set;
	}

	/**
	 * @return the hashSet
	 */
	public HashSet<?> getHashSet() {
		return hashSet;
	}

	/**
	 * @param hashSet
	 *            the hashSet to set
	 */
	public void setHashSet(HashSet<?> hashSet) {
		this.hashSet = hashSet;
	}

	/**
	 * @return the treeSet
	 */
	public TreeSet<String> getTreeSet() {
		return treeSet;
	}

	/**
	 * @param treeSet
	 *            the treeSet to set
	 */
	public void setTreeSet(TreeSet<String> treeSet) {
		this.treeSet = treeSet;
	}

	/**
	 * @return the linkedHashSet
	 */
	public LinkedHashSet<?> getLinkedHashSet() {
		return linkedHashSet;
	}

	/**
	 * @param linkedHashSet
	 *            the linkedHashSet to set
	 */
	public void setLinkedHashSet(LinkedHashSet<?> linkedHashSet) {
		this.linkedHashSet = linkedHashSet;
	}

}
