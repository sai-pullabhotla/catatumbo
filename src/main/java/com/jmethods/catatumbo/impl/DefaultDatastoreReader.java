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

import java.util.ArrayList;
import java.util.List;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.DatastoreReader;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.GqlQuery;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.ProjectionEntity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.Query.ResultType;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Transaction;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.DefaultDatastoreCursor;
import com.jmethods.catatumbo.DefaultDatastoreKey;
import com.jmethods.catatumbo.DefaultQueryResponse;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.EntityQueryRequest;
import com.jmethods.catatumbo.KeyQueryRequest;
import com.jmethods.catatumbo.ProjectionQueryRequest;
import com.jmethods.catatumbo.QueryResponse;

/**
 * Worker class for performing read operations on the Cloud Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public class DefaultDatastoreReader {

	/**
	 * A reference to the entity manager
	 */
	private DefaultEntityManager entityManager;

	/**
	 * Native datastore reader. This could either be {@link Datastore} or
	 * {@link Transaction}.
	 */
	private DatastoreReader nativeReader = null;

	/**
	 * A reference to the Datastore.
	 */
	private Datastore datastore;

	/**
	 * Creates a new instance of <code>DefaultDatastoreReader</code>.
	 * 
	 * @param entityManager
	 *            the entity manager that created this reader.
	 */
	public DefaultDatastoreReader(DefaultEntityManager entityManager) {
		this.entityManager = entityManager;
		this.datastore = entityManager.getDatastore();
		this.nativeReader = datastore;
	}

	/**
	 * Creates a new instance of <code>DefaultDatastoreReader</code>.
	 * 
	 * @param transaction
	 *            the transaction that created this reader.
	 */
	public DefaultDatastoreReader(DefaultDatastoreTransaction transaction) {
		this.entityManager = transaction.getEntityManager();
		this.datastore = entityManager.getDatastore();
		this.nativeReader = transaction.getNativeTransaction();
	}

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
	public <E> E load(Class<E> entityClass, long id) {
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = entityManager.newNativeKeyFactory().setKind(entityMetadata.getKind()).newKey(id);
			Entity nativeEntity = nativeReader.get(key);
			E entity = Unmarshaller.unmarshal(nativeEntity, entityClass);
			entityManager.executeEntityListeners(CallbackType.POST_LOAD, entity);
			return entity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

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
	public <E> List<E> loadById(Class<E> entityClass, List<Long> identifiers) {
		try {
			Key[] nativeKeys = longListToNativeKeys(entityClass, identifiers);
			List<Entity> nativeEntities = nativeReader.fetch(nativeKeys);
			List<E> entities = DatastoreUtils.toEntities(entityClass, nativeEntities);
			entityManager.executeEntityListeners(CallbackType.POST_LOAD, entities);
			return entities;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

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
	public <E> E load(Class<E> entityClass, DatastoreKey parentKey, long id) {
		if (parentKey == null) {
			return load(entityClass, id);
		}
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = Key.newBuilder(parentKey.nativeKey(), entityMetadata.getKind(), id).build();
			Entity nativeEntity = nativeReader.get(key);
			E entity = Unmarshaller.unmarshal(nativeEntity, entityClass);
			entityManager.executeEntityListeners(CallbackType.POST_LOAD, entity);
			return entity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

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
	public <E> E load(Class<E> entityClass, String id) {
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = entityManager.newNativeKeyFactory().setKind(entityMetadata.getKind()).newKey(id);
			Entity nativeEntity = nativeReader.get(key);
			E entity = Unmarshaller.unmarshal(nativeEntity, entityClass);
			entityManager.executeEntityListeners(CallbackType.POST_LOAD, entity);
			return entity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

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
	public <E> List<E> loadByName(Class<E> entityClass, List<String> identifiers) {
		try {
			Key[] nativeKeys = stringListToNativeKeys(entityClass, identifiers);
			List<Entity> nativeEntities = nativeReader.fetch(nativeKeys);
			List<E> entities = DatastoreUtils.toEntities(entityClass, nativeEntities);
			entityManager.executeEntityListeners(CallbackType.POST_LOAD, entities);
			return entities;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

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
	public <E> E load(Class<E> entityClass, DatastoreKey parentKey, String id) {
		if (parentKey == null) {
			return load(entityClass, id);
		}
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = Key.newBuilder(parentKey.nativeKey(), entityMetadata.getKind(), id).build();
			Entity nativeEntity = nativeReader.get(key);
			E entity = Unmarshaller.unmarshal(nativeEntity, entityClass);
			entityManager.executeEntityListeners(CallbackType.POST_LOAD, entity);
			return entity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

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
	public EntityQueryRequest createEntityQueryRequest(String query) {
		return new EntityQueryRequest(query);
	}

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
	public ProjectionQueryRequest createProjectionQueryRequest(String query) {
		return new ProjectionQueryRequest(query);
	}

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
	public KeyQueryRequest createKeyQueryRequest(String query) {
		return new KeyQueryRequest(query);
	}

	/**
	 * Executes the given {@link EntityQueryRequest} and returns the response.
	 * 
	 * @param expectedResultType
	 *            the expected type of results.
	 * @param request
	 *            the entity query request
	 * @return the query response
	 */
	public <E> QueryResponse<E> executeEntityQueryRequest(Class<E> expectedResultType, EntityQueryRequest request) {
		try {
			GqlQuery.Builder<Entity> queryBuilder = Query.newGqlQueryBuilder(ResultType.ENTITY, request.getQuery());
			queryBuilder.setNamespace(entityManager.getEffectiveNamespace());
			queryBuilder.setAllowLiteral(request.isAllowLiterals());
			QueryUtils.applyNamedBindings(queryBuilder, request.getNamedBindings());
			QueryUtils.applyPositionalBindings(queryBuilder, request.getPositionalBindings());
			GqlQuery<Entity> gqlQuery = queryBuilder.build();
			QueryResults<Entity> results = nativeReader.run(gqlQuery);
			List<E> entities = new ArrayList<>();
			DefaultQueryResponse<E> response = new DefaultQueryResponse<>();
			response.setStartCursor(new DefaultDatastoreCursor(results.getCursorAfter().toUrlSafe()));
			while (results.hasNext()) {
				Entity result = results.next();
				E entity = Unmarshaller.unmarshal(result, expectedResultType);
				entities.add(entity);
			}
			response.setResults(entities);
			response.setEndCursor(new DefaultDatastoreCursor(results.getCursorAfter().toUrlSafe()));
			entityManager.executeEntityListeners(CallbackType.POST_LOAD, entities);
			return response;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

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
	public <E> QueryResponse<E> executeProjectionQueryRequest(Class<E> expectedResultType,
			ProjectionQueryRequest request) {
		try {
			GqlQuery.Builder<ProjectionEntity> queryBuilder = Query.newGqlQueryBuilder(ResultType.PROJECTION_ENTITY,
					request.getQuery());
			queryBuilder.setNamespace(entityManager.getEffectiveNamespace());
			queryBuilder.setAllowLiteral(request.isAllowLiterals());
			QueryUtils.applyNamedBindings(queryBuilder, request.getNamedBindings());
			QueryUtils.applyPositionalBindings(queryBuilder, request.getPositionalBindings());
			GqlQuery<ProjectionEntity> gqlQuery = queryBuilder.build();
			QueryResults<ProjectionEntity> results = nativeReader.run(gqlQuery);
			List<E> entities = new ArrayList<>();
			DefaultQueryResponse<E> response = new DefaultQueryResponse<>();
			response.setStartCursor(new DefaultDatastoreCursor(results.getCursorAfter().toUrlSafe()));
			while (results.hasNext()) {
				ProjectionEntity result = results.next();
				E entity = Unmarshaller.unmarshal(result, expectedResultType);
				entities.add(entity);
			}
			response.setResults(entities);
			response.setEndCursor(new DefaultDatastoreCursor(results.getCursorAfter().toUrlSafe()));
			// TODO should we invoke PostLoad callback for projected entities?
			return response;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Executes the given {@link KeyQueryRequest} and returns the response.
	 * 
	 * @param request
	 *            the key query request
	 * @return the query response
	 */
	public QueryResponse<DatastoreKey> executeKeyQueryRequest(KeyQueryRequest request) {
		try {
			GqlQuery.Builder<Key> queryBuilder = Query.newGqlQueryBuilder(ResultType.KEY, request.getQuery());
			queryBuilder.setNamespace(entityManager.getEffectiveNamespace());
			queryBuilder.setAllowLiteral(request.isAllowLiterals());
			QueryUtils.applyNamedBindings(queryBuilder, request.getNamedBindings());
			QueryUtils.applyPositionalBindings(queryBuilder, request.getPositionalBindings());
			GqlQuery<Key> gqlQuery = queryBuilder.build();
			QueryResults<Key> results = nativeReader.run(gqlQuery);
			List<DatastoreKey> entities = new ArrayList<>();
			DefaultQueryResponse<DatastoreKey> response = new DefaultQueryResponse<>();
			response.setStartCursor(new DefaultDatastoreCursor(results.getCursorAfter().toUrlSafe()));
			while (results.hasNext()) {
				Key result = results.next();
				DatastoreKey datastoreKey = new DefaultDatastoreKey(result);
				entities.add(datastoreKey);
			}
			response.setResults(entities);
			response.setEndCursor(new DefaultDatastoreCursor(results.getCursorAfter().toUrlSafe()));
			return response;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	/**
	 * Converts the given list of identifiers into an array of native Key
	 * objects.
	 * 
	 * @param entityClass
	 *            the entity class to which these identifiers belong to.
	 * @param identifiers
	 *            the list of identifiers to convert.
	 * @return an array of Key objects
	 */
	private Key[] longListToNativeKeys(Class<?> entityClass, List<Long> identifiers) {
		if (identifiers == null || identifiers.isEmpty()) {
			return new Key[0];
		}
		EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
		Key[] nativeKeys = new Key[identifiers.size()];
		KeyFactory keyFactory = entityManager.newNativeKeyFactory();
		keyFactory.setKind(entityMetadata.getKind());
		for (int i = 0; i < identifiers.size(); i++) {
			long id = identifiers.get(i);
			nativeKeys[i] = keyFactory.newKey(id);
		}
		return nativeKeys;
	}

	/**
	 * Converts the given list of identifiers into an array of native Key
	 * objects.
	 * 
	 * @param entityClass
	 *            the entity class to which these identifiers belong to.
	 * @param identifiers
	 *            the list of identifiers to convert.
	 * @return an array of Key objects
	 */
	private Key[] stringListToNativeKeys(Class<?> entityClass, List<String> identifiers) {
		if (identifiers == null || identifiers.isEmpty()) {
			return new Key[0];
		}
		EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
		Key[] nativeKeys = new Key[identifiers.size()];
		KeyFactory keyFactory = entityManager.newNativeKeyFactory();
		keyFactory.setKind(entityMetadata.getKind());
		for (int i = 0; i < identifiers.size(); i++) {
			String id = identifiers.get(i);
			nativeKeys[i] = keyFactory.newKey(id);
		}
		return nativeKeys;
	}

}
