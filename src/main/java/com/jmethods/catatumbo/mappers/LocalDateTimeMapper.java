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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.MappingException;

/**
 * An implementation of {@link Mapper} for mapping {@link LocalDateTime} to/from
 * Cloud Datastore. {@link LocalDateTime} types are mapped to String type in the
 * Cloud Datastore. {@link LocalDateTime} objects are persisted into the
 * datastore with a nano-second precision in the
 * <code>uuuu-MM-dd'T'HH:mm:ss.nnnnnnnnn</code> format.
 * 
 * @author Sai Pullabhotla
 *
 */
public class LocalDateTimeMapper implements Mapper {

	/**
	 * The formatter to use for converting LocalTime to String and vice versa.
	 */
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.nnnnnnnnn");

	@Override
	public ValueBuilder<?, ?, ?> toDatastore(Object input) {
		if (input == null) {
			return NullValue.newBuilder();
		}
		return StringValue.newBuilder(((LocalDateTime) input).format(FORMATTER));
	}

	@Override
	public Object toModel(Value<?> input) {
		if (input instanceof NullValue) {
			return null;
		}
		try {
			return LocalDateTime.parse(((StringValue) input).get(), FORMATTER);
		} catch (ClassCastException exp) {
			String pattern = "Mapping of type %s to %s is not supported";
			throw new MappingException(
					String.format(pattern, input.getClass().getName(), LocalDateTime.class.getName()), exp);
		} catch (DateTimeParseException exp) {
			throw new MappingException(exp);
		}
	}

}
