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

/**
 * An exception to indicate any data conversion errors.
 * 
 * @author Sai Pullabhotla
 *
 */
public class DataConversionException extends RuntimeException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 648197695147327873L;

	/**
	 * Creates a new instance of <code>DataConversionException</code>.
	 */
	public DataConversionException() {
		super();
	}

	/**
	 * Creates a new instance of <code>DataConversionException</code>.
	 * 
	 * @param message
	 *            the message
	 */
	public DataConversionException(String message) {
		super(message);
	}

	/**
	 * Creates a new instance of <code>DataConversionException</code>.
	 * 
	 * @param cause
	 *            the cause
	 */
	public DataConversionException(Throwable cause) {
		super(cause);
	}

	/**
	 * Creates a new instance of <code>DataConversionException</code>.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public DataConversionException(String message, Throwable cause) {
		super(message, cause);
	}
}
