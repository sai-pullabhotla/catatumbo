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

import java.util.Date;

import com.google.cloud.datastore.DateTime;
import com.google.cloud.datastore.DateTimeValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.Mapper;

/**
 * An implementation of {@link Mapper} for mapping Dates to/from Cloud
 * Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public class DateMapper implements Mapper {

	@Override
	public ValueBuilder<?, ?, ?> toDatastore(Object input) {
		if (input == null) {
			return NullValue.newBuilder();
		}
		return DateTimeValue.newBuilder(DateTime.copyFrom((Date) input));
	}

	@Override
	public Object toModel(Value<?> input) {
		if (input instanceof NullValue) {
			return null;
		}
		return ((DateTimeValue) input).get().toDate();
	}

}
