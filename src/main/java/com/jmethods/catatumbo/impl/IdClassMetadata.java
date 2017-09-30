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

package com.jmethods.catatumbo.impl;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.impl.IdentifierMetadata.DataType;

/**
 * Objects of this class contain the metadata of an ID Class.
 * 
 * @author Sai Pullabhotla
 *
 */
public class IdClassMetadata {

  /**
   * Name of the method for reading the underlying ID
   */
  private static final String READ_METHOD_NAME = "getValue";

  /**
   * ID Class
   */
  private final Class<?> clazz;

  /**
   * Method Handle for reading the real ID
   */
  private final MethodHandle readMethod;

  /**
   * Method Handle for the constructor to instantiate and setting the real ID.
   */
  private final MethodHandle constructor;

  /**
   * Creates a new instance of the <code>IdClassMetadata</code>.
   * 
   * @param clazz
   *          the ID class to which this metadata belongs to.
   */
  public IdClassMetadata(Class<?> clazz) {
    this.clazz = clazz;
    this.readMethod = findIdReadMethod();
    this.constructor = findConstructor();
  }

  /**
   * Returns the MethodHandle for reading the underlying ID.
   * 
   * @return the MethodHandle for reading the underlying ID.
   */
  public MethodHandle getReadMethod() {
    return readMethod;
  }

  /**
   * Returns the constructor for instantiating the setting the underlying ID.
   * 
   * @return the constructor for instantiating the setting the underlying ID.
   */
  public MethodHandle getConstructor() {
    return constructor;
  }

  /**
   * Returns the Class to which this metadata belongs.
   * 
   * @return the Class to which this metadata belongs.
   */
  public Class<?> getClazz() {
    return clazz;
  }

  /**
   * Returns the underlying type of the ID.
   * 
   * @return the underlying type of the ID.
   */
  public Class<?> getIdType() {
    return readMethod.type().returnType();
  }

  /**
   * Creates and returns the MethodHandle for reading the underlying ID.
   * 
   * @return the MethodHandle for reading the underlying ID.
   */
  private MethodHandle findIdReadMethod() {
    try {
      Method readMethod = clazz.getMethod(READ_METHOD_NAME);
      Class<?> dataClass = readMethod.getReturnType();
      DataType dataType = IdentifierMetadata.DataType.forClass(dataClass);
      if (dataType == null) {
        String pattern = "Method %s in class %s must have a return type of long, Long or String";
        String error = String.format(pattern, READ_METHOD_NAME, clazz.getName());
        throw new EntityManagerException(error);
      }
      return MethodHandles.lookup().unreflect(readMethod);
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException exp) {
      String error = String.format("Class %s must have a public %s method", clazz.getName(),
          READ_METHOD_NAME);
      throw new EntityManagerException(error, exp);
    }
  }

  /**
   * Creates and returns the MethodHandle for the constructor.
   * 
   * @return the MethodHandle for the constructor.
   */
  private MethodHandle findConstructor() {
    try {
      MethodHandle mh = MethodHandles.publicLookup().findConstructor(clazz,
          MethodType.methodType(void.class, getIdType()));
      return mh;
    } catch (NoSuchMethodException | IllegalAccessException exp) {
      String pattern = "Class %s requires a public constructor with one parameter of type %s";
      String error = String.format(pattern, clazz.getName(), getIdType());
      throw new EntityManagerException(error, exp);
    }
  }

}
