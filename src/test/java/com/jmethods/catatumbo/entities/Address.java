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

import com.jmethods.catatumbo.Embeddable;
import com.jmethods.catatumbo.Embedded;
import com.jmethods.catatumbo.Ignore;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.SecondaryIndex;

/**
 * @author Sai Pullabhotla
 *
 */
@Embeddable
public class Address {

	private static String test;

	@Property(name = "line1", indexed = false)
	private String street1;

	@Property(name = "line2", indexed = false)
	private String street2;

	@SecondaryIndex
	private String city;

	private String state;

	@Embedded(name = "postal_code")
	private ZipCode zipCode;

	@Ignore
	private String printableAddress;

	/**
	 * @return the street1
	 */
	public String getStreet1() {
		return street1;
	}

	/**
	 * @param street1
	 *            the street to set
	 */
	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	/**
	 * @return the street2
	 */
	public String getStreet2() {
		return street2;
	}

	/**
	 * @param street2
	 *            the street2 to set
	 */
	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the printableAddress
	 */
	public String getPrintableAddress() {
		return printableAddress;
	}

	/**
	 * @param printableAddress
	 *            the printableAddress to set
	 */
	public void setPrintableAddress(String printableAddress) {
		this.printableAddress = printableAddress;
	}

	/**
	 * @return the zipCode
	 */
	public ZipCode getZipCode() {
		return zipCode;
	}

	/**
	 * @param zipCode
	 *            the zipCode to set
	 */
	public void setZipCode(ZipCode zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public String toString() {
		return street1 + "\n" + street2 + "\n" + city + "\n" + state + "\n" + zipCode;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Address)) {
			return false;
		}
		Address that = (Address) obj;
		return Objects.equals(this.street1, that.street1) && Objects.equals(this.street2, that.street2)
				&& Objects.equals(this.city, that.city) && Objects.equals(this.state, that.state)
				&& Objects.equals(this.zipCode, that.zipCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(street1, street2, city, state, zipCode);
	}

	public static Address getSample1() {
		Address address = new Address();
		address.setStreet1("1 Main St.");
		address.setStreet2("Apt 1");
		address.setCity("Omaha");
		address.setState("NE");
		ZipCode zip = new ZipCode();
		zip.setFiveDigits("55555");
		zip.setFourDigits("4444");
		zip.setAnotherEmbeddable(new AnotherEmbeddable("Field 1", "Field 2"));
		address.setZipCode(zip);
		return address;
	}

	public static Address getSample2() {
		Address address = new Address();
		address.setStreet1("2 Secondary St.");
		address.setCity("Lincoln");
		address.setState("NE");
		ZipCode workZipCode = new ZipCode();
		workZipCode.setFiveDigits("99999");
		workZipCode.setFourDigits("0000");
		address.setZipCode(workZipCode);
		return address;
	}

	public static Address getSample3() {
		Address address = new Address();
		address.setStreet1("3 Third St.");
		address.setCity("San Jose");
		address.setState("CA");
		return address;
	}

}
