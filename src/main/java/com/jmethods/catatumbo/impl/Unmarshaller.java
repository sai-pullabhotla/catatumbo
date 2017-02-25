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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import com.google.cloud.datastore.BaseEntity;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityValue;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.ProjectionEntity;
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
	 * Input - Native Entity to unmarshal, could be a ProjectionEntity or an
	 * Entity
	 */
	private BaseEntity<?> nativeEntity;

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
	 * 
	 * @param nativeEntity
	 *            the native entity to unmarshal
	 * @param entityClass
	 *            the expected model type
	 */
	private Unmarshaller(BaseEntity<?> nativeEntity, Class<?> entityClass) {
		this.nativeEntity = nativeEntity;
		entityMetadata = EntityIntrospector.introspect(entityClass);

	}

	/**
	 * Unmarshals the given native Entity into an object of given type,
	 * entityClass.
	 * 
	 * @param <T>
	 *            target object type
	 * @param nativeEntity
	 *            the native Entity
	 * @param entityClass
	 *            the target type
	 * @return Object that is equivalent to the given native entity. If the
	 *         given <code>datastoreEntity</code> is <code>null</code>, returns
	 *         <code>null</code>.
	 */
	public static <T> T unmarshal(Entity nativeEntity, Class<T> entityClass) {
		return unmarshalBaseEntity(nativeEntity, entityClass);
	}

	/**
	 * Unmarshals the given native ProjectionEntity into an object of given
	 * type, entityClass.
	 * 
	 * @param <T>
	 *            target object type
	 * @param nativeEntity
	 *            the native Entity
	 * @param entityClass
	 *            the target type
	 * @return Object that is equivalent to the given native entity. If the
	 *         given <code>datastoreEntity</code> is <code>null</code>, returns
	 *         <code>null</code>.
	 */
	public static <T> T unmarshal(ProjectionEntity nativeEntity, Class<T> entityClass) {
		return unmarshalBaseEntity(nativeEntity, entityClass);
	}

	/**
	 * Unmarshals the given BaseEntity and returns the equivalent model object.
	 * 
	 * @param nativeEntity
	 *            the native entity to unmarshal
	 * @param entityClass
	 *            the target type of the model class
	 * @return the model object
	 */
	private static <T> T unmarshalBaseEntity(BaseEntity<?> nativeEntity, Class<T> entityClass) {
		if (nativeEntity == null) {
			return null;
		}
		Unmarshaller unmarshaller = new Unmarshaller(nativeEntity, entityClass);
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
	@SuppressWarnings("unchecked")
	private <T> T unmarshal() {

		try {
			instantiateEntity();
			unmarshalIdentifier();
			unmarshalKeyAndParentKey();
			unmarshalProperties();
			unmarshalEmbeddedFields();
			return (T) entity;
		} catch (Exception ex) {
			throw new EntityManagerException(ex.getMessage(), ex);
		}
	}

	/**
	 * Instantiates the entity.
	 */
	private void instantiateEntity() {
		entity = IntrospectionUtils.instantiateObject(entityMetadata.getEntityClass());
	}

	/**
	 * Unamrshals the identifier.
	 * 
	 * @throws IllegalAccessException
	 *             propagated
	 * @throws IllegalArgumentException
	 *             propagated
	 * @throws InvocationTargetException
	 *             propagated
	 */
	private void unmarshalIdentifier() throws IllegalAccessException, InvocationTargetException {
		IdentifierMetadata identifierMetadata = entityMetadata.getIdentifierMetadata();
		Method writeMethod = identifierMetadata.getWriteMethod();
		writeMethod.invoke(entity, ((Key) nativeEntity.getKey()).getNameOrId());
	}

	/**
	 * Unamrshals the entity's key and parent key.
	 * 
	 * @throws IllegalAccessException
	 *             propagated
	 * @throws IllegalArgumentException
	 *             propagated
	 * @throws InvocationTargetException
	 *             propagated
	 */
	private void unmarshalKeyAndParentKey() throws IllegalAccessException, InvocationTargetException {
		KeyMetadata keyMetadata = entityMetadata.getKeyMetadata();
		if (keyMetadata != null) {
			Method writeMethod = keyMetadata.getWriteMethod();
			Key entityKey = (Key) nativeEntity.getKey();
			writeMethod.invoke(entity, new DefaultDatastoreKey(entityKey));
		}

		ParentKeyMetadata parentKeyMetadata = entityMetadata.getParentKeyMetadata();
		if (parentKeyMetadata != null) {
			Method writeMethod = parentKeyMetadata.getWriteMethod();
			Key parentKey = nativeEntity.getKey().getParent();
			if (parentKey != null) {
				writeMethod.invoke(entity, new DefaultDatastoreKey(parentKey));
			}
		}
	}

	/**
	 * Unmarshal all the properties.
	 * 
	 * @throws IllegalAccessException
	 *             propagated
	 * @throws IllegalArgumentException
	 *             propagated
	 * @throws InvocationTargetException
	 *             propagated
	 */
	private void unmarshalProperties() throws IllegalAccessException, InvocationTargetException {
		Collection<PropertyMetadata> propertyMetadataCollection = entityMetadata.getPropertyMetadataCollection();
		for (PropertyMetadata propertyMetadata : propertyMetadataCollection) {
			unmarshalProperty(propertyMetadata, entity);
		}
	}

	/**
	 * Unmarshal the embedded fields of this entity.
	 * 
	 * @throws NoSuchMethodException
	 *             propagated
	 * @throws SecurityException
	 *             propagated
	 * @throws InstantiationException
	 *             propagated
	 * @throws IllegalAccessException
	 *             propagated
	 * @throws IllegalArgumentException
	 *             propagated
	 * @throws InvocationTargetException
	 *             propagated
	 */
	private void unmarshalEmbeddedFields()
			throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		for (EmbeddedMetadata embeddedMetadata : entityMetadata.getEmbeddedMetadataCollection()) {
			if (embeddedMetadata.getStorageStrategy() == StorageStrategy.EXPLODED) {
				unmarshalWithExplodedStrategy(embeddedMetadata, entity);
			} else {
				unmarshalWithImplodedStrategy(embeddedMetadata, entity, nativeEntity);
			}
		}
	}

	/**
	 * Unmarshals the emdedded field represented by the given embedded metadata.
	 * 
	 * @param embeddedMetadata
	 *            the embedded metadata
	 * @param target
	 *            the target object that needs to be updated
	 * @throws NoSuchMethodException
	 *             propagated
	 * @throws SecurityException
	 *             propagated
	 * @throws InstantiationException
	 *             propagated
	 * @throws IllegalAccessException
	 *             propagated
	 * @throws InvocationTargetException
	 *             propagated
	 */
	private void unmarshalWithExplodedStrategy(EmbeddedMetadata embeddedMetadata, Object target)
			throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Object embeddedObject = IntrospectionUtils.initializeEmbedded(embeddedMetadata, target);
		for (PropertyMetadata propertyMetadata : embeddedMetadata.getPropertyMetadataCollection()) {
			unmarshalProperty(propertyMetadata, embeddedObject);
		}
		for (EmbeddedMetadata embeddedMetadata2 : embeddedMetadata.getEmbeddedMetadataCollection()) {
			unmarshalWithExplodedStrategy(embeddedMetadata2, embeddedObject);
		}
	}

	/**
	 * Unmarshals the embedded field represented by the given metadata.
	 * 
	 * @param embeddedMetadata
	 *            the metadata of the field to unmarshal
	 * @param target
	 *            the object in which the embedded field is declared/accessible
	 *            from
	 * @param nativeEntity
	 *            the native entity from which the embedded entity is to be
	 *            extracted
	 * @throws InvocationTargetException
	 *             propagated
	 * @throws IllegalArgumentException
	 *             propagated
	 * @throws IllegalAccessException
	 *             propagated
	 */
	private static void unmarshalWithImplodedStrategy(EmbeddedMetadata embeddedMetadata, Object target,
			BaseEntity<?> nativeEntity) throws IllegalAccessException, InvocationTargetException {
		Object embeddedObject = null;
		FullEntity<?> nativeEmbeddedEntity = null;
		String propertyName = embeddedMetadata.getMappedName();
		if (nativeEntity.contains(propertyName)) {
			Value<?> nativeValue = nativeEntity.getValue(propertyName);
			if (nativeValue instanceof NullValue) {
				embeddedMetadata.getWriteMethod().invoke(target, embeddedObject);
			} else {
				nativeEmbeddedEntity = ((EntityValue) nativeValue).get();
				embeddedObject = IntrospectionUtils.initializeEmbedded(embeddedMetadata, target);
			}
		}
		if (embeddedObject == null) {
			return;
		}
		for (PropertyMetadata propertyMetadata : embeddedMetadata.getPropertyMetadataCollection()) {
			unmarshalProperty(propertyMetadata, embeddedObject, nativeEmbeddedEntity);
		}
		for (EmbeddedMetadata embeddedMetadata2 : embeddedMetadata.getEmbeddedMetadataCollection()) {
			unmarshalWithImplodedStrategy(embeddedMetadata2, embeddedObject, nativeEmbeddedEntity);
		}

	}

	/**
	 * Unmarshals the property represented by the given property metadata and
	 * updates the target object with the property value.
	 * 
	 * @param propertyMetadata
	 *            the property metadata
	 * @param target
	 *            the target object to update
	 * @throws IllegalAccessException
	 *             propagated
	 * @throws IllegalArgumentException
	 *             propagated
	 * @throws InvocationTargetException
	 *             propagated
	 */
	private void unmarshalProperty(PropertyMetadata propertyMetadata, Object target)
			throws IllegalAccessException, InvocationTargetException {
		unmarshalProperty(propertyMetadata, target, nativeEntity);
	}

	/**
	 * Unmarshals the property with the given metadata and sets the unmarshalled
	 * value on the given <code>target</code> object.
	 * 
	 * @param propertyMetadata
	 *            the metadata of the property
	 * @param target
	 *            the target object to set the unmarshalled value on
	 * @param nativeEntity
	 *            the native entity containing the source property
	 * @throws IllegalAccessException
	 *             propagated
	 * @throws InvocationTargetException
	 *             propagated
	 */
	private static void unmarshalProperty(PropertyMetadata propertyMetadata, Object target, BaseEntity<?> nativeEntity)
			throws IllegalAccessException, InvocationTargetException {
		// The datastore may not have every property that the entity class has
		// defined. For example, if we are running a projection query or if the
		// entity class added a new field without updating existing data...So
		// make sure there is a property or else, we get an exception from the
		// datastore.
		if (nativeEntity.contains(propertyMetadata.getMappedName())) {
			Value<?> datastoreValue = nativeEntity.getValue(propertyMetadata.getMappedName());
			Object entityValue = propertyMetadata.getMapper().toModel(datastoreValue);
			Method writeMethod = propertyMetadata.getWriteMethod();
			writeMethod.invoke(target, entityValue);
		}
	}

}
