
package com.jmethods.catatumbo.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.jmethods.catatumbo.Embeddable;

@Embeddable
public class ImmutablePhone {

  private final String countryCode;
  private final String areaCode;
  private final String subscriberNumber;

  public ImmutablePhone(Builder builder) {
    this.countryCode = builder.countryCode;
    this.areaCode = builder.areaCode;
    this.subscriberNumber = builder.subscriberNumber;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getAreaCode() {
    return areaCode;
  }

  public String getSubscriberNumber() {
    return subscriberNumber;
  }

  @Override
  public String toString() {
    StringBuilder builder2 = new StringBuilder();
    builder2.append("ImmutablePhone [countryCode=").append(countryCode).append(", areaCode=")
        .append(areaCode).append(", subscriberNumber=").append(subscriberNumber).append("]");
    return builder2.toString();
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static int newBuilder2() {
    return 0;
  }

  public static class Builder {
    private String countryCode;
    private String areaCode;
    private String subscriberNumber;

    public Builder setCountryCode(String countryCode) {
      this.countryCode = countryCode;
      return this;
    }

    public Builder setAreaCode(String areaCode) {
      this.areaCode = areaCode;
      return this;
    }

    public Builder setSubscriberNumber(String subscriberNumber) {
      this.subscriberNumber = subscriberNumber;
      return this;
    }

    public ImmutablePhone build() {
      return new ImmutablePhone(this);
    }

  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!this.getClass().equals(obj.getClass())) {
      return false;
    }
    ImmutablePhone that = (ImmutablePhone) obj;
    return Objects.equals(this.countryCode, that.countryCode)
        && Objects.equals(this.areaCode, that.areaCode)
        && Objects.equals(this.subscriberNumber, that.subscriberNumber);
  }

  public static ImmutablePhone getSample1() {
    return ImmutablePhone.newBuilder().setAreaCode("4").setAreaCode("20")
        .setSubscriberNumber("98765432").build();
  }

  public static ImmutablePhone getSample2() {
    return ImmutablePhone.newBuilder().setAreaCode("91").setAreaCode("040")
        .setSubscriberNumber("1122334455").build();
  }

  public static ImmutablePhone getSample3() {
    return ImmutablePhone.newBuilder().setAreaCode("91").setAreaCode("080")
        .setSubscriberNumber("9988776655").build();
  }

  public static List<ImmutablePhone> getSampleList() {
    return Arrays.asList(new ImmutablePhone[] { getSample2(), getSample3() });
  }
}
