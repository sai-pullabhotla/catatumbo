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
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.GqlQuery;
import com.google.cloud.datastore.Key;
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

	private DatastoreReaderWriter datastoreReaderWriter;

	/**
	 * Creates a new instance of <code>DefaultEntityManager</code>.
	 * 
	 * @param datastore
	 *            the Datastore object
	 */
	protected BaseDatastoreAccess(Datastore datastore) {
		this.datastore = datastore;
		this.datastoreReaderWriter = datastore;
	}

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
			FullEntity<?> datastoreEntity = (FullEntity<?>) Marshaller.marshal(datastore, entity);
			Entity insertedDatastoreEntity = datastoreReaderWriter.add(datastoreEntity);
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
			FullEntity<?>[] datastoreEntities = new FullEntity<?>[entities.size()];
			Class<?> entityClass = entities.get(0).getClass();
			for (int i = 0; i < entities.size(); i++) {
				datastoreEntities[i] = (FullEntity<?>) Marshaller.marshal(datastore, entities.get(i));
			}
			List<Entity> insertedDatastoreEntities = datastoreReaderWriter.add(datastoreEntities);
			List<E> insertedEntities = new ArrayList<>(insertedDatastoreEntities.size());
			for (Entity insertedDatastoreEntity : insertedDatastoreEntities) {
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
			Entity datastoreEntity = (Entity) Marshaller.marshal(datastore, entity);
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
				datastoreEntities[i] = (Entity) Marshaller.marshal(datastore, entities.get(i));
			}
			datastoreReaderWriter.update(datastoreEntities);
			return entities;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> E load(Class<E> entityClass, long id) {
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = datastore.newKeyFactory().kind(entityMetadata.getKind()).newKey(id);
			com.google.cloud.datastore.Entity datastoreEntity = datastoreReaderWriter.get(key);
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
	public <E> E load(Class<E> entityClass, DatastoreKey parentKey, long id) {
		if (parentKey == null) {
			return load(entityClass, id);
		}
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = Key.builder(parentKey.nativeKey(), entityMetadata.getKind(), id).build();
			com.google.cloud.datastore.Entity datastoreEntity = datastoreReaderWriter.get(key);
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
			com.google.cloud.datastore.Entity datastoreEntity = datastoreReaderWriter.get(key);
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
	public <E> E load(Class<E> entityClass, DatastoreKey parentKey, String id) {
		if (parentKey == null) {
			return load(entityClass, id);
		}
		try {
			EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
			Key key = Key.builder(parentKey.nativeKey(), entityMetadata.getKind(), id).build();
			com.google.cloud.datastore.Entity datastoreEntity = datastoreReaderWriter.get(key);
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
			Key key = (Key) Marshaller.marshalKey(datastore, entity);
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
				keys[i] = (Key) Marshaller.marshalKey(datastore, entities.get(i));
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
}
