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

import java.time.LocalDate;

import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.MappingException;

/**
 * An implementation of {@link Mapper} for mapping {@link LocalDate} to/from
 * Cloud Datastore. {@link LocalDate} types are mapped to String type in the
 * Cloud Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public class LocalDateMapper implements Mapper {

	@Override
	public ValueBuilder<?, ?, ?> toDatastore(Object input) {
		if (input == null) {
			return NullValue.newBuilder();
		}
		return StringValue.newBuilder(input.toString());
	}

	@Override
	public Object toModel(Value<?> input) {
		if (input instanceof NullValue) {
			return null;
		}
		try {
			return LocalDate.parse(((StringValue) input).get());
		} catch (ClassCastException exp) {
			String pattern = "Mapping of type %s to %s is not supported";
			throw new MappingException(String.format(pattern, input.getClass().getName(), LocalDate.class.getName()),
					exp);
		}
	}

}
