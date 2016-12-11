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
 * An exception thrown by {@link Indexer} to indicate any errors during data
 * indexing.
 * 
 * @author Sai Pullabhotla
 *
 */
public class IndexingException extends EntityManagerException {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 998960795045630795L;

	/**
	 * Creates a new instance of <code>IndexingException</code>.
	 */
	public IndexingException() {
		super();
	}

	/**
	 * Creates a new instance of <code>IndexingException</code>.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public IndexingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a new instance of <code>IndexingException</code>.
	 * 
	 * @param message
	 *            the message
	 */
	public IndexingException(String message) {
		super(message);
	}

	/**
	 * Creates a new instance of <code>IndexingException</code>.
	 * 
	 * @param cause
	 *            the cause
	 */
	public IndexingException(Throwable cause) {
		super(cause);
	}

}
