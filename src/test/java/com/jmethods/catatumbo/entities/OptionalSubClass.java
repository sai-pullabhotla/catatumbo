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

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.PropertyOverride;
import com.jmethods.catatumbo.PropertyOverrides;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
@PropertyOverrides({ @PropertyOverride(name = "createdBy", property = @Property(optional = true)) })
public class OptionalSubClass extends SuperClass1 {

  @Identifier
  private String id;

  private String name;

  @Key
  private DatastoreKey key;

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the key
   */
  public DatastoreKey getKey() {
    return key;
  }

  /**
   * @param key
   *          the key to set
   */
  public void setKey(DatastoreKey key) {
    this.key = key;
  }

}
