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

import com.google.api.client.util.Objects;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.ParentKey;

/**
 * @author Sai Pullabhotla
 *
 */

@Entity
public class UserContact {

	@Identifier
	private long id;

	@ParentKey
	private DatastoreKey userKey;

	@Key
	private DatastoreKey contactKey;

	private String contactName;

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
	 * @return the userKey
	 */
	public DatastoreKey getUserKey() {
		return userKey;
	}

	/**
	 * @param userKey
	 *            the userKey to set
	 */
	public void setUserKey(DatastoreKey userKey) {
		this.userKey = userKey;
	}

	/**
	 * @return the contactKey
	 */
	public DatastoreKey getContactKey() {
		return contactKey;
	}

	/**
	 * @param contactKey
	 *            the contactKey to set
	 */
	public void setContactKey(DatastoreKey contactKey) {
		this.contactKey = contactKey;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName
	 *            the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !this.getClass().equals(obj.getClass())) {
			return false;
		}
		UserContact that = (UserContact) obj;
		return Objects.equal(this.id, that.id) && Objects.equal(this.userKey, that.userKey)
				&& Objects.equal(this.contactKey, that.contactKey) && Objects.equal(this.contactName, that.contactName);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserContact [id=").append(id).append(", userKey=").append(userKey).append(", contactKey=")
				.append(contactKey).append(", contactName=").append(contactName).append("]");
		return builder.toString();
	}

}
