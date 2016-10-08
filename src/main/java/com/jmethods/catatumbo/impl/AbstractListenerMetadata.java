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
import java.util.HashMap;
import java.util.Map;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.EntityListener;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.MappedSuperClass;

/**
 * Base class for holding the metadata of listeners defined in an
 * {@link Entity}, {@link MappedSuperClass} or {@link EntityListener}.
 * 
 * @author Sai Pullabhotla
 *
 */
public class AbstractListenerMetadata {

	/**
	 * The listener class to which this metadata belongs
	 */
	private Class<?> listenerClass;

	/**
	 * Mapping of callback type to its callback method. Created lazily, if
	 * needed.
	 */
	private Map<CallbackType, Method> callbacks;

	/**
	 * Creates a new instance of <code>AbstractListenerMetadata</code>.
	 * 
	 * @param listenerClass
	 *            the listener class to which this metadata belongs.
	 */
	public AbstractListenerMetadata(Class<?> listenerClass) {
		this.listenerClass = listenerClass;
	}

	/**
	 * Registers the given method as the callback method for the given event
	 * type.
	 * 
	 * @param callbackType
	 *            the callback type
	 * @param method
	 *            the callback method
	 * @throws EntityManagerException
	 *             if there was already a callback method for the given event
	 *             type.
	 */
	public void putListener(CallbackType callbackType, Method method) {
		if (callbacks == null) {
			callbacks = new HashMap<>();
		}
		Method oldMethod = callbacks.put(callbackType, method);
		if (oldMethod != null) {
			String format = "Class %s has at least two methods, %s and %s, with annotation of %s. "
					+ "At most one method is allowed for a given callback type. ";
			String message = String.format(format, listenerClass.getName(), oldMethod.getName(), method.getName(),
					callbackType.getAnnotationClass().getName());
			throw new EntityManagerException(message);
		}
	}

	/**
	 * Returns all defined callbacks.
	 * 
	 * @return all defined callbacks. Returns <code>null</code>, if there are no
	 *         callback methods defined in the listener class.
	 */
	public Map<CallbackType, Method> getCallbacks() {
		return callbacks;
	}

}
