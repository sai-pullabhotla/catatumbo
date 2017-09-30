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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.GeoLocation;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class MapFields {
  @Identifier
  private long id;

  private Map<String, Boolean> booleanMap;
  private Map<String, Character> charMap;
  private Map<String, Short> shortMap;
  private Map<String, Integer> intMap;
  private Map<String, Long> longMap;
  private Map<String, String> stringMap;
  private Map<String, Color> enumMap;
  private Map<String, Float> floatMap;
  private Map<String, Double> doubleMap;
  private Map<String, Date> dateMap;
  private Map<String, Calendar> calendarMap;
  private Map<String, byte[]> blobMap;
  private Map<String, char[]> clobMap;
  private Map<String, GeoLocation> geoLocationMap;
  private Map<String, DatastoreKey> keyMap;
  private Map<String, Object> objectMap;
  private Map genericMap;
  private Map<String, ?> wildcardValueMap;
  private Map<?, ?> wildcardMap;
  private HashMap<String, String> hashMap;
  private SortedMap<String, Integer> sortedMap;
  private LinkedHashMap<String, Long> linkedHashMap;
  private TreeMap<String, Double> treeMap;
  private Map<String, LocalDate> localDateMap;

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @return the booleanMap
   */
  public Map<String, Boolean> getBooleanMap() {
    return booleanMap;
  }

  /**
   * @param booleanMap
   *          the booleanMap to set
   */
  public void setBooleanMap(Map<String, Boolean> booleanMap) {
    this.booleanMap = booleanMap;
  }

  /**
   * @return the charMap
   */
  public Map<String, Character> getCharMap() {
    return charMap;
  }

  /**
   * @param charMap
   *          the charMap to set
   */
  public void setCharMap(Map<String, Character> charMap) {
    this.charMap = charMap;
  }

  /**
   * @return the shortMap
   */
  public Map<String, Short> getShortMap() {
    return shortMap;
  }

  /**
   * @param shortMap
   *          the shortMap to set
   */
  public void setShortMap(Map<String, Short> shortMap) {
    this.shortMap = shortMap;
  }

  /**
   * @return the intMap
   */
  public Map<String, Integer> getIntMap() {
    return intMap;
  }

  /**
   * @param intMap
   *          the intMap to set
   */
  public void setIntMap(Map<String, Integer> intMap) {
    this.intMap = intMap;
  }

  /**
   * @return the longMap
   */
  public Map<String, Long> getLongMap() {
    return longMap;
  }

  /**
   * @param longMap
   *          the longMap to set
   */
  public void setLongMap(Map<String, Long> longMap) {
    this.longMap = longMap;
  }

  /**
   * @return the stringMap
   */
  public Map<String, String> getStringMap() {
    return stringMap;
  }

  /**
   * @param stringMap
   *          the stringMap to set
   */
  public void setStringMap(Map<String, String> stringMap) {
    this.stringMap = stringMap;
  }

  /**
   * @return the enumMap
   */
  public Map<String, Color> getEnumMap() {
    return enumMap;
  }

  /**
   * @param enumMap
   *          the enumMap to set
   */
  public void setEnumMap(Map<String, Color> enumMap) {
    this.enumMap = enumMap;
  }

  /**
   * @return the floatMap
   */
  public Map<String, Float> getFloatMap() {
    return floatMap;
  }

  /**
   * @param floatMap
   *          the floatMap to set
   */
  public void setFloatMap(Map<String, Float> floatMap) {
    this.floatMap = floatMap;
  }

  /**
   * @return the doubleMap
   */
  public Map<String, Double> getDoubleMap() {
    return doubleMap;
  }

  /**
   * @param doubleMap
   *          the doubleMap to set
   */
  public void setDoubleMap(Map<String, Double> doubleMap) {
    this.doubleMap = doubleMap;
  }

  /**
   * @return the dateMap
   */
  public Map<String, Date> getDateMap() {
    return dateMap;
  }

  /**
   * @param dateMap
   *          the dateMap to set
   */
  public void setDateMap(Map<String, Date> dateMap) {
    this.dateMap = dateMap;
  }

  /**
   * @return the calendarMap
   */
  public Map<String, Calendar> getCalendarMap() {
    return calendarMap;
  }

  /**
   * @param calendarMap
   *          the calendarMap to set
   */
  public void setCalendarMap(Map<String, Calendar> calendarMap) {
    this.calendarMap = calendarMap;
  }

  /**
   * @return the blobMap
   */
  public Map<String, byte[]> getBlobMap() {
    return blobMap;
  }

  /**
   * @param blobMap
   *          the blobMap to set
   */
  public void setBlobMap(Map<String, byte[]> blobMap) {
    this.blobMap = blobMap;
  }

  /**
   * @return the clobMap
   */
  public Map<String, char[]> getClobMap() {
    return clobMap;
  }

  /**
   * @param clobMap
   *          the clobMap to set
   */
  public void setClobMap(Map<String, char[]> clobMap) {
    this.clobMap = clobMap;
  }

  /**
   * @return the geoLocationMap
   */
  public Map<String, GeoLocation> getGeoLocationMap() {
    return geoLocationMap;
  }

  /**
   * @param geoLocationMap
   *          the geoLocationMap to set
   */
  public void setGeoLocationMap(Map<String, GeoLocation> geoLocationMap) {
    this.geoLocationMap = geoLocationMap;
  }

  /**
   * @return the keyMap
   */
  public Map<String, DatastoreKey> getKeyMap() {
    return keyMap;
  }

  /**
   * @param keyMap
   *          the keyMap to set
   */
  public void setKeyMap(Map<String, DatastoreKey> keyMap) {
    this.keyMap = keyMap;
  }

  /**
   * @return the objectMap
   */
  public Map<String, Object> getObjectMap() {
    return objectMap;
  }

  /**
   * @param objectMap
   *          the objectMap to set
   */
  public void setObjectMap(Map<String, Object> objectMap) {
    this.objectMap = objectMap;
  }

  /**
   * @return the genericMap
   */
  public Map getGenericMap() {
    return genericMap;
  }

  /**
   * @param genericMap
   *          the genericMap to set
   */
  public void setGenericMap(Map genericMap) {
    this.genericMap = genericMap;
  }

  /**
   * @return the wildcardValueMap
   */
  public Map<String, ?> getWildcardValueMap() {
    return wildcardValueMap;
  }

  /**
   * @param wildcardValueMap
   *          the wildcardValueMap to set
   */
  public void setWildcardValueMap(Map<String, ?> wildcardValueMap) {
    this.wildcardValueMap = wildcardValueMap;
  }

  /**
   * @return the wildcardMap
   */
  public Map<?, ?> getWildcardMap() {
    return wildcardMap;
  }

  /**
   * @param wildcardMap
   *          the wildcardMap to set
   */
  public void setWildcardMap(Map<?, ?> wildcardMap) {
    this.wildcardMap = wildcardMap;
  }

  /**
   * @return the hasMap
   */
  public HashMap<String, String> getHashMap() {
    return hashMap;
  }

  /**
   * @param hashMap
   *          the hasMap to set
   */
  public void setHashMap(HashMap<String, String> hashMap) {
    this.hashMap = hashMap;
  }

  /**
   * @return the sortedMap
   */
  public SortedMap<String, Integer> getSortedMap() {
    return sortedMap;
  }

  /**
   * @param sortedMap
   *          the sortedMap to set
   */
  public void setSortedMap(SortedMap<String, Integer> sortedMap) {
    this.sortedMap = sortedMap;
  }

  /**
   * @return the linkedHashMap
   */
  public LinkedHashMap<String, Long> getLinkedHashMap() {
    return linkedHashMap;
  }

  /**
   * @param linkedHashMap
   *          the linkedHashMap to set
   */
  public void setLinkedHashMap(LinkedHashMap<String, Long> linkedHashMap) {
    this.linkedHashMap = linkedHashMap;
  }

  /**
   * @return the treeMap
   */
  public TreeMap<String, Double> getTreeMap() {
    return treeMap;
  }

  /**
   * @param treeMap
   *          the treeMap to set
   */
  public void setTreeMap(TreeMap<String, Double> treeMap) {
    this.treeMap = treeMap;
  }

  /**
   * @return the localDateMap
   */
  public Map<String, LocalDate> getLocalDateMap() {
    return localDateMap;
  }

  /**
   * @param localDateMap
   *          the localDateMap to set
   */
  public void setLocalDateMap(Map<String, LocalDate> localDateMap) {
    this.localDateMap = localDateMap;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof MapFields)) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    MapFields that = (MapFields) obj;
    return Objects.equals(this.booleanMap, that.booleanMap)
        && Objects.equals(this.charMap, that.charMap)
        && Objects.equals(this.shortMap, that.shortMap) && Objects.equals(this.intMap, that.intMap)
        && Objects.equals(this.longMap, that.longMap)
        && Objects.equals(this.stringMap, that.stringMap)
        && Objects.equals(this.enumMap, that.enumMap)
        && Objects.equals(this.floatMap, that.floatMap)
        && Objects.equals(this.doubleMap, that.doubleMap)
        && Objects.equals(this.dateMap, that.dateMap)
        && Objects.equals(this.calendarMap, that.calendarMap)
        && Objects.equals(this.geoLocationMap, that.geoLocationMap)
        && Objects.equals(this.keyMap, that.keyMap)
        && Objects.equals(this.objectMap, that.objectMap)
        && Objects.equals(this.genericMap, that.genericMap)
        && Objects.equals(this.wildcardValueMap, that.wildcardValueMap)
        && Objects.equals(this.wildcardMap, that.wildcardMap)
        && Objects.equals(this.hashMap, that.hashMap)
        && Objects.equals(this.sortedMap, that.sortedMap)
        && Objects.equals(this.linkedHashMap, that.linkedHashMap)
        && Objects.equals(this.treeMap, that.treeMap)
        && Objects.equals(this.localDateMap, that.localDateMap);
  }

  public static MapFields getSampleEntity1() {
    MapFields entity = new MapFields();

    Map<String, Boolean> booleanMap = new HashMap<>();
    booleanMap.put("isTall", Boolean.FALSE);
    booleanMap.put("isTricky", Boolean.TRUE);
    booleanMap.put("isHelpful", null);
    entity.setBooleanMap(booleanMap);

    Map<String, Character> charMap = new HashMap<>();
    charMap.put("a", 'Z');
    charMap.put("b", 'Y');
    charMap.put(" ", null);
    entity.setCharMap(charMap);

    Map<String, Short> shortMap = new HashMap<>();
    shortMap.put("binary", (short) 2);
    shortMap.put("octal", (short) 8);
    shortMap.put("decimal", (short) 10);
    shortMap.put("hexadecimal", (short) 16);
    shortMap.put("unknown", null);
    entity.setShortMap(shortMap);

    Map<String, Integer> intMap = new HashMap<>();
    intMap.put("binary", 2);
    intMap.put("octal", 8);
    intMap.put("decimal", 10);
    intMap.put("hexadecimal", 16);
    shortMap.put("unknown", null);
    entity.setIntMap(intMap);

    Map<String, Long> longMap = new HashMap<>();
    longMap.put("revenue", 10000000000000L);
    longMap.put("proft", -10000000L);
    longMap.put("forecast", null);
    entity.setLongMap(longMap);

    Map<String, String> stringMap = new HashMap<>();
    stringMap.put("hair-color", "black");
    stringMap.put("eye-color", "brown");
    stringMap.put("licene-plate", null);
    entity.setStringMap(stringMap);

    Map<String, Color> enumMap = new HashMap<>();
    enumMap.put("#1", Color.BLACK);
    enumMap.put("#2", Color.WHITE);
    enumMap.put("#3", null);
    entity.setEnumMap(enumMap);

    Map<String, Float> floatMap = new HashMap<>();
    floatMap.put("pi", 3.14f);
    floatMap.put("log(10)", 1f);
    floatMap.put("log(0)", null);
    entity.setFloatMap(floatMap);

    Map<String, Double> doubleMap = new HashMap<>();
    doubleMap.put("pi", 3.14);
    doubleMap.put("log(10)", 1d);
    doubleMap.put("log(0)", null);
    entity.setDoubleMap(doubleMap);

    Map<String, Date> dateMap = new HashMap<>();
    Calendar today = Calendar.getInstance();
    Calendar tomorrow = Calendar.getInstance();
    tomorrow.add(Calendar.DATE, 1);
    dateMap.put("today", today.getTime());
    dateMap.put("tomorrow", tomorrow.getTime());
    dateMap.put("end", null);
    entity.setDateMap(dateMap);

    Map<String, Calendar> calendarMap = new HashMap<>();
    calendarMap.put("today", today);
    calendarMap.put("tomorrow", tomorrow);
    calendarMap.put("end", null);
    entity.setCalendarMap(calendarMap);

    Map<String, byte[]> blobMap = new HashMap<>();
    blobMap.put("pwd", "secret".getBytes());
    blobMap.put("pin", "0000".getBytes());
    blobMap.put("ssn", null);
    entity.setBlobMap(blobMap);

    Map<String, char[]> clobMap = new HashMap<>();
    clobMap.put("pwd", "secret".toCharArray());
    clobMap.put("pin", "1111".toCharArray());
    clobMap.put("ssn", null);
    entity.setClobMap(clobMap);

    Map<String, GeoLocation> geoLocationMap = new HashMap<>();
    geoLocationMap.put("New York City", new GeoLocation(40.7142700, -74.0059700));
    geoLocationMap.put("Paris", new GeoLocation(48.8534100, 2.3488000));
    geoLocationMap.put("Nowhere", null);
    entity.setGeoLocationMap(geoLocationMap);

    Map<String, Object> objectMap = new HashMap<>();
    objectMap.put("String", "Hello, World!");
    objectMap.put("Long", 999999999999L);
    objectMap.put("Double", 10.589);
    objectMap.put("Boolean", Boolean.FALSE);
    objectMap.put("GeoLocation", new GeoLocation(48.8534100, 2.3488000));
    objectMap.put("Null", null);
    entity.setObjectMap(objectMap);

    Map genericMap = new HashMap<>();
    genericMap.put("String", "Hello, World!");
    genericMap.put("Long", 999999999999L);
    genericMap.put("Double", 10.589);
    genericMap.put("Boolean", Boolean.FALSE);
    genericMap.put("GeoLocation", new GeoLocation(48.8534100, 2.3488000));
    genericMap.put("Null", null);
    entity.setGenericMap(genericMap);

    Map wildcardValueMap = new HashMap<>();
    wildcardValueMap.put("String", "Hello, World!");
    wildcardValueMap.put("Long", 999999999999L);
    wildcardValueMap.put("Double", 10.589);
    wildcardValueMap.put("Boolean", Boolean.FALSE);
    wildcardValueMap.put("GeoLocation", new GeoLocation(48.8534100, 2.3488000));
    wildcardValueMap.put("Null", null);
    entity.setWildcardValueMap(wildcardValueMap);

    Map wildcardMap = new HashMap<>();
    wildcardMap.put("String", "Hello, World!");
    wildcardMap.put("Long", 999999999999L);
    wildcardMap.put("Double", 10.589);
    wildcardMap.put("Boolean", Boolean.FALSE);
    wildcardMap.put("GeoLocation", new GeoLocation(48.8534100, 2.3488000));
    wildcardMap.put("Null", null);
    entity.setWildcardMap(wildcardMap);

    HashMap<String, String> hashMap = new HashMap<>();
    hashMap.put("en", "Hello");
    hashMap.put("es", "Hola");
    hashMap.put("hi", "Namste");
    hashMap.put("el", null);
    entity.setHashMap(hashMap);

    SortedMap<String, Integer> sortedMap = new TreeMap<>();
    sortedMap.put("A", 1);
    sortedMap.put("C", 3);
    sortedMap.put("B", 2);
    sortedMap.put(" ", 0);
    entity.setSortedMap(sortedMap);

    LinkedHashMap<String, Long> linkedHashMap = new LinkedHashMap<>();
    linkedHashMap.put("Hundred", 100L);
    linkedHashMap.put("Thousand", 1000L);
    linkedHashMap.put("Million", 1000000L);
    entity.setLinkedHashMap(linkedHashMap);

    TreeMap<String, Double> treeMap = new TreeMap<>();
    treeMap.put("Hundred", 100d);
    treeMap.put("Thousand", 1000d);
    treeMap.put("Million", 1000000d);
    entity.setTreeMap(treeMap);

    Map<String, LocalDate> localDateMap = new HashMap<>();
    LocalDate todayLocalDate = LocalDate.now();
    LocalDate yesterdayLocalDate = todayLocalDate.minusDays(1);
    LocalDate nextYearLocalDate = todayLocalDate.plusYears(1);
    localDateMap.put("today", todayLocalDate);
    localDateMap.put("yesterday", yesterdayLocalDate);
    localDateMap.put("nextYear", nextYearLocalDate);
    localDateMap.put("earthFormedOn", null);
    entity.setLocalDateMap(localDateMap);

    return entity;
  }

}
