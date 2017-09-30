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
import com.jmethods.catatumbo.Version;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class OptimisticLockBad2 {

  @Identifier
  private long id;

  private String name;

  @Version
  private long version = 1;

  @Version
  private long anotherVersion = 1;

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
   * @return the version
   */
  public long getVersion() {
    return version;
  }

  /**
   * @param version
   *          the version to set
   */
  public void setVersion(long version) {
    this.version = version;
  }

  /**
   * @return the anotherVersion
   */
  public long getAnotherVersion() {
    return anotherVersion;
  }

  /**
   * @param anotherVersion
   *          the anotherVersion to set
   */
  public void setAnotherVersion(long anotherVersion) {
    this.anotherVersion = anotherVersion;
  }
}
