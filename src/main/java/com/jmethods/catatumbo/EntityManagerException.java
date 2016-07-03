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
 * Exception thrown by EntityManager when mapping objects to/from the Cloud
 * Datastore.
 * 
 * @author Sai Pullabhotla
 */
public class EntityManagerException extends RuntimeException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -4427708611101283783L;

	/**
	 * Creates a new instance of <code>EntityManagerException</code> without
	 * detail message.
	 */
	public EntityManagerException() {
	}

	/**
	 * Constructs an instance of <code>EntityManagerException</code> with the
	 * specified detail message.
	 *
	 * @param msg
	 *            the detail message.
	 */
	public EntityManagerException(String msg) {
		super(msg);
	}

	/**
	 * Creates an instance of <code>EntityManagerException</code> with the given
	 * message and cause.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public EntityManagerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new instance of <code>EntityManagerException</code> with the
	 * given cause.
	 * 
	 * @param cause
	 *            the cause.
	 */
	public EntityManagerException(Throwable cause) {
		super(cause.getMessage(), cause);
	}

}
