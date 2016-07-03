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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.ParentKey;
import com.jmethods.catatumbo.Property;

/**
 * Objects of this class hold metadata information about an entity. Metadata
 * includes all the information that is needed to map Java objects to the Cloud
 * Datastore and vice versa.
 * 
 *
 * @author Sai Pullabhotla
 */

// TODO reduce visibility of various methods.
public class EntityMetadata {

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
	 * Metadata about various entity properties. The keys of this map are the
	 * property names in the Cloud Datastore. Key references are part of this
	 * map.
	 */
	private Map<String, PropertyMetadata> propertyMetadataMap;

	/**
	 * Creates a new instance of <code>EntityMetadata</code>.
	 *
	 * @param entityClass
	 *            the entity class
	 * @param kind
	 *            the entity kind
	 */
	public EntityMetadata(Class<?> entityClass, String kind) {
		this.entityClass = entityClass;
		this.kind = kind;
		propertyMetadataMap = new HashMap<>();
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
	 * Sets the entity class.
	 *
	 * @param entityClass
	 *            the entity class.
	 */
	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
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
	 * Sets the entity kind.
	 *
	 * @param kind
	 *            the entity kind.
	 */
	public void setKind(String kind) {
		this.kind = kind;
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
	 * Puts/adds the given property metadata.
	 *
	 * @param mappedName
	 *            the property name in the Cloud Datastore.
	 * @param propertyMetadata
	 *            the property metadata
	 */
	public void putPropertyMetadata(String mappedName, PropertyMetadata propertyMetadata) {
		PropertyMetadata old = propertyMetadataMap.put(mappedName, propertyMetadata);
		if (old != null) {
			String format = "Class %s has two fields, %s and %s, marked with %s annotation and both are set to use the same name, %s";
			String message = String.format(format, entityClass.getName(), old.getName(), propertyMetadata.getName(),
					Property.class.getName(), mappedName);
			throw new EntityManagerException(message);
		}
	}

	/**
	 * Returns the property metadata for the given property name. The property
	 * name is the name used in the Cloud Datastore.
	 *
	 * @param mappedName
	 *            the property name in the Cloud Datastore
	 * @return the property metadata
	 */
	public PropertyMetadata getPropertyMetadata(String mappedName) {
		return propertyMetadataMap.get(mappedName);
	}

	/**
	 * Returns the collection of PropertyMetadata.
	 *
	 * @return the collection of property metadata.
	 */
	public Collection<PropertyMetadata> getPropertyMetadataCollection() {
		return propertyMetadataMap.values();
	}

	/**
	 * Returns a map of property names and their metadata.
	 * 
	 * @return a map of property names and their metadata.
	 */
	public Map<String, PropertyMetadata> getPropertyMetadataMap() {
		return propertyMetadataMap;
	}

	/**
	 * Sets the property metadata to the given map.
	 * 
	 * @param propertyMetadataMap
	 *            the metadata of the properties.
	 */
	public void setPropertyMetadataMap(Map<String, PropertyMetadata> propertyMetadataMap) {
		this.propertyMetadataMap = propertyMetadataMap;
	}

}
