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

import java.lang.reflect.Field;
import java.math.BigDecimal;

import com.google.cloud.datastore.LongValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.MappingException;;

/**
 * A custom mapper for testing the custom mapper functionality. This mapper maps
 * a BigDecimal type to an Integer property in the Cloud Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public class CurrencyMapper implements Mapper {

	/**
	 * Fractional digits
	 */
	private int fractionalDigits;

	/**
	 * Creates a new instance of CurrencyMapper.
	 * 
	 * @param field
	 *            the field to which this mapper is to be attached.
	 */
	public CurrencyMapper(Field field) {
		if (!field.getType().equals(BigDecimal.class)) {
			throw new IllegalArgumentException("Field type must be BigDecimal");
		}
		this.fractionalDigits = 2;
	}

	@Override
	public ValueBuilder<?, ?, ?> toDatastore(Object input) {
		if (input == null) {
			return NullValue.newBuilder();
		}
		try {
			BigDecimal n = (BigDecimal) input;
			n = n.movePointRight(fractionalDigits);
			return LongValue.newBuilder(n.longValueExact());
		} catch (Exception e) {
			throw new MappingException(e);
		}
	}

	@Override
	public Object toModel(Value<?> input) {
		if (input instanceof NullValue) {
			return null;
		}
		BigDecimal n = new BigDecimal(((LongValue) input).get());
		n = n.movePointLeft(fractionalDigits);
		return n;
	}

}
