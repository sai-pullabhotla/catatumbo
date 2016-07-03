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

/**
 * Objects of this class represent Keys in the Cloud Datastore. Keys contain the
 * kind and identifier (long or String) as well as any ancestor keys.The primary
 * purpose of this interface is to facilitate support for Keys, Parent Keys and
 * Key References. The EntityManager sets the keys when entities are loaded from
 * the Datastore or saved to the Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public interface DatastoreKey {

	/**
	 * Returns the kind.
	 * 
	 * @return the kind.
	 */
	String kind();

	/**
	 * Tells whether or not this key has a numeric (long) ID.
	 * 
	 * @return <code>true</code>, if this key has a numeric (long) ID;
	 *         <code>false</code>, otherwise.
	 */
	boolean hasId();

	/**
	 * Tells whether or not this key has a String ID.
	 * 
	 * @return <code>true</code>, if this key has a String ID;
	 *         <code>false</code>, otherwise.
	 */
	boolean hasName();

	/**
	 * Returns the numeric ID of this key.
	 * 
	 * @return the numeric ID of this key.
	 */
	long id();

	/**
	 * Returns the String ID of this key.
	 * 
	 * @return the String ID of this key.
	 */
	String name();

	/**
	 * Returns the ID of this key. Returned type depends on if this key has a
	 * numeric ID or String ID.
	 * 
	 * @return the ID of this key. Returned type depends on if this key has a
	 *         numeric ID or String ID.
	 * 
	 */
	Object nameOrId();

	/**
	 * Returns the parent key, if one exists.
	 * 
	 * @return the parent key or <code>null</code>, if this key is a root key
	 *         (no parent).
	 */
	DatastoreKey parent();

	/**
	 * Returns a String representation of this key.
	 * 
	 * @return a String representation of this key.
	 */
	String getEncoded();

	/**
	 * Returns the underlying Datastore key.
	 * 
	 * @return the underlying Datastore key.
	 */
	// Don't really like this here.
	com.google.cloud.datastore.Key nativeKey();

}
