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

}
