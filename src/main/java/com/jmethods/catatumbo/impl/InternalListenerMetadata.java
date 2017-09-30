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
 * Metadata of listeners defined in an entity or mapped super class.
 * 
 * @author Sai Pullabhotla
 *
 */
public class InternalListenerMetadata extends AbstractListenerMetadata {

  /**
   * Creates a new instance of <code>InternalListenerMetadata</code>.
   * 
   * @param listenerClass
   *          the listener class to which this metadata belongs.
   */
  public InternalListenerMetadata(Class<?> listenerClass) {
    super(listenerClass);
  }

}
