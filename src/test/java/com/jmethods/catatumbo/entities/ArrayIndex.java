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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.ParentKey;
import com.jmethods.catatumbo.Property;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class ArrayIndex {

  @Identifier
  private long id;
  @ParentKey
  private DatastoreKey parentKey;

  private List<String> stringList;
  @Property(indexed = false)
  private List<String> unindexedStringList;
  private Set<String> stringSet;
  @Property(indexed = false)
  private Set<String> unindexedStringSet;

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
   * @return the parentKey
   */
  public DatastoreKey getParentKey() {
    return parentKey;
  }

  /**
   * @param parentKey
   *          the parentKey to set
   */
  public void setParentKey(DatastoreKey parentKey) {
    this.parentKey = parentKey;
  }

  /**
   * @return the stringList
   */
  public List<String> getStringList() {
    return stringList;
  }

  /**
   * @param stringList
   *          the stringList to set
   */
  public void setStringList(List<String> stringList) {
    this.stringList = stringList;
  }

  /**
   * @return the unindexedStringList
   */
  public List<String> getUnindexedStringList() {
    return unindexedStringList;
  }

  /**
   * @param unindexedStringList
   *          the unindexedStringList to set
   */
  public void setUnindexedStringList(List<String> unindexedStringList) {
    this.unindexedStringList = unindexedStringList;
  }

  /**
   * @return the stringSet
   */
  public Set<String> getStringSet() {
    return stringSet;
  }

  /**
   * @param stringSet
   *          the stringSet to set
   */
  public void setStringSet(Set<String> stringSet) {
    this.stringSet = stringSet;
  }

  /**
   * @return the unindexedStringSet
   */
  public Set<String> getUnindexedStringSet() {
    return unindexedStringSet;
  }

  /**
   * @param unindexedStringSet
   *          the unindexedStringSet to set
   */
  public void setUnindexedStringSet(Set<String> unindexedStringSet) {
    this.unindexedStringSet = unindexedStringSet;
  }

  public static ArrayIndex getSampleEntity() {
    List<String> items = Arrays.asList("One", "Two", "Three");
    ArrayIndex entity = new ArrayIndex();
    entity.setStringList(items);
    entity.setUnindexedStringList(items);
    entity.setStringSet(new HashSet<>(items));
    entity.setUnindexedStringSet(new HashSet<>(items));
    return entity;
  }

}
