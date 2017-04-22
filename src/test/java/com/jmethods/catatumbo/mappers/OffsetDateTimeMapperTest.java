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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.OffsetDateTime;
import java.util.Calendar;

import org.junit.Test;

import com.google.cloud.datastore.DateTime;
import com.google.cloud.datastore.DateTimeValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.StringValue;
import com.jmethods.catatumbo.MappingException;

/**
 * @author Sai Pullabhotla
 *
 */
public class OffsetDateTimeMapperTest {

	@Test
	public void testToDatastore_Now() {
		OffsetDateTimeMapper mapper = new OffsetDateTimeMapper();
		OffsetDateTime now = OffsetDateTime.now();
		DateTimeValue v = (DateTimeValue) mapper.toDatastore(now).build();
		assertTrue(now.toInstant().toEpochMilli() == v.get().getTimestampMillis());
	}

	@Test
	public void testToDatastore_Null() {
		OffsetDateTimeMapper mapper = new OffsetDateTimeMapper();
		NullValue v = (NullValue) mapper.toDatastore(null).build();
		assertNull(v.get());
	}

	@Test
	public void testToModel_Now() {
		Calendar now = Calendar.getInstance();
		DateTimeValue v = DateTimeValue.newBuilder(DateTime.copyFrom(now)).build();
		OffsetDateTimeMapper mapper = new OffsetDateTimeMapper();
		OffsetDateTime output = (OffsetDateTime) mapper.toModel(v);
		assertTrue(now.getTimeInMillis() == output.toInstant().toEpochMilli());
	}

	@Test
	public void testToModel_Null() {
		OffsetDateTimeMapper mapper = new OffsetDateTimeMapper();
		OffsetDateTime output = (OffsetDateTime) mapper.toModel(NullValue.of());
		assertNull(output);
	}

	@Test(expected = MappingException.class)
	public void testToModel2() {
		StringValue v = StringValue.of("Hello");
		OffsetDateTimeMapper mapper = new OffsetDateTimeMapper();
		try {
			mapper.toModel(v);
		} catch (MappingException e) {
			e.printStackTrace();
			throw e;
		}
	}

}
