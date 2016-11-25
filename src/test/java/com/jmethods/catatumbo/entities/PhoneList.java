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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class PhoneList {

	@Identifier
	private long id;

	private List<PhoneNumber> mobileNumbers;
	private Set<PhoneNumber> landLineNumbers;

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
	public List<PhoneNumber> getMobileNumbers() {
		return mobileNumbers;
	}

	/**
	 * @param phoneNumbers
	 *            the phoneNumbers to set
	 */
	public void setMobileNumbers(List<PhoneNumber> phoneNumbers) {
		this.mobileNumbers = phoneNumbers;
	}

	/**
	 * @return the landLineNumbers
	 */
	public Set<PhoneNumber> getLandLineNumbers() {
		return landLineNumbers;
	}

	/**
	 * @param landLineNumbers
	 *            the landLineNumbers to set
	 */
	public void setLandLineNumbers(Set<PhoneNumber> landLineNumbers) {
		this.landLineNumbers = landLineNumbers;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof PhoneList)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		PhoneList that = (PhoneList) obj;
		return this.id == that.id && Objects.equals(this.mobileNumbers, that.mobileNumbers)
				&& Objects.equals(this.landLineNumbers, that.landLineNumbers);
	}

	public static PhoneList getSample1() {
		PhoneList entity = new PhoneList();
		List<PhoneNumber> phoneNumbers = new ArrayList<>();
		phoneNumbers.add(PhoneNumber.getSample1());
		phoneNumbers.add(PhoneNumber.getSample2());
		entity.setMobileNumbers(phoneNumbers);
		Set<PhoneNumber> landLineNumbers = new HashSet<>();
		landLineNumbers.add(PhoneNumber.getSample3());
		landLineNumbers.add(PhoneNumber.getSample4());
		entity.setLandLineNumbers(landLineNumbers);
		return entity;
	}

}
