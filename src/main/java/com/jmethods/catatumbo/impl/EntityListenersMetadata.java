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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Objects of this class hold the metadata data of various entity listeners.
 * 
 * @author Sai Pullabhotla
 *
 */
public class EntityListenersMetadata {

	/**
	 * The entity class to which this metadata belongs to.
	 */
	private final Class<?> entityClass;

	/**
	 * A map containing all {@link CallbackMetadata} by the callback type,
	 * Lazily loaded.
	 */
	private Map<CallbackType, List<CallbackMetadata>> callbacks;

	/**
	 * Whether or not to exclude global listeners.
	 */
	private boolean excludeDefaultListeners;

	/**
	 * Whether or not to exclude super class listeners (this is really not
	 * needed as we exclude them from the callbacks map).
	 */
	private boolean excludeSuperClassListeners;

	/**
	 * Creates a new instance of <code>EntityListenersMetadata</code>.
	 * 
	 * @param entityClass
	 *            the entity class
	 */
	public EntityListenersMetadata(Class<?> entityClass) {
		this.entityClass = entityClass;
		excludeDefaultListeners = false;
		excludeSuperClassListeners = false;
	}

	/**
	 * Returns the entity class to which this metadata belongs.
	 * 
	 * @return the entityClass the entity class to which this metadata belongs.
	 */
	public Class<?> getEntityClass() {
		return entityClass;
	}

	/**
	 * Tells whether or not the default (aka global) listeners should be
	 * excluded.
	 * 
	 * @return <code>true</code>, if global listeners should be excluded;
	 *         <code>false</code>, otherwise.
	 */
	public boolean isExcludeDefaultListeners() {
		return excludeDefaultListeners;
	}

	/**
	 * Sets whether or not to exclude the default (aka global) listeners.
	 * 
	 * @param excludeGlobalListeners
	 *            whether or not to exclude the global listeners
	 */
	public void setExcludeDefaultListeners(boolean excludeGlobalListeners) {
		this.excludeDefaultListeners = excludeGlobalListeners;
	}

	/**
	 * Tells whether or not to exclude the listeners defined in the super
	 * classes of the entity.
	 * 
	 * @return <code>true</code>, if super class listeners should be excluded;
	 *         <code>false</code>, otherwise.
	 */
	public boolean isExcludeSuperClassListeners() {
		return excludeSuperClassListeners;
	}

	/**
	 * Sets whether or not to exclude the super class listeners.
	 * 
	 * @param excludeSuperClassListeners
	 *            whether or not to exclude the super class listeners
	 */
	public void setExcludeSuperClassListeners(boolean excludeSuperClassListeners) {
		this.excludeSuperClassListeners = excludeSuperClassListeners;
	}

	/**
	 * Adds the given CallbackEventMetadata.
	 * 
	 * @param callbackType
	 *            the callback type
	 * @param callbackMetadata
	 *            the metadata of the callback
	 */
	public void put(CallbackType callbackType, CallbackMetadata callbackMetadata) {
		if (callbacks == null) {
			callbacks = new HashMap<>();
		}
		List<CallbackMetadata> callbackMetadataList = callbacks.get(callbackType);
		if (callbackMetadataList == null) {
			callbackMetadataList = new ArrayList<>();
			callbacks.put(callbackType, callbackMetadataList);
		}
		callbackMetadataList.add(callbackMetadata);
	}

	/**
	 * Returns the callbacks for the given callback type.
	 * 
	 * @param callbackType
	 *            the callback type
	 * @return the list of callbacks for the given callback type
	 */
	public List<CallbackMetadata> getCallbacks(CallbackType callbackType) {
		return callbacks == null ? null : callbacks.get(callbackType);
	}

	/**
	 * Returns all callbacks.
	 * 
	 * @return All call backs.
	 */
	public Map<CallbackType, List<CallbackMetadata>> getCallbacks() {
		return callbacks;
	}

}
