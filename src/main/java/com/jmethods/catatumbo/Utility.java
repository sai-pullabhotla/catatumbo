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

import java.io.Closeable;
import java.io.IOException;

/**
 * Utility methods.
 * 
 * @author Sai Pullabhotla
 *
 */
public class Utility {

	/**
	 * Creates a new instance of <code>Utility</code>. This exists to hide the
	 * implicit public constructor.
	 */
	private Utility() {
		// Do nothing
	}

	/**
	 * Closes the given Closeable.
	 * 
	 * @param closeable
	 *            the Closeable (e.g. InputStream, OutputStream, etc.) to close.
	 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException exp) {
				// Ignore
			}
		}
	}

	/**
	 * Tells whether or not the given input String is null or empty.
	 * 
	 * @param input
	 *            the input string to check
	 * @return <code>true</code>, if the given input is null or empty;
	 *         otherwise, <code>false</code>.
	 */
	public static boolean isNullOrEmpty(String input) {
		return input == null || input.trim().length() == 0;
	}

	/**
	 * Throws a {@link NullPointerException} if the given input is
	 * <code>null</code>.
	 * 
	 * @param input
	 *            the input to check
	 */
	public static void ensureNotNull(Object input) {
		if (input == null) {
			throw new NullPointerException();
		}
	}

	/**
	 * Throws a {@link NullPointerException} if the input is null. If the input
	 * is empty )<code>input.trim().length() == 0</code>, an
	 * {@link IllegalArgumentException} is thrown.
	 * 
	 * @param input
	 *            the input String to validate
	 */
	public static void ensureNotEmpty(String input) {
		ensureNotNull(input);
		if (input.trim().length() == 0) {
			throw new IllegalArgumentException();
		}
	}

}
