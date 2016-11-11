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

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.cloud.datastore.DoubleValue;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.jmethods.catatumbo.MappingException;

/**
 * @author Sai Pullabhotla
 *
 */
public class FloatMapperTest {

	@Test
	public void testToModel_1() {
		double d = 0.000000;
		DoubleValue input = DoubleValue.of(d);
		FloatMapper mapper = new FloatMapper();
		float output = (float) mapper.toModel(input);
		System.out.printf("%s ---> %s%n", d, output);
		assertTrue(0.000000f == output);
	}

	@Test
	public void testToModel_2() {
		double d = 3.1415927;
		DoubleValue input = DoubleValue.of(d);
		FloatMapper mapper = new FloatMapper();
		float output = (float) mapper.toModel(input);
		System.out.printf("%s ---> %s%n", d, output);
		assertTrue(3.1415927f == output);
	}

	@Test
	public void testToModel_3() {
		double d = Float.MAX_VALUE;
		DoubleValue input = DoubleValue.of(d);
		FloatMapper mapper = new FloatMapper();
		float output = (float) mapper.toModel(input);
		System.out.printf("%s ---> %s%n", d, output);
		assertTrue(Float.MAX_VALUE == output);
	}

	@Test
	public void testToModel_4() {
		double d = -Float.MAX_VALUE;
		DoubleValue input = DoubleValue.of(d);
		FloatMapper mapper = new FloatMapper();
		float output = (float) mapper.toModel(input);
		System.out.printf("%s ---> %s%n", d, output);
		assertTrue(-Float.MAX_VALUE == output);
	}

	@Test(expected = MappingException.class)
	public void testToModel_5() {
		double d = Float.MAX_VALUE * 2d;
		DoubleValue input = DoubleValue.of(d);
		FloatMapper mapper = new FloatMapper();
		try {
			float output = (float) mapper.toModel(input);
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}

	@Test(expected = MappingException.class)
	public void testToModel_6() {
		double d = -Float.MAX_VALUE * 2d;
		DoubleValue input = DoubleValue.of(d);
		FloatMapper mapper = new FloatMapper();
		try {
			float output = (float) mapper.toModel(input);
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}

	@Test
	public void testToModel_7() {
		double d = 1.0000;
		DoubleValue input = DoubleValue.of(d);
		FloatMapper mapper = new FloatMapper();
		float output = (float) mapper.toModel(input);
		System.out.printf("%s ---> %s%n", d, output);
		assertTrue(1.0000f == output);
	}

	@Test
	public void testToModel_8() {
		double d = 999.9999;
		DoubleValue input = DoubleValue.of(d);
		FloatMapper mapper = new FloatMapper();
		float output = (float) mapper.toModel(input);
		System.out.printf("%s ---> %s%n", d, output);
		assertTrue(999.9999f == output);
	}

	@Test
	public void testToModel_9() {
		FloatMapper mapper = new FloatMapper();
		Object output = mapper.toModel(NullValue.of());
		assertNull(output);
	}

	@Test
	public void testToDatastore_1() {
		float f = 0.0f;
		FloatMapper mapper = new FloatMapper();
		Value output = mapper.toDatastore(f).build();
		System.out.printf("%s ---> %s%n", f, output.get());
		assertTrue(Double.valueOf(f).equals(output.get()));
	}

	@Test
	public void testToDatastore_2() {
		float f = 1.00000f;
		FloatMapper mapper = new FloatMapper();
		Value output = mapper.toDatastore(f).build();
		System.out.printf("%s ---> %s%n", f, output.get());
		assertTrue(Double.valueOf(f).equals(output.get()));
	}

	@Test
	public void testToDatastore_3() {
		float f = -1.00000f;
		FloatMapper mapper = new FloatMapper();
		Value output = mapper.toDatastore(f).build();
		System.out.printf("%s ---> %s%n", f, output.get());
		assertTrue(Double.valueOf(f).equals(output.get()));
	}

	@Test
	public void testToDatastore_4() {
		float f = 3.1415927f;
		FloatMapper mapper = new FloatMapper();
		Value output = mapper.toDatastore(f).build();
		System.out.printf("%s ---> %s%n", f, output.get());
		assertTrue(Double.valueOf(f).equals(output.get()));
	}

	@Test
	public void testToDatastore_5() {
		float f = 1f / 3f;
		FloatMapper mapper = new FloatMapper();
		Value output = mapper.toDatastore(f).build();
		System.out.printf("%s ---> %s%n", f, output.get());
		assertTrue(Double.valueOf(f).equals(output.get()));
	}

	@Test
	public void testToDatastore_6() {
		float f = Float.MAX_VALUE;
		FloatMapper mapper = new FloatMapper();
		Value output = mapper.toDatastore(f).build();
		System.out.printf("%s ---> %s%n", f, output.get());
		assertTrue(Double.valueOf(f).equals(output.get()));
	}

	@Test
	public void testToDatastore_7() {
		float f = -Float.MAX_VALUE;
		FloatMapper mapper = new FloatMapper();
		Value output = mapper.toDatastore(f).build();
		System.out.printf("%s ---> %s%n", f, output.get());
		assertTrue(Double.valueOf(f).equals(output.get()));
	}

	@Test
	public void testToDatastore_8() {
		float f = Float.MIN_VALUE;
		FloatMapper mapper = new FloatMapper();
		Value output = mapper.toDatastore(f).build();
		System.out.printf("%s ---> %s%n", f, output.get());
		assertTrue(Double.valueOf(f).equals(output.get()));
	}

	@Test
	public void testToDatastore_9() {
		float f = -Float.MIN_VALUE;
		FloatMapper mapper = new FloatMapper();
		Value output = mapper.toDatastore(f).build();
		System.out.printf("%s ---> %s%n", f, output.get());
		assertTrue(Double.valueOf(f).equals(output.get()));
	}

}
