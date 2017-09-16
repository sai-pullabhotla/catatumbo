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

import java.util.List;

/**
 * Interface for executing Batch updates (Insert/Update/Delete). Instances of
 * this class are obtained by calling {@link EntityManager#newBatch()}.
 * 
 * @author Sai Pullabhotla
 *
 */
public interface DatastoreBatch {

	/**
	 * Adds the given entity to this batch for insertion. If the entity does not
	 * have an identifier set, ID may be automatically generated. If the same
	 * entity was added to this batch for deletion, the insert request is
	 * changed into an upsert request.
	 * 
	 * @param entity
	 *            the entity to insert.
	 * @return the inserted entity. The inserted entity may not be same as the
	 *         passed in entity. For example, the inserted entity may contain
	 *         any generated ID, key, parent key, etc.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	<E> E insert(E entity);

	/**
	 * Adds the given entities to this batch for insertion. If any of the
	 * entities do not have an identifier set, ID may be generated
	 * automatically. If any of entities were added to this batch for deletion,
	 * the insert request is changed to an upsert request.
	 * 
	 * @param entities
	 *            the entities to insert.
	 * @return the inserted entities. The inserted entities may not be same as
	 *         the passed in entities. For example, the inserted entities may
	 *         contain generated ID, key, parent key, etc.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	<E> List<E> insert(List<E> entities);

	/**
	 * Adds the given entity to this batch for insertion. The ID allocation is
	 * deferred to the submit time of this batch. Generated keys can be
	 * retrieved using {@link DatastoreBatch.Response#getGeneratedKeys()}.This
	 * method throws {@link EntityManagerException} if the entity is using a
	 * String identifier. String identifiers are allocated at add time, instead
	 * of submit time. For entities with String identifiers, use the
	 * {@link DatastoreBatch#insert(Object)} method.
	 * 
	 * @param entity
	 *            the entity to insert
	 * @throws EntityManagerException
	 *             if the entity has a String identifier or if any error occurs
	 */
	<E> void insertWithDeferredIdAllocation(E entity);

	/**
	 * Adds the given entities to this batch for insertion. The ID allocation is
	 * deferred to the submit time of this batch. Generated keys can be
	 * retrieved using {@link DatastoreBatch.Response#getGeneratedKeys()}.This
	 * method throws {@link EntityManagerException} if the entity is using a
	 * String identifier. String identifiers are allocated at add time, instead
	 * of submit time. For entities with String identifiers, use the
	 * {@link DatastoreBatch#insert(List)} method.
	 * 
	 * @param entities
	 *            the entities to insert
	 * @throws EntityManagerException
	 *             if the entity have a String identifier any error occurs
	 */
	<E> void insertWithDeferredIdAllocation(List<E> entities);

	/**
	 * Adds the given entity to this batch for update. The operation will fail
	 * if the entity with the same key does not exist in the underlying
	 * Datastore. This method does not use optimistic locking even if the given
	 * entity has a field with {@link Version} annotation.
	 * 
	 * @param entity
	 *            the entity to update
	 * @return the updated entity.
	 * @throws EntityManagerException
	 *             if any error occurs while updating.
	 */
	<E> E update(E entity);

	/**
	 * Adds the given entities to this batch for update. The operation will fail
	 * if the entities with the same key do not already exist in the underlying
	 * datastore. This method does not use optimistic locking even if the given
	 * entity has a field with {@link Version} annotation.
	 * 
	 * 
	 * @param entities
	 *            the entities to update.
	 * @return the updated entities
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	<E> List<E> update(List<E> entities);

	/**
	 * Adds the given entity to this batch for update or insert. Any prior
	 * writes to this entity in this batch will be removed from this batch. If
	 * the entity does not have an ID set, ID may be generated automatically.
	 * 
	 * @param entity
	 *            the entity to update or insert
	 * @return the updated/inserted entity.
	 * @throws EntityManagerException
	 *             if any error occurs while saving.
	 */
	<E> E upsert(E entity);

	/**
	 * Adds the given entities to this batch for update or insert. Any prior
	 * writes of any of the entities will be removed from this batch. If any of
	 * the entities do not have their IDs set, IDs may be generated
	 * automatically.
	 * 
	 * @param entities
	 *            the entities to update/or insert.
	 * @return the updated or inserted entities
	 * @throws EntityManagerException
	 *             if any error occurs while saving.
	 */
	<E> List<E> upsert(List<E> entities);

	/**
	 * Adds the given entity to this batch for insert or update. The ID (for
	 * numeric ID) allocation will be deferred to the submit time of this batch.
	 * This method throws an {@link EntityManagerException} if the entity is
	 * using a String ID. Entity with String ID should use
	 * {@link DatastoreBatch#upsert(Object)} method.
	 * 
	 * @param entity
	 *            the entity to update or insert
	 * @throws EntityManagerException
	 *             if the entity has a String ID or any other error occurs while
	 *             accessing the underlying datastore.
	 */
	<E> void upsertWithDeferredIdAllocation(E entity);

	/**
	 * Adds the given entities to this batch for update or insert. The ID (for
	 * numeric ID) allocation will be deferred to the submit time of this batch.
	 * This method throws an {@link EntityManagerException} if the entities have
	 * String identifiers. Entities with String identifiers should use
	 * {@link DatastoreBatch#upsert(List)} method.
	 * 
	 * @param entities
	 *            the entities to update or insert
	 * @throws EntityManagerException
	 *             if the entities have String identifiers or any other error
	 *             occurs while accessing the underlying datastore.
	 */
	<E> void upsertWithDeferredIdAllocation(List<E> entities);

	/**
	 * Adds the given entity to this batch for deletion.
	 * 
	 * @param entity
	 *            the entity to delete.
	 * @throws EntityManagerException
	 *             if any error occurs while deleting.
	 */
	void delete(Object entity);

	/**
	 * Adds the given entities to this batch for deletion.
	 * 
	 * @param entities
	 *            the entities to delete.
	 * @throws EntityManagerException
	 *             if any error occurs while deleting.
	 */
	void delete(List<?> entities);

	/**
	 * Adds the given key to this batch for deletion.
	 * 
	 * @param key
	 *            the key to delete
	 * @throws EntityManagerException
	 *             if any error occurs while deleting.
	 */
	void deleteByKey(DatastoreKey key);

	/**
	 * Adds the given keys to this batch for deletion.
	 * 
	 * @param keys
	 *            the keys to delete
	 * @throws EntityManagerException
	 *             if any error occurs while deleting.
	 */
	void deleteByKey(List<DatastoreKey> keys);

	/**
	 * Adds the given ID and entity type to this batch for deletion.
	 * 
	 * @param entityClass
	 *            the entity class.
	 * @param id
	 *            the ID of the entity.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	<E> void delete(Class<E> entityClass, long id);

	/**
	 * Adds the given ID and entity type to this batch for deletion.
	 * 
	 * @param entityClass
	 *            the entity class.
	 * @param id
	 *            the ID of the entity.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	<E> void delete(Class<E> entityClass, String id);

	/**
	 * Adds the given entity type, parent key and ID to this batch for deletion.
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
	<E> void delete(Class<E> entityClass, DatastoreKey parentKey, long id);

	/**
	 * Adds the given entity type, parent key and ID to this batch for deletion.
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
	<E> void delete(Class<E> entityClass, DatastoreKey parentKey, String id);

	/**
	 * Tells whether or not this DatastoreBatch is still active. A
	 * DatastoreBatch is considered active if it is not submitted yet.
	 * 
	 * @return <code>true</code>, if this batch is active; <code>false</code>,
	 *         otherwise.
	 */
	boolean isActive();

	/**
	 * Submits the batch operations to the Cloud Datastore.
	 * 
	 * @return the response of the batch operation.
	 * @throws EntityManagerException
	 *             if any error occurs
	 */
	Response submit();

	/**
	 * Response when the the batch is submitted.
	 * 
	 * @author Sai Pullabhotla
	 *
	 */
	interface Response {
		/**
		 * Returns a list of generated keys.
		 * 
		 * @return a list of generated keys.
		 */
		List<DatastoreKey> getGeneratedKeys();
	}

}
