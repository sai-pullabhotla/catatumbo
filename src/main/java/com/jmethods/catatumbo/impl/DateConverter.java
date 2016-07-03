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

import java.util.Date;

import com.google.cloud.datastore.DateTime;
import com.google.cloud.datastore.DateTimeValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;

/**
 * An implementation of the {@link PropertyConverter} interface for dealing with
 * Date objects (Date/time).
 * 
 * @author Sai Pullabhotla
 *
 */
public class DateConverter extends AbstractConverter {

	/**
	 * Singleton instance
	 */
	private static final DateConverter INSTANCE = new DateConverter();

	/**
	 * Creates a new instance of <code>DateConverter</code>.
	 */
	private DateConverter() {
		// Do nothing
	}

	@Override
	public ValueBuilder<?, ?, ?> toValueBuilder(Object input) {
		return DateTimeValue.builder(DateTime.copyFrom((Date) input));
	}

	@Override
	public Object toObject(Value<?> input) {
		return ((DateTimeValue) input).get().toDate();
	}

	/**
	 * @return the instance
	 */
	public static DateConverter getInstance() {
		return INSTANCE;
	}

}
