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

import static com.jmethods.catatumbo.impl.DatastoreUtils.incrementVersion;
import static com.jmethods.catatumbo.impl.DatastoreUtils.rollbackIfActive;
import static com.jmethods.catatumbo.impl.DatastoreUtils.toEntities;
import static com.jmethods.catatumbo.impl.DatastoreUtils.toNativeEntities;
import static com.jmethods.catatumbo.impl.DatastoreUtils.toNativeFullEntities;

import java.util.ArrayList;
import java.util.List;

import com.google.cloud.datastore.Batch;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.DatastoreWriter;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Transaction;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.OptimisticLockException;
import com.jmethods.catatumbo.impl.Marshaller.Intent;

/**
 * Worker class for performing write operations on the Cloud Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public class DefaultDatastoreWriter {

	/**
	 * A reference to the entity manager
	 */
	private DefaultEntityManager entityManager;

	/**
	 * Reference to the native DatastoreWriter for updating the Cloud Datastore.
	 * This could be the {@link Datastore}, {@link Transaction} or
	 * {@link Batch}.
	 */
	private DatastoreWriter nativeWriter;

	/**
	 * A reference to the Datastore
	 */
	private Datastore datastore;

	/**
	 * Creates a new instance of <code>DefaultDatastoreWriter</code>.
	 * 
	 * @param entityManager
	 *            a reference to the entity manager.
	 */
	public DefaultDatastoreWriter(DefaultEntityManager entityManager) {
		this.entityManager = entityManager;
		this.datastore = entityManager.getDatastore();
		this.nativeWriter = datastore;
	}

	/**
	 * Creates a new instance of <code>DefaultDatastoreWriter</code> for
	 * executing batch updates.
	 * 
	 * @param batch
	 *            the {@link DefaultDatastoreBatch}.
	 */
	public DefaultDatastoreWriter(DefaultDatastoreBatch batch) {
		this.entityManager = batch.getEntityManager();
		this.datastore = entityManager.getDatastore();
		this.nativeWriter = batch.getNativeBatch();
	}

	/**
	 * Creates a new instance of <code>DefaultDatastoreWriter</code> for
	 * transactional updates.
	 * 
	 * @param transaction
	 *            the {@link DefaultDatastoreTransaction}.
	 */
	public DefaultDatastoreWriter(DefaultDatastoreTransaction transaction) {
		this.entityManager = transaction.getEntityManager();
		this.datastore = entityManager.getDatastore();
		this.nativeWriter = transaction.getNativeTransaction();
	}

	/**
	 * Inserts the given entity into the Cloud Datastore.
	 * 
	 * @param entity
	 *            the entity to insert
	 * @return the inserted entity. The inserted entity will not be same as the
	 *         passed in entity. For example, the inserted entity may contain
	 *         any generated ID, key, parent key, etc.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	public <E> E insert(E entity) {
		try {
			entityManager.executeEntityListeners(CallbackType.PRE_INSERT, entity);
			FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(entityManager, entity, Intent.INSERT);
			Entity insertedNativeEntity = nativeWriter.add(nativeEntity);
			@SuppressWarnings("unchecked")
			E insertedEntity = (E) Unmarshaller.unmarshal(insertedNativeEntity, entity.getClass());
			entityManager.executeEntityListeners(CallbackType.POST_INSERT, insertedEntity);
			return insertedEntity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Inserts the given list of entities into the Cloud Datastore.
	 * 
	 * @param entities
	 *            the entities to insert.
	 * @return the inserted entities. The inserted entities will not be same as
	 *         the passed in entities. For example, the inserted entities may
	 *         contain generated ID, key, parent key, etc.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> insert(List<E> entities) {
		if (entities == null || entities.isEmpty()) {
			return new ArrayList<>();
		}
		try {
			entityManager.executeEntityListeners(CallbackType.PRE_INSERT, entities);
			FullEntity<?>[] nativeEntities = toNativeFullEntities(entities, entityManager, Intent.INSERT);
			Class<?> entityClass = entities.get(0).getClass();
			List<Entity> insertedNativeEntities = nativeWriter.add(nativeEntities);
			List<E> insertedEntities = (List<E>) toEntities(entityClass, insertedNativeEntities);
			entityManager.executeEntityListeners(CallbackType.POST_INSERT, insertedEntities);
			return insertedEntities;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Updates the given entity in the Cloud Datastore. The passed in Entity
	 * must have its ID set for the update to work.
	 * 
	 * @param entity
	 *            the entity to update
	 * @return the updated entity.
	 * @throws EntityManagerException
	 *             if any error occurs while updating.
	 */
	@SuppressWarnings("unchecked")
	public <E> E update(E entity) {
		try {
			entityManager.executeEntityListeners(CallbackType.PRE_UPDATE, entity);
			Entity nativeEntity = (Entity) Marshaller.marshal(entityManager, entity, Intent.UPDATE);
			nativeWriter.update(nativeEntity);
			E updatedEntity = (E) Unmarshaller.unmarshal(nativeEntity, entity.getClass());
			entityManager.executeEntityListeners(CallbackType.POST_UPDATE, updatedEntity);
			return updatedEntity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}

	}

	/**
	 * Updates the given entity with optimistic locking, if the entity is set up
	 * to support optimistic locking. Otherwise, a normal update is performed.
	 * 
	 * @param entity
	 *            the entity to update
	 * @return the updated entity which may be different than the given entity.
	 */
	public <E> E updateWithOptimisticLock(E entity) {
		PropertyMetadata versionMetadata = EntityIntrospector.getVersionMetadata(entity);
		if (versionMetadata == null) {
			return update(entity);
		} else {
			return updateWithOptimisticLockingInternal(entity, versionMetadata);
		}

	}

	/**
	 * Worker method for updating the given entity with optimistic locking.
	 * 
	 * @param entity
	 *            the entity to update
	 * @param versionMetadata
	 *            the metadata for optimistic locking
	 * @return the updated entity
	 */
	@SuppressWarnings("unchecked")
	private <E> E updateWithOptimisticLockingInternal(E entity, PropertyMetadata versionMetadata) {
		Transaction transaction = null;
		try {
			entityManager.executeEntityListeners(CallbackType.PRE_UPDATE, entity);
			Entity nativeEntity = (Entity) Marshaller.marshal(entityManager, entity, Intent.UPDATE);
			transaction = datastore.newTransaction();
			Entity storedNativeEntity = transaction.get(nativeEntity.getKey());
			if (storedNativeEntity == null) {
				throw new OptimisticLockException(String.format("Entity does not exist: %s", nativeEntity.getKey()));
			}
			String versionPropertyName = versionMetadata.getMappedName();
			long version = nativeEntity.getLong(versionPropertyName);
			long storedVersion = storedNativeEntity.getLong(versionPropertyName);
			if (version != storedVersion) {
				throw new OptimisticLockException(
						String.format("Expecting version %d, but found %d", version, storedVersion));
			}
			nativeEntity = incrementVersion(nativeEntity, versionMetadata);
			transaction.update(nativeEntity);
			transaction.commit();
			E updatedEntity = (E) Unmarshaller.unmarshal(nativeEntity, entity.getClass());
			entityManager.executeEntityListeners(CallbackType.POST_UPDATE, updatedEntity);
			return updatedEntity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		} finally {
			rollbackIfActive(transaction);
		}
	}

	/**
	 * Updates the given list of entities in the Cloud Datastore.
	 * 
	 * @param entities
	 *            the entities to update. The passed in entities must have their
	 *            ID set for the update to work.
	 * @return the updated entities
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> update(List<E> entities) {
		if (entities == null || entities.isEmpty()) {
			return new ArrayList<>();
		}
		try {
			Class<E> entityClass = (Class<E>) entities.get(0).getClass();
			entityManager.executeEntityListeners(CallbackType.PRE_UPDATE, entities);
			Entity[] nativeEntities = toNativeEntities(entities, entityManager);
			nativeWriter.update(nativeEntities);
			List<E> updatedEntities = toEntities(entityClass, nativeEntities);
			entityManager.executeEntityListeners(CallbackType.POST_UPDATE, updatedEntities);
			return updatedEntities;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Updates or inserts the given entity in the Cloud Datastore. If the entity
	 * does not have an ID, it may be generated.
	 * 
	 * @param entity
	 *            the entity to update or insert
	 * @return the updated/inserted entity.
	 * @throws EntityManagerException
	 *             if any error occurs while saving.
	 */
	public <E> E upsert(E entity) {
		try {
			entityManager.executeEntityListeners(CallbackType.PRE_UPSERT, entity);
			FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(entityManager, entity, Intent.UPSERT);
			Entity upsertedNativeEntity = nativeWriter.put(nativeEntity);
			@SuppressWarnings("unchecked")
			E upsertedEntity = (E) Unmarshaller.unmarshal(upsertedNativeEntity, entity.getClass());
			entityManager.executeEntityListeners(CallbackType.POST_UPSERT, upsertedEntity);
			return upsertedEntity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Updates or inserts the given list of entities in the Cloud Datastore. If
	 * the entities do not have a valid ID, IDs may be generated.
	 * 
	 * @param entities
	 *            the entities to update/or insert.
	 * @return the updated or inserted entities
	 * @throws EntityManagerException
	 *             if any error occurs while saving.
	 */
	@SuppressWarnings("unchecked")
	public <E> List<E> upsert(List<E> entities) {
		if (entities == null || entities.isEmpty()) {
			return new ArrayList<>();
		}
		try {
			entityManager.executeEntityListeners(CallbackType.PRE_UPSERT, entities);
			FullEntity<?>[] nativeEntities = toNativeFullEntities(entities, entityManager, Intent.UPSERT);
			Class<?> entityClass = entities.get(0).getClass();
			List<Entity> upsertedNativeEntities = nativeWriter.put(nativeEntities);
			List<E> upsertedEntities = (List<E>) toEntities(entityClass, upsertedNativeEntities);
			entityManager.executeEntityListeners(CallbackType.POST_UPSERT, upsertedEntities);
			return upsertedEntities;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Deletes the given entity from the Cloud Datastore.
	 * 
	 * @param entity
	 *            the entity to delete. The entity must have it ID set for the
	 *            deletion to succeed.
	 * @throws EntityManagerException
	 *             if any error occurs while deleting.
	 */
	public void delete(Object entity) {
		try {
			entityManager.executeEntityListeners(CallbackType.PRE_DELETE, entity);
			Key nativeKey = Marshaller.marshalKey(entityManager, entity);
			nativeWriter.delete(nativeKey);
			entityManager.executeEntityListeners(CallbackType.POST_DELETE, entity);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Deletes the given entities from the Cloud Datastore.
	 * 
	 * @param entities
	 *            the entities to delete. The entities must have it ID set for
	 *            the deletion to succeed.
	 * @throws EntityManagerException
	 *             if any error occurs while deleting.
	 */
	public void delete(List<?> entities) {
		try {
			entityManager.executeEntityListeners(CallbackType.PRE_DELETE, entities);
			Key[] nativeKeys = new Key[entities.size()];
			for (int i = 0; i < entities.size(); i++) {
				nativeKeys[i] = Marshaller.marshalKey(entityManager, entities.get(i));
			}
			nativeWriter.delete(nativeKeys);
			entityManager.executeEntityListeners(CallbackType.POST_DELETE, entities);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Deletes an entity given its key.
	 * 
	 * @param key
	 *            the entity's key
	 * @throws EntityManagerException
	 *             if any error occurs while deleting.
	 */
	public void deleteByKey(DatastoreKey key) {
		try {
			nativeWriter.delete(key.nativeKey());
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Deletes the entities having the given keys.
	 * 
	 * @param keys
	 *            the entities' keys
	 * @throws EntityManagerException
	 *             if any error occurs while deleting.
	 */
	public void deleteByKey(List<DatastoreKey> keys) {
		try {
			Key[] nativeKeys = new Key[keys.size()];
			for (int i = 0; i < keys.size(); i++) {
				nativeKeys[i] = keys.get(i).nativeKey();
			}
			nativeWriter.delete(nativeKeys);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Deletes the entity with the given ID. The entity is assumed to be a root
	 * entity (no parent). The entity kind will be determined from the supplied
	 * entity class.
	 * 
	 * @param entityClass
	 *            the entity class.
	 * @param id
	 *            the ID of the entity.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	public <E> void delete(Class<E> entityClass, long id) {
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key nativeKey = entityManager.newNativeKeyFactory().setKind(entityMetadata.getKind()).newKey(id);
			nativeWriter.delete(nativeKey);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Deletes the entity with the given ID. The entity is assumed to be a root
	 * entity (no parent). The entity kind will be determined from the supplied
	 * entity class.
	 * 
	 * @param entityClass
	 *            the entity class.
	 * @param id
	 *            the ID of the entity.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	public <E> void delete(Class<E> entityClass, String id) {
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key nativeKey = entityManager.newNativeKeyFactory().setKind(entityMetadata.getKind()).newKey(id);
			nativeWriter.delete(nativeKey);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Deletes the entity with the given ID and parent key.
	 * 
	 * @param entityClass
	 *            the entity class.
	 * @param parentKey
	 *            the parent key
	 * @param id
	 *            the ID of the entity.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	public <E> void delete(Class<E> entityClass, DatastoreKey parentKey, long id) {
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key nativeKey = Key.newBuilder(parentKey.nativeKey(), entityMetadata.getKind(), id).build();
			nativeWriter.delete(nativeKey);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Deletes the entity with the given ID and parent key.
	 * 
	 * @param entityClass
	 *            the entity class.
	 * @param parentKey
	 *            the parent key
	 * @param id
	 *            the ID of the entity.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	public <E> void delete(Class<E> entityClass, DatastoreKey parentKey, String id) {
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key nativeKey = Key.newBuilder(parentKey.nativeKey(), entityMetadata.getKind(), id).build();
			nativeWriter.delete(nativeKey);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

}
