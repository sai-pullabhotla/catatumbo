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

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Property;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class AccessorTestEntity {

	@Identifier
	private long id;

	private int a;
	private int ab;
	@Property(name = "xy")
	private int xY;
	private int URL;
	private int IPAddress;
	private int a1;
	private int $name;
	private int myLongVariableName;
	private int $12;
	private int $$$;
	private int _a;
	private int _eventId;
	private int __;

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
	 * @return the a
	 */
	public int getA() {
		return a;
	}

	/**
	 * @param a
	 *            the a to set
	 */
	public void setA(int a) {
		this.a = a;
	}

	/**
	 * @return the ab
	 */
	public int getAb() {
		return ab;
	}

	/**
	 * @param ab
	 *            the ab to set
	 */
	public void setAb(int ab) {
		this.ab = ab;
	}

	/**
	 * @return the xY
	 */
	public int getxY() {
		return xY;
	}

	/**
	 * @param xY
	 *            the xY to set
	 */
	public void setxY(int xY) {
		this.xY = xY;
	}

	/**
	 * @return the uRL
	 */
	public int getURL() {
		return URL;
	}

	/**
	 * @param uRL
	 *            the uRL to set
	 */
	public void setURL(int uRL) {
		URL = uRL;
	}

	/**
	 * @return the iPAddress
	 */
	public int getIPAddress() {
		return IPAddress;
	}

	/**
	 * @param iPAddress
	 *            the iPAddress to set
	 */
	public void setIPAddress(int iPAddress) {
		IPAddress = iPAddress;
	}

	/**
	 * @return the a1
	 */
	public int getA1() {
		return a1;
	}

	/**
	 * @param a1
	 *            the a1 to set
	 */
	public void setA1(int a1) {
		this.a1 = a1;
	}

	/**
	 * @return the $name
	 */
	public int get$name() {
		return $name;
	}

	/**
	 * @param $name
	 *            the $name to set
	 */
	public void set$name(int $name) {
		this.$name = $name;
	}

	/**
	 * @return the myLongVariableName
	 */
	public int getMyLongVariableName() {
		return myLongVariableName;
	}

	/**
	 * @param myLongVariableName
	 *            the myLongVariableName to set
	 */
	public void setMyLongVariableName(int myLongVariableName) {
		this.myLongVariableName = myLongVariableName;
	}

	/**
	 * @return the $12
	 */
	public int get$12() {
		return $12;
	}

	/**
	 * @param $12
	 *            the $12 to set
	 */
	public void set$12(int $12) {
		this.$12 = $12;
	}

	/**
	 * @return the $$$
	 */
	public int get$$$() {
		return $$$;
	}

	/**
	 * @param $$$
	 *            the $$$ to set
	 */
	public void set$$$(int $$$) {
		this.$$$ = $$$;
	}

	/**
	 * @return the _a
	 */
	public int get_a() {
		return _a;
	}

	/**
	 * @param _a
	 *            the _a to set
	 */
	public void set_a(int _a) {
		this._a = _a;
	}

	/**
	 * @return the _eventId
	 */
	public int get_eventId() {
		return _eventId;
	}

	/**
	 * @param _eventId
	 *            the _eventId to set
	 */
	public void set_eventId(int _eventId) {
		this._eventId = _eventId;
	}

	/**
	 * @return the __
	 */
	public int get__() {
		return __;
	}

	/**
	 * @param __
	 *            the __ to set
	 */
	public void set__(int __) {
		this.__ = __;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AccessorTestEntity)) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		AccessorTestEntity that = (AccessorTestEntity) obj;
		return this.id == that.id && this.equalsExceptId(that);
	}

	public boolean equalsExceptId(AccessorTestEntity that) {
		return this.$$$ == that.$$$ && this.$12 == that.$12 && this.$name == that.$name && this.__ == that.__
				&& this._a == that._a && this._eventId == that._eventId && this.a == that.a && this.a1 == that.a1
				&& this.ab == that.ab && this.IPAddress == that.IPAddress
				&& this.myLongVariableName == that.myLongVariableName && this.URL == that.URL && this.xY == that.xY;
	}

	public static AccessorTestEntity getSample1() {
		AccessorTestEntity entity = new AccessorTestEntity();
		entity.$$$ = 1;
		entity.$12 = 2;
		entity.$name = 3;
		entity.__ = 4;
		entity._a = 5;
		entity._eventId = 6;
		entity.a = 7;
		entity.a1 = 8;
		entity.ab = 9;
		entity.IPAddress = 10;
		entity.myLongVariableName = 11;
		entity.URL = 12;
		entity.xY = 13;
		return entity;
	}

}
