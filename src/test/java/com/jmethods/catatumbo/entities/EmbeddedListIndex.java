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

import java.util.List;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.ParentKey;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class EmbeddedListIndex {

  @Identifier
  private long id;

  @ParentKey
  private DatastoreKey parentKey;

  private List<Address> addresses;

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
   * @return the addresses
   */
  public List<Address> getAddresses() {
    return addresses;
  }

  /**
   * @param addresses
   *          the addresses to set
   */
  public void setAddresses(List<Address> addresses) {
    this.addresses = addresses;
  }

}
