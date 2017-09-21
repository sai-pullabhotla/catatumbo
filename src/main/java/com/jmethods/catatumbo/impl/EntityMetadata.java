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

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import com.jmethods.catatumbo.CreatedTimestamp;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.ParentKey;
import com.jmethods.catatumbo.ProjectedEntity;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.PropertyOverride;
import com.jmethods.catatumbo.UpdatedTimestamp;
import com.jmethods.catatumbo.Version;

/**
 * Objects of this class hold metadata information about an entity. Metadata
 * includes all the information that is needed to map Java objects to the Cloud
 * Datastore and vice versa.
 * 
 *
 * @author Sai Pullabhotla
 */
public class EntityMetadata extends MetadataBase {

	/**
	 * Entity class to which this metadata belongs
	 */
	private Class<?> entityClass;

	/**
	 * Whether or not this metadata belongs to a ProjectedEntity
	 */
	private boolean projectedEntity;

	/**
	 * Entity kind
	 */
	private String kind;

	/**
	 * Metadata about the entity's identifier
	 */
	private IdentifierMetadata identifierMetadata;

	/**
	 * Metadata of the full key, including any ancestor keys.
	 */
	private KeyMetadata keyMetadata;

	/**
	 * Metadata of the parent key.
	 */
	private ParentKeyMetadata parentKeyMetadata;

	/**
	 * Metadata of the field that is used for optimistic locking/entity
	 * versioning
	 */
	private PropertyMetadata versionMetadata;

	/**
	 * Metadata of the field that is used to store the creation timestamp of an
	 * entity.
	 */
	private PropertyMetadata createdTimestampMetadata;

	/**
	 * Metadata of the field that is used to store the modification timestamp of
	 * an entity.
	 */
	private PropertyMetadata updatedTimestampMetadata;

	/**
	 * Property overrides for embedded fields of the entity. The key is the
	 * override name that uniquely identifies a primitive property in the entity
	 * tree.
	 */
	private Map<String, Property> propertyOverrideMap;

	/**
	 * Master list of properties, used for detecting duplicate property names.
	 * Key is the mapped name (or property name in the datastore) and value is
	 * the qualified name of the field to report in the exception.
	 */
	private Map<String, String> masterPropertyMetadataMap;

	/**
	 * Metadata of various entity listeners
	 */
	private EntityListenersMetadata entityListenersMetadata;

	/**
	 * Creates a new instance of <code>EntityMetadata</code>.
	 *
	 * @param entityClass
	 *            the entity class
	 * @param kind
	 *            the entity kind
	 */
	public EntityMetadata(Class<?> entityClass, String kind) {
		this(entityClass, kind, false);
	}

	/**
	 * Creates a new instance of <code>EntityMetadata</code>.
	 *
	 * @param entityClass
	 *            the entity class
	 * @param kind
	 *            the entity kind
	 * @param projectedEntity
	 *            whether or not the entity is a projected entity
	 */
	public EntityMetadata(Class<?> entityClass, String kind, boolean projectedEntity) {
		super(entityClass);
		this.entityClass = entityClass;
		this.kind = kind;
		this.projectedEntity = projectedEntity;
		propertyOverrideMap = new HashMap<>();
		masterPropertyMetadataMap = new HashMap<>();
	}

	/**
	 * Returns the entity class to which this metadata belongs.
	 *
	 * @return the entity class to which this metadata belongs.
	 */
	public Class<?> getEntityClass() {
		return entityClass;
	}

	/**
	 * Tells whether or not this metadata belongs to a {@link ProjectedEntity}.
	 * 
	 * @return <code>true</code>, if this metadata belongs to a
	 *         {@link ProjectedEntity}; <code>false</code>, otherwise.
	 */
	public boolean isProjectedEntity() {
		return projectedEntity;
	}

	/**
	 * Returns the entity kind.
	 *
	 * @return the entity kind.
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * Returns the metadata of the identifier.
	 *
	 * @return the metadata of the identifier.
	 */
	public IdentifierMetadata getIdentifierMetadata() {
		return identifierMetadata;
	}

	/**
	 * Sets the metadata of the identifier. An exception will be thrown if there
	 * is an IdentifierMetadata already set.
	 *
	 * @param identifierMetadata
	 *            the metadata of the identifier.
	 */
	public void setIdentifierMetadata(IdentifierMetadata identifierMetadata) {
		if (this.identifierMetadata != null) {
			String format = "Class %s has at least two fields, %s and %s, marked with %s annotation. Only one field can be marked as an identifier. ";
			String message = String.format(format, entityClass.getName(), this.identifierMetadata.getName(),
					identifierMetadata.getName(), Identifier.class.getName());
			throw new EntityManagerException(message);
		}
		this.identifierMetadata = identifierMetadata;
	}

	/**
	 * Returns the metadata of the entity's full key.
	 *
	 * @return the metadata of the entity's full key. May return
	 *         <code>null</code>, if the entity does not have a field annotated
	 *         with @Key.
	 */
	public KeyMetadata getKeyMetadata() {
		return keyMetadata;
	}

	/**
	 * Sets the metadata of the Key field.
	 * 
	 * @param keyMetadata
	 *            the key metadata.
	 */
	public void setKeyMetadata(KeyMetadata keyMetadata) {
		if (this.keyMetadata != null) {
			String format = "Class %s has two fields, %s and %s marked with %s annotation. Only one field can be marked as Key. ";
			String message = String.format(format, entityClass.getName(), this.keyMetadata.getName(),
					keyMetadata.getName(), Key.class.getName());
			throw new EntityManagerException(message);

		}
		this.keyMetadata = keyMetadata;
	}

	/**
	 * Returns the metadata of the Parent Key.
	 * 
	 * @return the metadata of the Parent Key.May return <code>null</code>.
	 */
	public ParentKeyMetadata getParentKeyMetadata() {
		return parentKeyMetadata;
	}

	/**
	 * Sets the metadata about the parent key.
	 * 
	 * @param parentKeyMetadata
	 *            the parent key metadata.
	 */
	public void setParentKetMetadata(ParentKeyMetadata parentKeyMetadata) {
		if (this.parentKeyMetadata != null) {
			String format = "Class %s has two fields, %s and %s marked with %s annotation. Only one field can be marked as ParentKey. ";
			String message = String.format(format, entityClass.getName(), this.parentKeyMetadata.getName(),
					parentKeyMetadata.getName(), ParentKey.class.getName());
			throw new EntityManagerException(message);

		}
		this.parentKeyMetadata = parentKeyMetadata;
	}

	/**
	 * Returns the metadata of the field that is used for optimistic locking.
	 * 
	 * @return the versionMetadata the metadata of the field that is used for
	 *         optimistic locking.
	 */
	public PropertyMetadata getVersionMetadata() {
		return versionMetadata;
	}

	/**
	 * Sets the metadata of the field that is used for optimistic locking.
	 * 
	 * @param versionMetadata
	 *            metadata of the field that is used for optimistic locking.
	 */
	public void setVersionMetadata(PropertyMetadata versionMetadata) {
		if (this.versionMetadata != null) {
			throwDuplicateAnnotationException(entityClass, Version.class, this.versionMetadata, versionMetadata);
		}
		this.versionMetadata = versionMetadata;
	}

	/**
	 * Returns the metadata of the field that was marked with
	 * {@link CreatedTimestamp} annotation.
	 * 
	 * @return the metadata of the field that was marked with
	 *         {@link CreatedTimestamp} annotation. The returned value may be
	 *         <code>null</code>, if the entity does not have a field with
	 *         {@link CreatedTimestamp} annotation.
	 */
	public PropertyMetadata getCreatedTimestampMetadata() {
		return createdTimestampMetadata;
	}

	/**
	 * Sets the created timestamp metadata to the given value.
	 * 
	 * @param createdTimestampMetadata
	 *            the created timestamp metadata
	 */
	public void setCreatedTimestampMetadata(PropertyMetadata createdTimestampMetadata) {
		if (this.createdTimestampMetadata != null) {
			throwDuplicateAnnotationException(entityClass, CreatedTimestamp.class, this.createdTimestampMetadata,
					createdTimestampMetadata);
		}
		this.createdTimestampMetadata = createdTimestampMetadata;
	}

	/**
	 * Returns the metadata of the field that was marked with
	 * {@link UpdatedTimestamp} annotation.
	 * 
	 * @return the metadata of the field that was marked with
	 *         {@link UpdatedTimestamp} annotation. The returned value may be
	 *         <code>null</code>, if the entity does not have a field with
	 *         {@link UpdatedTimestamp} annotation.
	 */
	public PropertyMetadata getUpdatedTimestampMetadata() {
		return updatedTimestampMetadata;
	}

	/**
	 * @param updatedTimestampMetadata
	 *            the updatedTimestampMetadata to set
	 */
	public void setUpdatedTimestampMetadata(PropertyMetadata updatedTimestampMetadata) {
		if (this.updatedTimestampMetadata != null) {
			throwDuplicateAnnotationException(entityClass, UpdatedTimestamp.class, this.updatedTimestampMetadata,
					updatedTimestampMetadata);
		}
		this.updatedTimestampMetadata = updatedTimestampMetadata;
	}

	/**
	 * Puts/adds the given property override.
	 * 
	 * @param propertyOverride
	 *            the property override
	 */
	public void putPropertyOverride(PropertyOverride propertyOverride) {
		propertyOverrideMap.put(propertyOverride.name(), propertyOverride.property());
	}

	/**
	 * Returns the property override, if any for the given name. May return
	 * <code>null</code> if there is no override exists for the given name.
	 * 
	 * @param name
	 *            the name of the property
	 * @return the property override for the given property name. May return
	 *         <code>null</code>.
	 */
	public Property getPropertyOverride(String name) {
		return propertyOverrideMap.get(name);
	}

	/**
	 * Updates the master property metadata map with the given property
	 * metadata.
	 * 
	 * @param mappedName
	 *            the mapped name (or property name in the datastore)
	 * @param qualifiedName
	 *            the qualified name of the field
	 * 
	 * @throws EntityManagerException
	 *             if a property with the same mapped name already exists.
	 */
	public void updateMasterPropertyMetadataMap(String mappedName, String qualifiedName) {
		String old = masterPropertyMetadataMap.put(mappedName, qualifiedName);
		if (old != null) {
			String message = "Duplicate property %s in entity %s. Check fields %s and %s";
			throw new EntityManagerException(
					String.format(message, mappedName, entityClass.getName(), old, qualifiedName));
		}

	}

	/**
	 * Returns the metadata of the entity listeners.
	 * 
	 * @return the metadata of the entity listeners.
	 */
	public EntityListenersMetadata getEntityListenersMetadata() {
		return entityListenersMetadata;
	}

	/**
	 * Sets the metadata of the entity listeners.
	 * 
	 * @param entityListenersMetadata
	 *            the metadata of the entity listeners.
	 */
	public void setEntityListenersMetadata(EntityListenersMetadata entityListenersMetadata) {
		this.entityListenersMetadata = entityListenersMetadata;
	}

	/**
	 * Cleans up this metadata by clearing unnecessary data.
	 */
	public void cleanup() {
		propertyOverrideMap.clear();
		masterPropertyMetadataMap.clear();
	}

	/**
	 * Validates this metadata to ensure there are no duplicate property names
	 * defined in this entity/embedded objects.
	 */
	public void ensureUniqueProperties() {
		masterPropertyMetadataMap.clear();
		for (PropertyMetadata propertyMetadata : getPropertyMetadataCollection()) {
			updateMasterPropertyMetadataMap(propertyMetadata.getMappedName(), propertyMetadata.getName());
		}
		// top level embedded objects
		for (EmbeddedMetadata embeddedMetadata : getEmbeddedMetadataCollection()) {
			ensureUniqueProperties(embeddedMetadata, embeddedMetadata.getStorageStrategy());
		}
	}

	/**
	 * Validates the embedded field represented by the given metadata to ensure
	 * there are no duplicate property names defined across the entity.
	 * 
	 * @param embeddedMetadata
	 *            the metadata of the embedded field
	 * @param storageStrategy
	 *            the storage strategy of the embedded field
	 */
	private void ensureUniqueProperties(EmbeddedMetadata embeddedMetadata, StorageStrategy storageStrategy) {
		if (embeddedMetadata.getStorageStrategy() == StorageStrategy.EXPLODED) {
			for (PropertyMetadata propertyMetadata : embeddedMetadata.getPropertyMetadataCollection()) {
				updateMasterPropertyMetadataMap(propertyMetadata.getMappedName(),
						embeddedMetadata.getField().getQualifiedName() + "." + propertyMetadata.getName());
			}
			// Run through the nested embedded objects recursively
			for (EmbeddedMetadata embeddedMetadata2 : embeddedMetadata.getEmbeddedMetadataCollection()) {
				ensureUniqueProperties(embeddedMetadata2, storageStrategy);
			}
		} else {
			// IMPLODED storage strategy... we don't have to check the
			// individual properties or nested embeddables
			updateMasterPropertyMetadataMap(embeddedMetadata.getMappedName(),
					embeddedMetadata.getField().getQualifiedName());
		}
	}

	/**
	 * Raises an exception with a detailed message reporting that the entity has
	 * more than one field that has a specific annotation.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param annotationClass
	 *            the annotation class
	 * @param metadata1
	 *            the metadata of the first field
	 * @param metadata2
	 *            the metadata of the second field that conflicts with the first
	 */
	private static void throwDuplicateAnnotationException(Class<?> entityClass,
			Class<? extends Annotation> annotationClass, PropertyMetadata metadata1, PropertyMetadata metadata2) {
		String format = "Class %s has at least two fields, %s and %s, with an annotation of %s. A given entity "
				+ "can have at most one field with this annotation. ";
		String message = String.format(format, entityClass.getName(), metadata1.getName(), metadata2.getName(),
				annotationClass.getName());
		throw new EntityManagerException(message);
	}

}
