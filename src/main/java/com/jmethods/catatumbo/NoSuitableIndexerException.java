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
 * An exception to indicate that there is no {@link Indexer} to handle the indexing of a specific
 * type.
 * 
 * @author Sai Pullabhotla
 *
 */
public class NoSuitableIndexerException extends EntityManagerException {

  /**
   * Serial version UID
   */
  private static final long serialVersionUID = 6166844206650805406L;

  /**
   * Creates a new instance of <code>NoSuitableIndexerException</code>.
   */
  public NoSuitableIndexerException() {
    super();
  }

  /**
   * Creates a new instance of <code>NoSuitableIndexerException</code>.
   * 
   * @param message
   *          the detailed message
   * @param cause
   *          the cause
   */
  public NoSuitableIndexerException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a new instance of <code>NoSuitableIndexerException</code>.
   * 
   * @param message
   *          the detailed message
   */
  public NoSuitableIndexerException(String message) {
    super(message);
  }

  /**
   * Creates a new instance of <code>NoSuitableIndexerException</code>.
   * 
   * @param cause
   *          the cause
   */
  public NoSuitableIndexerException(Throwable cause) {
    super(cause);
  }

}
