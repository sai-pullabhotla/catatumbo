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

import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.Indexer;
import com.jmethods.catatumbo.IndexerFactory;
import com.jmethods.catatumbo.SecondaryIndex;

/**
 * Objects of this class contain metadata about a property of an entity.
 *
 * @author Sai Pullabhotla
 */
public class PropertyMetadata extends FieldMetadata {

	/**
	 * Default prefix for secondary index name.
	 */
	private static final String DEFAULT_SECONDARY_INDEX_PREFIX = "$";

	/**
	 * The property name, in the Cloud Datastore, to which a field is mapped
	 */
	private String mappedName;

	/**
	 * If the property is indexed or not
	 */
	private boolean indexed;

	/**
	 * Secondary indexer for this property
	 */
	private Indexer secondaryIndexer;

	/**
	 * Secondary index name
	 */
	private String secondaryIndexName;

	/**
	 * Creates an instance of <code>PropertyMetadata</code>.
	 *
	 * @param field
	 *            the field
	 * @param mappedName
	 *            the property name in the Cloud Datastore
	 * @param indexed
	 *            whether or not to index
	 */
	public PropertyMetadata(Field field, String mappedName, boolean indexed) {
		super(field);
		this.mappedName = mappedName;
		this.indexed = indexed;
		initializeSecondaryIndexer();
	}

	/**
	 * Returns the mapped name.
	 *
	 * @return the mapped name.
	 */
	public String getMappedName() {
		return mappedName;
	}

	/**
	 * Sets the mapped name.
	 *
	 * @param mappedName
	 *            the mapped name.
	 */
	public void setMappedName(String mappedName) {
		this.mappedName = mappedName;
	}

	/**
	 * Returns whether or not the property is indexed.
	 *
	 * @return true, if indexed; false, otherwise.
	 */
	public boolean isIndexed() {
		return indexed;
	}

	/**
	 * Sets whether or not the property is indexed.
	 *
	 * @param indexed
	 *            whether or not the property is indexed.
	 */
	public void setIndexed(boolean indexed) {
		this.indexed = indexed;
	}

	/**
	 * Returns the secondary indexer associated with this property, if any.
	 * 
	 * @return the secondaryIndexer the secondary indexer associated with this
	 *         property. May be <code>null</code>.
	 */
	public Indexer getSecondaryIndexer() {
		return secondaryIndexer;
	}

	/**
	 * Returns the secondary index name, if any.
	 * 
	 * @return the secondary index name. May be <code>null</code>.
	 */
	public String getSecondaryIndexName() {
		return secondaryIndexName;
	}

	private void initializeSecondaryIndexer() {
		SecondaryIndex secondaryIndexAnnotation = field.getAnnotation(SecondaryIndex.class);
		if (secondaryIndexAnnotation == null) {
			return;
		}
		String indexName = secondaryIndexAnnotation.name();
		if (indexName == null || indexName.trim().length() == 0) {
			indexName = DEFAULT_SECONDARY_INDEX_PREFIX + mappedName;
		}
		this.secondaryIndexName = indexName;
		try {
			secondaryIndexer = IndexerFactory.getInstance().getIndexer(field);
		} catch (Exception exp) {
			String message = String.format(
					"No suitable Indexer found or error occurred while creating the indexer for field %s in class %s",
					field.getName(), field.getDeclaringClass().getName());
			throw new EntityManagerException(message, exp);
		}

	}
}
