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

package com.jmethods.catatumbo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import com.jmethods.catatumbo.impl.Cache;
import com.jmethods.catatumbo.impl.IntrospectionUtils;
import com.jmethods.catatumbo.indexers.LowerCaseStringIndexer;
import com.jmethods.catatumbo.indexers.LowerCaseStringListIndexer;

/**
 * A factory for producing indexers. This factory ensures there is at most one instance of a given
 * Indexer implementation.
 * 
 * @author Sai Pullabhotla
 *
 */
public class IndexerFactory {

  /**
   * Singleton instance
   */
  private static final IndexerFactory INSTANCE = new IndexerFactory();

  /**
   * Cache of previously produced indexers
   */
  private Cache<Class<? extends Indexer>, Indexer> cache = null;

  /**
   * Creates a new instance of <code>IndexerFactory</code>.
   */
  private IndexerFactory() {
    cache = new Cache<>();
  }

  /**
   * Returns the singleton instance of this class.
   * 
   * @return the singleton instance of this class.
   */
  public static IndexerFactory getInstance() {
    return INSTANCE;
  }

  /**
   * Gets the {@link Indexer} for the given field. If the field has a {@link PropertyIndexer}
   * annotation, the specified indexer will be returned. Otherwise, a default indexer will be
   * returned based on the field's type.
   * 
   * @param field
   *          the field
   * @return the indexer for the specified field.
   */
  public Indexer getIndexer(Field field) {
    PropertyIndexer indexerAnnotation = field.getAnnotation(PropertyIndexer.class);
    if (indexerAnnotation != null) {
      Class<? extends Indexer> indexerClass = indexerAnnotation.value();
      return getIndexer(indexerClass);
    }
    return getDefaultIndexer(field);
  }

  /**
   * Returns the {@link Indexer} of the given implementation class.
   * 
   * @param indexerClass
   *          the implementation class of the Indexer interface
   * @return the Indexer
   */
  @SuppressWarnings("unchecked")
  public <T extends Indexer> T getIndexer(Class<T> indexerClass) {
    Indexer indexer = cache.get(indexerClass);
    if (indexer == null) {
      indexer = createIndexer(indexerClass);
    }
    return (T) indexer;
  }

  /**
   * Creates the Indexer of the given class.
   * 
   * @param indexerClass
   *          the indexer implementation class
   * @return the Indexer.
   */
  @SuppressWarnings("unchecked")
  private <T extends Indexer> T createIndexer(Class<T> indexerClass) {
    synchronized (indexerClass) {
      Indexer indexer = cache.get(indexerClass);
      if (indexer == null) {
        indexer = (Indexer) IntrospectionUtils.instantiateObject(indexerClass);
        cache.put(indexerClass, indexer);
      }
      return (T) indexer;
    }
  }

  /**
   * Returns the default indexer for the given field.
   * 
   * @param field
   *          the field.
   * @return the default indexer.
   */
  private Indexer getDefaultIndexer(Field field) {
    Type type = field.getGenericType();
    if (type instanceof Class && type.equals(String.class)) {
      return getIndexer(LowerCaseStringIndexer.class);
    } else if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      Class<?> rawType = (Class<?>) parameterizedType.getRawType();
      if (List.class.isAssignableFrom(rawType) || Set.class.isAssignableFrom(rawType)) {
        Class<?>[] collectionType = IntrospectionUtils.resolveCollectionType(type);
        if (String.class.equals(collectionType[1])) {
          return getIndexer(LowerCaseStringListIndexer.class);
        }
      }
    }
    String message = String.format("No default indexer found for field %s in class %s",
        field.getName(), field.getDeclaringClass().getName());
    throw new NoSuitableIndexerException(message);
  }

}
