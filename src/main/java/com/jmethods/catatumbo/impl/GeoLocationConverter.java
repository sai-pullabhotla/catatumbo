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

package com.jmethods.catatumbo.impl;

import com.google.cloud.datastore.LatLng;
import com.google.cloud.datastore.LatLngValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.GeoLocation;

/**
 * A converter for converting {@link GeoLocation} to the Cloud Datastore
 * equivalent type and vice versa.
 * 
 * @author Sai Pullabhotla
 *
 */
public class GeoLocationConverter extends AbstractConverter {

	/**
	 * Singleton instance
	 */
	private static GeoLocationConverter INSTANCE = new GeoLocationConverter();

	/**
	 * Creates a new instance of <code>GeoLocationConverter</code>.
	 */
	private GeoLocationConverter() {
		// Hiding the constructor
	}

	@Override
	public ValueBuilder<?, ?, ?> toValueBuilder(Object input) {
		GeoLocation geoLocation = (GeoLocation) input;
		return LatLngValue.builder(LatLng.of(geoLocation.getLatitude(), geoLocation.getLongitude()));
	}

	@Override
	public Object toObject(Value<?> input) {
		LatLngValue value = (LatLngValue) input;
		LatLng coordinates = value.get();
		return new GeoLocation(coordinates.latitude(), coordinates.longitude());
	}

	/**
	 * Returns the singleton instance of this class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static GeoLocationConverter getInstance() {
		return INSTANCE;
	}

}
