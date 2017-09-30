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
public class NestedMapEntity {

  @Identifier
  private long id;

  private Map nestedMap;

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
   * @return the nestedMap
   */
  public Map getNestedMap() {
    return nestedMap;
  }

  /**
   * @param nestedMap
   *          the nestedMap to set
   */
  public void setNestedMap(Map nestedMap) {
    this.nestedMap = nestedMap;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!this.getClass().equals(obj.getClass())) {
      return false;
    }
    NestedMapEntity that = (NestedMapEntity) obj;
    return this.id == that.id && Objects.equals(this.nestedMap, that.nestedMap);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("NestedMapEntity [id=").append(id).append(", nestedMap=").append(nestedMap)
        .append("]");
    return builder.toString();
  }

  public static NestedMapEntity getSample1() {
    NestedMapEntity entity = new NestedMapEntity();
    Map<String, Object> m1 = new HashMap<>();
    m1.put("string", "Hello World!");
    m1.put("long", 1000L);
    m1.put("null", null);

    Map<String, Object> childMap = new HashMap<>();
    childMap.put("childprop1", "child value 1");
    childMap.put("childprop2", "child value 2");
    childMap.put("childlong", -23L);
    childMap.put("childbool", true);
    childMap.put("childnull", null);

    Map<String, Object> grandchildMap = new HashMap<>();
    grandchildMap.put("grandchildprop1", "grnadchild value 1");
    grandchildMap.put("grandchildprop2", "grandchild value 2");
    grandchildMap.put("grandchildlong", 900L);
    grandchildMap.put("grandchildMapdouble", 9.99);
    grandchildMap.put("grandchildnull", null);

    childMap.put("grandchildMap", grandchildMap);
    m1.put("childMap", childMap);

    entity.setNestedMap(m1);
    return entity;
  }

}
