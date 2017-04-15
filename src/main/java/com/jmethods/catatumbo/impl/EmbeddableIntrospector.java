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

import com.jmethods.catatumbo.Embeddable;
import com.jmethods.catatumbo.Embedded;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.Ignore;

/**
 * Introspects an {@link Embeddable} class and prepares the metadata for the
 * class.
 * 
 * @author Sai Pullabhotla
 *
 */
public class EmbeddableIntrospector {

	/**
	 * Embeddable class.
	 */
	private final Class<?> embeddableClass;

	/**
	 * Output - the metadata of the Embeddable class.
	 */
	private EmbeddableMetadata metadata;

	/**
	 * Creates a new instance if <code>EmbeddableIntrospector</code>.
	 * 
	 * @param embeddableClass
	 *            the Embeddable class to introspect
	 */
	private EmbeddableIntrospector(Class<?> embeddableClass) {
		this.embeddableClass = embeddableClass;
	}

	/**
	 * Introspects the given Embeddable class and returns the metadata.
	 * 
	 * @param embeddableClass
	 *            the Embeddable class
	 * @return the metadata of the given class
	 */
	public static EmbeddableMetadata introspect(Class<?> embeddableClass) {
		EmbeddableIntrospector introspector = new EmbeddableIntrospector(embeddableClass);
		introspector.introspect();
		return introspector.metadata;
	}

	/**
	 * Introspects the Embeddable.
	 */
	private void introspect() {
		// Make sure the class is Embeddable
		if (!embeddableClass.isAnnotationPresent(Embeddable.class)) {
			String message = String.format("Class %s must have %s annotation", embeddableClass.getName(),
					Embeddable.class.getName());
			throw new EntityManagerException(message);
		}
		metadata = new EmbeddableMetadata(embeddableClass);
		processFields();
	}

	/**
	 * Processes each field in this Embeddable and updates the metadata.
	 */
	private void processFields() {
		Field[] fields = embeddableClass.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Ignore.class) || IntrospectionUtils.isStatic(field)) {
				continue;
			} else if (field.isAnnotationPresent(Embedded.class)) {
				processEmbeddedField(field);
			} else {
				processSimpleField(field);
			}
		}
	}

	/**
	 * Processes the given simple (or primitive) field and updates the
	 * meatadata.
	 * 
	 * @param field
	 *            the field to process
	 */
	private void processSimpleField(Field field) {
		PropertyMetadata propertyMetadata = IntrospectionUtils.getPropertyMetadata(field);
		if (propertyMetadata != null) {
			metadata.putPropertyMetadata(propertyMetadata);
		}
	}

	/**
	 * Processes a nested embedded field.
	 * 
	 * @param field
	 *            the embedded field.
	 */
	private void processEmbeddedField(Field field) {
		// We are creating a PropertyMetadata for the embedded field... The new
		// Mapper, EmbeddedObjectMapper, takes care of mapping embedded fields
		// to embedded entities.
		Embedded embeddedAnnotation = field.getAnnotation(Embedded.class);
		String name = embeddedAnnotation.name();
		if (name == null || name.trim().length() == 0) {
			name = field.getName();
		}
		boolean indexed = embeddedAnnotation.indexed();
		PropertyMetadata propertyMetadata = new PropertyMetadata(field, name, indexed);
		metadata.putPropertyMetadata(propertyMetadata);
	}

}
