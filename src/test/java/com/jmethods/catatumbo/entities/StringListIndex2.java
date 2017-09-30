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

import java.util.LinkedList;
import java.util.List;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.PropertyIndexer;
import com.jmethods.catatumbo.SecondaryIndex;
import com.jmethods.catatumbo.indexers.UpperCaseStringListIndexer;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity(kind = "StringListIndex")
public class StringListIndex2 {

  @Identifier
  private long id;

  @SecondaryIndex
  private List<String> colors;

  @SecondaryIndex
  @PropertyIndexer(UpperCaseStringListIndexer.class)
  private LinkedList<String> sizes;

  @Property(name = "$colors")
  private List<String> colorsIndex;

  @Property(name = "$sizes")
  private LinkedList<String> sizesIndex;

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
   * @return the colors
   */
  public List<String> getColors() {
    return colors;
  }

  /**
   * @param colors
   *          the colors to set
   */
  public void setColors(List<String> colors) {
    this.colors = colors;
  }

  /**
   * @return the sizes
   */
  public LinkedList<String> getSizes() {
    return sizes;
  }

  /**
   * @param sizes
   *          the sizes to set
   */
  public void setSizes(LinkedList<String> sizes) {
    this.sizes = sizes;
  }

  /**
   * @return the colorsIndex
   */
  public List<String> getColorsIndex() {
    return colorsIndex;
  }

  /**
   * @param colorsIndex
   *          the colorsIndex to set
   */
  public void setColorsIndex(List<String> colorsIndex) {
    this.colorsIndex = colorsIndex;
  }

  /**
   * @return the sizesIndex
   */
  public LinkedList<String> getSizesIndex() {
    return sizesIndex;
  }

  /**
   * @param sizesIndex
   *          the sizesIndex to set
   */
  public void setSizesIndex(LinkedList<String> sizesIndex) {
    this.sizesIndex = sizesIndex;
  }

}
