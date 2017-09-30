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

import com.jmethods.catatumbo.CreatedTimestamp;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.UpdatedTimestamp;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class AutoTimestampLong {

  @Identifier
  private long id;

  private String name;

  @CreatedTimestamp
  @Property(name = "CREATED_TS", indexed = false)
  private Long createdDate;

  @UpdatedTimestamp
  @Property(name = "UPDATED_TS", indexed = true)
  private Long modifiedDate;

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

  /**
   * @return the createdDate
   */
  public Long getCreatedDate() {
    return createdDate;
  }

  /**
   * @param createdDate
   *          the createdDate to set
   */
  public void setCreatedDate(Long createdDate) {
    this.createdDate = createdDate;
  }

  /**
   * @return the modifiedDate
   */
  public Long getModifiedDate() {
    return modifiedDate;
  }

  /**
   * @param modifiedDate
   *          the modifiedDate to set
   */
  public void setModifiedDate(Long modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "AutoTimestampDate [id=" + id + ", name=" + name + ", createdDate=" + createdDate
        + ", modifiedDate=" + modifiedDate + "]";
  }

}
