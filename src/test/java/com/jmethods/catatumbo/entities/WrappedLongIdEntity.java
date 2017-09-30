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

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class WrappedLongIdEntity {

  @Identifier
  private WrappedLongId id;

  private String name;

  /**
   * @return the id
   */
  public WrappedLongId getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(WrappedLongId id) {
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("User [id=").append(id).append(", name=").append(name).append("]");
    return builder.toString();
  }

  public static WrappedLongIdEntity getSample1() {
    WrappedLongIdEntity user = new WrappedLongIdEntity();
    user.setName("johndoe");
    return user;
  }

  public static WrappedLongIdEntity getSample2() {
    WrappedLongIdEntity user = new WrappedLongIdEntity();
    user.setId(new WrappedLongId(0L));
    user.setName("johndoe");
    return user;
  }

  public static WrappedLongIdEntity getSample3() {
    WrappedLongIdEntity user = new WrappedLongIdEntity();
    user.setId(new WrappedLongId(50000L));
    user.setName("johndoe");
    return user;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || !this.getClass().equals(obj.getClass())) {
      return false;
    }
    WrappedLongIdEntity that = (WrappedLongIdEntity) obj;
    return Objects.equals(this.id, that.id) && Objects.equals(this.name, that.name);
  }

}
