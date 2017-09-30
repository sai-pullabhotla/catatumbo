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

import static com.jmethods.catatumbo.impl.DatastoreUtils.toNativeFullEntities;

import java.util.List;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.Transaction;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.DatastoreTransaction;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.EntityQueryRequest;
import com.jmethods.catatumbo.KeyQueryRequest;
import com.jmethods.catatumbo.ProjectionQueryRequest;
import com.jmethods.catatumbo.QueryResponse;
import com.jmethods.catatumbo.impl.Marshaller.Intent;

/**
 * Default implementation of the {@link DatastoreTransaction} interface.
 * 
 * @author Sai Pullabhotla
 *
 */
public class DefaultDatastoreTransaction implements DatastoreTransaction {

  /**
   * Entity manager that created this transaction
   */
  private DefaultEntityManager entityManager;

  /**
   * Native transaction
   */
  private Transaction nativeTransaction;

  /**
   * Datastore
   */
  private Datastore datastore;

  /**
   * Reader
   */
  private DefaultDatastoreReader reader;

  /**
   * Writer
   */
  private DefaultDatastoreWriter writer;

  /**
   * Creates a new instance of <code>DatastoreTransaction</code>.
   * 
   * @param entityManager
   *          the entity manager that created this transaction.
   */
  public DefaultDatastoreTransaction(DefaultEntityManager entityManager) {
    this.entityManager = entityManager;
    this.datastore = entityManager.getDatastore();
    this.nativeTransaction = datastore.newTransaction();
    this.reader = new DefaultDatastoreReader(this);
    this.writer = new DefaultDatastoreWriter(this);
  }

  /**
   * Returns the entity manager that created this transaction.
   * 
   * @return the entity manager that created this transaction.
   */
  public DefaultEntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * Returns the native transaction.
   * 
   * @return the native transaction.
   */
  public Transaction getNativeTransaction() {
    return nativeTransaction;
  }

  @Override
  public <E> void insertWithDeferredIdAllocation(E entity) {
    try {
      DatastoreUtils.validateDeferredIdAllocation(entity);
      FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(entityManager, entity,
          Intent.INSERT);
      nativeTransaction.addWithDeferredIdAllocation(nativeEntity);
    } catch (DatastoreException exp) {
      throw DatastoreUtils.wrap(exp);
    }

  }

  @Override
  public <E> void insertWithDeferredIdAllocation(List<E> entities) {
    if (entities == null || entities.isEmpty()) {
      return;
    }
    try {
      DatastoreUtils.validateDeferredIdAllocation(entities.get(0));
      FullEntity<?>[] nativeEntities = toNativeFullEntities(entities, entityManager, Intent.INSERT);
      nativeTransaction.addWithDeferredIdAllocation(nativeEntities);
    } catch (DatastoreException exp) {
      throw DatastoreUtils.wrap(exp);
    }
  }

  @Override
  public <E> void upsertWithDeferredIdAllocation(E entity) {
    try {
      DatastoreUtils.validateDeferredIdAllocation(entity);
      FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(entityManager, entity,
          Intent.UPSERT);
      nativeTransaction.putWithDeferredIdAllocation(nativeEntity);
    } catch (DatastoreException exp) {
      throw DatastoreUtils.wrap(exp);
    }

  }

  @Override
  public <E> void upsertWithDeferredIdAllocation(List<E> entities) {
    if (entities == null || entities.isEmpty()) {
      return;
    }
    try {
      DatastoreUtils.validateDeferredIdAllocation(entities.get(0));
      FullEntity<?>[] nativeEntities = toNativeFullEntities(entities, entityManager, Intent.UPSERT);
      nativeTransaction.putWithDeferredIdAllocation(nativeEntities);
    } catch (DatastoreException exp) {
      throw DatastoreUtils.wrap(exp);
    }
  }

  @Override
  public boolean isActive() {
    try {
      return nativeTransaction.isActive();
    } catch (DatastoreException exp) {
      throw DatastoreUtils.wrap(exp);
    } catch (Exception exp) {
      throw new EntityManagerException(exp);
    }
  }

  @Override
  public Response commit() {
    try {
      Transaction.Response nativeResponse = nativeTransaction.commit();
      return new DefaultResponse(nativeResponse);
    } catch (DatastoreException exp) {
      throw DatastoreUtils.wrap(exp);
    } catch (Exception exp) {
      throw new EntityManagerException(exp);
    }
  }

  @Override
  public void rollback() {
    try {
      nativeTransaction.rollback();
    } catch (DatastoreException exp) {
      throw DatastoreUtils.wrap(exp);
    } catch (Exception exp) {
      throw new EntityManagerException(exp);
    }
  }

  /**
   * Transaction Response containing the results of a transaction commit.
   * 
   * @author Sai Pullabhotla
   *
   */
  static class DefaultResponse implements Response {

    /**
     * Native response
     */
    private final Transaction.Response nativeResponse;

    /**
     * Creates a new instance of <code>DefaultResponse</code>.
     * 
     * @param nativeResponse
     *          the native transaction response
     */
    public DefaultResponse(Transaction.Response nativeResponse) {
      this.nativeResponse = nativeResponse;
    }

    @Override
    public List<DatastoreKey> getGeneratedKeys() {
      return DatastoreUtils.toDatastoreKeys(nativeResponse.getGeneratedKeys());
    }
  }

  @Override
  public <E> E insert(E entity) {
    return writer.insert(entity);
  }

  @Override
  public <E> List<E> insert(List<E> entities) {
    return writer.insert(entities);
  }

  @Override
  public <E> E update(E entity) {
    return writer.updateWithOptimisticLock(entity);
  }

  @Override
  public <E> List<E> update(List<E> entities) {
    return writer.updateWithOptimisticLock(entities);
  }

  @Override
  public <E> E upsert(E entity) {
    return writer.upsert(entity);
  }

  @Override
  public <E> List<E> upsert(List<E> entities) {
    return writer.upsert(entities);
  }

  @Override
  public void delete(Object entity) {
    writer.delete(entity);
  }

  @Override
  public void delete(List<?> entities) {
    writer.delete(entities);
  }

  @Override
  public <E> void delete(Class<E> entityClass, long id) {
    writer.delete(entityClass, id);
  }

  @Override
  public <E> void delete(Class<E> entityClass, String id) {
    writer.delete(entityClass, id);
  }

  @Override
  public <E> void delete(Class<E> entityClass, DatastoreKey parentKey, long id) {
    writer.delete(entityClass, parentKey, id);
  }

  @Override
  public <E> void delete(Class<E> entityClass, DatastoreKey parentKey, String id) {
    writer.delete(entityClass, parentKey, id);
  }

  @Override
  public void deleteByKey(DatastoreKey key) {
    writer.deleteByKey(key);
  }

  @Override
  public void deleteByKey(List<DatastoreKey> keys) {
    writer.deleteByKey(keys);
  }

  @Override
  public <E> E load(Class<E> entityClass, long id) {
    return reader.load(entityClass, id);
  }

  @Override
  public <E> E load(Class<E> entityClass, String id) {
    return reader.load(entityClass, id);
  }

  @Override
  public <E> E load(Class<E> entityClass, DatastoreKey parentKey, long id) {
    return reader.load(entityClass, parentKey, id);
  }

  @Override
  public <E> E load(Class<E> entityClass, DatastoreKey parentKey, String id) {
    return reader.load(entityClass, parentKey, id);
  }

  @Override
  public <E> E load(Class<E> entityClass, DatastoreKey key) {
    return reader.load(entityClass, key);
  }

  @Override
  public <E> List<E> loadById(Class<E> entityClass, List<Long> identifiers) {
    return reader.loadById(entityClass, identifiers);
  }

  @Override
  public <E> List<E> loadByName(Class<E> entityClass, List<String> identifiers) {
    return reader.loadByName(entityClass, identifiers);
  }

  @Override
  public <E> List<E> loadByKey(Class<E> entityClass, List<DatastoreKey> keys) {
    return reader.loadByKey(entityClass, keys);
  }

  @Override
  public EntityQueryRequest createEntityQueryRequest(String query) {
    return reader.createEntityQueryRequest(query);
  }

  @Override
  public ProjectionQueryRequest createProjectionQueryRequest(String query) {
    return reader.createProjectionQueryRequest(query);
  }

  @Override
  public KeyQueryRequest createKeyQueryRequest(String query) {
    return reader.createKeyQueryRequest(query);
  }

  @Override
  public <E> QueryResponse<E> executeEntityQueryRequest(Class<E> expectedResultType,
      EntityQueryRequest request) {
    return reader.executeEntityQueryRequest(expectedResultType, request);
  }

  @Override
  public <E> QueryResponse<E> executeProjectionQueryRequest(Class<E> expectedResultType,
      ProjectionQueryRequest request) {
    return reader.executeProjectionQueryRequest(expectedResultType, request);
  }

  @Override
  public QueryResponse<DatastoreKey> executeKeyQueryRequest(KeyQueryRequest request) {
    return reader.executeKeyQueryRequest(request);
  }

}
