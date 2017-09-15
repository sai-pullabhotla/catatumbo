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

/**
 * Base class for class metadata.
 * 
 * @author Sai Pullabhotla
 *
 */
public abstract class MetadataBase {

	/**
	 * The class
	 */
	protected final Class<?> clazz;

	/**
	 * Metadata about various properties. The keys of this map are the property
	 * names in the Cloud Datastore.
	 */
	protected Map<String, PropertyMetadata> propertyMetadataMap;

	/**
	 * Metadata of embedded fields
	 */
	protected Map<EmbeddedField, EmbeddedMetadata> embeddedMetadataMap = null;

	/**
	 * Constructor metadata
	 */
	private final ConstructorMetadata constructorMetadata;

	/**
	 * Creates a new instance of <code>MetadataBase</code>.
	 * 
	 * @param clazz
	 *            the class to which this metadata belongs (either an Entity or
	 *            an Embeddable)
	 */
	public MetadataBase(Class<?> clazz) {
		this.clazz = clazz;
		propertyMetadataMap = new HashMap<>();
		embeddedMetadataMap = new HashMap<>();
		this.constructorMetadata = ConstructorIntrospector.introspect(clazz);
	}

	/**
	 * Returns the class object to which this metadata belongs.
	 * 
	 * @return the class object to which this metadata belongs.
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * Returns the {@link ConstructorMetadata} of the persistence class to which
	 * this metadata belongs.
	 * 
	 * @return the constructorMetadata the {@link ConstructorMetadata} of the
	 *         persistence class to which this metadata belongs.
	 */
	public ConstructorMetadata getConstructorMetadata() {
		return constructorMetadata;
	}

	/**
	 * Puts/adds the given property metadata.
	 *
	 * @param propertyMetadata
	 *            the property metadata
	 */
	public void putPropertyMetadata(PropertyMetadata propertyMetadata) {
		String mappedName = propertyMetadata.getMappedName();
		PropertyMetadata old = propertyMetadataMap.put(mappedName, propertyMetadata);
		if (old != null) {
			String format = "Class %s has two fields, %s and %s, both mapped to the same property name, %s";
			String message = String.format(format, clazz.getName(), old.getName(), propertyMetadata.getName(),
					mappedName);
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
	 * Puts/adds the given embedded metadata.
	 * 
	 * @param embeddedMetadata
	 *            the embedded metadata.
	 */
	public void putEmbeddedMetadata(EmbeddedMetadata embeddedMetadata) {
		embeddedMetadataMap.put(embeddedMetadata.getField(), embeddedMetadata);
	}

	/**
	 * Returns a collection of the embedded metadata.
	 * 
	 * @return a collection of the embedded metadata.
	 */
	public Collection<EmbeddedMetadata> getEmbeddedMetadataCollection() {
		return embeddedMetadataMap.values();
	}

	/**
	 * @return the embeddedMetadataMap
	 */
	public Map<EmbeddedField, EmbeddedMetadata> getEmbeddedMetadataMap() {
		return embeddedMetadataMap;
	}

}
