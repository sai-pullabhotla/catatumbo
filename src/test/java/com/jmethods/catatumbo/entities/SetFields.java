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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.GeoLocation;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class SetFields {

	@Identifier
	private long id;

	private Set<Boolean> booleanSet;
	private Set<Character> charSet;
	private Set<Short> shortSet;
	private Set<Integer> intSet;
	private Set<Long> longSet;
	private Set<Float> floatSet;
	private Set<Double> doubleSet;
	private Set<String> stringSet;
	private Set<Color> enumSet;
	private Set<Date> dateSet;
	private Set<Calendar> calendarSet;
	private Set<byte[]> blobSet;
	private Set<char[]> clobSet;
	private Set<GeoLocation> geoLocationSet;
	private Set<DatastoreKey> keySet;
	private Set objectSet;
	private HashSet<String> hashSet;
	private SortedSet<Integer> sortedSet;
	private LinkedHashSet<Double> linkedHashSet;
	private TreeSet<Boolean> treeSet;
	private Set<?> wildcardSet;
	private Set<LocalDate> localDateSet;

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
	 * @return the booleanSet
	 */
	public Set<Boolean> getBooleanSet() {
		return booleanSet;
	}

	/**
	 * @param booleanSet
	 *            the booleanSet to set
	 */
	public void setBooleanSet(Set<Boolean> booleanSet) {
		this.booleanSet = booleanSet;
	}

	/**
	 * @return the charSet
	 */
	public Set<Character> getCharSet() {
		return charSet;
	}

	/**
	 * @param charSet
	 *            the charSet to set
	 */
	public void setCharSet(Set<Character> charSet) {
		this.charSet = charSet;
	}

	/**
	 * @return the shortSet
	 */
	public Set<Short> getShortSet() {
		return shortSet;
	}

	/**
	 * @param shortSet
	 *            the shortSet to set
	 */
	public void setShortSet(Set<Short> shortSet) {
		this.shortSet = shortSet;
	}

	/**
	 * @return the intSet
	 */
	public Set<Integer> getIntSet() {
		return intSet;
	}

	/**
	 * @param intSet
	 *            the intSet to set
	 */
	public void setIntSet(Set<Integer> intSet) {
		this.intSet = intSet;
	}

	/**
	 * @return the longSet
	 */
	public Set<Long> getLongSet() {
		return longSet;
	}

	/**
	 * @param longSet
	 *            the longSet to set
	 */
	public void setLongSet(Set<Long> longSet) {
		this.longSet = longSet;
	}

	/**
	 * @return the floatSet
	 */
	public Set<Float> getFloatSet() {
		return floatSet;
	}

	/**
	 * @param floatSet
	 *            the floatSet to set
	 */
	public void setFloatSet(Set<Float> floatSet) {
		this.floatSet = floatSet;
	}

	/**
	 * @return the doubleSet
	 */
	public Set<Double> getDoubleSet() {
		return doubleSet;
	}

	/**
	 * @param doubleSet
	 *            the doubleSet to set
	 */
	public void setDoubleSet(Set<Double> doubleSet) {
		this.doubleSet = doubleSet;
	}

	/**
	 * @return the stringSet
	 */
	public Set<String> getStringSet() {
		return stringSet;
	}

	/**
	 * @param stringSet
	 *            the stringSet to set
	 */
	public void setStringSet(Set<String> stringSet) {
		this.stringSet = stringSet;
	}

	/**
	 * @return the enumSet
	 */
	public Set<Color> getEnumSet() {
		return enumSet;
	}

	/**
	 * @param enumSet
	 *            the enumSet to set
	 */
	public void setEnumSet(Set<Color> enumSet) {
		this.enumSet = enumSet;
	}

	/**
	 * @return the dateSet
	 */
	public Set<Date> getDateSet() {
		return dateSet;
	}

	/**
	 * @param dateSet
	 *            the dateSet to set
	 */
	public void setDateSet(Set<Date> dateSet) {
		this.dateSet = dateSet;
	}

	/**
	 * @return the calendarSet
	 */
	public Set<Calendar> getCalendarSet() {
		return calendarSet;
	}

	/**
	 * @param calendarSet
	 *            the calendarSet to set
	 */
	public void setCalendarSet(Set<Calendar> calendarSet) {
		this.calendarSet = calendarSet;
	}

	/**
	 * @return the blobSet
	 */
	public Set<byte[]> getBlobSet() {
		return blobSet;
	}

	/**
	 * @param blobSet
	 *            the blobSet to set
	 */
	public void setBlobSet(Set<byte[]> blobSet) {
		this.blobSet = blobSet;
	}

	/**
	 * @return the clobSet
	 */
	public Set<char[]> getClobSet() {
		return clobSet;
	}

	/**
	 * @param clobSet
	 *            the clobSet to set
	 */
	public void setClobSet(Set<char[]> clobSet) {
		this.clobSet = clobSet;
	}

	/**
	 * @return the geoLocationSet
	 */
	public Set<GeoLocation> getGeoLocationSet() {
		return geoLocationSet;
	}

	/**
	 * @param geoLocationSet
	 *            the geoLocationSet to set
	 */
	public void setGeoLocationSet(Set<GeoLocation> geoLocationSet) {
		this.geoLocationSet = geoLocationSet;
	}

	/**
	 * @return the keySet
	 */
	public Set<DatastoreKey> getKeySet() {
		return keySet;
	}

	/**
	 * @param keySet
	 *            the keySet to set
	 */
	public void setKeySet(Set<DatastoreKey> keySet) {
		this.keySet = keySet;
	}

	/**
	 * @return the objectSet
	 */
	public Set getObjectSet() {
		return objectSet;
	}

	/**
	 * @param objectSet
	 *            the objectSet to set
	 */
	public void setObjectSet(Set objectSet) {
		this.objectSet = objectSet;
	}

	/**
	 * @return the hashSet
	 */
	public HashSet<String> getHashSet() {
		return hashSet;
	}

	/**
	 * @param hashSet
	 *            the hashSet to set
	 */
	public void setHashSet(HashSet<String> hashSet) {
		this.hashSet = hashSet;
	}

	/**
	 * @return the sortedSet
	 */
	public SortedSet<Integer> getSortedSet() {
		return sortedSet;
	}

	/**
	 * @param sortedSet
	 *            the sortedSet to set
	 */
	public void setSortedSet(SortedSet<Integer> sortedSet) {
		this.sortedSet = sortedSet;
	}

	/**
	 * @return the linkedHashSet
	 */
	public LinkedHashSet<Double> getLinkedHashSet() {
		return linkedHashSet;
	}

	/**
	 * @param linkedHashSet
	 *            the linkedHashSet to set
	 */
	public void setLinkedHashSet(LinkedHashSet<Double> linkedHashSet) {
		this.linkedHashSet = linkedHashSet;
	}

	/**
	 * @return the treeSet
	 */
	public TreeSet<Boolean> getTreeSet() {
		return treeSet;
	}

	/**
	 * @param treeSet
	 *            the treeSet to set
	 */
	public void setTreeSet(TreeSet<Boolean> treeSet) {
		this.treeSet = treeSet;
	}

	/**
	 * @return the wildcardSet
	 */
	public Set<?> getWildcardSet() {
		return wildcardSet;
	}

	/**
	 * @param wildcardSet
	 *            the wildcardSet to set
	 */
	public void setWildcardSet(Set<?> wildcardSet) {
		this.wildcardSet = wildcardSet;
	}

	/**
	 * @return the localDateSet
	 */
	public Set<LocalDate> getLocalDateSet() {
		return localDateSet;
	}

	/**
	 * @param localDateSet
	 *            the localDateSet to set
	 */
	public void setLocalDateSet(Set<LocalDate> localDateSet) {
		this.localDateSet = localDateSet;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SetFields)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		SetFields that = (SetFields) obj;
		return this.id == that.id && Objects.equals(this.booleanSet, that.booleanSet)
				&& Objects.equals(this.charSet, that.charSet) && Objects.equals(this.shortSet, that.shortSet)
				&& Objects.equals(this.intSet, that.intSet) && Objects.equals(this.longSet, that.longSet)
				&& Objects.equals(this.floatSet, that.floatSet) && Objects.equals(this.doubleSet, that.doubleSet)
				&& Objects.equals(this.stringSet, that.stringSet) && Objects.equals(this.enumSet, enumSet)
				&& Objects.equals(this.dateSet, that.dateSet) && Objects.equals(this.calendarSet, that.calendarSet)
				&& Objects.equals(this.geoLocationSet, that.geoLocationSet) && Objects.equals(this.keySet, that.keySet)
				&& Objects.equals(this.objectSet, that.objectSet) && Objects.equals(this.hashSet, that.hashSet)
				&& Objects.equals(this.sortedSet, that.sortedSet)
				&& Objects.equals(this.linkedHashSet, that.linkedHashSet) && Objects.equals(this.treeSet, that.treeSet)
				&& Objects.equals(this.wildcardSet, that.wildcardSet)
				&& Objects.equals(this.localDateSet, that.localDateSet);
	}

	public static SetFields getSampleEntity1() {
		SetFields entity = new SetFields();
		entity.setBooleanSet(new HashSet<>(Arrays.asList(true, false, null)));
		entity.setCharSet(new HashSet<>(Arrays.asList('S', 'M', 'L', null)));
		entity.setShortSet(new HashSet<>(Arrays.asList((short) 5, (short) 9, null)));
		entity.setIntSet(new HashSet<>(Arrays.asList(88, 888, 888, null)));
		entity.setLongSet(new HashSet<>(Arrays.asList(6L, 66L, 666L, null)));
		entity.setFloatSet(new HashSet<>(Arrays.asList(3.14f, 97f, 999.998f, null)));
		entity.setDoubleSet(new HashSet<>(Arrays.asList(5.5, null, Double.MAX_VALUE, null)));
		entity.setStringSet(new HashSet<>(Arrays.asList("Hello", "World", "Awesome", null)));
		entity.setEnumSet(new HashSet<>(Arrays.asList(Color.BLACK, Color.BLUE, Color.PURPLE, null)));
		entity.setDateSet(new HashSet<>(Arrays.asList(new Date(), new Date(System.currentTimeMillis() + 60000), null)));
		entity.setCalendarSet(new HashSet<>(Arrays.asList(Calendar.getInstance(), null, Calendar.getInstance())));
		entity.setBlobSet(new HashSet<>(Arrays.asList("ok".getBytes(), "naah".getBytes(), null)));
		entity.setClobSet(new HashSet<>(Arrays.asList("Oh".toCharArray(), "My".toCharArray(), null)));
		entity.setGeoLocationSet(new HashSet<>(
				Arrays.asList(new GeoLocation(40.7142700, -74.0059700), new GeoLocation(48.8534100, 2.3488000))));
		entity.setObjectSet(new HashSet<>(Arrays.asList("One", 5000L, 99.99d, Boolean.FALSE, (String) null)));
		entity.setHashSet(new HashSet<>(Arrays.asList("Whatever", "Let's see", null)));
		entity.setSortedSet(new TreeSet<>(Arrays.asList(5, 1, 3)));
		entity.setLinkedHashSet(new LinkedHashSet<>(Arrays.asList(Double.MIN_VALUE, 0.0, Double.MAX_VALUE)));
		entity.setTreeSet(new TreeSet<>(Arrays.asList(false, true, false, true)));
		entity.setWildcardSet(new HashSet<>(Arrays.asList("One", 1L, true, 1.0d)));
		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		entity.setLocalDateSet(new HashSet<>(Arrays.asList(today, tomorrow)));
		return entity;
	}

}
