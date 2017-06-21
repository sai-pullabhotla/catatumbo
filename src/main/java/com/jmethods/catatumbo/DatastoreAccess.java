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
 * An interface for working with (reading from and writing to) the Cloud
 * Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public interface DatastoreAccess {
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
	<E> E insert(E entity);

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
	<E> List<E> insert(List<E> entities);

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
	<E> E update(E entity);

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
	<E> List<E> update(List<E> entities);

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
	<E> E upsert(E entity);

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
	<E> List<E> upsert(List<E> entities);

	/**
	 * Deletes the given entity from the Cloud Datastore.
	 * 
	 * @param entity
	 *            the entity to delete. The entity must have it ID set for the
	 *            deletion to succeed.
	 * @throws EntityManagerException
	 *             if any error occurs while deleting.
	 */
	void delete(Object entity);

	/**
	 * Deletes the given entities from the Cloud Datastore.
	 * 
	 * @param entities
	 *            the entities to delete. The entities must have it ID set for
	 *            the deletion to succeed.
	 * @throws EntityManagerException
	 *             if any error occurs while deleting.
	 */
	void delete(List<?> entities);

	/**
	 * Deletes an entity given its key.
	 * 
	 * @param key
	 *            the entity's key
	 * @throws EntityManagerException
	 *             if any error occurs while deleting.
	 */
	void deleteByKey(DatastoreKey key);

	/**
	 * Deletes the entities having the given keys.
	 * 
	 * @param keys
	 *            the entities' keys
	 * @throws EntityManagerException
	 *             if any error occurs while deleting.
	 */
	void deleteByKey(List<DatastoreKey> keys);

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
	<E> void delete(Class<E> entityClass, long id);

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
	<E> void delete(Class<E> entityClass, String id);

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
	<E> void delete(Class<E> entityClass, DatastoreKey parentKey, long id);

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
	<E> void delete(Class<E> entityClass, DatastoreKey parentKey, String id);

	/**
	 * Loads and returns the entity with the given ID. The entity is assumed to
	 * be a root entity (no parent). The entity kind is determined from the
	 * supplied class.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param id
	 *            the ID of the entity
	 * @return the Entity object or <code>null</code>, if the the entity with
	 *         the given ID does not exist in the Cloud Datastore.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	<E> E load(Class<E> entityClass, long id);

	/**
	 * Loads and returns the entities with the given <b>numeric IDs</b>. The
	 * entities are assumed to be a root entities (no parent). The entity kind
	 * is determined from the supplied class.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param identifiers
	 *            the IDs of the entities
	 * @return the list of entity objects in the same order as the given list of
	 *         identifiers. If one or more requested IDs do not exist in the
	 *         Cloud Datastore, the corresponding item in the returned list be
	 *         <code>null</code>.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	<E> List<E> loadById(Class<E> entityClass, List<Long> identifiers);

	/**
	 * Loads and returns the entity with the given ID. The entity is assumed to
	 * be a root entity (no parent). The entity kind is determined from the
	 * supplied class.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param id
	 *            the ID of the entity
	 * @return the Entity object or <code>null</code>, if the the entity with
	 *         the given ID does not exist in the Cloud Datastore.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	<E> E load(Class<E> entityClass, String id);

	/**
	 * Loads and returns the entities with the given <b>names (a.k.a String
	 * IDs)</b>. The entities are assumed to be root entities (no parent). The
	 * entity kind is determined from the supplied class.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param identifiers
	 *            the IDs of the entities
	 * @return the list of entity objects in the same order as the given list of
	 *         identifiers. If one or more requested IDs do not exist in the
	 *         Cloud Datastore, the corresponding item in the returned list be
	 *         <code>null</code>.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	<E> List<E> loadByName(Class<E> entityClass, List<String> identifiers);

	/**
	 * Loads and returns the entity with the given ID. The entity kind is
	 * determined from the supplied class.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param parentKey
	 *            the parent key of the entity.
	 * @param id
	 *            the ID of the entity
	 * @return the Entity object or <code>null</code>, if the the entity with
	 *         the given ID does not exist in the Cloud Datastore.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	<E> E load(Class<E> entityClass, DatastoreKey parentKey, long id);

	/**
	 * Loads and returns the entity with the given ID. The entity kind is
	 * determined from the supplied class.
	 * 
	 * @param entityClass
	 *            the entity class
	 * @param parentKey
	 *            the parent key of the entity.
	 * @param id
	 *            the ID of the entity
	 * @return the Entity object or <code>null</code>, if the the entity with
	 *         the given ID does not exist in the Cloud Datastore.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	<E> E load(Class<E> entityClass, DatastoreKey parentKey, String id);

	/**
	 * Loads and returns the entity with the given key.
	 * 
	 * @param entityClass
	 *            the entity class (expected result type)
	 * @param key
	 *            full key of the entity
	 * @return the Entity object or <code>null</code>, if the the entity with
	 *         the given key does not exist in the Cloud Datastore.
	 * @throws EntityManagerException
	 *             if any error occurs while accessing the Cloud Datastore.
	 */
	<E> E load(Class<E> entityClass, DatastoreKey key);

	/**
	 * Loads and returns the entities for the given keys.
	 * 
	 * @param entityClass
	 *            the entity class (expected result type)
	 * @param keys
	 *            entity keys to load
	 * @return the Entity objects for the given keys. If one or more requested
	 *         keys do not exist in the Cloud Datastore, the corresponding item
	 *         in the returned list be <code>null</code>.
	 * 
	 * @throws EntityManagerException
	 *             if any error occurs while accessing the Cloud Datastore.
	 */
	<E> List<E> loadByKey(Class<E> entityClass, List<DatastoreKey> keys);

	/**
	 * Creates and returns a new {@link EntityQueryRequest} for the given GQL
	 * query string. The returned {@link EntityQueryRequest} can be further
	 * customized to set any bindings (positional or named), and then be
	 * executed by calling the <code>execute</code> or
	 * <code>executeEntityQuery</code> methods.
	 * 
	 * @param query
	 *            the GQL query
	 * @return a new QueryRequest for the given GQL query
	 */
	EntityQueryRequest createEntityQueryRequest(String query);

	/**
	 * Creates and returns a new {@link ProjectionQueryRequest} for the given
	 * GQL query string. The returned {@link ProjectionQueryRequest} can further
	 * be customized to set any positional and/or named bindings, and then be
	 * executed by calling the <code>execute</code> or
	 * <code>executeProjectionQuery</code> methods.
	 * 
	 * @param query
	 *            the GQL projection query
	 * @return a new ProjectionQueryRequest for the given query
	 */
	ProjectionQueryRequest createProjectionQueryRequest(String query);

	/**
	 * Creates and returns a new {@link KeyQueryRequest} for the given GQL query
	 * string. Key query requests must only have __key__ in the
	 * <code>SELECT</code> list of field. The returned {@link KeyQueryRequest}
	 * can further be customized to set any positional and/or named bindings,
	 * and then be executed by calling the <code>executeKeyQuery</code> method.
	 * 
	 * @param query
	 *            the GQL projection query
	 * @return a new ProjectionQueryRequest for the given query
	 */
	KeyQueryRequest createKeyQueryRequest(String query);

	/**
	 * Executes the given {@link EntityQueryRequest} and returns the response.
	 * 
	 * @param expectedResultType
	 *            the expected type of results.
	 * @param request
	 *            the entity query request
	 * @return the query response
	 */
	<E> QueryResponse<E> executeEntityQueryRequest(Class<E> expectedResultType, EntityQueryRequest request);

	/**
	 * Executes the given {@link ProjectionQueryRequest} and returns the
	 * response.
	 * 
	 * @param expectedResultType
	 *            the expected type of results.
	 * @param request
	 *            the projection query request
	 * @return the query response
	 */
	<E> QueryResponse<E> executeProjectionQueryRequest(Class<E> expectedResultType, ProjectionQueryRequest request);

	/**
	 * Executes the given {@link KeyQueryRequest} and returns the response.
	 * 
	 * @param request
	 *            the key query request
	 * @return the query response
	 */
	QueryResponse<DatastoreKey> executeKeyQueryRequest(KeyQueryRequest request);

}
