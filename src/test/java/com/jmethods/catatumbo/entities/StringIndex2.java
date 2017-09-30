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

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.PropertyIndexer;
import com.jmethods.catatumbo.SecondaryIndex;
import com.jmethods.catatumbo.indexers.UpperCaseStringIndexer;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity(kind = "StringIndex")
public class StringIndex2 {
  @Identifier
  private long id;

  @SecondaryIndex
  private String firstName;

  @SecondaryIndex
  @PropertyIndexer(UpperCaseStringIndexer.class)
  private String lastName;

  @SecondaryIndex(name = "emailIndex")
  private String email = "x";

  @Property(name = "$firstName")
  private String firstNameIndex = "y";

  @Property(name = "$lastName")
  private String lastNameIndex;

  private String emailIndex = "z";

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

  /**
   * @return the firstNameIndex
   */
  public String getFirstNameIndex() {
    return firstNameIndex;
  }

  /**
   * @param firstNameIndex
   *          the firstNameIndex to set
   */
  public void setFirstNameIndex(String firstNameIndex) {
    this.firstNameIndex = firstNameIndex;
  }

  /**
   * @return the lastNameIndex
   */
  public String getLastNameIndex() {
    return lastNameIndex;
  }

  /**
   * @param lastNameIndex
   *          the lastNameIndex to set
   */
  public void setLastNameIndex(String lastNameIndex) {
    this.lastNameIndex = lastNameIndex;
  }

  /**
   * @return the emailIndex
   */
  public String getEmailIndex() {
    return emailIndex;
  }

  /**
   * @param emailIndex
   *          the emailIndex to set
   */
  public void setEmailIndex(String emailIndex) {
    this.emailIndex = emailIndex;
  }

}
