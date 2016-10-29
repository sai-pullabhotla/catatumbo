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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.Vector;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.GeoLocation;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class ListFields {

	@Identifier
	private long id;

	private List<Boolean> booleanList;

	private List<Character> charList;

	private List<Short> shortList;

	private List<Integer> intList;

	private List<Long> longList;

	private List<String> stringList;

	private List<Date> dateList;

	private List<Calendar> calendarList;

	private List<Color> colorList;

	private List<Float> floatList;

	private List<Double> doubleList;

	private List<byte[]> blobList;

	private List<char[]> clobList;

	private List<Object> objectList;

	private List<?> wildcardList;

	private ArrayList arrayList;

	private LinkedList linkedList;

	private Vector<Float> vector;

	private Stack<Integer> stack;

	private List<DatastoreKey> keyList;

	private List<GeoLocation> geoLocationList;

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
	 * @return the booleanList
	 */
	public List<Boolean> getBooleanList() {
		return booleanList;
	}

	/**
	 * @param booleanList
	 *            the booleanList to set
	 */
	public void setBooleanList(List<Boolean> booleanList) {
		this.booleanList = booleanList;
	}

	/**
	 * @return the charList
	 */
	public List<Character> getCharList() {
		return charList;
	}

	/**
	 * @param charList
	 *            the charList to set
	 */
	public void setCharList(List<Character> charList) {
		this.charList = charList;
	}

	/**
	 * @return the shortList
	 */
	public List<Short> getShortList() {
		return shortList;
	}

	/**
	 * @param shortList
	 *            the shortList to set
	 */
	public void setShortList(List<Short> shortList) {
		this.shortList = shortList;
	}

	/**
	 * @return the intList
	 */
	public List<Integer> getIntList() {
		return intList;
	}

	/**
	 * @param intList
	 *            the intList to set
	 */
	public void setIntList(List<Integer> intList) {
		this.intList = intList;
	}

	/**
	 * @return the longList
	 */
	public List<Long> getLongList() {
		return longList;
	}

	/**
	 * @param longList
	 *            the longList to set
	 */
	public void setLongList(List<Long> longList) {
		this.longList = longList;
	}

	/**
	 * @return the stringList
	 */
	public List<String> getStringList() {
		return stringList;
	}

	/**
	 * @param stringList
	 *            the stringList to set
	 */
	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}

	/**
	 * @return the dateList
	 */
	public List<Date> getDateList() {
		return dateList;
	}

	/**
	 * @param dateList
	 *            the dateList to set
	 */
	public void setDateList(List<Date> dateList) {
		this.dateList = dateList;
	}

	/**
	 * @return the calendarList
	 */
	public List<Calendar> getCalendarList() {
		return calendarList;
	}

	/**
	 * @param calendarList
	 *            the calendarList to set
	 */
	public void setCalendarList(List<Calendar> calendarList) {
		this.calendarList = calendarList;
	}

	/**
	 * @return the colorList
	 */
	public List<Color> getColorList() {
		return colorList;
	}

	/**
	 * @param colorList
	 *            the colorList to set
	 */
	public void setColorList(List<Color> colorList) {
		this.colorList = colorList;
	}

	/**
	 * @return the floatList
	 */
	public List<Float> getFloatList() {
		return floatList;
	}

	/**
	 * @param floatList
	 *            the floatList to set
	 */
	public void setFloatList(List<Float> floatList) {
		this.floatList = floatList;
	}

	/**
	 * @return the doubleList
	 */
	public List<Double> getDoubleList() {
		return doubleList;
	}

	/**
	 * @param doubleList
	 *            the doubleList to set
	 */
	public void setDoubleList(List<Double> doubleList) {
		this.doubleList = doubleList;
	}

	/**
	 * @return the blobList
	 */
	public List<byte[]> getBlobList() {
		return blobList;
	}

	/**
	 * @param blobList
	 *            the blobList to set
	 */
	public void setBlobList(List<byte[]> blobList) {
		this.blobList = blobList;
	}

	/**
	 * @return the clobList
	 */
	public List<char[]> getClobList() {
		return clobList;
	}

	/**
	 * @param clobList
	 *            the clobList to set
	 */
	public void setClobList(List<char[]> clobList) {
		this.clobList = clobList;
	}

	/**
	 * @return the objectList
	 */
	public List<Object> getObjectList() {
		return objectList;
	}

	/**
	 * @param objectList
	 *            the objectList to set
	 */
	public void setObjectList(List<Object> objectList) {
		this.objectList = objectList;
	}

	/**
	 * @return the wildcardList
	 */
	public List<?> getWildcardList() {
		return wildcardList;
	}

	/**
	 * @param wildcardList
	 *            the wildcardList to set
	 */
	public void setWildcardList(List<?> wildcardList) {
		this.wildcardList = wildcardList;
	}

	/**
	 * @return the arrayList
	 */
	public ArrayList getArrayList() {
		return arrayList;
	}

	/**
	 * @param arrayList
	 *            the arrayList to set
	 */
	public void setArrayList(ArrayList arrayList) {
		this.arrayList = arrayList;
	}

	/**
	 * @return the linkedList
	 */
	public LinkedList getLinkedList() {
		return linkedList;
	}

	/**
	 * @param linkedList
	 *            the linkedList to set
	 */
	public void setLinkedList(LinkedList linkedList) {
		this.linkedList = linkedList;
	}

	/**
	 * @return the stack
	 */
	public Stack<Integer> getStack() {
		return stack;
	}

	/**
	 * @param stack
	 *            the stack to set
	 */
	public void setStack(Stack<Integer> stack) {
		this.stack = stack;
	}

	/**
	 * @return the vector
	 */
	public Vector<Float> getVector() {
		return vector;
	}

	/**
	 * @param vector
	 *            the vector to set
	 */
	public void setVector(Vector<Float> vector) {
		this.vector = vector;
	}

	/**
	 * @return the keyList
	 */
	public List<DatastoreKey> getKeyList() {
		return keyList;
	}

	/**
	 * @param keyList
	 *            the keyList to set
	 */
	public void setKeyList(List<DatastoreKey> keyList) {
		this.keyList = keyList;
	}

	/**
	 * @return the geoLocationList
	 */
	public List<GeoLocation> getGeoLocationList() {
		return geoLocationList;
	}

	/**
	 * @param geoLocationList
	 *            the geoLocationList to set
	 */
	public void setGeoLocationList(List<GeoLocation> geoLocationList) {
		this.geoLocationList = geoLocationList;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ListFields)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		ListFields that = (ListFields) obj;

		return Objects.equals(this.booleanList, that.booleanList) && Objects.equals(this.charList, that.charList)
				&& Objects.equals(this.shortList, that.shortList) && Objects.equals(this.intList, that.intList)
				&& Objects.equals(this.longList, that.longList) && Objects.equals(this.stringList, that.stringList)
				&& Objects.equals(this.dateList, that.dateList) && Objects.equals(this.calendarList, that.calendarList)
				&& Objects.equals(this.floatList, that.floatList) && Objects.equals(this.doubleList, that.doubleList)
				// && Objects.deepEquals(this.blobList, that.blobList) &&
				// Objects.deepEquals(this.clobList, that.clobList)
				&& Objects.equals(this.colorList, that.colorList) && Objects.equals(this.objectList, that.objectList)
				&& Objects.equals(this.wildcardList, that.wildcardList)
				&& Objects.equals(this.arrayList, that.arrayList) && Objects.equals(this.linkedList, that.linkedList)
				&& Objects.equals(this.stack, that.stack) && Objects.equals(this.vector, that.vector)
				&& Objects.equals(this.keyList, that.keyList)
				&& Objects.equals(this.geoLocationList, that.geoLocationList);
	}

	public static ListFields getSampleEntity1() {
		ListFields entity = new ListFields();
		entity.setBooleanList(Arrays.asList(true, false, false, true));
		entity.setCharList(Arrays.asList('Z', 'Y', 'X'));
		entity.setShortList(Arrays.asList((short) 3, (short) 33, (short) 333));
		entity.setIntList(Arrays.asList(100, 200, 300));
		entity.setLongList(Arrays.asList(-3L, 0L, 3L, 987654321L));
		entity.setStringList(Arrays.asList("One", "Ten", "Hundred"));
		long millis = System.currentTimeMillis();
		entity.setDateList(Arrays.asList(new Date(millis), new Date(millis + 60000), new Date(millis - 60000)));
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal2.add(Calendar.DATE, 1);
		Calendar cal3 = Calendar.getInstance();
		cal2.add(Calendar.DATE, -1);
		entity.setCalendarList(Arrays.asList(cal1, cal2, cal3));
		entity.setColorList(Arrays.asList(Color.PINK, Color.BLACK, Color.YELLOW));
		entity.setFloatList(Arrays.asList(1.5f, 200f, Float.MAX_VALUE));
		entity.setDoubleList(Arrays.asList(Double.MIN_VALUE, 5000d, Double.MAX_VALUE));
		entity.setBlobList(Arrays.asList("One".getBytes(), "Two".getBytes()));
		entity.setClobList(Arrays.asList("One".toCharArray(), "Two".toCharArray()));
		entity.setObjectList(Arrays.asList("One", 5000L, (Object) 99.99d, Boolean.FALSE, (String) null));
		entity.setWildcardList(Arrays.asList("Hey!", 999L, (Object) 9999.9999d, Boolean.TRUE, (Integer) null));
		ArrayList arrayList = new ArrayList<>();
		arrayList.addAll(Arrays.asList("Hey!", 999L, (Object) 9999.9999d, Boolean.TRUE, (Integer) null));
		entity.setArrayList(arrayList);
		LinkedList linkedList = new LinkedList<>();
		linkedList.addAll(Arrays.asList("Hey!", 999L, (Object) 9999.9999d, Boolean.TRUE, (Integer) null));
		entity.setLinkedList(linkedList);
		Stack<Integer> stack = new Stack<>();
		stack.addAll(Arrays.asList(3, 2, 1));
		entity.setStack(stack);
		Vector vector = new Vector<>();
		vector.addAll(Arrays.asList(1.5f, 2.5f, 4.98f));
		entity.setVector(vector);
		List<GeoLocation> geoLocationList = Arrays.asList(new GeoLocation(40.7142700, -74.0059700),
				new GeoLocation(48.8534100, 2.3488000));
		entity.setGeoLocationList(geoLocationList);
		return entity;
	}

}
