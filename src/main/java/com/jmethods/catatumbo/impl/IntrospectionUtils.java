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
import java.lang.invoke.MethodType;
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
import com.jmethods.catatumbo.NoDefaultConstructorException;
import com.jmethods.catatumbo.Property;

/**
 * Utility methods for helping with introspection/reflection.
 * 
 * @author Sai Pullabhotla
 *
 */
public class IntrospectionUtils {

	/**
	 * Hide the default constructor.
	 */
	private IntrospectionUtils() {
		// Do nothing
	}

	/**
	 * Returns the default constructor for the class given its metadata.
	 * 
	 * @param metadata
	 *            the metadata of the class
	 * @return the default constructor
	 * @throws EntityManagerException
	 *             if no default constructor exists or any other error occurs.
	 */
	public static MethodHandle getDefaultConstructor(MetadataBase metadata) {
		try {
			return MethodHandles.publicLookup().findConstructor(metadata.getClazz(), MethodType.methodType(void.class));
		} catch (NoSuchMethodException | IllegalAccessException e) {
			String pattern = "Class %s requires a public no-arg constructor";
			throw new NoDefaultConstructorException(String.format(pattern, metadata.getClazz()), e);
		}
	}

	/**
	 * Creates and returns a new instance of the Class for the given metadata.
	 * 
	 * @param metadata
	 *            the metadata of the class
	 * @return a new instance of the of the Class to which the given metadata
	 *         belongs.
	 * @throws EntityManagerException
	 *             if any error occurs during instantiation.
	 */
	public static Object instantiate(MetadataBase metadata) {
		try {
			return metadata.getConstructor().invoke();
		} catch (Throwable t) {
			throw new EntityManagerException(t);
		}
	}

	/**
	 * Returns the meatdata for the given field.
	 * 
	 * @param field
	 *            the field whose metadata has to be prepared
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
		} catch (NoAccessorMethodException exp) {
			if (property != null) {
				throw exp;
			}
		}
		return null;
	}

	/**
	 * Finds and returns a {@link MethodHandle} that can be used to read the
	 * field represented by the given metadata.
	 * 
	 * @param metadata
	 *            the metadata of the field
	 * @return the {@link MethodHandle} for reading the fieldd's value
	 * @throws EntityManagerException
	 *             if no read method exists
	 */
	public static MethodHandle findReadMethodHandle(FieldMetadata metadata) {
		Field field = metadata.getField();
		String readMethodName;
		if (boolean.class.equals(field.getType())) {
			try {
				readMethodName = IntrospectionUtils.getReadMethodNameForBoolean(field);
				return findReadMethodHandle(field.getDeclaringClass(), readMethodName, field.getType());
			} catch (EntityManagerException e) {
				// Do nothing... perhaps there is no isXXX method, so we will
				// try the getXXX method.
			}
		}
		readMethodName = getReadMethodName(field);
		return findReadMethodHandle(field.getDeclaringClass(), readMethodName, field.getType());
	}

	/**
	 * Finds and returns a {@link MethodHandle} that can be used to read a
	 * field.
	 * 
	 * @param clazz
	 *            the class name
	 * @param methodName
	 *            the method name
	 * @param returnType
	 *            the return type
	 * @return a {@link MethodHandle} that can be used to read a field.
	 * @throws EntityManagerException
	 *             if no matching method exists
	 */
	public static MethodHandle findReadMethodHandle(Class<?> clazz, String methodName, Class<?> returnType) {
		try {
			return MethodHandles.publicLookup().findVirtual(clazz, methodName, MethodType.methodType(returnType));
		} catch (NoSuchMethodException | IllegalAccessException e) {
			String pattern = "Class %s requires public %s %s() method. ";
			throw new NoAccessorMethodException(
					String.format(pattern, clazz.getName(), returnType.getName(), methodName), e);
		}
	}

	/**
	 * Finds and returns a {@link MethodHandle} that can be used to update a
	 * field represented by the given metadata.
	 * 
	 * @param metadata
	 *            the metadata of the field.
	 * @return the {@link MethodHandle} to update the field
	 * @throws EntityManagerException
	 *             if no matching method exists
	 */
	public static MethodHandle findWriteMethodHandle(FieldMetadata metadata) {
		Field field = metadata.getField();
		String writeMethodName = getWriteMethodName(field);
		return findWriteMethodHandle(field.getDeclaringClass(), writeMethodName, field.getType());
	}

	/**
	 * Finds and returns a {@link MethodHandle} for updating a field.
	 * 
	 * @param clazz
	 *            the class
	 * @param methodName
	 *            the write method name
	 * @param parameterType
	 *            the parameter type
	 * @return the {@link MethodHandle}
	 * @throws EntityManagerException
	 *             if no matching method exists.
	 */
	public static MethodHandle findWriteMethodHandle(Class<?> clazz, String methodName, Class<?> parameterType) {
		try {
			return MethodHandles.publicLookup().findVirtual(clazz, methodName,
					MethodType.methodType(void.class, parameterType));
		} catch (NoSuchMethodException | IllegalAccessException e) {
			String pattern = "Class %s requires public void %s(%s) method. ";
			throw new NoAccessorMethodException(
					String.format(pattern, clazz.getName(), methodName, parameterType.getName()), e);
		}
	}

	/**
	 * Returns the read method for the given property.
	 *
	 * @param propertyMetadata
	 *            the property metadata.
	 * @return the read method for the given property.
	 */
	public static Method getReadMethod(PropertyMetadata propertyMetadata) {
		Field field = propertyMetadata.getField();
		Method readMethod;
		if (boolean.class.equals(propertyMetadata.getDeclaredType())) {
			String booleanReadMethodName = getReadMethodNameForBoolean(field);
			try {
				readMethod = getReadMethod(propertyMetadata, booleanReadMethodName);
				return readMethod;
			} catch (EntityManagerException exp) {
				// Do nothing... try the default option - getXXX method.
			}
		}
		String readMethodName = getReadMethodName(field);
		readMethod = getReadMethod(propertyMetadata, readMethodName);
		return readMethod;
	}

	/**
	 * Gets the method object with the given name and return type.
	 * 
	 * @param clazz
	 *            the class that is supposed to have the specified method
	 *
	 * @param readMethodName
	 *            the method name
	 * @param expectedReturnType
	 *            the return type
	 * @return the Method object with the given name and return type.
	 */
	public static Method getReadMethod(Class<?> clazz, String readMethodName, Class<?> expectedReturnType) {
		try {
			Method readMethod = clazz.getMethod(readMethodName);
			int modifier = readMethod.getModifiers();
			if (Modifier.isStatic(modifier)) {
				throw new EntityManagerException(
						String.format("Method %s in class %s must not be static", readMethodName, clazz.getName()));
			}
			if (Modifier.isAbstract(modifier)) {
				throw new EntityManagerException(
						String.format("Method %s in class %s must not be abstract", readMethodName, clazz.getName()));
			}
			if (!Modifier.isPublic(modifier)) {
				throw new EntityManagerException(
						String.format("Method %s in class %s must  be public", readMethodName, clazz.getName()));
			}

			if (!expectedReturnType.isAssignableFrom(readMethod.getReturnType())) {
				throw new EntityManagerException(String.format("Method %s in class %s must have a return type of %s",
						readMethodName, clazz.getName(), expectedReturnType));
			}
			return readMethod;
		} catch (NoSuchMethodException exp) {
			throw new EntityManagerException(String.format("Method %s %s() is required in class %s",
					expectedReturnType.getName(), readMethodName, clazz.getName()), exp);
		} catch (SecurityException exp) {
			throw new EntityManagerException(exp.getMessage(), exp);
		}

	}

	/**
	 * Returns the Method object that allows reading of the given property.
	 *
	 * @param propertyMetadata
	 *            the property metadata
	 * @param readMethodName
	 *            the method name (e.g. getXXX or isXXX).
	 * @return the read Method.
	 */
	public static Method getReadMethod(PropertyMetadata propertyMetadata, String readMethodName) {
		return getReadMethod(propertyMetadata.getField().getDeclaringClass(), readMethodName,
				propertyMetadata.getDeclaredType());
	}

	/**
	 * Returns the write method(setter method) for the given property.
	 *
	 * @param propertyMetadata
	 *            the property
	 * @return the write Method
	 */
	public static Method getWriteMethod(PropertyMetadata propertyMetadata) {
		String writeMethodName = getWriteMethodName(propertyMetadata.getField());
		return getWriteMethod(propertyMetadata.getField().getDeclaringClass(), writeMethodName,
				propertyMetadata.getField().getType());
	}

	/**
	 * Returns the write method with the given name and parameter type.
	 * 
	 * @param clazz
	 *            The class that is supposed to have the specified method.
	 *
	 * @param writeMethodName
	 *            the method name
	 * @param parameterType
	 *            the parameter type.
	 * @return the write Method.
	 */
	public static Method getWriteMethod(Class<?> clazz, String writeMethodName, Class<?> parameterType) {
		try {
			Method writeMethod = clazz.getMethod(writeMethodName, parameterType);
			int modifier = writeMethod.getModifiers();
			if (Modifier.isStatic(modifier)) {
				throw new EntityManagerException(
						String.format("Method %s in class %s must not be static", writeMethodName, clazz.getName()));
			}
			if (Modifier.isAbstract(modifier)) {
				throw new EntityManagerException(
						String.format("Method %s in class %s must not be abstract", writeMethodName, clazz.getName()));
			}
			return writeMethod;
		} catch (NoSuchMethodException exp) {
			throw new EntityManagerException(String.format("Method %s(%s) is required in class %s", writeMethodName,
					parameterType.getName(), clazz.getName()), exp);
		}

	}

	/**
	 * Returns all potentially persistable fields that were declared in the
	 * specified class. This method filters out the static fields and any fields
	 * that have an annotation of {@link Ignore}, and returns the rest of the
	 * declared fields.
	 * 
	 * @param clazz
	 *            the class
	 * @return all potentially persistable fields that were declared in the
	 *         specified class.
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
	 *            the field
	 * @return the name of the read method,
	 */
	public static String getReadMethodName(Field field) {
		return "get" + getCapitalizedName(field.getName());

	}

	/**
	 * Returns the name of the method that can be used to read the given boolean
	 * field.
	 *
	 * @param field
	 *            the field name
	 * @return the name of the read method.
	 */
	public static String getReadMethodNameForBoolean(Field field) {
		return "is" + getCapitalizedName(field.getName());

	}

	/**
	 * Returns the name of the method that can be used to write (or set) the
	 * given field.
	 *
	 * @param field
	 *            the name of the field
	 * @return the name of the write method.
	 */
	public static String getWriteMethodName(Field field) {
		return "set" + getCapitalizedName(field.getName());

	}

	/**
	 * Capitalizes the given field name.
	 *
	 * @param fieldName
	 *            the field name
	 * @return capitalized field name.
	 */
	public static String getCapitalizedName(String fieldName) {
		if (fieldName.length() > 1 && Character.isUpperCase(fieldName.charAt(1))) {
			return fieldName;
		}
		return Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
	}

	/**
	 * Creates a new object of given class by invoking the class' default public
	 * constructor.
	 * 
	 * @param clazz
	 *            the class whose instance needs to be created
	 * @return a new instance of the given class
	 * @throws EntityManagerException
	 *             if any error occurs
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
	 * Initializes the Embedded object represented by the given metadata.
	 * 
	 * @param embeddedMetadata
	 *            the metadata of the embedded field
	 * @param target
	 *            the object in which the embedded field is declared/accessible
	 *            from
	 * @return the initialized object
	 * @throws EntityManagerException
	 *             if any error occurs during initialization of the embedded
	 *             object
	 */
	public static Object initializeEmbedded(EmbeddedMetadata embeddedMetadata, Object target) {
		try {
			Object embeddedObject = embeddedMetadata.getReadMethod().invoke(target);
			if (embeddedObject == null) {
				embeddedObject = instantiate(embeddedMetadata);
				embeddedMetadata.getWriteMethod().invoke(target, embeddedObject);
			}
			return embeddedObject;
		} catch (Throwable t) {
			throw new EntityManagerException(t);
		}
	}

	/**
	 * Examines the given Collection type (List and Set) and returns the Class
	 * and Parameterized type, if any.
	 * 
	 * @param type
	 *            the Collection type
	 * @return an array of Class objects with two elements. The first element
	 *         will contain the raw type of the collection and the second will
	 *         contain the parameterized type. If the collection declaration is
	 *         not parameterized, the second element in the array is set to
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
	 * Examines the given Map type and returns the raw type, type of keys, type
	 * of values in the map.
	 * 
	 * @param type
	 *            the type of map
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
	 * Returns a public constructor of the given class with the given parameter
	 * types. Returns <code>null</code>, if there is no matching constructor.
	 * 
	 * @param clazz
	 *            the class
	 * @param parameterTypes
	 *            expected types of parameters
	 * @return the matching public constructor or <code>null</code>, if there is
	 *         no matching constructor.
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
	 *            the field to test
	 * @return <code>true</code>, if the given field is static;
	 *         <code>false</code>, otherwise.
	 */
	public static boolean isStatic(Field field) {
		int modifiers = field.getModifiers();
		return Modifier.isStatic(modifiers);
	}

	/**
	 * Returns the value of the field represented by the given metadata.
	 * 
	 * @param fieldMetadata
	 *            the metadata of the field
	 * @param target
	 *            the target object to which the field belongs.
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

}
