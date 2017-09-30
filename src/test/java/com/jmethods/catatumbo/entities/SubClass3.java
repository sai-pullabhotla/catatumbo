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
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.PropertyOverride;
import com.jmethods.catatumbo.PropertyOverrides;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
@PropertyOverrides({
    @PropertyOverride(name = "fieldx", property = @Property(name = "myfieldx", indexed = true)),
    @PropertyOverride(name = "address.city", property = @Property(name = "mycity", indexed = true)),
    @PropertyOverride(name = "address.zipCode.fourDigits", property = @Property(name = "zip_four_digits", indexed = true)), })
public class SubClass3 extends SuperClass3 {

  @Identifier
  private long id;
  private String name;

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
    if (obj == null) {
      return false;
    }
    if (this == obj) {
      return true;
    }
    SubClass3 that = (SubClass3) obj;
    return Objects.equals(this.id, that.id) && Objects.equals(this.name, that.name)
        && Objects.equals(this.getCreatedBy(), that.getCreatedBy())
        && Objects.equals(this.getCreatedOn(), that.getCreatedOn())
        && Objects.equals(this.getModifiedBy(), that.getModifiedBy())
        && Objects.equals(this.getModifiedOn(), that.getModifiedOn())
        && Objects.equals(this.getFieldx(), that.getFieldx())
        && Objects.equals(this.getAddress(), that.getAddress());
  }

}
