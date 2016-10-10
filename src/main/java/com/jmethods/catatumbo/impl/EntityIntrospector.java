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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jmethods.catatumbo.Embedded;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Ignore;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.MappedSuperClass;
import com.jmethods.catatumbo.ParentKey;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.PropertyOverride;
import com.jmethods.catatumbo.PropertyOverrides;
import com.jmethods.catatumbo.Version;

/**
 * Introspector for entity classes. The introspect method gathers metadata about
 * the given entity class. This metadata is needed for performing the object to
 * Datastore mapping and vice versa. The first time an entity class is
 * introspected, the metadata is cached and reused for better performance. The
 * introspector traverses through the entire entity graph to process simple (or
 * primitive) fields as well as an embedded complex objects.
 *
 * @author Sai Pullabhotla
 */
public class EntityIntrospector {

	/**
	 * Cache of introspected classes
	 */
	private static Cache<Class<?>, EntityMetadata> cache = new Cache<>(64);

	/**
	 * Data types that are valid for identifiers
	 */
	private static final DataType[] VALID_IDENTIFIER_TYPES = { DataType.LONG, DataType.LONG_OBJECT, DataType.STRING };

	static {
		// Sort the valid types so we can do binary search.
		Arrays.sort(VALID_IDENTIFIER_TYPES);
	}

	/**
	 * The class to introspect
	 */
	private final Class<?> entityClass;

	/**
	 * Output of the introspection, the metadata about the entity
	 */
	private EntityMetadata entityMetadata;

	/**
	 * Creates a new instance of <code>EntityIntrospector</code>.
	 *
	 * @param entityClass
	 *            the entity class to introspect
	 */
	private EntityIntrospector(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	/**
	 * Introspects the given entity and returns the metadata associated with the
	 * entity.
	 *
	 * @param entity
	 *            the entity object to introspect.
	 * @return the meatadata of the entity
	 */
	public static EntityMetadata introspect(Object entity) {
		return introspect(entity.getClass());
	}

	/**
	 * Introspects the given entity class and returns the metadata of the
	 * entity.
	 *
	 * @param entityClass
	 *            the entity class to introspect
	 * @return the metadata of the entity
	 */
	public static EntityMetadata introspect(Class<?> entityClass) {
		EntityMetadata cachedMetadata = cache.get(entityClass);
		if (cachedMetadata != null) {
			return cachedMetadata;
		}
		return loadMetadata(entityClass);
	}

	/**
	 * Loads the metadata for the given class and puts it in the cache and
	 * returns it.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @return the metadata for the given class
	 */
	private static EntityMetadata loadMetadata(Class<?> entityClass) {
		synchronized (entityClass) {
			EntityMetadata metadata = cache.get(entityClass);
			if (metadata == null) {
				EntityIntrospector introspector = new EntityIntrospector(entityClass);
				introspector.process();
				metadata = introspector.entityMetadata;
				cache.put(entityClass, metadata);
			}
			return metadata;
		}
	}

	/**
	 * Processes the entity class using reflection and builds the metadata.
	 */
	private void process() {
		// If the class does not have the Entity annotation, throw an exception.
		Entity entity = entityClass.getAnnotation(Entity.class);
		if (entity == null) {
			String message = String.format("Class %s must have %s annotation", entityClass.getName(),
					Entity.class.getName());
			throw new EntityManagerException(message);
		}
		String kind = entity.kind();
		if (kind.trim().length() == 0) {
			kind = entityClass.getSimpleName();
		}
		entityMetadata = new EntityMetadata(entityClass, kind);

		processPropertyOverrides();

		processFields();
		// If we did not find valid Identifier...
		if (entityMetadata.getIdentifierMetadata() == null) {
			throw new EntityManagerException(String.format("Class %s requires a field with annotation of %s",
					entityClass.getName(), Identifier.class.getName()));
		}
		entityMetadata.setEntityListenersMetadata(EntityListenersIntrospector.introspect(entityClass));
		entityMetadata.cleanup();
	}

	/**
	 * Processes the property overrides for the embedded objects, if any.
	 */
	private void processPropertyOverrides() {
		PropertyOverrides propertyOverrides = entityClass.getAnnotation(PropertyOverrides.class);
		if (propertyOverrides == null) {
			return;
		}
		PropertyOverride[] propertyOverridesArray = propertyOverrides.value();
		for (PropertyOverride propertyOverride : propertyOverridesArray) {
			entityMetadata.putPropertyOverride(propertyOverride);
		}
	}

	/**
	 * Processes the fields defined in this entity and updates the meatadata.
	 */
	private void processFields() {
		List<Field> fields = getAllFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Ignore.class)) {
				continue;
			} else if (field.isAnnotationPresent(Identifier.class)) {
				processIdentifierField(field);
			} else if (field.isAnnotationPresent(Key.class)) {
				processKeyField(field);
			} else if (field.isAnnotationPresent(ParentKey.class)) {
				processParentKeyField(field);
			} else if (field.isAnnotationPresent(Embedded.class)) {
				processEmbeddedField(field);
			} else {
				processField(field);
			}
		}
	}

	/**
	 * Processes the entity class and any super classes that are
	 * MappedSupperClasses and returns the fields.
	 * 
	 * @return all fields of the entity hierarchy.
	 */
	private List<Field> getAllFields() {
		List<Field> allFields = new ArrayList<>();
		Class<?> clazz = entityClass;
		boolean stop = false;
		do {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				allFields.add(field);
			}
			clazz = clazz.getSuperclass();
			stop = (clazz == null || !clazz.isAnnotationPresent(MappedSuperClass.class));
		} while (!stop);
		return allFields;
	}

	/**
	 * Processes the identifier field and builds the identifier metadata.
	 *
	 * @param field
	 *            the identifier field
	 */
	private void processIdentifierField(Field field) {
		String fieldName = field.getName();
		Identifier identifier = field.getAnnotation(Identifier.class);
		boolean autoGenerated = identifier.autoGenerated();
		Class<?> type = field.getType();
		DataType dataType = DataType.forClass(type);
		if (dataType == null) {
			String message = String.format("Unknown or unsupported type, %s, for field %s in class %s. ", type,
					fieldName, entityClass.getName());
			throw new EntityManagerException(message);
		}
		if (!isValidIdentifierType(dataType)) {
			throw new EntityManagerException(String.format("Invalid identifier type: %s. ", type));
		}
		IdentifierMetadata identifierMetadata = new IdentifierMetadata(field, dataType, autoGenerated);
		String readMethodName = IntrospectionUtils.getReadMethodName(field);
		Method readMethod = IntrospectionUtils.getReadMethod(entityClass, readMethodName, type);
		identifierMetadata.setReadMethod(readMethod);

		String writeMethodName = IntrospectionUtils.getWriteMethodName(field);
		Method writeMethod = IntrospectionUtils.getWriteMethod(entityClass, writeMethodName, type);
		identifierMetadata.setWriteMethod(writeMethod);

		entityMetadata.setIdentifierMetadata(identifierMetadata);
	}

	/**
	 * Processes the Key field and builds the entity metadata.
	 * 
	 * @param field
	 *            the Key field
	 */
	private void processKeyField(Field field) {
		String fieldName = field.getName();
		Class<?> type = field.getType();
		DataType dataType = DataType.KEY;
		if (!type.equals(dataType.getDataClass())) {
			String message = String.format("Invalid type, %s, for Key field %s in class %s. ", type, fieldName,
					entityClass);
			throw new EntityManagerException(message);
		}
		KeyMetadata keyMetadata = new KeyMetadata(field);
		String readMethodName = IntrospectionUtils.getReadMethodName(field);
		Method readMethod = IntrospectionUtils.getReadMethod(entityClass, readMethodName, type);
		keyMetadata.setReadMethod(readMethod);

		String writeMethodName = IntrospectionUtils.getWriteMethodName(field);
		Method writeMethod = IntrospectionUtils.getWriteMethod(entityClass, writeMethodName, type);
		keyMetadata.setWriteMethod(writeMethod);

		entityMetadata.setKeyMetadata(keyMetadata);
	}

	/**
	 * Processes the ParentKey field and builds the entity metadata.
	 * 
	 * @param field
	 *            the ParentKey field
	 */
	private void processParentKeyField(Field field) {
		String fieldName = field.getName();
		Class<?> type = field.getType();
		DataType dataType = DataType.KEY;
		if (!type.equals(dataType.getDataClass())) {
			String message = String.format("Invalid type, %s, for ParentKey field %s in class %s. ", type, fieldName,
					entityClass);
			throw new EntityManagerException(message);
		}
		ParentKeyMetadata parentKeyMetadata = new ParentKeyMetadata(field);
		String readMethodName = IntrospectionUtils.getReadMethodName(field);
		Method readMethod = IntrospectionUtils.getReadMethod(entityClass, readMethodName, type);
		parentKeyMetadata.setReadMethod(readMethod);

		String writeMethodName = IntrospectionUtils.getWriteMethodName(field);
		Method writeMethod = IntrospectionUtils.getWriteMethod(entityClass, writeMethodName, type);
		parentKeyMetadata.setWriteMethod(writeMethod);

		entityMetadata.setParentKetMetadata(parentKeyMetadata);
	}

	/**
	 * Processes the given field and generates the meatadata.
	 *
	 * @param field
	 *            the field to process
	 */
	private void processField(Field field) {
		PropertyMetadata propertyMetadata = IntrospectionUtils.getPropertyMetadata(field);
		if (propertyMetadata != null) {
			// If the field is from a super class, there might be some
			// overrides, so process those.
			if (!field.getDeclaringClass().equals(entityClass)) {
				applyPropertyOverride(propertyMetadata);
			}
			entityMetadata.putPropertyMetadata(propertyMetadata);
			if (field.isAnnotationPresent(Version.class)) {
				processVersionField(propertyMetadata);
			}
		}
	}

	/**
	 * Processes the Version annotation of the field with the given metadata.
	 * 
	 * @param propertyMetadata
	 *            the metadata of the field that has the Version annotation.
	 */
	private void processVersionField(PropertyMetadata propertyMetadata) {
		DataType dataType = propertyMetadata.getDataType();
		if (dataType != DataType.LONG) {
			String messageFormat = "Field %s in class %s must be of type %s";
			throw new EntityManagerException(String.format(messageFormat, propertyMetadata.getField(), entityClass,
					DataType.LONG.getDataClass()));
		}
		entityMetadata.setVersionMetadata(propertyMetadata);
	}

	/**
	 * Applies any override information for the property with the given
	 * metadata.
	 * 
	 * @param propertyMetadata
	 *            the metadata of the property
	 */
	private void applyPropertyOverride(PropertyMetadata propertyMetadata) {
		String name = propertyMetadata.getName();
		Property override = entityMetadata.getPropertyOverride(name);
		if (override != null) {
			String mappedName = override.name();
			if (mappedName != null && mappedName.trim().length() > 0) {
				propertyMetadata.setMappedName(mappedName);
			}
			propertyMetadata.setIndexed(override.indexed());
		}

	}

	/**
	 * Processes and gathers the metadata for the given embedded field.
	 * 
	 * @param field
	 *            the embedded field
	 */
	private void processEmbeddedField(Field field) {
		// First create EmbeddedField so we can maintain the path/depth of the
		// embedded field
		EmbeddedField embeddedField = new EmbeddedField(field);
		// Introspect the embedded field.
		EmbeddedMetadata embeddedMetadata = EmbeddedIntrospector.introspect(embeddedField, entityMetadata);
		entityMetadata.putEmbeddedMetadata(embeddedMetadata);
	}

	/**
	 * Checks to see if the given data type is valid to be used as an
	 * identifier.
	 *
	 * @param dataType
	 *            the data type to check
	 * @return true, if the given data type is valid to be used as an
	 *         identifier; false, otherwise.
	 */
	private static boolean isValidIdentifierType(DataType dataType) {
		return Arrays.binarySearch(VALID_IDENTIFIER_TYPES, dataType) >= 0;
	}

	/**
	 * Convenient method for getting the metadata of the field used for
	 * optimistic locking.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @return the metadata of the field used for optimistic locking. Returns
	 *         <code>null</code>, if the class does not have a field with
	 *         {@link Version} annotation.
	 */
	public static PropertyMetadata getVersionMetadata(Class<?> entityClass) {
		return introspect(entityClass).getVersionMetadata();
	}

	/**
	 * Convenient method for getting the metadata of the field used for
	 * optimistic locking.
	 * 
	 * @param entity
	 *            the entity
	 * @return the metadata of the field used for optimistic locking. Returns
	 *         <code>null</code>, if the class does not have a field with
	 *         {@link Version} annotation.
	 */
	public static PropertyMetadata getVersionMetadata(Object entity) {
		return introspect(entity.getClass()).getVersionMetadata();
	}

	/**
	 * Returns the Identifier Metadata for the given entity.
	 * 
	 * @param entity
	 *            the entity
	 * @return the Identifier Metadata
	 */
	public static IdentifierMetadata getIdentifierMetadata(Object entity) {
		return getIdentifierMetadata(entity.getClass());
	}

	/**
	 * Returns the Identifier Metadata for the given entity class.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @return the Identifier Metadata
	 */
	public static IdentifierMetadata getIdentifierMetadata(Class<?> entityClass) {
		return introspect(entityClass).getIdentifierMetadata();

	}

	/**
	 * Returns the metadata of entity listeners associated with the given
	 * entity.
	 * 
	 * @param entity
	 *            the entity
	 * @return the metadata of EntityListeners associated with the given entity.
	 */
	public static EntityListenersMetadata getEntityListenersMetadata(Object entity) {
		return introspect(entity.getClass()).getEntityListenersMetadata();
	}

	/**
	 * Returns the metadata of entity listeners associated with the given entity
	 * class.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @return the metadata of entity listeners associated with the given entity
	 *         class.
	 */
	public static EntityListenersMetadata getEntityListenersMetadata(Class<?> entityClass) {
		return introspect(entityClass).getEntityListenersMetadata();
	}

}
