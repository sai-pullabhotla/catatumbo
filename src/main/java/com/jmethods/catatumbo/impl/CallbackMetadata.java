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

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Objects of this class contain the metadata of a callback event (e.g.
 * PreInsert).
 * 
 * @author Sai Pullabhotla
 *
 */
public class CallbackMetadata {

	/**
	 * Listener type
	 */
	private final EntityListenerType listenerType;

	/**
	 * Callback type
	 */
	private final CallbackType callbackType;

	/**
	 * Callback method
	 */
	private final Method callbackMethod;

	/**
	 * Listener class
	 */
	private final Class<?> listenerClass;

	/**
	 * Creates a new instance of <code>CallbackMetadata</code>.
	 * 
	 * @param listenerType
	 *            the listener type
	 * @param callbackType
	 *            the callback type
	 * @param callbackMethod
	 *            the callback method
	 * 
	 */
	public CallbackMetadata(EntityListenerType listenerType, CallbackType callbackType, Method callbackMethod) {
		this.listenerType = listenerType;
		this.callbackType = callbackType;
		this.callbackMethod = callbackMethod;
		this.listenerClass = callbackMethod.getDeclaringClass();
	}

	/**
	 * Returns the listener type.
	 * 
	 * @return the listener type.
	 */
	public EntityListenerType getListenerType() {
		return listenerType;
	}

	/**
	 * Returns the callback type.
	 * 
	 * @return the callback type.
	 */
	public CallbackType getCallbackType() {
		return callbackType;
	}

	/**
	 * Returns the callback method.
	 * 
	 * @return the callback method.
	 */
	public Method getCallbackMethod() {
		return callbackMethod;
	}

	/**
	 * Returns the listener class.
	 * 
	 * @return the listener class.
	 */
	public Class<?> getListenerClass() {
		return listenerClass;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof CallbackMetadata)) {
			return false;
		}
		CallbackMetadata that = (CallbackMetadata) obj;
		return this.listenerType == that.listenerType && this.callbackType == that.callbackType
				&& this.callbackMethod.equals(that.callbackMethod);
	}

	@Override
	public int hashCode() {
		return Objects.hash(listenerType, callbackType, callbackMethod);
	}

	@Override
	public String toString() {
		return callbackType + "->" + callbackMethod;
	}

}
