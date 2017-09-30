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
import java.util.List;
import java.util.Objects;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Embedded;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.EntityListener;
import com.jmethods.catatumbo.EntityListeners;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Imploded;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.PostInsert;
import com.jmethods.catatumbo.PostLoad;
import com.jmethods.catatumbo.PreInsert;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.UpdatedTimestamp;
import com.jmethods.catatumbo.entities.ImmutablePerson.ImmutablePersonListener;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
@EntityListeners(ImmutablePersonListener.class)
public class ImmutablePerson {

  @Identifier
  private final long id;

  private final String name;

  @Embedded
  private final ImmutableAddress address;

  @Key
  private final DatastoreKey key;

  @Embedded(optional = true)
  @Imploded
  private ImmutablePhone phoneNumber;

  @Property(optional = true)
  private final List<ImmutablePhone> otherNumbers;

  @UpdatedTimestamp
  @Property(name = "lastChanged")
  private final OffsetDateTime modifiedOn;

  private boolean preInsertFired;
  private boolean postInsertFired;
  private boolean postLoadFired;

  private ImmutablePerson(Builder builder) {
    this.id = builder.id;
    this.name = builder.name;
    this.address = builder.address;
    this.phoneNumber = builder.phoneNumber;
    this.key = builder.key;
    this.modifiedOn = builder.modifiedOn;
    this.otherNumbers = builder.otherNumbers;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public ImmutableAddress getAddress() {
    return address;
  }

  public ImmutablePhone getPhoneNumber() {
    return phoneNumber;
  }

  public DatastoreKey getKey() {
    return key;
  }

  public OffsetDateTime getModifiedOn() {
    return modifiedOn;
  }

  public List<ImmutablePhone> getOtherNumbers() {
    return otherNumbers;
  }

  public boolean isPreInsertFired() {
    return preInsertFired;
  }

  public boolean isPostInsertFired() {
    return postInsertFired;
  }

  public boolean isPostLoadFired() {
    return postLoadFired;
  }

  @Override
  public String toString() {
    StringBuilder builder2 = new StringBuilder();
    builder2.append("ImmutablePerson [id=").append(id).append(", name=").append(name)
        .append(", address=").append(address).append(", key=").append(key).append(", phoneNumber=")
        .append(phoneNumber).append(", otherNumbers=").append(otherNumbers).append(", modifiedOn=")
        .append(modifiedOn).append("]");
    return builder2.toString();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private long id;

    private String name;

    private ImmutableAddress address;

    private ImmutablePhone phoneNumber;

    private List<ImmutablePhone> otherNumbers;

    private DatastoreKey key;

    private OffsetDateTime modifiedOn;

    private Builder() {

    }

    public Builder setId(long id) {
      this.id = id;
      return this;
    }

    public Builder withName(String name) {
      this.name = name;
      return this;
    }

    public Builder setAddress(ImmutableAddress address) {
      this.address = address;
      return this;
    }

    public Builder phoneNumber(ImmutablePhone phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    public Builder setOtherNumbers(List<ImmutablePhone> otherNumbers) {
      this.otherNumbers = otherNumbers;
      return this;
    }

    public Builder setKey(DatastoreKey key) {
      this.key = key;
      return this;
    }

    public Builder setModifiedOn(OffsetDateTime modifiedOn) {
      this.modifiedOn = modifiedOn;
      return this;
    }

    public ImmutablePerson build() {
      return new ImmutablePerson(this);
    }

  }

  @Override
  public boolean equals(Object obj) {
    if (equalsExceptAutoGeneratedFields(obj)) {
      ImmutablePerson that = (ImmutablePerson) obj;
      return Objects.equals(this.id, that.id) && Objects.equals(this.modifiedOn, that.modifiedOn)
          && Objects.equals(this.key, that.key);
    }
    return false;
  }

  public boolean equalsExceptAutoGeneratedFields(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!this.getClass().equals(obj.getClass())) {
      return false;
    }
    ImmutablePerson that = (ImmutablePerson) obj;
    return Objects.equals(this.name, that.name) && Objects.equals(this.address, that.address)
        && Objects.equals(this.phoneNumber, that.phoneNumber)
        && Objects.equals(this.otherNumbers, that.otherNumbers);
  }

  public static ImmutablePerson getSample1() {
    ImmutableZipCode.Builder zipBuilder = ImmutableZipCode.newBuilder();
    zipBuilder.setFiveDigits("55555");
    zipBuilder.setFourDgits("4444");
    ImmutableZipCode zip = zipBuilder.build();

    ImmutablePerson person = ImmutablePerson.newBuilder().withName("John Doe")
        .setAddress(ImmutableAddress.builder().setLine1("1 Main St.").setLine2("Apt 450")
            .setCity("San Francisco").setState("CA").setZipCode(zip).build())
        .phoneNumber(ImmutablePhone.getSample1()).setOtherNumbers(ImmutablePhone.getSampleList())
        .build();
    return person;
  }

  public static ImmutablePerson getSample2() {
    return ImmutablePerson.newBuilder().build();
  }

  public static ImmutablePerson getSample3() {
    ImmutableZipCode.Builder zipBuilder = ImmutableZipCode.newBuilder();
    zipBuilder.setFiveDigits("55555");
    ImmutableZipCode zip = zipBuilder.build();

    ImmutablePerson person = ImmutablePerson.newBuilder().withName("John Doe")
        .setAddress(ImmutableAddress.builder().setLine1("1 Main St.").setLine2("Apt 450")
            .setCity("San Francisco").setState("CA").setZipCode(zip).build())
        .phoneNumber(ImmutablePhone.getSample1()).setOtherNumbers(ImmutablePhone.getSampleList())
        .build();
    return person;
  }

  @PreInsert
  public void beforeInsert() {
    preInsertFired = true;
  }

  @PostInsert
  public void afterInsert() {
    postInsertFired = true;
  }

  @EntityListener
  public static class ImmutablePersonListener {
    public ImmutablePersonListener() {
      // TODO Auto-generated constructor stub
    }

    @PostLoad
    public void afterLoad(ImmutablePerson person) {
      person.postLoadFired = true;
    }
  }

}
