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

package com.jmethods.catatumbo.mappers;

import com.google.cloud.datastore.BooleanValue;
import com.google.cloud.datastore.DoubleValue;
import com.google.cloud.datastore.KeyValue;
import com.google.cloud.datastore.LatLng;
import com.google.cloud.datastore.LatLngValue;
import com.google.cloud.datastore.LongValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.DefaultDatastoreKey;
import com.jmethods.catatumbo.GeoLocation;
import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.MappingException;

/**
 * An implementation of {@link Mapper} for mapping select set of
 * primitive/wrapper types to the Cloud Datastore. The primary purpose if this
 * mapper is to aid with mapping Lists, Sets and Maps when the collections are
 * not parameterized.
 * 
 * @author Sai Pullabhotla
 *
 */
public class CatchAllMapper implements Mapper {

	/**
	 * Singleton instance
	 */
	private static final CatchAllMapper INSTANCE = new CatchAllMapper();

	/**
	 * Creates a new instance of <code>CatchAllMapper</code>.
	 */
	private CatchAllMapper() {
		// Do nothing
	}

	/**
	 * Returns the singleton instance of this mapper.
	 * 
	 * @return the singleton instance of this mapper.
	 */
	public static Mapper getInstance() {
		return INSTANCE;
	}

	@Override
	public ValueBuilder<?, ?, ?> toDatastore(Object input) {
		ValueBuilder<?, ?, ?> builder;
		if (input == null) {
			builder = NullValue.newBuilder();
		} else if (input instanceof Long) {
			builder = LongValue.newBuilder((long) input);
		} else if (input instanceof Double) {
			builder = DoubleValue.newBuilder((double) input);
		} else if (input instanceof Boolean) {
			builder = BooleanValue.newBuilder((boolean) input);
		} else if (input instanceof String) {
			builder = StringValue.newBuilder((String) input);
		} else if (input instanceof DatastoreKey) {
			builder = KeyValue.newBuilder(((DatastoreKey) input).nativeKey());
		} else if (input instanceof GeoLocation) {
			GeoLocation geoLocation = (GeoLocation) input;
			builder = LatLngValue.newBuilder(LatLng.of(geoLocation.getLatitude(), geoLocation.getLongitude()));
		} else {
			throw new MappingException(String.format("Unsupported type: %s", input.getClass().getName()));
		}
		return builder;
	}

	@Override
	public Object toModel(Value<?> input) {
		Object javaValue;
		if (input instanceof NullValue) {
			javaValue = null;
		} else if (input instanceof StringValue) {
			javaValue = input.get();
		} else if (input instanceof LongValue) {
			javaValue = input.get();
		} else if (input instanceof DoubleValue) {
			javaValue = input.get();
		} else if (input instanceof BooleanValue) {
			javaValue = input.get();
		} else if (input instanceof KeyValue) {
			javaValue = new DefaultDatastoreKey(((KeyValue) input).get());
		} else if (input instanceof LatLngValue) {
			LatLng latLong = ((LatLngValue) input).get();
			javaValue = new GeoLocation(latLong.getLatitude(), latLong.getLongitude());
		} else {
			throw new MappingException(String.format("Unsupported type %s", input.getClass().getName()));
		}
		return javaValue;
	}

}
