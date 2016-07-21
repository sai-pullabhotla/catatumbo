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
import java.util.Objects;

/**
 * Default implementation of {@link DatastoreCursor} interface.
 * 
 * @author Sai Pullabhotla
 *
 */
public class DefaultDatastoreCursor implements DatastoreCursor, Serializable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -6434001816829294681L;

	/**
	 * Cursor data in URL-safe format
	 */
	private String encoded = null;

	/**
	 * Creates a new instance of <code>DefaultDatastoreCursor</code>.
	 * 
	 * @param encoded
	 *            cursor data, in URL-safe format. A <code>null</code> value
	 *            will be converted to an empty string.
	 */
	public DefaultDatastoreCursor(String encoded) {
		if (encoded == null) {
			encoded = "";
		}
		this.encoded = encoded;
	}

	/**
	 * Returns the encoded value of this cursor, in URL-safe format.
	 * 
	 * @return the encoded value of this cursor, in URL-safe format.
	 */
	@Override
	public String getEncoded() {
		return encoded;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof DefaultDatastoreCursor)) {
			return false;
		}
		DefaultDatastoreCursor that = (DefaultDatastoreCursor) obj;
		return this.encoded.equals(that.encoded);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(encoded);
	}

	@Override
	public String toString() {
		return encoded;
	}

}
