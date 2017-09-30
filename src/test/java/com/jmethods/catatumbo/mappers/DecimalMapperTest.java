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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.google.cloud.datastore.LongValue;
import com.google.cloud.datastore.Value;
import com.jmethods.catatumbo.MappingException;

/**
 * @author Sai Pullabhotla
 *
 */
public class DecimalMapperTest {

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor1() {
    try {
      DecimalMapper mapper = new DecimalMapper(5, 7);
    } catch (Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor2() {
    try {
      DecimalMapper mapper = new DecimalMapper(0, 0);
    } catch (Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor3() {
    try {
      DecimalMapper mapper = new DecimalMapper(20, 2);
    } catch (Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor4() {
    try {
      DecimalMapper mapper = new DecimalMapper(-1, 0);
    } catch (Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructor5() {
    try {
      DecimalMapper mapper = new DecimalMapper(5, -2);
    } catch (Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  @Test
  public void testConstructor6() {
    DecimalMapper mapper = new DecimalMapper(1, 0);
    assertEquals(1, mapper.getPrecision());
    assertEquals(0, mapper.getScale());
  }

  @Test
  public void testConstructor7() {
    DecimalMapper mapper = new DecimalMapper(10, 5);
    assertEquals(10, mapper.getPrecision());
    assertEquals(5, mapper.getScale());
  }

  @Test
  public void testConstructor8() {
    DecimalMapper mapper = new DecimalMapper(18, 18);
    assertEquals(18, mapper.getPrecision());
    assertEquals(18, mapper.getScale());
  }

  @Test
  public void testConstructor9() {
    DecimalMapper mapper = new DecimalMapper(18, 0);
    assertEquals(18, mapper.getPrecision());
    assertEquals(0, mapper.getScale());
  }

  @Test
  public void testToDatastore1() {
    DecimalMapper mapper = new DecimalMapper(6, 2);
    Value<?> value = mapper.toDatastore(new BigDecimal("9999.99")).build();
    assertTrue(value instanceof LongValue);
    assertEquals(999999L, value.get());
  }

  @Test
  public void testToDatastore2() {
    DecimalMapper mapper = new DecimalMapper(6, 2);
    Value<?> value = mapper.toDatastore(new BigDecimal("0")).build();
    assertTrue(value instanceof LongValue);
    assertEquals(0L, value.get());
  }

  @Test
  public void testToDatastore3() {
    DecimalMapper mapper = new DecimalMapper(18, 6);
    Value<?> value = mapper.toDatastore(new BigDecimal("123456789012.123456")).build();
    assertTrue(value instanceof LongValue);
    assertEquals(123456789012123456L, value.get());
  }

  @Test
  public void testToDatastore4() {
    DecimalMapper mapper = new DecimalMapper(18, 6);
    Value<?> value = mapper.toDatastore(new BigDecimal("-123456789012.123456")).build();
    assertTrue(value instanceof LongValue);
    assertEquals(-123456789012123456L, value.get());
  }

  @Test
  public void testToDatastore6() {
    DecimalMapper mapper = new DecimalMapper(1, 0);
    Value<?> value = mapper.toDatastore(new BigDecimal("9")).build();
    assertEquals(9L, value.get());
  }

  @Test
  public void testToDatastore7() {
    DecimalMapper mapper = new DecimalMapper(1, 1);
    Value<?> value = mapper.toDatastore(new BigDecimal("0.5")).build();
    assertTrue(value instanceof LongValue);
    assertEquals(5L, value.get());
  }

  @Test
  public void testToDatastore8() {
    DecimalMapper mapper = new DecimalMapper(1, 1);
    Value<?> value = mapper.toDatastore(new BigDecimal("0.500")).build();
    assertEquals(5L, value.get());
  }

  @Test
  public void testToDatastore9() {
    DecimalMapper mapper = new DecimalMapper(18, 18);
    Value<?> value = mapper.toDatastore(new BigDecimal("0.000000000000000565")).build();
    assertTrue(value instanceof LongValue);
    assertEquals(565L, value.get());
  }

  @Test
  public void testToDatastore10() {
    DecimalMapper mapper = new DecimalMapper(6, 2);
    Value<?> value = mapper.toDatastore(new BigDecimal("0.1")).build();
    assertTrue(value instanceof LongValue);
    assertEquals(10L, value.get());
  }

  @Test
  public void testToDatastore11() {
    DecimalMapper mapper = new DecimalMapper(6, 2);
    Value<?> value = mapper.toDatastore(new BigDecimal("0.10")).build();
    assertTrue(value instanceof LongValue);
    assertEquals(10L, value.get());
  }

  @Test
  public void testToDatastore12() {
    DecimalMapper mapper = new DecimalMapper(6, 2);
    Value<?> value = mapper.toDatastore(new BigDecimal("0.0")).build();
    assertTrue(value instanceof LongValue);
    assertEquals(0L, value.get());
  }

  @Test
  public void testToDatastore13() {
    DecimalMapper mapper = new DecimalMapper(6, 2);
    Value<?> value = mapper.toDatastore(new BigDecimal("0.01")).build();
    assertTrue(value instanceof LongValue);
    assertEquals(1L, value.get());
  }

  @Test(expected = MappingException.class)
  public void testToDatastore14() {
    try {
      DecimalMapper mapper = new DecimalMapper(5, 2);
      Value<?> value = mapper.toDatastore(new BigDecimal("1234.5")).build();
    } catch (Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  @Test(expected = MappingException.class)
  public void testToDatastore15() {
    try {
      DecimalMapper mapper = new DecimalMapper(5, 2);
      Value<?> value = mapper.toDatastore(new BigDecimal("1.234")).build();
    } catch (Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  @Test
  public void testToModel1() {
    DecimalMapper mapper = new DecimalMapper(6, 2);
    LongValue input = LongValue.of(999999L);
    BigDecimal output = (BigDecimal) mapper.toModel(input);
    assertEquals(new BigDecimal("9999.99"), output);

  }

  @Test
  public void testToModel2() {
    DecimalMapper mapper = new DecimalMapper(6, 2);
    LongValue input = LongValue.of(0);
    BigDecimal output = (BigDecimal) mapper.toModel(input);
    assertEquals(new BigDecimal("0.00"), output);
  }

  @Test
  public void testToModel3() {
    DecimalMapper mapper = new DecimalMapper(18, 18);
    LongValue input = LongValue.of(999999999999999999L);
    BigDecimal output = (BigDecimal) mapper.toModel(input);
    assertEquals(new BigDecimal("0.999999999999999999"), output);
  }

  @Test(expected = MappingException.class)
  public void testToModel4() {
    try {
      DecimalMapper mapper = new DecimalMapper(5, 2);
      LongValue input = LongValue.of(123456);
      BigDecimal output = (BigDecimal) mapper.toModel(input);
      assertEquals(new BigDecimal("123.456"), output);
    } catch (Exception e) {
      System.err.println(e);
      throw e;
    }
  }

  @Test
  public void testToModel5() {
    try {
      DecimalMapper mapper = new DecimalMapper(5, 2);
      LongValue input = LongValue.of(12345L);
      BigDecimal output = (BigDecimal) mapper.toModel(input);
      assertEquals(new BigDecimal("123.45"), output);
    } catch (Exception e) {
      System.err.println(e);
      throw e;
    }
  }

}
