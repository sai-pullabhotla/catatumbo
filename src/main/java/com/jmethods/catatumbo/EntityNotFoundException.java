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
 * Exception thrown by EntityManager to indicate that an update operation failed
 * because the entity with the specified key does not exist. This exception may
 * be thrown from variations of UPDATE methods of {@link EntityManager},
 * {@link DatastoreTransaction} and {@link DatastoreBatch}.
 * 
 * @author Sai Pullabhotla
 */
public class EntityNotFoundException extends EntityManagerException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -2594975589977183143L;

	/**
	 * Creates a new instance of {@code EntityNotFoundException} without detail
	 * message.
	 */
	public EntityNotFoundException() {
		super();
	}

	/**
	 * Constructs an instance of {@code EntityNotFoundException} with the
	 * specified detail message.
	 *
	 * @param msg
	 *            the detail message.
	 */
	public EntityNotFoundException(String msg) {
		super(msg);
	}

	/**
	 * Creates an instance of {@code EntityNotFoundException} with the given
	 * message and cause.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new instance of {@code EntityNotFoundException} with the given
	 * cause.
	 * 
	 * @param cause
	 *            the cause.
	 */
	public EntityNotFoundException(Throwable cause) {
		super(cause);
	}

}
