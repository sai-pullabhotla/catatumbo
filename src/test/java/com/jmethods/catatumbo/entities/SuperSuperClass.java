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

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Embedded;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.MappedSuperClass;
import com.jmethods.catatumbo.Property;

/**
 * @author Sai Pullabhotla
 *
 */
@MappedSuperClass
public class SuperSuperClass {

	@Property(name = "super.super", indexed = false)
	private String fieldx;

	@Key
	private DatastoreKey myKey;

	@Embedded
	private Address address;

	/**
	 * @return the fieldx
	 */
	public String getFieldx() {
		return fieldx;
	}

	/**
	 * @param fieldx
	 *            the fieldx to set
	 */
	public void setFieldx(String fieldx) {
		this.fieldx = fieldx;
	}

	/**
	 * @return the myKey
	 */
	public DatastoreKey getMyKey() {
		return myKey;
	}

	/**
	 * @param myKey
	 *            the myKey to set
	 */
	public void setMyKey(DatastoreKey myKey) {
		this.myKey = myKey;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

}
