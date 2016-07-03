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
 * @author Sai Pullabhotla
 *
 */
public class DefaultDatastoreCursor implements DatastoreCursor {

	private String encoded = null;

	/**
	 * Creates a new instance of <code>DatastoreCursor</code>.
	 * 
	 * @param encoded
	 *            cursor data
	 */
	public DefaultDatastoreCursor(String encoded) {
		super();
		this.encoded = encoded;
	}

	/**
	 * @return the data
	 */
	@Override
	public String getEncoded() {
		return encoded;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return encoded;
	}

}
