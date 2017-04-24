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

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.TimestampValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.MappingException;

/**
 * An implementation of {@link Mapper} for mapping Calendar type to/from Cloud
 * Datastore. Calendar objects are mapped to DateTime type in the Cloud
 * Datastore. The values are stored with a maximum precision of milliseconds.
 * 
 * @author Sai Pullabhotla
 *
 */
public class CalendarMapper implements Mapper {

	@Override
	public ValueBuilder<?, ?, ?> toDatastore(Object input) {
		if (input == null) {
			return NullValue.newBuilder();
		}
		Calendar calendar = (Calendar) input;
		return TimestampValue.newBuilder(Timestamp.of(calendar.getTime()));
	}

	@Override
	public Object toModel(Value<?> input) {
		if (input instanceof NullValue) {
			return null;
		}
		try {
			Timestamp ts = ((TimestampValue) input).get();
			long millis = TimeUnit.SECONDS.toMillis(ts.getSeconds()) + TimeUnit.NANOSECONDS.toMillis(ts.getNanos());
			return new Calendar.Builder().setInstant(millis).build();
		} catch (ClassCastException exp) {
			String pattern = "Expecting %s, but found %s";
			throw new MappingException(
					String.format(pattern, TimestampValue.class.getName(), input.getClass().getName()), exp);
		}
	}

}
