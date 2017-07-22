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

import java.util.ArrayList;
import java.util.List;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Key;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class OptionalFieldsEntity2 {

	@Identifier
	private long id;

	@Key
	private DatastoreKey key;

	private List<PhoneNumber> phoneNumbers;

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
	public List<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	/**
	 * @param phoneNumbers
	 *            the phoneNumbers to set
	 */
	public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OptionalFieldsEntity2 [id=").append(id).append(", phoneNumbers=").append(phoneNumbers)
				.append("]");
		return builder.toString();
	}

	public static OptionalFieldsEntity2 getSample1() {
		List<PhoneNumber> phones = new ArrayList<>();
		phones.add(PhoneNumber.getSample1());
		phones.add(PhoneNumber.getSample2());
		phones.add(PhoneNumber.getSample5());
		OptionalFieldsEntity2 entity = new OptionalFieldsEntity2();
		entity.setPhoneNumbers(phones);
		return entity;
	}

}
