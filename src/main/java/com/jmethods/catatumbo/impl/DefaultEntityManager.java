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

import java.util.Arrays;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.GqlQuery;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.jmethods.catatumbo.DatastoreBatch;
import com.jmethods.catatumbo.DatastoreTransaction;
import com.jmethods.catatumbo.EntityManager;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.TransactionalTask;

/**
 * Default implementation of {@link EntityManager} interface. Manages entities
 * in the Cloud Datastore such as inserting entities, updating, deleting,
 * retrieving, etc. In addition to the standard CRUD operations, the
 * EntityManager allows running GQL queries to retrieve multiple entities that
 * match the specified criteria.
 * 
 * @author Sai Pullabhotla
 */
public class DefaultEntityManager extends BaseDatastoreAccess implements EntityManager {

	/**
	 * Batch size for sending delete requests when using the deleteAll method
	 */
	private static final int DEFAULT_DELETE_ALL_BATCH_SIZE = 100;

	/**
	 * Creates a new instance of <code>DefaultEntityManager</code>.
	 * 
	 * @param datastore
	 *            the Datastore object
	 */
	public DefaultEntityManager(Datastore datastore) {
		super(datastore);
	}

	/**
	 * Returns the underlying Datastore object.
	 * 
	 * @return the underlying Datastore object.
	 */
	@Override
	public Datastore getDatastore() {
		return datastore;
	}

	@Override
	public <E> long deleteAll(Class<E> entityClass) {
		EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
		String query = "SELECT __key__ FROM " + entityMetadata.getKind();
		try {
			GqlQuery<Key> gqlQuery = Query.gqlQueryBuilder(Query.ResultType.KEY, query).build();
			QueryResults<Key> keys = datastore.run(gqlQuery);
			Key[] nativeKeys = new Key[DEFAULT_DELETE_ALL_BATCH_SIZE];
			long deleteCount = 0;
			int i = 0;
			while (keys.hasNext()) {
				nativeKeys[i++] = keys.next();
				if (i % DEFAULT_DELETE_ALL_BATCH_SIZE == 0) {
					datastore.delete(nativeKeys);
					deleteCount += DEFAULT_DELETE_ALL_BATCH_SIZE;
					i = 0;
				}
			}
			if (i > 0) {
				datastore.delete(Arrays.copyOfRange(nativeKeys, 0, i));
				deleteCount += i;
			}
			return deleteCount;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public DefaultDatastoreTransaction newTransaction() {
		return new DefaultDatastoreTransaction(datastore.newTransaction());
	}

	@Override
	public DatastoreBatch newBatch() {
		return new DefaultDatastoreBatch(datastore.newBatch());
	}

	@Override
	public <T> T executeInTransaction(TransactionalTask<T> task) {
		DatastoreTransaction transaction = null;
		try {
			transaction = newTransaction();
			T returnValue = task.execute(transaction);
			transaction.commit();
			return returnValue;
		} catch (Exception exp) {
			transaction.rollback();
			throw new EntityManagerException(exp);
		} finally {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
		}
	}

}
