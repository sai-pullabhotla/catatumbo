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

package com.jmethods.catatumbo.impl;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Represents an embedded object of an entity or a nested embedded object.
 * 
 * @author Sai Pullabhotla
 *
 */
public class EmbeddedField {

  /**
   * The underlying field
   */
  private Field field;

  /**
   * Parent embedded field, if any
   */
  private EmbeddedField parent;

  /**
   * Qualified name of this embedded field (e.g. If city is a field in Address class and Address is
   * embedded in a class called Customer as billingAddress, the qualified name of this field would
   * be billingAddress.city).
   */
  private String qualifiedName;

  /**
   * Creates a new instance of <code>EmbeddedField</code>.
   * 
   * @param field
   *          the underlying field
   */
  public EmbeddedField(Field field) {
    this(field, null);
  }

  /**
   * Creates a new instance of <code>EmbeddedField</code>.
   * 
   * @param field
   *          the underlying field
   * @param parent
   *          the parent embedded field. May be <code>null</code>.
   */
  public EmbeddedField(Field field, EmbeddedField parent) {
    this.field = field;
    this.parent = parent;
    computeQualifiedName();
  }

  /**
   * Returns the underlying field.
   * 
   * @return the field the underlying field.
   */
  public Field getField() {
    return field;
  }

  /**
   * Returns the parent field.
   * 
   * @return the parent field.
   */
  public EmbeddedField getParent() {
    return parent;
  }

  /**
   * Returns the qualified name of this embedded field.
   * 
   * @return the qualified name of this embedded field.
   */
  public String getQualifiedName() {
    return qualifiedName;
  }

  /**
   * Returns the type of this field.
   * 
   * @return the type of this field.
   */
  public Class<?> getType() {
    return field.getType();
  }

  /**
   * Returns the name of this field.
   * 
   * @return the name of this field.
   */
  public String getName() {
    return field.getName();
  }

  /**
   * Returns the class in which this field is declared.
   * 
   * @return the class in which this field is declared.
   */
  public Class<?> getDeclaringClass() {
    return field.getDeclaringClass();
  }

  /**
   * Computes and sets the qualified name of this field.
   */
  private void computeQualifiedName() {
    if (parent != null) {
      qualifiedName = parent.qualifiedName + "." + field.getName();
    } else {
      qualifiedName = field.getName();
    }
  }

  @Override
  public String toString() {
    return qualifiedName;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof EmbeddedField)) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    EmbeddedField that = (EmbeddedField) obj;
    return Objects.equals(this.field, that.field) && Objects.equals(this.parent, that.parent);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, parent);
  }

}
