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

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.PropertyIndexer;
import com.jmethods.catatumbo.SecondaryIndex;
import com.jmethods.catatumbo.indexers.UpperCaseStringIndexer;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class StringIndex {
  @Identifier
  private long id;

  @SecondaryIndex
  private String firstName;

  @SecondaryIndex
  @PropertyIndexer(UpperCaseStringIndexer.class)
  private String lastName;

  @SecondaryIndex(name = "emailIndex")
  private String email;

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
   * @return the firstName
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * @param firstName
   *          the firstName to set
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
   *          the lastName to set
   */
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email
   *          the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof StringIndex)) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    StringIndex that = (StringIndex) obj;
    return this.id == that.id && Objects.equals(this.firstName, that.firstName)
        && Objects.equals(this.lastName, that.lastName) && Objects.equals(this.email, that.email);
  }

  public static StringIndex getSample1() {
    StringIndex entity = new StringIndex();
    entity.setFirstName("John");
    entity.setLastName("Doe");
    entity.setEmail("John.Doe@Example.com");
    return entity;
  }

}
