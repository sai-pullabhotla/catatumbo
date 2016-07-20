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

package com.jmethods.catatumbo;

import java.io.Serializable;
import java.util.Objects;

/**
 * Geographical Location data type to store longitude and latitude information
 * in a single property of Cloud Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public class GeoLocation implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -7448392415206659989L;

	/**
	 * Latitude
	 */
	private double latitude;

	/**
	 * Longitude
	 */
	private double longitude;

	/**
	 * Creates a new instance of <code>GeoLocation</code>.
	 */
	public GeoLocation() {
		this(0.0, 0.0);
	}

	/**
	 * Creates a new instance of <code>GeoLocation</code>.
	 * 
	 * @param latitude
	 *            the latitude
	 * @param longitude
	 *            the longitude
	 */
	public GeoLocation(double latitude, double longitude) {
		// Validate the values??
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * Returns the latitude.
	 * 
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * Sets the latitude to the given value.
	 * 
	 * @param latitude
	 *            the latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * Returns the longitude.
	 * 
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the longitude to the given value.
	 * 
	 * @param longitude
	 *            the longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GeoLocation)) {
			return false;
		}
		GeoLocation that = (GeoLocation) obj;
		return this.latitude == that.latitude && this.longitude == that.longitude;
	}

	@Override
	public int hashCode() {
		return Objects.hash(latitude, longitude);
	}

	@Override
	public String toString() {
		return latitude + ", " + longitude;
	}
}
