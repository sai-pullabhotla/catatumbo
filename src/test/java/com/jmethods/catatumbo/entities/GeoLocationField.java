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
import com.jmethods.catatumbo.GeoLocation;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity(kind = "CityCoordinates")
public class GeoLocationField {

  public static final GeoLocationField NEW_YORK_CITY = new GeoLocationField("New York City",
      new GeoLocation(40.7142700, -74.0059700));

  public static final GeoLocationField OMAHA = new GeoLocationField("Omaha",
      new GeoLocation(41.2586100, -95.9377900));

  public static final GeoLocationField PARIS = new GeoLocationField("Paris",
      new GeoLocation(48.8534100, 2.3488000));

  @Identifier
  private long id;

  private String city;

  private GeoLocation coordinates;

  /**
   * Creates a new instance of <code>GeoLocationField</code>.
   */
  public GeoLocationField() {
    // Do nothing
  }

  /**
   * Creates a new instance of <code>GeoLocationField</code>.
   * 
   * @param city
   *          city
   * @param coordinates
   *          coordinates
   */
  public GeoLocationField(String city, GeoLocation coordinates) {
    this.city = city;
    this.coordinates = coordinates;
  }

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
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * @param city
   *          the city to set
   */
  public void setCity(String city) {
    this.city = city;
  }

  /**
   * @return the coordinates
   */
  public GeoLocation getCoordinates() {
    return coordinates;
  }

  /**
   * @param coordinates
   *          the coordinates to set
   */
  public void setCoordinates(GeoLocation coordinates) {
    this.coordinates = coordinates;
  }

}
