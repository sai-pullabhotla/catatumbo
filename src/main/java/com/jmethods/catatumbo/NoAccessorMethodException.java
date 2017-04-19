/*
 * Copyright 2017 Sai Pullabhotla.
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
 * An Exception to indicate that a model class (e.g. Entity, Embeddable, etc.)
 * is missing a required public accessor method.
 * 
 * @author Sai Pullabhotla
 *
 */
public class NoAccessorMethodException extends EntityManagerException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 5714470702079334554L;

	/**
	 * Creates a new instance of <code>NoAccessorMethodException</code>.
	 */
	public NoAccessorMethodException() {
	}

	/**
	 * Creates a new instance of <code>NoAccessorMethodException</code>.
	 * 
	 * @param msg
	 *            the detailed message.
	 */
	public NoAccessorMethodException(String msg) {
		super(msg);
	}

	/**
	 * Creates a new instance of <code>NoAccessorMethodException</code>.
	 * 
	 * @param message
	 *            the detailed message
	 * @param cause
	 *            the cause
	 */
	public NoAccessorMethodException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new instance of <code>NoAccessorMethodException</code>.
	 * 
	 * @param cause
	 *            the cause.
	 */
	public NoAccessorMethodException(Throwable cause) {
		super(cause);
	}

}
