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
 * An exception thrown by {@link Mapper} to indicate any errors during data
 * mapping.
 * 
 * @author Sai Pullabhotla
 *
 */
public class MappingException extends EntityManagerException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -3940373782421116805L;

	/**
	 * Creates a new instance of <code>MappingException</code>.
	 */
	public MappingException() {
		super();
	}

	/**
	 * Creates a new instance of <code>MappingException</code>.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public MappingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new instance of <code>MappingException</code>.
	 * 
	 * @param message
	 *            the message
	 */
	public MappingException(String message) {
		super(message);
	}

	/**
	 * Creates a new instance of <code>MappingException</code>.
	 * 
	 * @param cause
	 *            the cause
	 */
	public MappingException(Throwable cause) {
		super(cause);
	}

}
