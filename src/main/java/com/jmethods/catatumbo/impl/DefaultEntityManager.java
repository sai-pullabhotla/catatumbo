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

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.GqlQuery;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.jmethods.catatumbo.EntityManager;
import com.jmethods.catatumbo.EntityManagerException;

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
	 * Datastore object
	 */
	private Datastore datastore;

	/**
	 * Creates a new instance of <code>DefaultEntityManager</code>.
	 * 
	 * @param datastore
	 *            the Datastore object
	 */
	public DefaultEntityManager(Datastore datastore) {
		super(datastore);
		this.datastore = datastore;
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
	public <E> void deleteAll(Class<E> entityClass) {
		EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
		String query = "SELECT __key__ from " + entityMetadata.getKind();
		try {
			GqlQuery<Key> gqlQuery = Query.gqlQueryBuilder(Query.ResultType.KEY, query).build();
			QueryResults<Key> keys = datastore.run(gqlQuery);
			// TODO do batch of 100?
			while (keys.hasNext()) {
				Key key = keys.next();
				datastore.delete(key);
			}
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public DefaultDatastoreTransaction newTransaction() {
		return new DefaultDatastoreTransaction(datastore.newTransaction());
	}

}
