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

import com.jmethods.catatumbo.EntityListeners;
import com.jmethods.catatumbo.EntityManager;

/**
 * Enumeration of entity listener types.
 * 
 * @author Sai Pullabhotla
 *
 */
public enum EntityListenerType {

	/**
	 * Default (aka Global) Listener. These type of listeners are registered
	 * with the {@link EntityManager} using
	 * {@link EntityManager#setDefaultListeners(Class...)} and these listeners
	 * will be executed for all types of Entities managed by the
	 * {@link EntityManager}.
	 */
	DEFAULT,

	/**
	 * External Listener. These type of listeners are specified on the Entity
	 * class or MappedSuperClass using the {@link EntityListeners} annotations.
	 */
	EXTERNAL,

	/**
	 * Internal Listeners. These type of listeners are defined within the Entity
	 * or MappedSuperClass.
	 */
	INTERNAL;

}
