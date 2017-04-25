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

package com.jmethods.catatumbo.mappers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.TimestampValue;
import com.jmethods.catatumbo.MappingException;

/**
 * @author Sai Pullabhotla
 *
 */
public class ZonedDateTimeMapperTest {

	@Test
	public void testToDatastore_Now() {
		ZonedDateTimeMapper mapper = new ZonedDateTimeMapper();
		ZonedDateTime now = ZonedDateTime.now();
		Timestamp ts = ((TimestampValue) mapper.toDatastore(now).build()).get();
		assertEquals(now.toEpochSecond(), ts.getSeconds());
		assertEquals(now.getNano(), ts.getNanos());
	}

	@Test
	public void testToDatastorel_Nanos() {
		ZonedDateTime input = ZonedDateTime.now().withNano(999999999);
		ZonedDateTimeMapper mapper = new ZonedDateTimeMapper();
		Timestamp ts = ((TimestampValue) mapper.toDatastore(input).build()).get();
		assertEquals(ts.getSeconds(), input.toEpochSecond());
		assertEquals(TimeUnit.NANOSECONDS.toMicros(input.getNano()), TimeUnit.NANOSECONDS.toMicros(ts.getNanos()));
	}

	@Test
	public void testToDatastore_Null() {
		ZonedDateTimeMapper mapper = new ZonedDateTimeMapper();
		NullValue v = (NullValue) mapper.toDatastore(null).build();
		assertNull(v.get());
	}

	@Test
	public void testToModel_Now() {
		Timestamp now = Timestamp.now();
		TimestampValue v = TimestampValue.newBuilder(now).build();
		ZonedDateTimeMapper mapper = new ZonedDateTimeMapper();
		ZonedDateTime output = (ZonedDateTime) mapper.toModel(v);
		assertEquals(now.getSeconds(), output.toEpochSecond());
		assertEquals(now.getNanos(), output.getNano());
	}

	@Test
	public void testToModel_Null() {
		ZonedDateTimeMapper mapper = new ZonedDateTimeMapper();
		ZonedDateTime output = (ZonedDateTime) mapper.toModel(NullValue.of());
		assertNull(output);
	}

	@Test(expected = MappingException.class)
	public void testToModel2() {
		StringValue v = StringValue.of("Hello");
		ZonedDateTimeMapper mapper = new ZonedDateTimeMapper();
		try {
			mapper.toModel(v);
		} catch (MappingException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
