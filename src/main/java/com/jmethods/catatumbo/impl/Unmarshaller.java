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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.jmethods.catatumbo.DefaultDatastoreKey;
import com.jmethods.catatumbo.EntityManagerException;

/**
 * Converts Entities retrieved from the Cloud Datastore into Entity POJOs.
 *
 * @author Sai Pullabhotla
 */
public class Unmarshaller {

	/**
	 * Input - Datastore Entity to unmarshal
	 */
	private Entity datastoreEntity;

	/**
	 * Output - unmarshalled object
	 */
	private Object entity;

	/**
	 * Entity metadata
	 */
	private EntityMetadata entityMetadata;

	/**
	 * Creates a new instance of <code>Unmarshaller</code>.
	 */
	private Unmarshaller(Entity datastoreEntity, Class entityClass) {
		this.datastoreEntity = datastoreEntity;
		entityMetadata = EntityIntrospector.introspect(entityClass);

	}

	/**
	 * Unmarshals the given native Entity into an object of given type,
	 * entityClass.
	 * 
	 * @param <T>
	 *            target object type
	 * @param datastoreEntity
	 *            the native Entity
	 * @param entityClass
	 *            the target type
	 * @return Object that is equivalent to the given native entity. If the
	 *         given <code>datastoreEntity</code> is <code>null</code>, returns
	 *         <code>null</code>.
	 */
	public static <T> T unmarshal(Entity datastoreEntity, Class<T> entityClass) {
		if (datastoreEntity == null) {
			return null;
		}
		Unmarshaller unmarshaller = new Unmarshaller(datastoreEntity, entityClass);
		return unmarshaller.unmarshal();
	}

	/**
	 * Unmarshals the given Datastore Entity and returns the equivalent Entity
	 * POJO.
	 *
	 * @param <T>
	 *            type
	 * @return the entity POJO
	 */
	private <T> T unmarshal() {

		try {
			instantiateEntity();
			unmarshalIdentifier();
			unmarshalKeyAndParentKey();
			unmarshalProperties();
			return (T) entity;
		} catch (Exception ex) {
			throw new EntityManagerException(ex.getMessage(), ex);
		}
	}

	private void instantiateEntity() throws NoSuchMethodException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Class<?> entityClass = entityMetadata.getEntityClass();
		Constructor<?> constructor = entityClass.getConstructor();
		entity = constructor.newInstance();
	}

	private void unmarshalIdentifier()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		IdentifierMetadata identifierMetadata = entityMetadata.getIdentifierMetadata();
		Method writeMethod = identifierMetadata.getWriteMethod();
		writeMethod.invoke(entity, datastoreEntity.key().nameOrId());
	}

	private void unmarshalKeyAndParentKey()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		KeyMetadata keyMetadata = entityMetadata.getKeyMetadata();
		if (keyMetadata != null) {
			Method writeMethod = keyMetadata.getWriteMethod();
			Key entityKey = datastoreEntity.key();
			writeMethod.invoke(entity, new DefaultDatastoreKey(entityKey));
		}

		ParentKeyMetadata parentKeyMetadata = entityMetadata.getParentKeyMetadata();
		if (parentKeyMetadata != null) {
			Method writeMethod = parentKeyMetadata.getWriteMethod();
			Key parentKey = datastoreEntity.key().parent();
			if (parentKey != null) {
				writeMethod.invoke(entity, new DefaultDatastoreKey(parentKey));
			}
		}
	}

	private void unmarshalProperties()
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Set<String> properties = datastoreEntity.names();
		for (String property : properties) {
			unmarshalProperty(property);
		}
	}

	private void unmarshalProperty(String property)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		PropertyMetadata propertyMetadata = entityMetadata.getPropertyMetadata(property);
		if (propertyMetadata == null) {
			System.out.println("Unmodeled property: " + property);
			// @ToDo not in the model. Perhaps add support for unmodeled
			// properties.
			return;
		}
		Value<?> datastoreValue = datastoreEntity.getValue(property);
		Object entityValue = null;
		if (!(datastoreValue instanceof NullValue)) {
			PropertyConverter converter = propertyMetadata.getDataType().getConverter();
			entityValue = converter.toObject(datastoreValue);
			Method writeMethod = propertyMetadata.getWriteMethod();
			writeMethod.invoke(entity, entityValue);
		}
	}

}
