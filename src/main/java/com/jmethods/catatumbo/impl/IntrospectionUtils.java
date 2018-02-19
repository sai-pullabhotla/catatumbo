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

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.Ignore;
import com.jmethods.catatumbo.NoAccessorMethodException;
import com.jmethods.catatumbo.NoMutatorMethodException;
import com.jmethods.catatumbo.Property;

/**
 * Utility methods for helping with introspection/reflection.
 * 
 * @author Sai Pullabhotla
 *
 */
public class IntrospectionUtils {

  /**
   * Valid/Supported prefixes for mutator methods of a Builder class. Methods will be searched in
   * the order.
   */
  private static final String[] WRITE_METHOD_PREFIXES = { "set", "with", null };

  /**
   * Hide the default constructor.
   */
  private IntrospectionUtils() {
    // Do nothing
  }

  /**
   * Creates and returns a new instance of a persistence class for the given metadata. The returned
   * object will be an instance of the primary persistence class or its Builder.
   * 
   * @param metadata
   *          the metadata of the class
   * @return a new instance of the of the Class to which the given metadata belongs.
   * @throws EntityManagerException
   *           if any error occurs during instantiation.
   */
  public static Object instantiate(MetadataBase metadata) {
    try {
      return metadata.getConstructorMetadata().getConstructorMethodHandle().invoke();
    } catch (Throwable t) {
      throw new EntityManagerException(t);
    }
  }

  /**
   * Returns the metadata for the given field.
   * 
   * @param field
   *          the field whose metadata has to be prepared
   * @return metadata of the given field.
   */
  public static PropertyMetadata getPropertyMetadata(Field field) {
    Property property = field.getAnnotation(Property.class);
    // For fields that have @Property annotation, we expect both setter and
    // getter methods. For all other fields, we only treat them as
    // persistable if we find valid getter and setter methods.
    try {
      PropertyMetadata propertyMetadata = new PropertyMetadata(field);
      return propertyMetadata;
    } catch (NoAccessorMethodException | NoMutatorMethodException exp) {
      if (property != null) {
        throw exp;
      }
    }
    return null;
  }

  /**
   * Finds and returns a {@link MethodHandle} that can be used to read the field represented by the
   * given metadata.
   * 
   * @param field
   *          the field
   * 
   * @return the {@link MethodHandle} for reading the field's value
   * @throws EntityManagerException
   *           if no read method exists
   */
  public static MethodHandle findReadMethodHandle(Field field) {
    String readMethodName;
    MethodHandle mh = null;
    if (boolean.class.equals(field.getType())) {
      readMethodName = IntrospectionUtils.getReadMethodNameForBoolean(field);
      mh = findInstanceMethod(field.getDeclaringClass(), readMethodName, field.getType());
    }
    if (mh == null) {
      readMethodName = getReadMethodName(field);
      mh = findInstanceMethod(field.getDeclaringClass(), readMethodName, field.getType());
    }
    if (mh == null) {
      String pattern = "Class %s requires a public accessor method for field %s";
      throw new NoAccessorMethodException(
          String.format(pattern, field.getDeclaringClass().getName(), field.getName()));
    }
    return mh;
  }

  /**
   * Finds and returns a {@link MethodHandle} that can be used to update a field represented by the
   * given metadata.
   * 
   * @param field
   *          the field
   * 
   * @return the {@link MethodHandle} to update the field
   * @throws NoMutatorMethodException
   *           if no matching method exists
   */
  public static MethodHandle findWriteMethodHandle(Field field) {
    ConstructorMetadata constructorMetadata = ConstructorIntrospector
        .introspect(field.getDeclaringClass());
    Class<?> containerClass;
    MethodHandle mh = null;
    if (constructorMetadata.isBuilderConstructionStrategy()) {
      containerClass = constructorMetadata.getBuilderClass();
      for (String prefix : WRITE_METHOD_PREFIXES) {
        mh = findInstanceMethod(containerClass, getWriteMethodName(field, prefix), null,
            field.getType());
        if (mh != null) {
          break;
        }
      }
    } else {
      containerClass = field.getDeclaringClass();
      mh = findInstanceMethod(containerClass, getWriteMethodName(field), null, field.getType());
    }
    if (mh == null) {
      String pattern = "Class %s requires a public mutator method for field %s";
      throw new NoMutatorMethodException(
          String.format(pattern, containerClass.getName(), field.getName()));
    }
    return mh;
  }

  /**
   * Returns all potentially persistable fields that were declared in the specified class. This
   * method filters out the static fields and any fields that have an annotation of {@link Ignore},
   * and returns the rest of the declared fields.
   * 
   * @param clazz
   *          the class
   * @return all potentially persistable fields that were declared in the specified class.
   */
  public static List<Field> getPersistableFields(Class<?> clazz) {
    Field[] fields = clazz.getDeclaredFields();
    List<Field> output = new ArrayList<>(fields.length);
    for (Field field : fields) {
      if (!(field.isAnnotationPresent(Ignore.class) || isStatic(field))) {
        output.add(field);
      }
    }
    return output;
  }

  /**
   * Returns the name of the method that can be used to read the given field.
   *
   * @param field
   *          the field
   * @return the name of the read method,
   */
  public static String getReadMethodName(Field field) {
    return "get" + getCapitalizedName(field.getName());

  }

  /**
   * Returns the name of the method that can be used to read the given boolean field.
   *
   * @param field
   *          the field name
   * @return the name of the read method.
   */
  public static String getReadMethodNameForBoolean(Field field) {
    return "is" + getCapitalizedName(field.getName());

  }

  /**
   * Returns the name of the method that can be used to write (or set) the given field.
   *
   * @param field
   *          the name of the field
   * @return the name of the write method.
   */
  public static String getWriteMethodName(Field field) {
    return "set" + getCapitalizedName(field.getName());

  }

  /**
   * Returns the name of the method that can be used to write (or set) the given field.
   *
   * @param field
   *          the name of the field
   * @param prefix
   *          the prefix for the write method (e.g. set, with, etc.).
   * @return the name of the write method.
   */
  public static String getWriteMethodName(Field field, String prefix) {
    return prefix == null ? field.getName() : (prefix + getCapitalizedName(field.getName()));

  }

  /**
   * Capitalizes the given field name.
   *
   * @param fieldName
   *          the field name
   * @return capitalized field name.
   */
  public static String getCapitalizedName(String fieldName) {
    if (fieldName.length() > 1 && Character.isUpperCase(fieldName.charAt(1))) {
      return fieldName;
    }
    return Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
  }

  /**
   * Creates a new object of given class by invoking the class' default public constructor.
   * 
   * @param clazz
   *          the class whose instance needs to be created
   * @return a new instance of the given class
   * @throws EntityManagerException
   *           if any error occurs
   */
  public static Object instantiateObject(Class<?> clazz) {
    try {
      Constructor<?> constructor = clazz.getConstructor();
      return constructor.newInstance();
    } catch (Exception exp) {
      throw new EntityManagerException(exp);
    }

  }

  /**
   * Examines the given Collection type (List and Set) and returns the Class and Parameterized type,
   * if any.
   * 
   * @param type
   *          the Collection type
   * @return an array of Class objects with two elements. The first element will contain the raw
   *         type of the collection and the second will contain the parameterized type. If the
   *         collection declaration is not parameterized, the second element in the array is set to
   *         <code>null</code>.
   */
  public static Class<?>[] resolveCollectionType(Type type) {
    Class<?>[] output = new Class[2];
    if (type instanceof Class) {
      output[0] = (Class<?>) type;
    } else if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      Type[] argTypes = parameterizedType.getActualTypeArguments();
      output[0] = (Class<?>) parameterizedType.getRawType();
      if (argTypes != null && argTypes.length == 1 && argTypes[0] instanceof Class) {
        output[1] = (Class<?>) argTypes[0];
      }
    } else {
      throw new IllegalArgumentException(
          String.format("Type %s is neither a Class nor a ParameterizedType", type));
    }
    return output;
  }

  /**
   * Examines the given Map type and returns the raw type, type of keys, type of values in the map.
   * 
   * @param type
   *          the type of map
   * @return an array containing three elements:
   *         <ul>
   *         <li>Raw type of Map</li>
   *         <li>Type of Keys, may be <code>null</code></li>
   *         <li>Type of Values, may be <code>null</code></li>
   *         </ul>
   */
  public static Class<?>[] resolveMapType(Type type) {
    Class<?>[] output = new Class[3];
    if (type instanceof Class) {
      output[0] = (Class<?>) type;
    } else if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      output[0] = (Class<?>) parameterizedType.getRawType();
      Type[] argTypes = parameterizedType.getActualTypeArguments();
      if (argTypes != null && argTypes.length == 2) {
        if (argTypes[0] instanceof Class) {
          output[1] = (Class<?>) argTypes[0];
        }
        if (argTypes[1] instanceof Class) {
          output[2] = (Class<?>) argTypes[1];
        }
      }
    } else {
      throw new IllegalArgumentException(
          String.format("Type %s is neither a Class nor a ParameterizedType", type));
    }
    return output;
  }

  /**
   * Returns a public constructor of the given class with the given parameter types. Returns
   * <code>null</code>, if there is no matching constructor.
   * 
   * @param clazz
   *          the class
   * @param parameterTypes
   *          expected types of parameters
   * @return the matching public constructor or <code>null</code>, if there is no matching
   *         constructor.
   */
  public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
    try {
      if (parameterTypes != null && parameterTypes.length > 0) {
        return clazz.getConstructor(parameterTypes);
      } else {
        return clazz.getConstructor();
      }
    } catch (Exception exp) {
      return null;
    }
  }

  /**
   * Checks to see if the given field is a static field.
   * 
   * @param field
   *          the field to test
   * @return <code>true</code>, if the given field is static; <code>false</code>, otherwise.
   */
  public static boolean isStatic(Field field) {
    int modifiers = field.getModifiers();
    return Modifier.isStatic(modifiers);
  }

  /**
   * Returns the value of the field represented by the given metadata.
   * 
   * @param fieldMetadata
   *          the metadata of the field
   * @param target
   *          the target object to which the field belongs.
   * @return the value of the field.
   */
  public static Object getFieldValue(FieldMetadata fieldMetadata, Object target) {
    MethodHandle readMethod = fieldMetadata.getReadMethod();
    try {
      return readMethod.invoke(target);
    } catch (Throwable t) {
      throw new EntityManagerException(t.getMessage(), t);
    }
  }

  /**
   * Finds and returns a MethodHandle for the default constructor of the given class, {@code clazz}.
   * 
   * @param clazz
   *          the class
   * @return a MethodHandle for the default constructor. Returns {@code null} if the class does not
   *         have a public no-argument constructor.
   */
  public static MethodHandle findDefaultConstructor(Class<?> clazz) {
    MethodHandle methodHandle = null;
    try {
      Constructor<?> constructor = clazz.getConstructor();
      methodHandle = MethodHandles.publicLookup().unreflectConstructor(constructor);
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException e) {
      // No default constructor
    }
    return methodHandle;
  }

  /**
   * Finds and returns a MethodHandle for a public static method.
   * 
   * @param clazz
   *          the class to search
   * @param methodName
   *          the name of the method
   * @param expectedReturnType
   *          the expected return type. If {@code null}, any return type is treated as valid.
   * @param expectedParameterTypes
   *          expected parameter types
   * @return a MethodHandle for the specified criteria. Returns {@code null} if no method exists
   *         with the specified criteria.
   */
  public static MethodHandle findStaticMethod(Class<?> clazz, String methodName,
      Class<?> expectedReturnType, Class<?>... expectedParameterTypes) {
    return findMethod(clazz, methodName, true, expectedReturnType, expectedParameterTypes);
  }

  /**
   * Finds and returns a MethodHandle for a public instance method.
   * 
   * @param clazz
   *          the class to search
   * @param methodName
   *          the name of the method
   * @param expectedReturnType
   *          the expected return type. If {@code null}, any return type is treated as valid.
   * @param expectedParameterTypes
   *          expected parameter types
   * @return a MethodHandle for the specified criteria. Returns {@code null} if no method exists
   *         with the specified criteria.
   */
  public static MethodHandle findInstanceMethod(Class<?> clazz, String methodName,
      Class<?> expectedReturnType, Class<?>... expectedParameterTypes) {
    return findMethod(clazz, methodName, false, expectedReturnType, expectedParameterTypes);
  }

  /**
   * Finds and returns a method handle for the given criteria.
   * 
   * @param clazz
   *          the class to search
   * @param methodName
   *          the name of the method
   * @param staticMethod
   *          whether the method is static or not
   * @param expectedReturnType
   *          expected return type
   * @param expectedParameterTypes
   *          expected parameter types
   * @return a methodHandle for the specified criteria. Returns {@code null} if no method exists
   *         with the specified criteria.
   */
  private static MethodHandle findMethod(Class<?> clazz, String methodName, boolean staticMethod,
      Class<?> expectedReturnType, Class<?>... expectedParameterTypes) {
    MethodHandle methodHandle = null;
    try {
      Method method = clazz.getMethod(methodName, expectedParameterTypes);
      int modifiers = method.getModifiers();
      Class<?> returnType = method.getReturnType();
      if (Modifier.isStatic(modifiers) != staticMethod) {
        throw new NoSuchMethodException();
      }
      if (expectedReturnType != null && !expectedReturnType.isAssignableFrom(returnType)) {
        throw new NoSuchMethodException();
      }
      methodHandle = MethodHandles.publicLookup().unreflect(method);
    } catch (NoSuchMethodException | SecurityException | IllegalAccessException e) {
      // Method not found
    }
    return methodHandle;

  }

}
