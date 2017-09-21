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

import java.math.BigDecimal;

import com.google.cloud.datastore.LongValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.MappingException;

/**
 * An implementation of {@link Mapper} to map BigDecimal types to Integer fields
 * to/from the Cloud Datastore. The primary purpose of this Mapper is to
 * workaround the lack of Decimal (aka Numeric) type in Cloud Datastore. Cloud
 * Datastore does support Integer and Floating Point numbers, but storing
 * decimal data (e.g. Currency) as floating point would result in loss of
 * precision. This Mapper takes care of this issue by mapping decimal data to an
 * integer field.
 * 
 * @author Sai Pullabhotla
 *
 */
public class DecimalMapper implements Mapper {

	/**
	 * Max precision
	 */
	private static final int MAX_PRECISION = 18;

	/**
	 * Precision
	 */
	private final int precision;

	/**
	 * Scale
	 */
	private final int scale;

	/**
	 * Creates a new instance of <code>DecimalMapper</code> to map decimal
	 * values of given precision and scale.
	 * 
	 * @param precision
	 *            the precision (total number of digits before and after the
	 *            decimal)
	 * @param scale
	 *            the scale (number of digits after the decimal)
	 */
	public DecimalMapper(int precision, int scale) {
		if (precision <= 0 || precision > MAX_PRECISION) {
			throw new IllegalArgumentException(String.format("precision must be between %d and %d", 1, MAX_PRECISION));
		}
		if (scale < 0 || scale > precision) {
			throw new IllegalArgumentException(
					String.format("scale must be between %d and precision (%d)", 0, precision));
		}
		this.precision = precision;
		this.scale = scale;
	}

	/**
	 * Returns the precision.
	 * 
	 * @return the precision
	 */
	public int getPrecision() {
		return precision;
	}

	/**
	 * Returns the scale.
	 * 
	 * @return the scale
	 */
	public int getScale() {
		return scale;
	}

	@Override
	public ValueBuilder<?, ?, ?> toDatastore(Object input) {
		if (input == null) {
			return NullValue.newBuilder();
		}
		BigDecimal original = null;
		try {
			original = (BigDecimal) input;
			// Ensure we fix the scale, yes we don't round anything up or down.
			// It is the responsibility of the API user. We only set the scale
			// to zero fill any unused fractional digits, or remove any zeroes
			// from the end. This helps us validate the precision and ensure
			// that the number is within the expected bounds. Any better ways?
			BigDecimal n = original.setScale(scale);
			if (n.precision() > precision) {
				throw new MappingException(String.format("Value %s is not a valid Decimal(%d, %d)",
						original.toPlainString(), precision, scale));
			}
			n = n.movePointRight(scale);
			return LongValue.newBuilder(n.longValueExact());
		} catch (MappingException exp) {
			throw exp;
		} catch (Exception exp) {
			Object arg1 = original == null ? input : original.toPlainString();
			throw new MappingException(String.format("Value %s is not a valid Decimal(%d, %d)", arg1, precision, scale),
					exp);
		}
	}

	@Override
	public Object toModel(Value<?> input) {
		if (input instanceof NullValue) {
			return null;
		}
		try {
			BigDecimal n = new BigDecimal(((LongValue) input).get());
			if (n.precision() > precision) {
				throw new MappingException(
						String.format("Cannot map %s to Decimal(%d, %d). Value is larger than the defined precision. ",
								n.toPlainString(), precision, scale));
			}
			return n.movePointLeft(scale);
		} catch (MappingException exp) {
			throw exp;
		} catch (Exception exp) {
			throw new MappingException(String.format("Cannot map %s to Decimal(%d, %d", input, precision, scale), exp);
		}
	}

}
