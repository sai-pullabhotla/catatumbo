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

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import com.google.cloud.datastore.BaseEntity;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityValue;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.NullValue;
import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;
import com.google.cloud.datastore.ValueType;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.Indexer;
import com.jmethods.catatumbo.impl.IdentifierMetadata.DataType;

/**
 * Converts application's entities (POJOs) to the format needed for the
 * underlying Cloud Datastore API.
 *
 * @author Sai Pullabhotla
 */
public class Marshaller {

	/**
	 * The intent of marshalling an object. The marshalling may be different
	 * depending on the intended purpose. For example, if marshalling an object
	 * for UPDATE operation, the marshaller does not automatically generate a
	 * key.
	 * 
	 * @author Sai Pullabhotla
	 *
	 */
	public enum Intent {
		/**
		 * Insert
		 */
		INSERT(false, false),

		/**
		 * Update
		 */
		UPDATE(true, false),

		/**
		 * Upsert (Update or Insert)
		 */
		UPSERT(false, false),

		/**
		 * Delete
		 */
		DELETE(true, true),

		/**
		 * Batch Update
		 */
		BATCH_UPDATE(true, false);

		/**
		 * If a complete key is required for this Intent
		 */
		private boolean keyRequired;

		/**
		 * If this Intent (or operation) is valid on projected entities
		 */
		private boolean validOnProjectedEntities;

		/**
		 * Creates a new instance of <code>Intent</code>.
		 * 
		 * @param keyRequired
		 *            whether or not a complete key is required.
		 * @param validOnProjectedEntities
		 *            whether or not this intent is valid on projected entities
		 */
		private Intent(boolean keyRequired, boolean validOnProjectedEntities) {
			this.keyRequired = keyRequired;
			this.validOnProjectedEntities = validOnProjectedEntities;
		}

		/**
		 * Tells whether or not a complete key is required for this Intent.
		 * 
		 * @return <code>true</code>, if a complete key is required;
		 *         <code>false</code>, otherwise.
		 */
		public boolean isKeyRequired() {
			return keyRequired;
		}

		/**
		 * Tells whether or not this intent is valid on projected entities.
		 * 
		 * @return <code>true</code>, if this intent is valid/supported on
		 *         projected entities; <code>false</code>, otherwise.
		 */
		public boolean isValidOnProjectedEntities() {
			return validOnProjectedEntities;
		}

	}

	/**
	 * Reference to the EntityManager
	 */
	private DefaultEntityManager entityManager;

	/**
	 * Entity being marshaled
	 */
	private final Object entity;

	/**
	 * Metadata of the entity being marshaled
	 */
	private final EntityMetadata entityMetadata;

	/**
	 * Builder for building the Entity needed for Cloud Datastore
	 */
	private BaseEntity.Builder<?, ?> entityBuilder;

	/**
	 * Key
	 */
	private IncompleteKey key;

	/**
	 * The intent of marshalling
	 */
	private final Intent intent;

	/**
	 * Creates a new instance of <code>Marshaller</code>.
	 *
	 * @param entityManager
	 *            reference to the entity manager
	 * @param entity
	 *            the Entity to marshal
	 * @param intent
	 *            the intent of marshalling
	 */
	private Marshaller(DefaultEntityManager entityManager, Object entity, Intent intent) {
		this.entityManager = entityManager;
		this.entity = entity;
		this.intent = intent;
		entityMetadata = EntityIntrospector.introspect(entity.getClass());
		validateIntent();
	}

	/**
	 * Validates if the Intent is legal for the entity being marshalled.
	 * 
	 * @throws EntityManagerException
	 *             if the Intent is not valid for the entity being marshalled
	 */
	private void validateIntent() {
		if (entityMetadata.isProjectedEntity() && !intent.isValidOnProjectedEntities()) {
			String message = String.format("Operation %s is not allowed for ProjectedEntity %s", intent,
					entity.getClass().getName());
			throw new EntityManagerException(message);
		}
	}

	/**
	 * Marshals the given entity (POJO) into the format needed for the low level
	 * Cloud Datastore API.
	 * 
	 * @param entityManager
	 *            the entity manager
	 * @param entity
	 *            the entity to marshal
	 * @param intent
	 *            the intent or purpose of marshalling. Marshalling process
	 *            varies slightly depending on the purpose. For example, if the
	 *            purpose if INSERT or UPSERT, the marshaller would auto
	 *            generate any keys. Where as if the purpose is UPDATE, then
	 *            then marshaller will NOT generate any keys.
	 * @return the marshaled object
	 */
	@SuppressWarnings("rawtypes")
	public static BaseEntity marshal(DefaultEntityManager entityManager, Object entity, Intent intent) {
		Marshaller marshaller = new Marshaller(entityManager, entity, intent);
		return marshaller.marshal();
	}

	/**
	 * Extracts the key from the given object, entity, and returns it. The
	 * entity must have its ID set.
	 *
	 * @param entityManager
	 *            the entity manager.
	 * @param entity
	 *            the entity from which key is to be extracted
	 * @return extracted key.
	 */
	public static Key marshalKey(DefaultEntityManager entityManager, Object entity) {
		Marshaller marshaller = new Marshaller(entityManager, entity, Intent.DELETE);
		marshaller.marshalKey();
		return (Key) marshaller.key;
	}

	/**
	 * Marshals the given entity and and returns the equivalent Entity needed
	 * for the underlying Cloud Datastore API.
	 * 
	 * @return A native entity that is equivalent to the POJO being marshalled.
	 *         The returned value could either be a FullEntity or Entity.
	 */
	private BaseEntity<?> marshal() {
		marshalKey();
		if (key instanceof Key) {
			entityBuilder = Entity.newBuilder((Key) key);
		} else {
			entityBuilder = FullEntity.newBuilder(key);
		}
		marshalFields();
		marshalAutoTimestampFields();
		if (intent == Intent.UPDATE) {
			marshalVersionField();
		}
		marshalEmbeddedFields();
		return entityBuilder.build();
	}

	/**
	 * Marshals the key.
	 */
	private void marshalKey() {

		Key parent = null;

		ParentKeyMetadata parentKeyMetadata = entityMetadata.getParentKeyMetadata();
		if (parentKeyMetadata != null) {
			DatastoreKey parentDatastoreKey = (DatastoreKey) getFieldValue(parentKeyMetadata);
			if (parentDatastoreKey != null) {
				parent = parentDatastoreKey.nativeKey();
			}
		}

		IdentifierMetadata identifierMetadata = entityMetadata.getIdentifierMetadata();
		IdClassMetadata idClassMetadata = identifierMetadata.getIdClassMetadata();
		DataType identifierType = identifierMetadata.getDataType();
		Object idValue = getFieldValue(identifierMetadata);

		// If ID value is null, we don't have to worry about if it is a simple
		// type of a complex type. Otherwise, we need to see if the ID is a
		// complex type, and if it is, extract the real ID.
		if (idValue != null && idClassMetadata != null) {
			try {
				idValue = idClassMetadata.getReadMethod().invoke(idValue);
			} catch (Throwable t) {
				throw new EntityManagerException(t);
			}
		}

		boolean validId = isValidId(idValue, identifierType);
		boolean autoGenerateId = identifierMetadata.isAutoGenerated();

		if (validId) {
			if (identifierType == DataType.STRING) {
				createCompleteKey(parent, (String) idValue);
			} else {
				createCompleteKey(parent, (long) idValue);
			}
		} else {
			if (intent.isKeyRequired()) {
				throw new EntityManagerException(
						String.format("Identifier is not set or valid for entity of type %s", entity.getClass()));
			}
			if (!autoGenerateId) {
				throw new EntityManagerException(String.format(
						"Identifier is not set or valid for entity of type %s. Auto generation of ID is explicitly turned off. ",
						entity.getClass()));
			} else {
				if (identifierType == DataType.STRING) {
					createCompleteKey(parent);
				} else {
					createIncompleteKey(parent);
				}
			}
		}
	}

	/**
	 * Checks to see if the given value is a valid identifier for the given ID
	 * type.
	 * 
	 * @param idValue
	 *            the ID value
	 * @param identifierType
	 *            the identifier type
	 * @return <code>true</code>, if the given value is a valid identifier;
	 *         <code>false</code>, otherwise. For STRING type, the ID is valid
	 *         if it it contains at least one printable character. In other
	 *         words, if ((String) idValue).trim().length() > 0. For numeric
	 *         types, the ID is valid if it is not <code>null</code> or zero.
	 */
	private static boolean isValidId(Object idValue, DataType identifierType) {
		boolean validId = false;
		if (idValue != null) {
			switch (identifierType) {
			case LONG:
			case LONG_OBJECT:
				validId = (long) idValue != 0;
				break;
			case STRING:
				validId = ((String) idValue).trim().length() > 0;
				break;
			default:
				// we should never get here
				break;
			}
		}
		return validId;
	}

	/**
	 * Creates a complete key using the given parameters.
	 * 
	 * @param parent
	 *            the parent key, may be <code>null</code>.
	 * @param id
	 *            the numeric ID
	 */
	private void createCompleteKey(Key parent, long id) {
		String kind = entityMetadata.getKind();
		if (parent == null) {
			key = entityManager.newNativeKeyFactory().setKind(kind).newKey(id);
		} else {
			key = Key.newBuilder(parent, kind, id).build();
		}
	}

	/**
	 * Creates a complete key using the given parameters.
	 * 
	 * @param parent
	 *            the parent key, may be <code>null</code>.
	 * @param id
	 *            the String ID
	 */
	private void createCompleteKey(Key parent, String id) {
		String kind = entityMetadata.getKind();
		if (parent == null) {
			key = entityManager.newNativeKeyFactory().setKind(kind).newKey(id);
		} else {
			key = Key.newBuilder(parent, kind, id).build();
		}
	}

	/**
	 * Creates a CompleteKey using the given parameters. The actual ID is
	 * generated using <code>UUID.randomUUID().toString()</code>.
	 * 
	 * @param parent
	 *            the parent key, may be <code>null</code>.
	 */
	private void createCompleteKey(Key parent) {
		String kind = entityMetadata.getKind();
		String id = UUID.randomUUID().toString();
		if (parent == null) {
			key = entityManager.newNativeKeyFactory().setKind(kind).newKey(id);
		} else {
			key = Key.newBuilder(parent, kind, id).build();
		}
	}

	/**
	 * Creates an IncompleteKey.
	 * 
	 * @param parent
	 *            the parent key, may be <code>null</code>.
	 */
	private void createIncompleteKey(Key parent) {
		String kind = entityMetadata.getKind();
		if (parent == null) {
			key = entityManager.newNativeKeyFactory().setKind(kind).newKey();
		} else {
			key = IncompleteKey.newBuilder(parent, kind).build();
		}
	}

	/**
	 * Marshals all the fields.
	 */
	private void marshalFields() {
		Collection<PropertyMetadata> propertyMetadataCollection = entityMetadata.getPropertyMetadataCollection();
		for (PropertyMetadata propertyMetadata : propertyMetadataCollection) {
			marshalField(propertyMetadata, entity);
		}
	}

	/**
	 * Marshals the field with the given property metadata.
	 * 
	 * @param propertyMetadata
	 *            the metadata of the field to be marshaled.
	 * @param target
	 *            the object in which the field is defined/accessible from.
	 */
	private void marshalField(PropertyMetadata propertyMetadata, Object target) {
		marshalField(propertyMetadata, target, entityBuilder);
	}

	/**
	 * Marshals the field with the given property metadata.
	 * 
	 * @param propertyMetadata
	 *            the metadata of the field to be marshaled.
	 * @param target
	 *            the object in which the field is defined/accessible from
	 * @param entityBuilder
	 *            the native entity on which the marshaled field should be set
	 */
	private static void marshalField(PropertyMetadata propertyMetadata, Object target,
			BaseEntity.Builder<?, ?> entityBuilder) {
		Object fieldValue = IntrospectionUtils.getFieldValue(propertyMetadata, target);
		if (fieldValue == null && propertyMetadata.isOptional()) {
			return;
		}
		ValueBuilder<?, ?, ?> valueBuilder = propertyMetadata.getMapper().toDatastore(fieldValue);
		// ListValues cannot have indexing turned off. Indexing is turned on by
		// default, so we don't touch excludeFromIndexes for ListValues.
		if (valueBuilder.getValueType() != ValueType.LIST) {
			valueBuilder.setExcludeFromIndexes(!propertyMetadata.isIndexed());
		}
		Value<?> datastoreValue = valueBuilder.build();
		entityBuilder.set(propertyMetadata.getMappedName(), datastoreValue);
		Indexer indexer = propertyMetadata.getSecondaryIndexer();
		if (indexer != null) {
			entityBuilder.set(propertyMetadata.getSecondaryIndexName(), indexer.index(datastoreValue));
		}
	}

	/**
	 * Returns the value for the given field of the entity being marshaled.
	 *
	 * @param fieldMetadata
	 *            the field's metadata
	 * @return the field's value
	 */
	private Object getFieldValue(FieldMetadata fieldMetadata) {
		return IntrospectionUtils.getFieldValue(fieldMetadata, entity);
	}

	/**
	 * Marshals the embedded fields.
	 */
	private void marshalEmbeddedFields() {
		for (EmbeddedMetadata embeddedMetadata : entityMetadata.getEmbeddedMetadataCollection()) {
			if (embeddedMetadata.getStorageStrategy() == StorageStrategy.EXPLODED) {
				marshalWithExplodedStrategy(embeddedMetadata, entity);
			} else {
				ValueBuilder<?, ?, ?> embeddedEntityBuilder = marshalWithImplodedStrategy(embeddedMetadata, entity);
				if (embeddedEntityBuilder != null) {
					entityBuilder.set(embeddedMetadata.getMappedName(), embeddedEntityBuilder.build());
				}
			}
		}

	}

	/**
	 * Marshals an embedded field represented by the given metadata.
	 * 
	 * @param embeddedMetadata
	 *            the metadata of the embedded field
	 * @param target
	 *            the target object to which the embedded object belongs
	 */
	private void marshalWithExplodedStrategy(EmbeddedMetadata embeddedMetadata, Object target) {
		try {
			Object embeddedObject = initializeEmbedded(embeddedMetadata, target);
			for (PropertyMetadata propertyMetadata : embeddedMetadata.getPropertyMetadataCollection()) {
				marshalField(propertyMetadata, embeddedObject);
			}
			for (EmbeddedMetadata embeddedMetadata2 : embeddedMetadata.getEmbeddedMetadataCollection()) {
				marshalWithExplodedStrategy(embeddedMetadata2, embeddedObject);
			}
		} catch (Throwable t) {
			throw new EntityManagerException(t);
		}
	}

	/**
	 * Marshals the embedded field represented by the given metadata.
	 * 
	 * @param embeddedMetadata
	 *            the metadata of the embedded field.
	 * @param target
	 *            the object in which the embedded field is defined/accessible
	 *            from.
	 * @return the ValueBuilder equivalent to embedded object
	 */
	private ValueBuilder<?, ?, ?> marshalWithImplodedStrategy(EmbeddedMetadata embeddedMetadata, Object target) {
		try {
			Object embeddedObject = embeddedMetadata.getReadMethod().invoke(target);
			if (embeddedObject == null) {
				if (embeddedMetadata.isOptional()) {
					return null;
				}
				NullValue.Builder nullValueBuilder = NullValue.newBuilder();
				nullValueBuilder.setExcludeFromIndexes(!embeddedMetadata.isIndexed());
				return nullValueBuilder;
			}
			FullEntity.Builder<IncompleteKey> embeddedEntityBuilder = FullEntity.newBuilder();
			for (PropertyMetadata propertyMetadata : embeddedMetadata.getPropertyMetadataCollection()) {
				marshalField(propertyMetadata, embeddedObject, embeddedEntityBuilder);
			}
			for (EmbeddedMetadata embeddedMetadata2 : embeddedMetadata.getEmbeddedMetadataCollection()) {
				ValueBuilder<?, ?, ?> embeddedEntityBuilder2 = marshalWithImplodedStrategy(embeddedMetadata2,
						embeddedObject);
				if (embeddedEntityBuilder2 != null) {
					embeddedEntityBuilder.set(embeddedMetadata2.getMappedName(), embeddedEntityBuilder2.build());
				}
			}
			EntityValue.Builder valueBuilder = EntityValue.newBuilder(embeddedEntityBuilder.build());
			valueBuilder.setExcludeFromIndexes(!embeddedMetadata.isIndexed());
			return valueBuilder;

		} catch (Throwable t) {
			throw new EntityManagerException(t);
		}

	}

	/**
	 * Marshals the the automatic timestamp fields, if any.
	 */
	private void marshalAutoTimestampFields() {
		switch (intent) {
		case UPDATE:
		case UPSERT:
		case BATCH_UPDATE:
			marshalUpdatedTimestamp();
			break;
		case INSERT:
			marshalCreatedAndUpdatedTimestamp();
			break;
		default:
			break;
		}
	}

	/**
	 * Marshals the updated timestamp field.
	 */
	private void marshalUpdatedTimestamp() {
		PropertyMetadata updatedTimestampMetadata = entityMetadata.getUpdatedTimestampMetadata();
		if (updatedTimestampMetadata != null) {
			applyAutoTimestamp(updatedTimestampMetadata, System.currentTimeMillis());
		}
	}

	/**
	 * Marshals both created and updated timestamp fields.
	 */
	private void marshalCreatedAndUpdatedTimestamp() {
		PropertyMetadata createdTimestampMetadata = entityMetadata.getCreatedTimestampMetadata();
		PropertyMetadata updatedTimestampMetadata = entityMetadata.getUpdatedTimestampMetadata();
		long millis = System.currentTimeMillis();
		if (createdTimestampMetadata != null) {
			applyAutoTimestamp(createdTimestampMetadata, millis);
		}
		if (updatedTimestampMetadata != null) {
			applyAutoTimestamp(updatedTimestampMetadata, millis);
		}
	}

	/**
	 * Applies the given time, <code>millis</code>, to the property represented
	 * by the given metadata.
	 * 
	 * @param propertyMetadata
	 *            the property metadata of the field
	 * @param millis
	 *            the time in milliseconds
	 */
	private void applyAutoTimestamp(PropertyMetadata propertyMetadata, long millis) {
		Object timestamp = null;
		Class<?> fieldType = propertyMetadata.getDeclaredType();
		if (Date.class.equals(fieldType)) {
			timestamp = new Date(millis);
		} else if (Calendar.class.equals(fieldType)) {
			Calendar calendar = new Calendar.Builder().setInstant(millis).build();
			timestamp = calendar;
		} else if (Long.class.equals(fieldType) || long.class.equals(fieldType)) {
			timestamp = millis;
		} else if (OffsetDateTime.class.equals(fieldType)) {
			timestamp = OffsetDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
		} else if (ZonedDateTime.class.equals(fieldType)) {
			timestamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
		}
		ValueBuilder<?, ?, ?> valueBuilder = propertyMetadata.getMapper().toDatastore(timestamp);
		valueBuilder.setExcludeFromIndexes(!propertyMetadata.isIndexed());
		entityBuilder.set(propertyMetadata.getMappedName(), valueBuilder.build());
	}

	/**
	 * Marshals the version field, if it exists. The version will be set to one
	 * more than the previous value.
	 */
	private void marshalVersionField() {
		PropertyMetadata versionMetadata = entityMetadata.getVersionMetadata();
		if (versionMetadata != null) {
			long version = (long) IntrospectionUtils.getFieldValue(versionMetadata, entity);
			ValueBuilder<?, ?, ?> valueBuilder = versionMetadata.getMapper().toDatastore(version + 1);
			valueBuilder.setExcludeFromIndexes(!versionMetadata.isIndexed());
			entityBuilder.set(versionMetadata.getMappedName(), valueBuilder.build());
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
	private static Object initializeEmbedded(EmbeddedMetadata embeddedMetadata, Object target) {
		try {
			// If instantiation of Entity instantiated the embeddable, we will
			// use the pre-initialized embedded object.
			Object embeddedObject = embeddedMetadata.getReadMethod().invoke(target);
			if (embeddedObject == null) {
				// Otherwise, we will instantiate the embedded object, which
				// could be a Builder
				embeddedObject = IntrospectionUtils.instantiate(embeddedMetadata);
				ConstructorMetadata constructorMetadata = embeddedMetadata.getConstructorMetadata();
				if (constructorMetadata.isBuilderConstructionStrategy()) {
					// Build the Builder
					embeddedObject = constructorMetadata.getBuildMethodHandle().invoke(embeddedObject);
				} else {
					// TODO we should not be doing this?? There is no equivalent
					// of this for builder pattern
					embeddedMetadata.getWriteMethod().invoke(target, embeddedObject);
				}
			}
			return embeddedObject;
		} catch (Throwable t) {
			throw new EntityManagerException(t);
		}
	}

}
