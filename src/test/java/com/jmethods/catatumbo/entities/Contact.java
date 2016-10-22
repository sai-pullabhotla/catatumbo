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

import java.util.Objects;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Embedded;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Exploded;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Imploded;
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
		@PropertyOverride(name = "workAddress.zipCode.fourDigits", property = @Property(name = "zipx", indexed = false)) })
public class Contact {

	@Identifier
	private long id;

	@Key
	private DatastoreKey key;

	private String firstName;

	private String lastName;

	@Embedded(name = "cellNumber", indexed = true)
	@Imploded
	private PhoneNumber mobileNumber;

	@Embedded
	@Imploded
	private Address homeAddress;

	@Embedded
	@Exploded
	private Address workAddress;

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

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the contactNumber
	 */
	public PhoneNumber getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param contactNumber
	 *            the contactNumber to set
	 */
	public void setMobileNumber(PhoneNumber contactNumber) {
		this.mobileNumber = contactNumber;
	}

	/**
	 * @return the homeAddress
	 */
	public Address getHomeAddress() {
		return homeAddress;
	}

	/**
	 * @param homeAddress
	 *            the homeAddress to set
	 */
	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	/**
	 * @return the workAddress
	 */
	public Address getWorkAddress() {
		return workAddress;
	}

	/**
	 * @param workAddress
	 *            the workAddress to set
	 */
	public void setWorkAddress(Address workAddress) {
		this.workAddress = workAddress;
	}

	public boolean equalsExceptId(Contact that) {
		return Objects.equals(this.firstName, that.firstName) && Objects.equals(this.lastName, that.lastName)
				&& Objects.equals(this.mobileNumber, that.mobileNumber)
				&& Objects.equals(this.homeAddress, that.homeAddress)
				&& Objects.equals(this.workAddress, that.workAddress);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Contact)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Contact that = (Contact) obj;
		return Objects.equals(this.id, that.id) && Objects.equals(this.key, that.key) && this.equalsExceptId(that);
	}

	public static Contact createContact1() {
		Contact contact = new Contact();
		contact.setFirstName("John");
		contact.setLastName("Doe");
		return contact;
	}

	public static Contact createContact2() {
		Contact contact = new Contact();
		contact.setFirstName("John");
		contact.setLastName("Doe");
		PhoneNumber phone = new PhoneNumber();
		phone.setCountryCode("1");
		phone.setAreaCode("201");
		phone.setSubscriberNumber("3334444");
		contact.setMobileNumber(phone);
		Address homeAddress = new Address();
		homeAddress.setStreet1("1 Main St.");
		homeAddress.setStreet2("Apt 1");
		homeAddress.setCity("Omaha");
		homeAddress.setState("NE");
		ZipCode zip = new ZipCode();
		zip.setFiveDigits("55555");
		zip.setFourDigits("4444");
		zip.setAnotherEmbeddable(new AnotherEmbeddable("Field 1", "Field 2"));
		homeAddress.setZipCode(zip);
		contact.setHomeAddress(homeAddress);

		Address workAddress = new Address();
		workAddress.setStreet1("2 Secondary St.");
		workAddress.setCity("Lincoln");
		workAddress.setState("NE");
		ZipCode workZipCode = new ZipCode();
		workZipCode.setFiveDigits("99999");
		workZipCode.setFourDigits("0000");
		workAddress.setZipCode(workZipCode);

		contact.setWorkAddress(workAddress);
		return contact;
	}

}
