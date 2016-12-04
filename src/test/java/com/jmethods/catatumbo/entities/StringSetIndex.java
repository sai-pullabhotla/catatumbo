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

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Objects;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.PropertyIndexer;
import com.jmethods.catatumbo.SecondaryIndex;
import com.jmethods.catatumbo.indexers.UpperCaseStringListIndexer;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class StringSetIndex {

	@Identifier
	private long id;

	@SecondaryIndex
	private LinkedHashSet<String> colors;

	@SecondaryIndex
	@PropertyIndexer(UpperCaseStringListIndexer.class)
	private LinkedHashSet<String> sizes;

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
	 * @return the colors
	 */
	public LinkedHashSet<String> getColors() {
		return colors;
	}

	/**
	 * @param colors
	 *            the colors to set
	 */
	public void setColors(LinkedHashSet<String> colors) {
		this.colors = colors;
	}

	/**
	 * @return the sizes
	 */
	public LinkedHashSet<String> getSizes() {
		return sizes;
	}

	/**
	 * @param sizes
	 *            the sizes to set
	 */
	public void setSizes(LinkedHashSet<String> sizes) {
		this.sizes = sizes;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof StringSetIndex)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		StringSetIndex that = (StringSetIndex) obj;
		return this.id == that.id && Objects.equals(this.colors, that.colors) && Objects.equals(this.sizes, that.sizes);

	}

	public static StringSetIndex getSample1() {
		StringSetIndex entity = new StringSetIndex();
		entity.setColors(new LinkedHashSet<>(Arrays.asList(new String[] { "Black", "White", "Red" })));
		entity.setSizes(new LinkedHashSet<>(Arrays.asList(new String[] { "Small", "Medium", "Large", null })));
		return entity;
	}

}
