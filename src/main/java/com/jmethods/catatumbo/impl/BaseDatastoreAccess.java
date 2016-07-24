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
import com.google.cloud.datastore.DatastoreReaderWriter;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.GqlQuery;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.Query.ResultType;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.Transaction;
import com.jmethods.catatumbo.BaseQueryResponse;
import com.jmethods.catatumbo.DatastoreAccess;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.DefaultDatastoreCursor;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.EntityQueryRequest;
import com.jmethods.catatumbo.QueryRequest;
import com.jmethods.catatumbo.QueryResponse;

/**
 * @author Sai Pullabhotla
 *
 */
public abstract class BaseDatastoreAccess implements DatastoreAccess {
	/**
	 * Datastore object
	 */
	private Datastore datastore;

	/**
	 * DatastoreReaderWriter
	 */
	private DatastoreReaderWriter datastoreReaderWriter;

	/**
	 * Creates a new instance of <code>BaseDatastoreAccess</code>.
	 * 
	 * @param datastore
	 *            the Datastore object
	 */
	protected BaseDatastoreAccess(Datastore datastore) {
		this.datastore = datastore;
		this.datastoreReaderWriter = datastore;
	}

	/**
	 * Creates a new instance of <code>BaseDatastoreAccess</code>.
	 * 
	 * @param transaction
	 *            Native transaction
	 */
	protected BaseDatastoreAccess(Transaction transaction) {
		this.datastore = transaction.datastore();
		this.datastoreReaderWriter = transaction;
	}

	/**
	 * Returns the underlying Datastore object.
	 * 
	 * @return the underlying Datastore object.
	 */
	public Datastore getDatastore() {
		return datastore;
	}

	@Override
	public <E> E insert(E entity) {
		try {
			Entity datastoreEntity = Marshaller.marshal(datastore, entity);
			Entity insertedDatastoreEntity = datastoreReaderWriter.add(datastoreEntity);
			@SuppressWarnings("unchecked")
			E insertedEntity = (E) Unmarshaller.unmarshal(insertedDatastoreEntity, entity.getClass());
			return insertedEntity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> List<E> insert(List<E> entities) {
		if (entities == null) {
			return null;
		}
		if (entities.isEmpty()) {
			return new ArrayList<E>();
		}
		try {
			Entity[] datastoreEntities = new Entity[entities.size()];
			Class<?> entityClass = entities.get(0).getClass();
			for (int i = 0; i < entities.size(); i++) {
				datastoreEntities[i] = Marshaller.marshal(datastore, entities.get(i));
			}
			List<Entity> insertedDatastoreEntities = datastoreReaderWriter.add(datastoreEntities);
			List<E> insertedEntities = new ArrayList<>(insertedDatastoreEntities.size());
			for (Entity insertedDatastoreEntity : insertedDatastoreEntities) {
				@SuppressWarnings("unchecked")
				E insertedEntity = (E) Unmarshaller.unmarshal(insertedDatastoreEntity, entityClass);
				insertedEntities.add(insertedEntity);
			}
			return insertedEntities;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> E update(E entity) {
		try {
			Entity datastoreEntity = Marshaller.marshal(datastore, entity, true);
			datastoreReaderWriter.update(datastoreEntity);
			return entity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> List<E> update(List<E> entities) {
		if (entities == null) {
			return null;
		}
		if (entities.isEmpty()) {
			return new ArrayList<>();
		}
		try {
			Entity[] datastoreEntities = new Entity[entities.size()];
			for (int i = 0; i < entities.size(); i++) {
				datastoreEntities[i] = Marshaller.marshal(datastore, entities.get(i), true);
			}
			datastoreReaderWriter.update(datastoreEntities);
			return entities;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> E upsert(E entity) {
		try {
			Entity datastoreEntity = Marshaller.marshal(datastore, entity);
			Entity upsertedDatastoreEntity = datastoreReaderWriter.put(datastoreEntity);
			@SuppressWarnings("unchecked")
			E upsertedEntity = (E) Unmarshaller.unmarshal(upsertedDatastoreEntity, entity.getClass());
			return upsertedEntity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> List<E> upsert(List<E> entities) {
		if (entities == null) {
			return null;
		}
		if (entities.isEmpty()) {
			return new ArrayList<>();
		}
		try {
			Entity[] datastoreEntities = new Entity[entities.size()];
			Class<?> entityClass = entities.get(0).getClass();
			for (int i = 0; i < entities.size(); i++) {
				datastoreEntities[i] = Marshaller.marshal(datastore, entities.get(i));
			}
			List<Entity> upsertedDatastoreEntities = datastoreReaderWriter.put(datastoreEntities);
			List<E> upsertedEntities = new ArrayList<>(upsertedDatastoreEntities.size());
			for (Entity upsertedDatastoreEntity : upsertedDatastoreEntities) {
				@SuppressWarnings("unchecked")
				E upsertedEntity = (E) Unmarshaller.unmarshal(upsertedDatastoreEntity, entityClass);
				upsertedEntities.add(upsertedEntity);
			}
			return upsertedEntities;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> E load(Class<E> entityClass, long id) {
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = datastore.newKeyFactory().kind(entityMetadata.getKind()).newKey(id);
			Entity datastoreEntity = datastoreReaderWriter.get(key);
			if (datastoreEntity == null) {
				return null;
			}
			E entity = Unmarshaller.unmarshal(datastoreEntity, entityClass);
			return entity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> List<E> loadById(Class<E> entityClass, List<Long> identifiers) {
		try {
			Key[] nativeKeys = longListToNativeKeys(entityClass, identifiers);
			List<Entity> nativeEntities = datastore.fetch(nativeKeys);
			return toEntities(entityClass, nativeEntities);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> E load(Class<E> entityClass, DatastoreKey parentKey, long id) {
		if (parentKey == null) {
			return load(entityClass, id);
		}
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = Key.builder(parentKey.nativeKey(), entityMetadata.getKind(), id).build();
			Entity datastoreEntity = datastoreReaderWriter.get(key);
			if (datastoreEntity == null) {
				return null;
			}
			E entity = Unmarshaller.unmarshal(datastoreEntity, entityClass);
			return entity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> E load(Class<E> entityClass, String id) {
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = datastore.newKeyFactory().kind(entityMetadata.getKind()).newKey(id);
			Entity datastoreEntity = datastoreReaderWriter.get(key);
			if (datastoreEntity == null) {
				return null;
			}
			E entity = Unmarshaller.unmarshal(datastoreEntity, entityClass);
			return entity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> List<E> loadByName(Class<E> entityClass, List<String> identifiers) {
		try {
			Key[] nativeKeys = stringListToNativeKeys(entityClass, identifiers);
			List<Entity> nativeEntities = datastore.fetch(nativeKeys);
			return toEntities(entityClass, nativeEntities);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> E load(Class<E> entityClass, DatastoreKey parentKey, String id) {
		if (parentKey == null) {
			return load(entityClass, id);
		}
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = Key.builder(parentKey.nativeKey(), entityMetadata.getKind(), id).build();
			Entity datastoreEntity = datastoreReaderWriter.get(key);
			if (datastoreEntity == null) {
				return null;
			}
			E entity = Unmarshaller.unmarshal(datastoreEntity, entityClass);
			return entity;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public void delete(Object entity) {
		try {
			Key key = Marshaller.marshalKey(datastore, entity);
			datastoreReaderWriter.delete(key);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public void delete(List<?> entities) {
		try {
			Key[] keys = new Key[entities.size()];
			for (int i = 0; i < entities.size(); i++) {
				keys[i] = Marshaller.marshalKey(datastore, entities.get(i));
			}
			datastoreReaderWriter.delete(keys);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public void deleteByKey(DatastoreKey key) {
		try {
			datastoreReaderWriter.delete(key.nativeKey());
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public void deleteByKey(List<DatastoreKey> keys) {
		try {
			Key[] nativeKeys = new Key[keys.size()];
			for (int i = 0; i < keys.size(); i++) {
				nativeKeys[i] = keys.get(i).nativeKey();
			}
			datastoreReaderWriter.delete(nativeKeys);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> void delete(Class<E> entityClass, long id) {
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = datastore.newKeyFactory().kind(entityMetadata.getKind()).newKey(id);
			datastoreReaderWriter.delete(key);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> void delete(Class<E> entityClass, String id) {
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = datastore.newKeyFactory().kind(entityMetadata.getKind()).newKey(id);
			datastoreReaderWriter.delete(key);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> void delete(Class<E> entityClass, DatastoreKey parentKey, long id) {
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = Key.builder(parentKey.nativeKey(), entityMetadata.getKind(), id).build();
			datastoreReaderWriter.delete(key);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> void delete(Class<E> entityClass, DatastoreKey parentKey, String id) {
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = Key.builder(parentKey.nativeKey(), entityMetadata.getKind(), id).build();
			datastoreReaderWriter.delete(key);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public EntityQueryRequest createEntityQueryRequest(String query) {
		return new EntityQueryRequest(query);
	}

	@Override
	public <E> QueryResponse<E> execute(Class<E> expectedResultType, QueryRequest request) {
		if (request instanceof EntityQueryRequest) {
			return executeEntityQueryRequest(expectedResultType, (EntityQueryRequest) request);
		}
		throw new EntityManagerException(String.format("Unsupported QueryRequest: %s", request.getClass()));
	}

	private <E> QueryResponse<E> executeEntityQueryRequest(Class<E> expectedResultType, EntityQueryRequest request) {
		try {
			GqlQuery.Builder<Entity> queryBuilder = Query.gqlQueryBuilder(ResultType.ENTITY, request.getQuery());
			QueryUtils.applyNamedBindings(queryBuilder, request.getNamedBindings());
			QueryUtils.applyPositionalBindings(queryBuilder, request.getPositionalBindings());
			GqlQuery<Entity> gqlQuery = queryBuilder.build();
			QueryResults<Entity> results = datastoreReaderWriter.run(gqlQuery);
			List<E> entities = new ArrayList<>();
			BaseQueryResponse<E> response = new BaseQueryResponse<>();
			response.setStartCursor(new DefaultDatastoreCursor(results.cursorAfter().toUrlSafe()));
			while (results.hasNext()) {
				Entity result = results.next();
				E entity = Unmarshaller.unmarshal(result, expectedResultType);
				entities.add(entity);
			}
			response.setResults(entities);
			response.setEndCursor(new DefaultDatastoreCursor(results.cursorAfter().toUrlSafe()));
			return response;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	private Key[] longListToNativeKeys(Class<?> entityClass, List<Long> identifiers) {
		if (identifiers == null || identifiers.isEmpty()) {
			return new Key[0];
		}
		EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
		Key[] nativeKeys = new Key[identifiers.size()];
		KeyFactory keyFactory = datastore.newKeyFactory();
		keyFactory.kind(entityMetadata.getKind());
		for (int i = 0; i < identifiers.size(); i++) {
			long id = identifiers.get(i);
			nativeKeys[i] = keyFactory.newKey(id);
		}
		return nativeKeys;
	}

	private Key[] stringListToNativeKeys(Class<?> entityClass, List<String> identifiers) {
		if (identifiers == null || identifiers.isEmpty()) {
			return new Key[0];
		}
		EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
		Key[] nativeKeys = new Key[identifiers.size()];
		KeyFactory keyFactory = datastore.newKeyFactory();
		keyFactory.kind(entityMetadata.getKind());
		for (int i = 0; i < identifiers.size(); i++) {
			String id = identifiers.get(i);
			nativeKeys[i] = keyFactory.newKey(id);
		}
		return nativeKeys;
	}

	private <E> List<E> toEntities(Class<E> entityClass, List<Entity> nativeEntities) {
		if (nativeEntities == null || nativeEntities.isEmpty()) {
			return new ArrayList<>();
		}
		List<E> entities = new ArrayList<>(nativeEntities.size());
		for (Entity nativeEntity : nativeEntities) {
			E entity = Unmarshaller.unmarshal(nativeEntity, entityClass);
			entities.add(entity);
		}
		return entities;
	}
}
