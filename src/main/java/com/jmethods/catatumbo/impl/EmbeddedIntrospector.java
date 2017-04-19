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
import java.util.List;

import com.jmethods.catatumbo.Embeddable;
import com.jmethods.catatumbo.Embedded;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.Imploded;
import com.jmethods.catatumbo.Property;

/**
 * Introspects and prepares the meatdata of an embedded field. An embedded field
 * is a complex object that is embedded in an entity or another embedded field.
 * 
 * @author Sai Pullabhotla
 *
 */
public class EmbeddedIntrospector {

	/**
	 * Embedded field that is being introspected
	 */
	private final EmbeddedField field;

	/**
	 * The meatadata of the embedded field
	 */
	private EmbeddedMetadata metadata;

	/**
	 * The type of the embedded field
	 */
	private final Class<?> clazz;

	/**
	 * Metadata of the owning entity
	 */
	private EntityMetadata entityMetadata;

	/**
	 * Creates a new instance of <code>EmbeddedIntrospector</code>.
	 * 
	 * @param field
	 *            the embedded field to introspect.
	 * @param entityMetadata
	 *            metadata of the owning entity
	 */
	private EmbeddedIntrospector(EmbeddedField field, EntityMetadata entityMetadata) {
		this.field = field;
		this.entityMetadata = entityMetadata;
		this.clazz = field.getType();
	}

	/**
	 * Introspects the given embedded field and returns its metadata.
	 * 
	 * @param field
	 *            the embedded field to introspect
	 * @param entityMetadata
	 *            metadata of the owning entity
	 * @return the metadata of the embedded field
	 */
	public static EmbeddedMetadata introspect(EmbeddedField field, EntityMetadata entityMetadata) {
		EmbeddedIntrospector introspector = new EmbeddedIntrospector(field, entityMetadata);
		introspector.process();
		return introspector.metadata;
	}

	/**
	 * Worker class for introspection.
	 */
	private void process() {
		metadata = new EmbeddedMetadata(field);

		// Make sure the class has @Embeddable annotation
		if (!clazz.isAnnotationPresent(Embeddable.class)) {
			String message = String.format("Class %s must have %s annotation", clazz.getName(),
					Embeddable.class.getName());
			throw new EntityManagerException(message);
		}

		// Check the mapped name and indexed attributes
		Embedded embeddedAnnotation = field.getField().getAnnotation(Embedded.class);
		String mappedName = embeddedAnnotation.name();
		if (mappedName == null || mappedName.trim().length() == 0) {
			mappedName = field.getName();
		}
		metadata.setMappedName(mappedName);
		metadata.setIndexed(embeddedAnnotation.indexed());

		// If there is an annotation for storing the embedded with Imploded
		// strategy...
		if (field.getField().isAnnotationPresent(Imploded.class)) {
			metadata.setStorageStrategy(StorageStrategy.IMPLODED);
		}
		processFields();
	}

	/**
	 * Processes each field in this embedded object and updates the metadata.
	 */
	private void processFields() {
		List<Field> children = IntrospectionUtils.getPersistableFields(clazz);
		for (Field child : children) {
			if (child.isAnnotationPresent(Embedded.class)) {
				processEmbeddedField(child);
			} else {
				processSimpleField(child);
			}
		}
	}

	/**
	 * Processes the given simple (or primitive) field and updates the
	 * meatadata.
	 * 
	 * @param child
	 *            the field to process
	 */
	private void processSimpleField(Field child) {
		PropertyMetadata propertyMetadata = IntrospectionUtils.getPropertyMetadata(child);
		if (propertyMetadata != null) {
			// Process override
			processPropertyOverride(propertyMetadata);
			metadata.putPropertyMetadata(propertyMetadata);
		}
	}

	/**
	 * Processes the override, if any, for the given property.
	 * 
	 * @param propertyMetadata
	 *            the meatadata of the property
	 */
	private void processPropertyOverride(PropertyMetadata propertyMetadata) {
		String qualifiedName = field.getQualifiedName() + "." + propertyMetadata.getField().getName();
		Property override = entityMetadata.getPropertyOverride(qualifiedName);
		if (override != null) {
			String mappedName = override.name();
			boolean indexed = override.indexed();
			if (mappedName != null && mappedName.trim().length() > 0) {
				propertyMetadata.setMappedName(mappedName);
			}
			propertyMetadata.setIndexed(indexed);
		}
	}

	/**
	 * Processes a nested embedded field.
	 * 
	 * @param child
	 *            the nested embedded field.
	 */
	private void processEmbeddedField(Field child) {
		EmbeddedField embeddedChild = new EmbeddedField(child, field);
		EmbeddedMetadata childMetadata = EmbeddedIntrospector.introspect(embeddedChild, entityMetadata);
		metadata.putEmbeddedMetadata(childMetadata);
	}

}
