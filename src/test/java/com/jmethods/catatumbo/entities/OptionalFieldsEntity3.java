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

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Embedded;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.PropertyOverride;
import com.jmethods.catatumbo.PropertyOverrides;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
@PropertyOverrides({
		@PropertyOverride(name = "phone.countryCode", property = @Property(name = "ccode", optional = false)),
		@PropertyOverride(name = "phone.areaCode", property = @Property(name = "acode", optional = true)) })

public class OptionalFieldsEntity3 {

	@Identifier
	private long id;

	@Key
	private DatastoreKey key;

	@Embedded
	private PhoneNumber phone;

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
	 * @return the phoneNumbers
	 */
	public PhoneNumber getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phoneNumbers to set
	 */
	public void setPhone(PhoneNumber phone) {
		this.phone = phone;
	}

	/**
	 * @return the key
	 */
	public DatastoreKey getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(DatastoreKey key) {
		this.key = key;
	}

}
