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

package com.jmethods.catatumbo.custommappers;

import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.StringValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.entities.DeviceType;

/**
 * @author Sai Pullabhotla
 *
 */
public class DeviceTypeMapper implements Mapper {

	/**
	 * Creates a new instance of DeviceTypeMapper.
	 */
	public DeviceTypeMapper() {
		System.out.println("Creating DeviceTypeMapper");
	}

	@Override
	public ValueBuilder<?, ?, ?> toDatastore(Object input) {
		if (input == null) {
			return NullValue.newBuilder();
		}
		String s = ((DeviceType) input).toString().toLowerCase();
		return StringValue.newBuilder(s);
	}

	@Override
	public Object toModel(Value<?> input) {
		if (input instanceof NullValue) {
			return null;
		}
		String s = ((StringValue) input).get();
		return Enum.valueOf(DeviceType.class, s.toUpperCase());
	}

}
