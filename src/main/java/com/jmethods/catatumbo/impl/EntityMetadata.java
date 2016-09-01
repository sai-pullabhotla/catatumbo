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

import java.util.HashMap;
import java.util.Map;

import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.ParentKey;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.PropertyOverride;
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
	 * Meatdata of the field that is used for optimistic locking/entity
	 * versioning
	 */
	private PropertyMetadata versionMetadata;

	/**
	 * Property overrides for embeeded fields of the entity. The key is the
	 * override name that uniquely identifies a primitive property in the entity
	 * tree.
	 */
	private Map<String, Property> propertyOverrideMap;

	/**
	 * Master list of properties, used for detecting duplicate property names
	 */
	private Map<String, PropertyMetadata> masterPropertyMetadataMap;

	/**
	 * Creates a new instance of <code>EntityMetadata</code>.
	 *
	 * @param entityClass
	 *            the entity class
	 * @param kind
	 *            the entity kind
	 */
	public EntityMetadata(Class<?> entityClass, String kind) {
		super(entityClass);
		this.entityClass = entityClass;
		this.kind = kind;
		propertyOverrideMap = new HashMap<>();
		masterPropertyMetadataMap = new HashMap<>();

	}

	/**
	 * Returns the enity class to which this metadata belongs.
	 *
	 * @return the enity class to which this metadata belongs.
	 */
	public Class<?> getEntityClass() {
		return entityClass;
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
	 * is an IdentifierMeatadata already set.
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
	 *            the key meatadata.
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
	 * Sets the meatadata about the parent key.
	 * 
	 * @param parentKeyMetadata
	 *            the parent key meatdata.
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
	 * Returns the meatdata of the field that is used for optimistic locking.
	 * 
	 * @return the versionMetadata the meatdata of the field that is used for
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
			String format = "Class %s has at least two fields, %s and %s, with an annotation of %s. A given entity "
					+ "can have atmost one field with this annotation. ";
			String message = String.format(format, entityClass.getName(), this.versionMetadata.getName(),
					versionMetadata.getName(), Version.class.getName());
			throw new EntityManagerException(message);
		}
		this.versionMetadata = versionMetadata;
	}

	/**
	 * Puts/adds the given property metadata.
	 *
	 * @param propertyMetadata
	 *            the property metadata
	 */
	@Override
	public void putPropertyMetadata(PropertyMetadata propertyMetadata) {
		super.putPropertyMetadata(propertyMetadata);
		updateMasterPropertyMetadataMap(propertyMetadata);
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
	 * @param propertyMetadata
	 *            the property metadata
	 * @throws EntityManagerException
	 *             if a property with the same mapped name already exists.
	 */
	public void updateMasterPropertyMetadataMap(PropertyMetadata propertyMetadata) {
		String mappedName = propertyMetadata.getMappedName();
		PropertyMetadata old = masterPropertyMetadataMap.put(mappedName, propertyMetadata);
		if (old != null) {
			String message = "Duplicate property %s in entity %s. Check fields %s and %s";
			throw new EntityManagerException(String.format(message, mappedName, entityClass.getName(), old.getField(),
					propertyMetadata.getField()));
		}

	}

	/**
	 * Cleans up this metadata by clearing unnecessary data.
	 */
	public void cleanup() {
		propertyOverrideMap.clear();
		masterPropertyMetadataMap.clear();
	}

}
