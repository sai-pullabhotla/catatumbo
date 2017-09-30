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

import java.time.OffsetDateTime;

import com.jmethods.catatumbo.CreatedTimestamp;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.UpdatedTimestamp;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class AutoTimestampOffsetDateTime {

  @Identifier
  private long id;

  private String name;

  @CreatedTimestamp
  private OffsetDateTime createdOn;

  @UpdatedTimestamp
  private OffsetDateTime modifiedOn;

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
   * @return the createdOn
   */
  public OffsetDateTime getCreatedOn() {
    return createdOn;
  }

  /**
   * @param createdOn
   *          the createdOn to set
   */
  public void setCreatedOn(OffsetDateTime createdOn) {
    this.createdOn = createdOn;
  }

  /**
   * @return the modifiedOn
   */
  public OffsetDateTime getModifiedOn() {
    return modifiedOn;
  }

  /**
   * @param modifiedOn
   *          the modifiedOn to set
   */
  public void setModifiedOn(OffsetDateTime modifiedOn) {
    this.modifiedOn = modifiedOn;
  }

  @Override
  public String toString() {
    return "AutoTimestampOffsetDateTime [id=" + id + ", name=" + name + ", createdOn=" + createdOn
        + ", modifiedOn=" + modifiedOn + "]";
  }

}
