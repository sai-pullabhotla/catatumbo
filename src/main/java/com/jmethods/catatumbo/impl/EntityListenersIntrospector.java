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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jmethods.catatumbo.EntityListeners;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.ExcludeDefaultListeners;
import com.jmethods.catatumbo.ExcludeSuperclassListeners;
import com.jmethods.catatumbo.MappedSuperClass;

/**
 * Introspects the given entity class to find all listeners that would receive lifecycle callbacks.
 * 
 * @author Sai Pullabhotla
 *
 */
public class EntityListenersIntrospector {

  /**
   * Entity class
   */
  private Class<?> entityClass;

  /**
   * Metadata of various listeners
   */
  private EntityListenersMetadata metadata;

  /**
   * Creates a new instance of <code>EntityListenersMetadata</code>.
   * 
   * @param entityClass
   *          the entity class to introspect
   */
  private EntityListenersIntrospector(Class<?> entityClass) {
    this.entityClass = entityClass;
  }

  /**
   * Returns the metadata of various registered listeners for the given entity.
   * 
   * @param entity
   *          the entity to introspect
   * @return the metadata of various listeners.
   */
  public static EntityListenersMetadata introspect(Object entity) {
    return introspect(entity.getClass());
  }

  /**
   * Returns the metadata of various registered listeners for the given entity class.
   * 
   * @param entityClass
   *          the entity class to introspect
   * @return the metadata of various listeners.
   */
  public static EntityListenersMetadata introspect(Class<?> entityClass) {
    EntityListenersIntrospector introspector = new EntityListenersIntrospector(entityClass);
    introspector.introspect();
    return introspector.metadata;
  }

  /**
   * Introspects the entity class and builds the listeners metadata.
   */
  private void introspect() {
    metadata = new EntityListenersMetadata(entityClass);
    processExternalListeners();
    processInternalListeners();
  }

  /**
   * Gathers information about all external listeners (listeners specified with
   * {@link EntityListeners} annotation) and updates the metadata.
   */
  private void processExternalListeners() {
    List<Class<?>> externalListeners = getAllExternalListeners();
    for (Class<?> externalListener : externalListeners) {
      processExternalListener(externalListener);
    }
  }

  /**
   * Inspects the entity hierarchy and returns all external listeners.
   * 
   * @return list of all external listeners
   */
  private List<Class<?>> getAllExternalListeners() {
    Class<?> clazz = entityClass;
    List<Class<?>> allListeners = new ArrayList<>();
    boolean stop = false;
    while (!stop) {
      EntityListeners entityListenersAnnotation = clazz.getAnnotation(EntityListeners.class);
      if (entityListenersAnnotation != null) {
        Class<?>[] listeners = entityListenersAnnotation.value();
        if (listeners.length > 0) {
          allListeners.addAll(0, Arrays.asList(listeners));
        }
      }
      boolean excludeDefaultListeners = clazz.isAnnotationPresent(ExcludeDefaultListeners.class);
      boolean excludeSuperClassListeners = clazz
          .isAnnotationPresent(ExcludeSuperclassListeners.class);
      if (excludeDefaultListeners) {
        metadata.setExcludeDefaultListeners(true);
      }
      if (excludeSuperClassListeners) {
        metadata.setExcludeSuperClassListeners(true);
      }
      clazz = clazz.getSuperclass();
      stop = excludeSuperClassListeners || clazz == null
          || !clazz.isAnnotationPresent(MappedSuperClass.class);
    }
    return allListeners;
  }

  /**
   * Introspects the given listener class and finds all methods that should receive callback event
   * notifications.
   * 
   * @param listenerClass
   *          the listener class
   */
  private void processExternalListener(Class<?> listenerClass) {
    ExternalListenerMetadata listenerMetadata = ExternalListenerIntrospector
        .introspect(listenerClass);
    Map<CallbackType, Method> callbacks = listenerMetadata.getCallbacks();
    if (callbacks != null) {
      for (Map.Entry<CallbackType, Method> entry : callbacks.entrySet()) {
        validateExternalCallback(entry.getValue(), entry.getKey());
      }
    }
  }

  /**
   * Validates and registers the given callback method.
   * 
   * @param method
   *          the callback method
   * @param callbackType
   *          the callback type
   */
  private void validateExternalCallback(Method method, CallbackType callbackType) {
    Class<?>[] parameters = method.getParameterTypes();
    if (!parameters[0].isAssignableFrom(entityClass)) {
      String message = String.format("Method %s in class %s is not valid for entity %s",
          method.getName(), method.getDeclaringClass().getName(), entityClass.getName());
      throw new EntityManagerException(message);
    }
    CallbackMetadata callbackMetadata = new CallbackMetadata(EntityListenerType.EXTERNAL,
        callbackType, method);
    metadata.put(callbackType, callbackMetadata);
  }

  /**
   * Introspects the entity class hierarchy for any internal callback methods and updates the
   * metadata.
   */
  private void processInternalListeners() {
    List<Class<?>> internalListeners = getAllInternalListeners();
    for (Class<?> internalListener : internalListeners) {
      processInternalListener(internalListener);
    }
  }

  /**
   * Traverses up the entity class hierarchy and returns the list of all classes that may
   * potentially have callback listeners.
   * 
   * @return all internal callback listeners
   */
  private List<Class<?>> getAllInternalListeners() {
    Class<?> clazz = entityClass;
    List<Class<?>> allListeners = new ArrayList<>();
    boolean stop = false;
    while (!stop) {
      allListeners.add(0, clazz);
      boolean excludeSuperClassListeners = clazz
          .isAnnotationPresent(ExcludeSuperclassListeners.class);
      clazz = clazz.getSuperclass();
      stop = excludeSuperClassListeners || clazz == null
          || !clazz.isAnnotationPresent(MappedSuperClass.class);
    }
    return allListeners;

  }

  /**
   * Processes the given class and finds any internal callbacks.
   * 
   * @param listenerClass
   *          the class to introspect
   */
  private void processInternalListener(Class<?> listenerClass) {
    InternalListenerMetadata listenerMetadata = InternalListenerIntrospector
        .introspect(listenerClass);
    Map<CallbackType, Method> callbacks = listenerMetadata.getCallbacks();
    if (callbacks != null) {
      for (Map.Entry<CallbackType, Method> entry : callbacks.entrySet()) {
        CallbackType callbackType = entry.getKey();
        Method callbackMethod = entry.getValue();
        CallbackMetadata callbackMetadata = new CallbackMetadata(EntityListenerType.INTERNAL,
            callbackType, callbackMethod);
        metadata.put(callbackType, callbackMetadata);
      }
    }
  }

}
