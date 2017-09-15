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

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class ImmutableSubClass extends ImmutableSuperClass {

	private String name;

	private ImmutableSubClass(Builder builder) {
		super(builder);
		this.name = builder.name;
	}

	public String getName() {
		return name;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("ImmutableSubClass [name=").append(name).append(", getId()=").append(getId())
				.append(", getKey()=").append(getKey()).append(", getCreatedTimestamp()=").append(getCreatedTimestamp())
				.append(", getModifiedTimestamp()=").append(getModifiedTimestamp()).append(", getVersion()=")
				.append(getVersion()).append("]");
		return builder2.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}
		if (equalsExceptGeneratedValues(obj)) {
			ImmutableSubClass that = (ImmutableSubClass) obj;
			return Objects.equals(this.getId(), that.getId()) && Objects.equals(this.getKey(), that.getKey())
					&& Objects.equals(this.getCreatedTimestamp(), that.getCreatedTimestamp())
					&& Objects.equals(this.getModifiedTimestamp(), that.getModifiedTimestamp());

		}
		return false;
	}

	public boolean equalsExceptGeneratedValues(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}
		ImmutableSubClass that = (ImmutableSubClass) obj;
		return Objects.equals(this.name, that.name);
	}

	public static class Builder extends ImmutableSuperClass.Builder<Builder> {

		private String name;

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		@Override
		public ImmutableSubClass build() {
			return new ImmutableSubClass(this);
		}

	}

}
