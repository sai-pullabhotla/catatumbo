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

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Embedded;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Imploded;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.Property;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class OptionalFieldsEntity {

	@Identifier
	private long id;

	private String mandatoryString;

	@Key
	private DatastoreKey key;

	@Property(optional = true)
	private Short optionalShort;

	@Property(optional = true)
	private Integer optionalInteger;

	@Property(optional = true)
	private Long optionalLong;

	@Property(optional = true)
	private String optionalString;

	@Property(optional = true)
	private Date optionalDate;

	@Property(optional = true)
	private byte[] optionalBlob;

	@Property(optional = true)
	private List<String> optionalList;

	@Property(optional = true)
	private Set<Integer> optionalSet;

	@Property(optional = true)
	private Map<String, String> optionalMap;

	@Embedded(optional = true)
	@Imploded
	private PhoneNumber optionalPhone;

	@Embedded(name = "faxno", optional = false, indexed = true)
	@Imploded
	private PhoneNumber fax;

	@Embedded(optional = true)
	// Optional should not have any effect in
	// exploded mode
	private Address optionalAddress;

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
	 * @return the mandatoryString
	 */
	public String getMandatoryString() {
		return mandatoryString;
	}

	/**
	 * @param mandatoryString
	 *            the mandatoryString to set
	 */
	public void setMandatoryString(String mandatoryString) {
		this.mandatoryString = mandatoryString;
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
	 * @return the optionalShort
	 */
	public Short getOptionalShort() {
		return optionalShort;
	}

	/**
	 * @param optionalShort
	 *            the optionalShort to set
	 */
	public void setOptionalShort(Short optionalShort) {
		this.optionalShort = optionalShort;
	}

	/**
	 * @return the optionalInteger
	 */
	public Integer getOptionalInteger() {
		return optionalInteger;
	}

	/**
	 * @param optionalInteger
	 *            the optionalInteger to set
	 */
	public void setOptionalInteger(Integer optionalInteger) {
		this.optionalInteger = optionalInteger;
	}

	/**
	 * @return the optionalLong
	 */
	public Long getOptionalLong() {
		return optionalLong;
	}

	/**
	 * @param optionalLong
	 *            the optionalLong to set
	 */
	public void setOptionalLong(Long optionalLong) {
		this.optionalLong = optionalLong;
	}

	/**
	 * @return the optionalString
	 */
	public String getOptionalString() {
		return optionalString;
	}

	/**
	 * @param optionalString
	 *            the optionalString to set
	 */
	public void setOptionalString(String optionalString) {
		this.optionalString = optionalString;
	}

	/**
	 * @return the optionalDate
	 */
	public Date getOptionalDate() {
		return optionalDate;
	}

	/**
	 * @param optionalDate
	 *            the optionalDate to set
	 */
	public void setOptionalDate(Date optionalDate) {
		this.optionalDate = optionalDate;
	}

	/**
	 * @return the optionalBlob
	 */
	public byte[] getOptionalBlob() {
		return optionalBlob;
	}

	/**
	 * @param optionalBlob
	 *            the optionalBlob to set
	 */
	public void setOptionalBlob(byte[] optionalBlob) {
		this.optionalBlob = optionalBlob;
	}

	/**
	 * @return the optionalList
	 */
	public List<String> getOptionalList() {
		return optionalList;
	}

	/**
	 * @param optionalList
	 *            the optionalList to set
	 */
	public void setOptionalList(List<String> optionalList) {
		this.optionalList = optionalList;
	}

	/**
	 * @return the optionalSet
	 */
	public Set<Integer> getOptionalSet() {
		return optionalSet;
	}

	/**
	 * @param optionalSet
	 *            the optionalSet to set
	 */
	public void setOptionalSet(Set<Integer> optionalSet) {
		this.optionalSet = optionalSet;
	}

	/**
	 * @return the optionalMap
	 */
	public Map<String, String> getOptionalMap() {
		return optionalMap;
	}

	/**
	 * @param optionalMap
	 *            the optionalMap to set
	 */
	public void setOptionalMap(Map<String, String> optionalMap) {
		this.optionalMap = optionalMap;
	}

	/**
	 * @return the optionalPhone
	 */
	public PhoneNumber getOptionalPhone() {
		return optionalPhone;
	}

	/**
	 * @param optionalPhone
	 *            the optionalPhone to set
	 */
	public void setOptionalPhone(PhoneNumber optionalPhone) {
		this.optionalPhone = optionalPhone;
	}

	/**
	 * @return the fax
	 */
	public PhoneNumber getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(PhoneNumber fax) {
		this.fax = fax;
	}

	/**
	 * @return the optionalAddress
	 */
	public Address getOptionalAddress() {
		return optionalAddress;
	}

	/**
	 * @param optionalAddress
	 *            the optionalAddress to set
	 */
	public void setOptionalAddress(Address optionalAddress) {
		this.optionalAddress = optionalAddress;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}
		OptionalFieldsEntity that = (OptionalFieldsEntity) obj;
		return this.id == that.id && equalsExceptId(obj);
	}

	public boolean equalsExceptId(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}
		OptionalFieldsEntity that = (OptionalFieldsEntity) obj;
		return Objects.equals(this.mandatoryString, that.mandatoryString)
				&& Objects.equals(this.optionalShort, that.optionalShort)
				&& Objects.equals(this.optionalInteger, that.optionalInteger)
				&& Objects.equals(this.optionalLong, that.optionalLong)
				&& Objects.equals(this.optionalString, that.optionalString)
				&& Objects.equals(this.optionalDate, that.optionalDate)
				&& Objects.equals(this.optionalList, that.optionalList)
				&& Objects.equals(this.optionalSet, that.optionalSet)
				&& Objects.equals(this.optionalMap, that.optionalMap)
				&& Objects.equals(this.optionalPhone, that.optionalPhone) && Objects.equals(this.fax, that.fax)
				&& Objects.equals(this.optionalAddress, that.optionalAddress);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OptionalFieldsEntity [id=").append(id).append(", mandatoryString=").append(mandatoryString)
				.append(", key=").append(key).append(", optionalShort=").append(optionalShort)
				.append(", optionalInteger=").append(optionalInteger).append(", optionalLong=").append(optionalLong)
				.append(", optionalString=").append(optionalString).append(", optionalDate=").append(optionalDate)
				.append(", optionalBlob=").append(Arrays.toString(optionalBlob)).append(", optionalList=")
				.append(optionalList).append(", optionalSet=").append(optionalSet).append(", optionalMap=")
				.append(optionalMap).append(", optionalPhone=").append(optionalPhone).append(", fax=").append(fax)
				.append(", optionalAddress=").append(optionalAddress).append("]");
		return builder.toString();
	}

	public static OptionalFieldsEntity getSample1() {
		OptionalFieldsEntity entity = new OptionalFieldsEntity();
		return entity;
	}

	public static OptionalFieldsEntity getSample2() {
		OptionalFieldsEntity entity = new OptionalFieldsEntity();
		entity.setMandatoryString("Hello");
		entity.setOptionalAddress(Address.getSample3());
		entity.setOptionalBlob("abc".getBytes());
		entity.setOptionalDate(new Date());
		entity.setOptionalInteger(50000);
		entity.setOptionalList(Arrays.asList(new String[] { "One", "Two" }));
		entity.setOptionalLong(1000000000000000L);
		Map<String, String> map = new HashMap<>();
		map.put("key1", "Value 1");
		map.put("key2", "Value 2");
		entity.setOptionalMap(map);
		entity.setOptionalPhone(PhoneNumber.getSample5());
		entity.setFax(PhoneNumber.getSample5());
		entity.setOptionalSet(new HashSet<>(Arrays.asList(new Integer[] { 5, 10, 15 })));
		entity.setOptionalShort((short) 5);
		entity.setOptionalString("Yo!");
		return entity;
	}

}
