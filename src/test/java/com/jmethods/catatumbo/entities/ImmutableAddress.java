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

import com.jmethods.catatumbo.Embeddable;
import com.jmethods.catatumbo.Embedded;

/**
 * @author Sai Pullabhotla
 *
 */
@Embeddable
public class ImmutableAddress {

  private String line1;
  private String line2;
  private String city;
  private String state;
  @Embedded
  private ImmutableZipCode zipCode;

  private ImmutableAddress(Builder builder) {
    this.line1 = builder.line1;
    this.line2 = builder.line2;
    this.city = builder.city;
    this.state = builder.state;
    this.zipCode = builder.zipCode;
  }

  public static Builder builder() {
    return new Builder();
  }

  /**
   * @return the line1
   */
  public String getLine1() {
    return line1;
  }

  /**
   * @return the line2
   */
  public String getLine2() {
    return line2;
  }

  /**
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @return the state
   */
  public String getState() {
    return state;
  }

  /**
   * @return the zipCode
   */
  public ImmutableZipCode getZipCode() {
    return zipCode;
  }

  public static class Builder {
    private String line1;
    private String line2;
    private String city;
    private String state;
    private ImmutableZipCode zipCode;

    private Builder() {

    }

    /**
     * @param line1
     *          the line1 to set
     */
    public Builder setLine1(String line1) {
      this.line1 = line1;
      return this;
    }

    /**
     * @param line2
     *          the line2 to set
     */
    public Builder setLine2(String line2) {
      this.line2 = line2;
      return this;
    }

    /**
     * @param city
     *          the city to set
     */
    public Builder setCity(String city) {
      this.city = city;
      return this;
    }

    /**
     * @param state
     *          the state to set
     */
    public Builder setState(String state) {
      this.state = state;
      return this;
    }

    public Builder setZipCode(ImmutableZipCode zipCode) {
      this.zipCode = zipCode;
      return this;
    }

    public ImmutableAddress build() {
      return new ImmutableAddress(this);
    }

  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder2 = new StringBuilder();
    builder2.append("ImmutableAddress [line1=").append(line1).append(", line2=").append(line2)
        .append(", city=").append(city).append(", state=").append(state).append(", zipCode=")
        .append(zipCode).append("]");
    return builder2.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!this.getClass().equals(obj.getClass())) {
      return false;
    }
    ImmutableAddress that = (ImmutableAddress) obj;
    return Objects.equals(this.line1, that.line1) && Objects.equals(this.line2, that.line2)
        && Objects.equals(this.city, that.city) && Objects.equals(this.state, that.state)
        && Objects.equals(this.zipCode, that.zipCode);
  }

}
