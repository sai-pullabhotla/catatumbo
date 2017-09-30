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

import java.util.Date;

import com.jmethods.catatumbo.CreatedTimestamp;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.MappedSuperClass;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.UpdatedTimestamp;
import com.jmethods.catatumbo.Version;

/**
 * @author Sai Pullabhotla
 *
 */
@MappedSuperClass
public class ImmutableSuperClass {

  @Identifier
  private String id;

  @Key
  private DatastoreKey key;

  @CreatedTimestamp
  @Property(name = "createdTS")
  private Date createdTimestamp;

  @UpdatedTimestamp
  @Property(name = "updatedTS")
  private Date modifiedTimestamp;

  @Version
  private long version;

  private ImmutableSuperClass() {

  }

  public ImmutableSuperClass(Builder builder) {
    this.id = builder.id;
    this.key = builder.key;
    this.createdTimestamp = builder.createdTimestamp;
    this.modifiedTimestamp = builder.modifiedTimestamp;
    this.version = builder.version;
  }

  public String getId() {
    return id;
  }

  public DatastoreKey getKey() {
    return key;
  }

  public Date getCreatedTimestamp() {
    return createdTimestamp;
  }

  public Date getModifiedTimestamp() {
    return modifiedTimestamp;
  }

  public long getVersion() {
    return version;
  }

  public static Builder builder() {
    return new Builder<>();
  }

  public static class Builder<T extends Builder<T>> {
    private String id;

    private DatastoreKey key;

    private Date createdTimestamp;

    private Date modifiedTimestamp;

    private long version;

    public T setId(String id) {
      this.id = id;
      return (T) this;
    }

    public T key(DatastoreKey key) {
      this.key = key;
      return (T) this;
    }

    public T setCreatedTimestamp(Date createdTimestamp) {
      this.createdTimestamp = createdTimestamp;
      return (T) this;
    }

    public T setModifiedTimestamp(Date modifiedTimestamp) {
      this.modifiedTimestamp = modifiedTimestamp;
      return (T) this;
    }

    public T withVersion(long version) {
      this.version = version;
      return (T) this;
    }

    public ImmutableSuperClass build() {
      return new ImmutableSuperClass(this);
    }

  }

}
