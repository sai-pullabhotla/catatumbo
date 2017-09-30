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

import com.jmethods.catatumbo.CreatedTimestamp;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.Indexer;
import com.jmethods.catatumbo.IndexerFactory;
import com.jmethods.catatumbo.Mapper;
import com.jmethods.catatumbo.MapperFactory;
import com.jmethods.catatumbo.NoSuitableMapperException;
import com.jmethods.catatumbo.Property;
import com.jmethods.catatumbo.SecondaryIndex;
import com.jmethods.catatumbo.UpdatedTimestamp;
import com.jmethods.catatumbo.Utility;
import com.jmethods.catatumbo.Version;

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
   * If this property is optional
   */
  protected boolean optional;

  /**
   * Secondary indexer for this property
   */
  private Indexer secondaryIndexer;

  /**
   * Secondary index name
   */
  private String secondaryIndexName;

  /**
   * Mapper for the field represented by this metadata
   */
  protected final Mapper mapper;

  /**
   * Creates an instance of <code>PropertyMetadata</code>.
   *
   * @param field
   *          the field
   */
  public PropertyMetadata(Field field) {
    super(field);
    String mappedName = field.getName();
    boolean indexed = true;
    boolean optional = false;
    Property propertyAnnotation = field.getAnnotation(Property.class);
    if (propertyAnnotation != null) {
      String temp = propertyAnnotation.name();
      if (!Utility.isNullOrEmpty(temp)) {
        mappedName = temp;
      }
      indexed = propertyAnnotation.indexed();
      optional = propertyAnnotation.optional();
    }
    this.mappedName = mappedName;
    this.indexed = indexed;
    setOptional(optional);
    initializeSecondaryIndexer();
    this.mapper = initializeMapper();
  }

  /**
   * Creates a new instance of <code>PropertyMetadata</code>.
   * 
   * @param field
   *          the field
   * @param mappedName
   *          name of the property in the Datastore
   * @param indexed
   *          whether or not the property is indexed
   * @param optional
   *          whether or not the property is optional
   */
  public PropertyMetadata(Field field, String mappedName, boolean indexed, boolean optional) {
    super(field);
    this.mappedName = mappedName;
    this.indexed = indexed;
    setOptional(optional);
    initializeSecondaryIndexer();
    this.mapper = initializeMapper();
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
   *          the mapped name.
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
   *          whether or not the property is indexed.
   */
  public void setIndexed(boolean indexed) {
    this.indexed = indexed;
  }

  /**
   * Returns the secondary indexer associated with this property, if any.
   * 
   * @return the secondaryIndexer the secondary indexer associated with this property. May be
   *         <code>null</code>.
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

  /**
   * Tells whether or not the field represented by this metadata is optional.
   * 
   * @return <code>true</code>, if the field represented by this metadata is optional;
   *         <code>false</code>, otherwise.
   */
  public boolean isOptional() {
    return optional;
  }

  /**
   * Sets whether or not the field represented by this metadata is optional.
   * 
   * @param optional
   *          whether or not the field represented by this metadata is optional.
   */
  public void setOptional(boolean optional) {
    if (field.getType().isPrimitive() || field.isAnnotationPresent(Version.class)
        || field.isAnnotationPresent(CreatedTimestamp.class)
        || field.isAnnotationPresent(UpdatedTimestamp.class)) {
      this.optional = false;
    } else {
      this.optional = optional;
    }
  }

  /**
   * Initializes the secondary indexer for this property, if any.
   */
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
      String pattern = "No suitable Indexer found or error occurred while creating the indexer "
          + "for field %s in class %s";
      String message = String.format(pattern, field.getName(), field.getDeclaringClass().getName());
      throw new EntityManagerException(message, exp);
    }

  }

  /**
   * Returns the {@link Mapper} associated with the field to which this metadata belongs.
   * 
   * @return he {@link Mapper} associated with the field to which this metadata belongs.
   */
  public Mapper getMapper() {
    return mapper;
  }

  /**
   * Initializes the {@link Mapper} for this field.
   * 
   * @return the {@link Mapper} for the field represented by this metadata
   */
  private Mapper initializeMapper() {
    try {
      return MapperFactory.getInstance().getMapper(field);
    } catch (NoSuitableMapperException exp) {
      String message = String.format(
          "No suitable mapper found or error occurred creating a mapper for field %s in class %s",
          field.getName(), field.getDeclaringClass().getName());
      throw new NoSuitableMapperException(message, exp);
    }
  }

}
