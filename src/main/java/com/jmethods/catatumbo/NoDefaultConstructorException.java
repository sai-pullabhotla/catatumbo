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
 * An exception to indicate a model class (Entity, Embeddable, etc.) does not
 * have the required public no-argument constructor.
 * 
 * @author Sai Pullabhotla
 *
 */
public class NoDefaultConstructorException extends EntityManagerException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -8627001926046445483L;

	/**
	 * Creates a new instance of <code>NoDefaultConstructorException</code>.
	 */
	public NoDefaultConstructorException() {
		super();
	}

	/**
	 * Creates a new instance of <code>NoDefaultConstructorException</code>.
	 * 
	 * @param msg
	 *            the detailed message
	 */
	public NoDefaultConstructorException(String msg) {
		super(msg);
	}

	/**
	 * Creates a new instance of <code>NoDefaultConstructorException</code>.
	 * 
	 * @param message
	 *            the detailed message
	 * @param cause
	 *            the cause
	 */
	public NoDefaultConstructorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new instance of <code>NoDefaultConstructorException</code>.
	 * 
	 * @param cause
	 *            the cause
	 */
	public NoDefaultConstructorException(Throwable cause) {
		super(cause);
	}

}
