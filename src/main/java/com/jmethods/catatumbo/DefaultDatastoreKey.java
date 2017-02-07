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

import java.io.Serializable;

import com.google.cloud.datastore.Key;

/**
 * Default implementation of {@link DatastoreKey} interface.
 * 
 * @author Sai Pullabhotla
 *
 */
public class DefaultDatastoreKey implements DatastoreKey, Serializable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -5298686524012987642L;

	/**
	 * Native key from gcloud API
	 */
	private Key key;

	/**
	 * Creates a new instance of <code>DefaultDatastoreKey</code>.
	 * 
	 * @param encoded
	 *            the encoded string from which the key should be constructed.
	 */
	public DefaultDatastoreKey(String encoded) {
		this(Key.fromUrlSafe(encoded));
	}

	/**
	 * Creates a new instance of <code>DefaultDatastoreKey</code>.
	 * 
	 * @param key
	 *            the native key.
	 */
	public DefaultDatastoreKey(Key key) {
		this.key = key;
	}

	@Override
	public String kind() {
		return key.getKind();
	}

	@Override
	public boolean hasId() {
		return key.hasId();
	}

	@Override
	public boolean hasName() {
		return key.hasName();
	}

	@Override
	public long id() {
		return key.getId();
	}

	@Override
	public String name() {
		return key.getName();
	}

	@Override
	public Object nameOrId() {
		return key.getNameOrId();
	}

	@Override
	public DatastoreKey parent() {
		Key parent = key.getParent();
		if (parent != null) {
			return new DefaultDatastoreKey(parent);
		}
		return null;
	}

	@Override
	public String getEncoded() {
		return key.toUrlSafe();
	}

	@Override
	public Key nativeKey() {
		return key;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof DefaultDatastoreKey)) {
			return false;
		}
		DefaultDatastoreKey that = (DefaultDatastoreKey) obj;
		return this.key.equals(that.key);
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public String toString() {
		return key.toUrlSafe();
	}

}
