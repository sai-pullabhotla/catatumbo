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

import com.jmethods.catatumbo.EntityListeners;

/**
 * A factory for creating instances of classes that are marked with {@link EntityListeners}
 * annotation. This factory ensures that there exists only one instance of a given listener.
 * Instance is created the first time it is needed and then cached for subsequent uses.
 * 
 * @author Sai Pullabhotla
 *
 */
public class ListenerFactory {

  /**
   * A cache of listener class and the corresponding listener instance.
   */
  private Cache<Class<?>, Object> listeners;

  /**
   * Singleton instance of this class
   */
  private static final ListenerFactory INSTANCE = new ListenerFactory();

  /**
   * Creates a new instance of <code>ListenerFactory</code>.
   */
  private ListenerFactory() {
    listeners = new Cache<>();
  }

  /**
   * Returns the listener object for the given class.
   * 
   * @param listenerClass
   *          the listener class
   * @return the listener object for the given type
   */
  public Object getListener(Class<?> listenerClass) {
    Object listener = listeners.get(listenerClass);
    if (listener == null) {
      listener = loadListener(listenerClass);
    }
    return listener;
  }

  /**
   * Returns the singleton instance of this class.
   * 
   * @return the singleton instance of this class.
   */
  public static ListenerFactory getInstance() {
    return INSTANCE;
  }

  /**
   * Instantiates the listener of given type and caches it.
   * 
   * @param listenerClass
   *          the listener type
   * @return the instantiated object
   */
  private Object loadListener(Class<?> listenerClass) {
    synchronized (listenerClass) {
      Object listener = listeners.get(listenerClass);
      if (listener == null) {
        listener = IntrospectionUtils.instantiateObject(listenerClass);
        listeners.put(listenerClass, listener);
      }
      return listener;
    }
  }

}
