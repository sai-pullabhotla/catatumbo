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

package com.jmethods.catatumbo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;

import org.junit.Test;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.GeoLocation;
import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.MapperFactory;
import com.jmethods.catatumbo.NoSuitableMapperException;
import com.jmethods.catatumbo.entities.Color;
import com.jmethods.catatumbo.entities.EnumField.Size;
import com.jmethods.catatumbo.mappers.BooleanMapper;
import com.jmethods.catatumbo.mappers.ByteArrayMapper;
import com.jmethods.catatumbo.mappers.CalendarMapper;
import com.jmethods.catatumbo.mappers.CharArrayMapper;
import com.jmethods.catatumbo.mappers.CharMapper;
import com.jmethods.catatumbo.mappers.DateMapper;
import com.jmethods.catatumbo.mappers.DoubleMapper;
import com.jmethods.catatumbo.mappers.EnumMapper;
import com.jmethods.catatumbo.mappers.FloatMapper;
import com.jmethods.catatumbo.mappers.GeoLocationMapper;
import com.jmethods.catatumbo.mappers.IntegerMapper;
import com.jmethods.catatumbo.mappers.KeyMapper;
import com.jmethods.catatumbo.mappers.ListMapper;
import com.jmethods.catatumbo.mappers.LongMapper;
import com.jmethods.catatumbo.mappers.MapMapper;
import com.jmethods.catatumbo.mappers.SetMapper;
import com.jmethods.catatumbo.mappers.ShortMapper;
import com.jmethods.catatumbo.mappers.StringMapper;

/**
 * @author Sai Pullabhotla
 *
 */
public class MapperFactoryTest {

	@Test
	public void testGetMapper_Boolean() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(Boolean.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(boolean.class);
		Mapper mapper3 = MapperFactory.getInstance().getMapper(Boolean.class);
		Mapper mapper4 = MapperFactory.getInstance().getMapper(boolean.class);
		assertTrue(mapper1 instanceof BooleanMapper);
		assertTrue(mapper2 instanceof BooleanMapper);
		assertTrue(mapper3 instanceof BooleanMapper);
		assertTrue(mapper4 instanceof BooleanMapper);
		assertTrue(mapper1 == mapper2);
		assertTrue(mapper2 == mapper3);
		assertTrue(mapper3 == mapper4);

	}

	@Test
	public void testGetMapper_Char() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(char.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(Character.class);
		Mapper mapper3 = MapperFactory.getInstance().getMapper(char.class);
		Mapper mapper4 = MapperFactory.getInstance().getMapper(Character.class);
		assertTrue(mapper1 instanceof CharMapper);
		assertTrue(mapper2 instanceof CharMapper);
		assertTrue(mapper3 instanceof CharMapper);
		assertTrue(mapper4 instanceof CharMapper);
		assertTrue(mapper1 == mapper2);
		assertTrue(mapper2 == mapper3);
		assertTrue(mapper3 == mapper4);

	}

	@Test
	public void testGetMapper_Short() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(short.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(Short.class);
		Mapper mapper3 = MapperFactory.getInstance().getMapper(short.class);
		Mapper mapper4 = MapperFactory.getInstance().getMapper(Short.class);
		assertTrue(mapper1 instanceof ShortMapper);
		assertTrue(mapper2 instanceof ShortMapper);
		assertTrue(mapper3 instanceof ShortMapper);
		assertTrue(mapper4 instanceof ShortMapper);
		assertTrue(mapper1 == mapper2);
		assertTrue(mapper2 == mapper3);
		assertTrue(mapper3 == mapper4);

	}

	@Test
	public void testGetMapper_Integer() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(int.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(Integer.class);
		Mapper mapper3 = MapperFactory.getInstance().getMapper(int.class);
		Mapper mapper4 = MapperFactory.getInstance().getMapper(Integer.class);
		assertTrue(mapper1 instanceof IntegerMapper);
		assertTrue(mapper2 instanceof IntegerMapper);
		assertTrue(mapper3 instanceof IntegerMapper);
		assertTrue(mapper4 instanceof IntegerMapper);
		assertTrue(mapper1 == mapper2);
		assertTrue(mapper2 == mapper3);
		assertTrue(mapper3 == mapper4);

	}

	@Test
	public void testGetMapper_Long() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(long.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(Long.class);
		Mapper mapper3 = MapperFactory.getInstance().getMapper(long.class);
		Mapper mapper4 = MapperFactory.getInstance().getMapper(Long.class);
		assertTrue(mapper1 instanceof LongMapper);
		assertTrue(mapper2 instanceof LongMapper);
		assertTrue(mapper3 instanceof LongMapper);
		assertTrue(mapper4 instanceof LongMapper);
		assertTrue(mapper1 == mapper2);
		assertTrue(mapper2 == mapper3);
		assertTrue(mapper3 == mapper4);

	}

	@Test
	public void testGetMapper_Float() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(float.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(Float.class);
		Mapper mapper3 = MapperFactory.getInstance().getMapper(float.class);
		Mapper mapper4 = MapperFactory.getInstance().getMapper(Float.class);
		assertTrue(mapper1 instanceof FloatMapper);
		assertTrue(mapper2 instanceof FloatMapper);
		assertTrue(mapper3 instanceof FloatMapper);
		assertTrue(mapper4 instanceof FloatMapper);
		assertTrue(mapper1 == mapper2);
		assertTrue(mapper2 == mapper3);
		assertTrue(mapper3 == mapper4);

	}

	@Test
	public void testGetMapper_Double() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(double.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(Double.class);
		Mapper mapper3 = MapperFactory.getInstance().getMapper(double.class);
		Mapper mapper4 = MapperFactory.getInstance().getMapper(Double.class);
		assertTrue(mapper1 instanceof DoubleMapper);
		assertTrue(mapper2 instanceof DoubleMapper);
		assertTrue(mapper3 instanceof DoubleMapper);
		assertTrue(mapper4 instanceof DoubleMapper);
		assertTrue(mapper1 == mapper2);
		assertTrue(mapper2 == mapper3);
		assertTrue(mapper3 == mapper4);

	}

	@Test
	public void testGetMapper_String() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(String.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(String.class);
		assertTrue(mapper1 instanceof StringMapper);
		assertTrue(mapper2 instanceof StringMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test
	public void testGetMapper_ByteArray() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(byte[].class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(byte[].class);
		assertTrue(mapper1 instanceof ByteArrayMapper);
		assertTrue(mapper2 instanceof ByteArrayMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test
	public void testGetMapper_CharArray() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(char[].class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(char[].class);
		assertTrue(mapper1 instanceof CharArrayMapper);
		assertTrue(mapper2 instanceof CharArrayMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test
	public void testGetMapper_GeoLocation() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(GeoLocation.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(GeoLocation.class);
		assertTrue(mapper1 instanceof GeoLocationMapper);
		assertTrue(mapper2 instanceof GeoLocationMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test
	public void testGetMapper_DatastoreKey() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(DatastoreKey.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(DatastoreKey.class);
		assertTrue(mapper1 instanceof KeyMapper);
		assertTrue(mapper2 instanceof KeyMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test
	public void testGetMapper_Date() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(Date.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(Date.class);
		assertTrue(mapper1 instanceof DateMapper);
		assertTrue(mapper2 instanceof DateMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test
	public void testGetMapper_Calendar() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(Calendar.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(Calendar.class);
		assertTrue(mapper1 instanceof CalendarMapper);
		assertTrue(mapper2 instanceof CalendarMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test
	public void testGetMapper_Enum_1() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(Color.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(Color.class);
		assertTrue(mapper1 instanceof EnumMapper);
		assertTrue(mapper2 instanceof EnumMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test
	public void testGetMapper_Enum_2() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(Size.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(Size.class);
		assertTrue(mapper1 instanceof EnumMapper);
		assertTrue(mapper2 instanceof EnumMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test(expected = NoSuitableMapperException.class)
	public void testGetMapper_Object() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(Object.class);
	}

	@Test
	public void testGetMapper_List() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(List.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(List.class);
		assertTrue(mapper1 instanceof ListMapper);
		assertTrue(mapper2 instanceof ListMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test
	public void testGetMapper_ArrayList() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(ArrayList.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(ArrayList.class);
		assertTrue(mapper1 instanceof ListMapper);
		assertTrue(mapper2 instanceof ListMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test
	public void testGetMapper_Set() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(Set.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(Set.class);
		assertTrue(mapper1 instanceof SetMapper);
		assertTrue(mapper2 instanceof SetMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test
	public void testGetMapper_SortedSet() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(SortedSet.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(SortedSet.class);
		assertTrue(mapper1 instanceof SetMapper);
		assertTrue(mapper2 instanceof SetMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test
	public void testGetMapper_Map() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(Map.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(Map.class);
		assertTrue(mapper1 instanceof MapMapper);
		assertTrue(mapper2 instanceof MapMapper);
		assertTrue(mapper1 == mapper2);
	}

	@Test
	public void testGetMapper_TreeMap() {
		Mapper mapper1 = MapperFactory.getInstance().getMapper(TreeMap.class);
		Mapper mapper2 = MapperFactory.getInstance().getMapper(TreeMap.class);
		assertTrue(mapper1 instanceof MapMapper);
		assertTrue(mapper2 instanceof MapMapper);
		assertTrue(mapper1 == mapper2);
	}

}
