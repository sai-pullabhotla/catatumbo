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

import java.util.List;

import com.google.cloud.datastore.Batch;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.FullEntity;
import com.jmethods.catatumbo.DatastoreBatch;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.impl.Marshaller.Intent;

/**
 * Default implementation of {@link DatastoreBatch} to execute batch updates.
 * 
 * @author Sai Pullabhotla
 *
 */
public class DefaultDatastoreBatch implements DatastoreBatch {

  /**
   * Reference to the entity manager
   */
  private DefaultEntityManager entityManager;

  /**
   * Native Batch object
   */
  private Batch nativeBatch = null;

  /**
   * A reference to the Writer for performing the updates.
   */
  private DefaultDatastoreWriter writer = null;

  /**
   * A reference to the Datastore
   */
  private Datastore datastore = null;

  /**
   * Creates a new instance of <code>DefaultDatastoreBatch</code>.
   * 
   * @param entityManager
   *          a reference to the entity manager
   */
  public DefaultDatastoreBatch(DefaultEntityManager entityManager) {
    this.entityManager = entityManager;
    this.datastore = entityManager.getDatastore();
    this.nativeBatch = datastore.newBatch();
    this.writer = new DefaultDatastoreWriter(this);
  }

  /**
   * Returns the entity manager from which this batch was created.
   * 
   * @return the entity manager from which this batch was created.
   */
  public DefaultEntityManager getEntityManager() {
    return entityManager;
  }

  /**
   * Returns the native batch.
   * 
   * @return the native batch
   */
  public Batch getNativeBatch() {
    return nativeBatch;
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
  public <E> void insertWithDeferredIdAllocation(E entity) {
    try {
      DatastoreUtils.validateDeferredIdAllocation(entity);
      FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(entityManager, entity,
          Intent.INSERT);
      nativeBatch.addWithDeferredIdAllocation(nativeEntity);
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
      FullEntity<?>[] nativeEntities = DatastoreUtils.toNativeFullEntities(entities, entityManager,
          Intent.INSERT);
      nativeBatch.addWithDeferredIdAllocation(nativeEntities);
    } catch (DatastoreException exp) {
      throw DatastoreUtils.wrap(exp);
    }

  }

  @Override
  public <E> E update(E entity) {
    return writer.update(entity);
  }

  @Override
  public <E> List<E> update(List<E> entities) {
    return writer.update(entities);
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
  public <E> void upsertWithDeferredIdAllocation(E entity) {
    try {
      DatastoreUtils.validateDeferredIdAllocation(entity);
      FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(entityManager, entity,
          Intent.UPSERT);
      nativeBatch.putWithDeferredIdAllocation(nativeEntity);
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
      FullEntity<?>[] nativeEntities = DatastoreUtils.toNativeFullEntities(entities, entityManager,
          Intent.UPSERT);
      nativeBatch.putWithDeferredIdAllocation(nativeEntities);
    } catch (DatastoreException exp) {
      throw DatastoreUtils.wrap(exp);
    }

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
  public boolean isActive() {
    return nativeBatch.isActive();
  }

  @Override
  public Response submit() {
    try {
      Batch.Response nativeResponse = nativeBatch.submit();
      return new DefaultResponse(nativeResponse);
    } catch (DatastoreException exp) {
      throw DatastoreUtils.wrap(exp);
    }
  }

  /**
   * Implementation of {@link com.jmethods.catatumbo.DatastoreBatch.Response}.
   * 
   * @author Sai Pullabhotla
   *
   */
  static class DefaultResponse implements Response {

    /**
     * Native response
     */
    private final Batch.Response nativeResponse;

    /**
     * Creates a new instance of <code>DefaultResponse</code>.
     * 
     * @param nativeResponse
     *          the native response
     */
    public DefaultResponse(Batch.Response nativeResponse) {
      this.nativeResponse = nativeResponse;
    }

    @Override
    public List<DatastoreKey> getGeneratedKeys() {
      return DatastoreUtils.toDatastoreKeys(nativeResponse.getGeneratedKeys());
    }
  }

}
