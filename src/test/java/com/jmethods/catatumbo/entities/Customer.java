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

import com.jmethods.catatumbo.Embedded;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.PropertyOverride;
import com.jmethods.catatumbo.PropertyOverrides;

@Entity
@PropertyOverrides({
    @PropertyOverride(name = "billingAddress.street1", property = @Property(name = "ba_line1", indexed = false)),
    @PropertyOverride(name = "billingAddress.street2", property = @Property(name = "ba_line2", indexed = false)),
    @PropertyOverride(name = "billingAddress.city", property = @Property(name = "ba_city", indexed = false)),
    @PropertyOverride(name = "billingAddress.state", property = @Property(name = "ba_state", indexed = false)),
    @PropertyOverride(name = "billingAddress.zipCode.fiveDigits", property = @Property(name = "ba_zip")),
    @PropertyOverride(name = "billingAddress.zipCode.fourDigits", property = @Property(name = "ba_zipx")),
    @PropertyOverride(name = "shippingAddress.street1", property = @Property(name = "sa_line1", indexed = false)),
    @PropertyOverride(name = "shippingAddress.street2", property = @Property(name = "sa_line2", indexed = false)),
    @PropertyOverride(name = "shippingAddress.city", property = @Property(name = "sa_city", indexed = false)),
    @PropertyOverride(name = "shippingAddress.state", property = @Property(name = "sa_state", indexed = false)),
    @PropertyOverride(name = "shippingAddress.zipCode.fiveDigits", property = @Property(name = "sa_zip")),
    @PropertyOverride(name = "shippingAddress.zipCode.fourDigits", property = @Property(name = "sa_zipx")),
    @PropertyOverride(name = "shippingAddress.zipCode.anotherEmbeddable.field1", property = @Property(name = "sa_f1")),
    @PropertyOverride(name = "shippingAddress.zipCode.anotherEmbeddable.field2", property = @Property(name = "sa_f2")),

})
public class Customer {

  @Identifier
  private long id;

  private String name;

  @Embedded
  private Address billingAddress;

  @Embedded
  // @Ignore
  private Address shippingAddress;

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
   * @return the billingAddress
   */
  public Address getBillingAddress() {
    return billingAddress;
  }

  /**
   * @param billingAddress
   *          the billingAddress to set
   */
  public void setBillingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
  }

  /**
   * @return the shippingAddress
   */
  public Address getShippingAddress() {
    return shippingAddress;
  }

  /**
   * @param shippingAddress
   *          the shippingAddress to set
   */
  public void setShippingAddress(Address shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof Customer)) {
      return false;
    }
    Customer that = (Customer) obj;
    return Objects.equals(this.id, that.id) && Objects.equals(this.name, that.name)
        && Objects.equals(this.billingAddress, that.billingAddress)
        && Objects.equals(this.shippingAddress, that.shippingAddress);
  }

  public static Customer createSampleCustomer1() {
    Customer customer = new Customer();
    customer.setName("John Doe");
    Address ba = new Address();
    ba.setStreet1("1 Main St.");
    ba.setStreet2("Apt 1");
    ba.setCity("Omaha");
    ba.setState("NE");
    ZipCode baZip = new ZipCode();
    baZip.setFiveDigits("68101");
    baZip.setFourDigits("0000");

    AnotherEmbeddable ae1 = new AnotherEmbeddable();
    ae1.setField1("billing1");
    ae1.setField2("billing2");
    // baZip.setAnotherEmbeddable(ae1);

    ba.setZipCode(baZip);

    Address sa = new Address();
    sa.setStreet1("2 Second St.");
    sa.setStreet2("Apt 2");
    sa.setCity("New York City");
    sa.setState("NY");
    ZipCode saZip = new ZipCode();
    saZip.setFiveDigits("11111");
    saZip.setFourDigits("9999");

    AnotherEmbeddable ae2 = new AnotherEmbeddable();
    ae2.setField1("shipping1");
    ae2.setField2("shipping2");
    // saZip.setAnotherEmbeddable(ae2);

    sa.setZipCode(saZip);

    customer.setBillingAddress(ba);
    customer.setShippingAddress(sa);

    return customer;
  }

  public static Customer createSampleCustomer2() {
    Customer customer = new Customer();
    customer.setName("Super Customer");
    return customer;
  }

  public static Customer createSampleCustomer3() {
    Customer customer = new Customer();
    customer.setName("Super Customer");
    Address ba = new Address();
    ba.setStreet1("1 Main St.");
    ba.setStreet2("Apt 1");
    ba.setCity("Omaha");
    ba.setState("NE");
    customer.setBillingAddress(ba);
    customer.setShippingAddress(ba);
    return customer;
  }

}
