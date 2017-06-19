/*
 * Copyright 2017 Sai Pullabhotla.
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

import java.util.Objects;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class WrappedStringIdEntity {

	@Identifier
	private WrappedStringId id;

	private String name;

	/**
	 * @return the id
	 */
	public WrappedStringId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(WrappedStringId id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WrappedStringIdEntity [id=").append(id).append(", name=").append(name).append("]");
		return builder.toString();
	}

	public static WrappedStringIdEntity getSample1() {
		WrappedStringIdEntity entity = new WrappedStringIdEntity();
		entity.setName("johndoe");
		return entity;
	}

	public static WrappedStringIdEntity getSample2() {
		WrappedStringIdEntity entity = new WrappedStringIdEntity();
		entity.setId(new WrappedStringId(null));
		entity.setName("johndoe");
		return entity;
	}

	public static WrappedStringIdEntity getSample3() {
		WrappedStringIdEntity entity = new WrappedStringIdEntity();
		entity.setId(new WrappedStringId(""));
		entity.setName("johndoe");
		return entity;
	}

	public static WrappedStringIdEntity getSample4() {
		WrappedStringIdEntity entity = new WrappedStringIdEntity();
		entity.setId(new WrappedStringId("mykey"));
		entity.setName("johndoe");
		return entity;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !this.getClass().equals(obj.getClass())) {
			return false;
		}
		WrappedStringIdEntity that = (WrappedStringIdEntity) obj;
		return Objects.equals(this.id, that.id) && Objects.equals(this.name, that.name);
	}

}
