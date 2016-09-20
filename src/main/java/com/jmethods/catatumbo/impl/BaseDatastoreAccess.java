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

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Transaction;
import com.jmethods.catatumbo.DatastoreAccess;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.EntityQueryRequest;
import com.jmethods.catatumbo.KeyQueryRequest;
import com.jmethods.catatumbo.ProjectionQueryRequest;
import com.jmethods.catatumbo.QueryRequest;
import com.jmethods.catatumbo.QueryResponse;

/**
 * Base implementation of {@link DatastoreAccess} interface.
 * 
 * @author Sai Pullabhotla
 *
 */
public abstract class BaseDatastoreAccess implements DatastoreAccess {
	/**
	 * Datastore object
	 */
	protected Datastore datastore;

	/**
	 * Native DatastoreReaderWriter
	 */
	protected DefaultDatastoreReader reader;

	/**
	 * A Writer for performing the updates
	 */
	protected DefaultDatastoreWriter writer;

	/**
	 * Creates a new instance of <code>BaseDatastoreAccess</code>.
	 * 
	 * @param datastore
	 *            the Datastore object
	 */
	protected BaseDatastoreAccess(Datastore datastore) {
		this.datastore = datastore;
		this.writer = new DefaultDatastoreWriter(datastore);
		this.reader = new DefaultDatastoreReader(datastore);
	}

	/**
	 * Creates a new instance of <code>BaseDatastoreAccess</code>.
	 * 
	 * @param transaction
	 *            Native transaction
	 */
	protected BaseDatastoreAccess(Transaction transaction) {
		this.datastore = transaction.datastore();
		this.writer = new DefaultDatastoreWriter(transaction);
		this.reader = new DefaultDatastoreReader(transaction);
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
	public <E> E load(Class<E> entityClass, long id) {
		return reader.load(entityClass, id);
	}

	@Override
	public <E> List<E> loadById(Class<E> entityClass, List<Long> identifiers) {
		return reader.loadById(entityClass, identifiers);
	}

	@Override
	public <E> E load(Class<E> entityClass, DatastoreKey parentKey, long id) {
		return reader.load(entityClass, parentKey, id);
	}

	@Override
	public <E> E load(Class<E> entityClass, String id) {
		return reader.load(entityClass, id);
	}

	@Override
	public <E> List<E> loadByName(Class<E> entityClass, List<String> identifiers) {
		return reader.loadByName(entityClass, identifiers);
	}

	@Override
	public <E> E load(Class<E> entityClass, DatastoreKey parentKey, String id) {
		return reader.load(entityClass, parentKey, id);
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
	public void deleteByKey(DatastoreKey key) {
		writer.deleteByKey(key);
	}

	@Override
	public void deleteByKey(List<DatastoreKey> keys) {
		writer.deleteByKey(keys);
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
	public EntityQueryRequest createEntityQueryRequest(String query) {
		return new EntityQueryRequest(query);
	}

	@Override
	public ProjectionQueryRequest createProjectionQueryRequest(String query) {
		return new ProjectionQueryRequest(query);
	}

	@Override
	public KeyQueryRequest createKeyQueryRequest(String query) {
		return new KeyQueryRequest(query);
	}

	@Override
	public <E> QueryResponse<E> execute(Class<E> expectedResultType, QueryRequest request) {
		if (request instanceof EntityQueryRequest) {
			return executeEntityQueryRequest(expectedResultType, (EntityQueryRequest) request);
		}
		if (request instanceof ProjectionQueryRequest) {
			return executeProjectionQueryRequest(expectedResultType, (ProjectionQueryRequest) request);
		}
		throw new EntityManagerException(String.format("Unsupported QueryRequest: %s", request.getClass()));
	}

	@Override
	public <E> QueryResponse<E> executeEntityQueryRequest(Class<E> expectedResultType, EntityQueryRequest request) {
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
