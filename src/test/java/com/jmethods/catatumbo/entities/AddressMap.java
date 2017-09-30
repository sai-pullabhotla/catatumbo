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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class AddressMap {

  @Identifier
  private long id;

  private Map<String, Address> addresses;

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
   * @return the addresses
   */
  public Map<String, Address> getAddresses() {
    return addresses;
  }

  /**
   * @param addresses
   *          the addresses to set
   */
  public void setAddresses(Map<String, Address> addresses) {
    this.addresses = addresses;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof AddressMap)) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    AddressMap that = (AddressMap) obj;
    return this.id == that.id && Objects.equals(this.addresses, that.addresses);
  }

  public static AddressMap getSample1() {
    AddressMap entity = new AddressMap();
    Map<String, Address> addresses = new HashMap<>();
    addresses.put("home", Address.getSample1());
    addresses.put("work", Address.getSample2());
    entity.setAddresses(addresses);
    return entity;
  }

  public static AddressMap getSample2() {
    AddressMap entity = new AddressMap();
    Map<String, Address> addresses = new HashMap<>();
    addresses.put("home", Address.getSample1());
    addresses.put("work", Address.getSample2());
    addresses.put("nullZip", Address.getSample3());
    entity.setAddresses(addresses);
    return entity;
  }

}
