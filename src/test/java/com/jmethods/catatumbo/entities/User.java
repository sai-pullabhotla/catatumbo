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

import java.util.Objects;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Key;

/**
 * @author Sai Pullabhotla
 *
 */

@Entity
public class User {

  @Identifier
  private UserId id;

  @Key
  private DatastoreKey key;

  private String name;

  /**
   * @return the id
   */
  public UserId getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(UserId id) {
    this.id = id;
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || !this.getClass().equals(obj.getClass())) {
      return false;
    }
    User that = (User) obj;
    return Objects.equals(this.id, that.id) && Objects.equals(this.key, that.key)
        && Objects.equals(this.name, that.name);
  }

  public static User getSample1() {
    User entity = new User();
    entity.setName("John Doe");
    return entity;
  }

  public static User getSample2() {
    User entity = new User();
    entity.setId(new UserId(0));
    entity.setName("John Doe");
    return entity;
  }

}
