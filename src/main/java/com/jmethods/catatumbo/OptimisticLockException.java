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
 * An exception thrown by the {@link EntityManager} to report a conflict with optimistic locks. This
 * exception is thrown when the entity being updated does not exist the in the Datastore or the
 * version property of the entity does not match with the entity being updated.
 * 
 * @author Sai Pullabhotla
 *
 */
public class OptimisticLockException extends EntityManagerException {

  /**
   * Serial version UID
   */
  private static final long serialVersionUID = 1879640702345246635L;

  /**
   * Creates a new instance of <code>OptimisticLockException</code>.
   */
  public OptimisticLockException() {
    super();
  }

  /**
   * Creates a new instance of <code>OptimisticLockException</code>.
   * 
   * @param message
   *          the message
   * @param cause
   *          the cause
   */
  public OptimisticLockException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a new instance of <code>OptimisticLockException</code>.
   * 
   * @param message
   *          the message
   */
  public OptimisticLockException(String message) {
    super(message);
  }

  /**
   * Creates a new instance of <code>OptimisticLockException</code>.
   * 
   * @param cause
   *          the cause
   */
  public OptimisticLockException(Throwable cause) {
    super(cause.getMessage(), cause);
  }

}
