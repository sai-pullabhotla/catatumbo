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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import com.jmethods.catatumbo.EntityListener;
import com.jmethods.catatumbo.EntityManagerException;

/**
 * Introspector for external EntityListener classes.
 * 
 * @author Sai Pullabhotla
 *
 */
public class ExternalListenerIntrospector {

  /**
   * EntityListener class
   */
  private Class<?> listenerClass;

  /**
   * Metadata of the listener class
   */
  private ExternalListenerMetadata metadata;

  /**
   * A cache of previously processed listener classes
   */
  private static Cache<Class<?>, ExternalListenerMetadata> cache = new Cache<>();

  /**
   * Creates a new instance of <code>EntityListenerIntrospector</code>.
   * 
   * @param listenerClass
   *          the listener class to introspect
   */
  private ExternalListenerIntrospector(Class<?> listenerClass) {
    this.listenerClass = listenerClass;
  }

  /**
   * Introspects the given entity listener class and returns its metadata.
   * 
   * @param listenerClass
   *          the entity listener class
   * @return the entity listener metadata
   */
  public static ExternalListenerMetadata introspect(Class<?> listenerClass) {
    ExternalListenerMetadata cachedMetadata = cache.get(listenerClass);
    if (cachedMetadata != null) {
      return cachedMetadata;
    }
    synchronized (listenerClass) {
      cachedMetadata = cache.get(listenerClass);
      if (cachedMetadata != null) {
        return cachedMetadata;
      }
      ExternalListenerIntrospector introspector = new ExternalListenerIntrospector(listenerClass);
      introspector.introspect();
      cache.put(listenerClass, introspector.metadata);
      return introspector.metadata;
    }
  }

  /**
   * Introspects the listener class and creates the metadata.
   */
  private void introspect() {
    if (!listenerClass.isAnnotationPresent(EntityListener.class)) {
      String message = String.format(
          "Class %s must have %s annotation to be used as an EntityListener",
          listenerClass.getName(), EntityListener.class.getName());
      throw new EntityManagerException(message);
    }
    metadata = new ExternalListenerMetadata(listenerClass);
    processMethods();
  }

  /**
   * Processes the methods in the listener class and updates the metadata for any valid methods
   * found.
   */
  private void processMethods() {
    Method[] methods = listenerClass.getMethods();
    for (Method method : methods) {
      for (CallbackType callbackType : CallbackType.values()) {
        if (method.isAnnotationPresent(callbackType.getAnnotationClass())) {
          validateMethod(method, callbackType);
          metadata.putListener(callbackType, method);
        }
      }
    }
  }

  /**
   * Validates the given method to ensure if it is a valid callback method for the given event type.
   * 
   * @param method
   *          the method to validate
   * @param callbackType
   *          the callback type
   */
  private void validateMethod(Method method, CallbackType callbackType) {
    int modifiers = method.getModifiers();
    if (Modifier.isStatic(modifiers)) {
      String message = String.format("Method %s in class %s must not be static", method.getName(),
          method.getDeclaringClass().getName());
      throw new EntityManagerException(message);
    }
    if (Modifier.isAbstract(modifiers)) {
      String message = String.format("Method %s in class %s must not be abstract", method.getName(),
          method.getDeclaringClass().getName());
      throw new EntityManagerException(message);
    }
    Class<?>[] parameters = method.getParameterTypes();
    if (parameters.length != 1) {
      String pattern = "Method %s in class %s is not a valid %s callback method. Method must have "
          + "one parameter. ";
      String message = String.format(pattern, method.getName(),
          method.getDeclaringClass().getName(), callbackType);
      throw new EntityManagerException(message);
    }
    if (method.getReturnType() != void.class) {
      String message = String.format("Method %s in class %s must have a return type of %s",
          method.getName(), method.getDeclaringClass().getName(), void.class.getName());
      throw new EntityManagerException(message);
    }
  }

}
