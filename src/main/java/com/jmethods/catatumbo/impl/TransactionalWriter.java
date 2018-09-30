/*
 * Copyright 2017 Sai Pullabhotla.
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

import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Transaction;
import com.jmethods.catatumbo.OptimisticLockException;
import com.jmethods.catatumbo.impl.Marshaller.Intent;

/**
 * An extension of {@link DefaultDatastoreWriter} for Transactional writes. The primary purpose of
 * this class is to implement optimistic locking using the existing transaction, rather than
 * creating a child transaction.
 * 
 * @author Sai Pullabhotla
 *
 */
public class TransactionalWriter extends DefaultDatastoreWriter {

  /**
   * Reference to the native transaction
   */
  private Transaction nativeTransaction;

  /**
   * Creates a new instance of @code{TransactionalWriter}
   * 
   * @param transaction
   *          the transaction
   */
  public TransactionalWriter(DefaultDatastoreTransaction transaction) {
    super(transaction);
    this.nativeTransaction = (Transaction) nativeWriter;
  }

  @Override
  @SuppressWarnings("unchecked")
  protected <E> E updateWithOptimisticLockingInternal(E entity, PropertyMetadata versionMetadata) {
    try {
      entityManager.executeEntityListeners(CallbackType.PRE_UPDATE, entity);
      Entity nativeEntity = (Entity) Marshaller.marshal(entityManager, entity, Intent.UPDATE);
      Entity storedNativeEntity = nativeTransaction.get(nativeEntity.getKey());
      if (storedNativeEntity == null) {
        throw new OptimisticLockException(
            String.format("Entity does not exist: %s", nativeEntity.getKey()));
      }
      String versionPropertyName = versionMetadata.getMappedName();
      long version = nativeEntity.getLong(versionPropertyName) - 1;
      long storedVersion = storedNativeEntity.getLong(versionPropertyName);
      if (version != storedVersion) {
        throw new OptimisticLockException(
            String.format("Expecting version %d, but found %d", version, storedVersion));
      }
      nativeTransaction.update(nativeEntity);
      E updatedEntity = (E) Unmarshaller.unmarshal(nativeEntity, entity.getClass());
      entityManager.executeEntityListeners(CallbackType.POST_UPDATE, updatedEntity);
      return updatedEntity;
    } catch (DatastoreException exp) {
      throw DatastoreUtils.wrap(exp);
    }
  }

  @Override
  @SuppressWarnings("unchecked")
  public <E> List<E> updateWithOptimisticLockInternal(List<E> entities,
      PropertyMetadata versionMetadata) {
    try {
      entityManager.executeEntityListeners(CallbackType.PRE_UPDATE, entities);
      Entity[] nativeEntities = DatastoreUtils.toNativeEntities(entities, entityManager,
          Intent.UPDATE);
      // The above native entities already have the version incremented by
      // the marshalling process
      Key[] nativeKeys = new Key[nativeEntities.length];
      for (int i = 0; i < nativeEntities.length; i++) {
        nativeKeys[i] = nativeEntities[i].getKey();
      }
      List<Entity> storedNativeEntities = nativeTransaction.fetch(nativeKeys);
      String versionPropertyName = versionMetadata.getMappedName();

      for (int i = 0; i < nativeEntities.length; i++) {
        long version = nativeEntities[i].getLong(versionPropertyName) - 1;
        Entity storedNativeEntity = storedNativeEntities.get(i);
        if (storedNativeEntity == null) {
          throw new OptimisticLockException(
              String.format("Entity does not exist: %s", nativeKeys[i]));
        }
        long storedVersion = storedNativeEntities.get(i).getLong(versionPropertyName);
        if (version != storedVersion) {
          throw new OptimisticLockException(
              String.format("Expecting version %d, but found %d", version, storedVersion));
        }
      }
      nativeTransaction.update(nativeEntities);
      List<E> updatedEntities = (List<E>) DatastoreUtils.toEntities(entities.get(0).getClass(),
          nativeEntities);
      entityManager.executeEntityListeners(CallbackType.POST_UPDATE, updatedEntities);
      return updatedEntities;

    } catch (DatastoreException exp) {
      throw DatastoreUtils.wrap(exp);
    }

  }

}
