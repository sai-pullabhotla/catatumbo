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

/**
 * @author Sai Pullabhotla
 *
 */
@Embeddable
public class PhoneNumber {

	private String countryCode;

	private String areaCode;

	private String subscriberNumber;

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode
	 *            the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the number
	 */
	public String getSubscriberNumber() {
		return subscriberNumber;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setSubscriberNumber(String number) {
		this.subscriberNumber = number;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "+" + countryCode + " (" + areaCode + ")" + subscriberNumber;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof PhoneNumber)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		PhoneNumber that = (PhoneNumber) obj;
		return Objects.equals(this.areaCode, that.areaCode) && Objects.equals(this.areaCode, that.areaCode)
				&& Objects.equals(this.subscriberNumber, that.subscriberNumber);
	}

}
