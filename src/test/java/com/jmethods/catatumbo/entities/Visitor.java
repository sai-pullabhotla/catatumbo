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

package com.jmethods.catatumbo.entities;

import java.util.Date;
import java.util.Objects;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class Visitor {

	public Visitor() {
		timestamp = new Date();
	}

	public Visitor(DeviceType deviceType) {
		timestamp = new Date();
		this.deviceType = deviceType;
	}

	@Identifier
	private long id;

	private Date timestamp;

	private DeviceType deviceType;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 *            the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the deviceType
	 */
	public DeviceType getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || !obj.getClass().equals(this.getClass())) {
			return false;
		}
		Visitor that = (Visitor) obj;
		return this.id == that.id && Objects.equals(this.timestamp, that.timestamp)
				&& Objects.equals(this.deviceType, that.deviceType);
	}

}
