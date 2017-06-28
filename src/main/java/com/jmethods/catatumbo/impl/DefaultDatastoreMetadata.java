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

import com.google.cloud.datastore.Cursor;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.GqlQuery;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.Query.ResultType;
import com.google.cloud.datastore.QueryResults;
import com.jmethods.catatumbo.DatastoreCursor;
import com.jmethods.catatumbo.DatastoreMetadata;
import com.jmethods.catatumbo.DatastoreProperty;
import com.jmethods.catatumbo.DefaultDatastoreCursor;
import com.jmethods.catatumbo.DefaultDatastoreKey;
import com.jmethods.catatumbo.DefaultQueryResponse;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.EntityQueryRequest;
import com.jmethods.catatumbo.QueryResponse;

/**
 * Default implementation of {@link DatastoreMetadata}.
 * 
 * @author Sai Pullabhotla
 *
 */
public class DefaultDatastoreMetadata implements DatastoreMetadata {

	/**
	 * Name of the System entity that contains the namespaces
	 */
	private static final String ENTITY_NAMESPACES = "__namespace__";

	/**
	 * Name of the System entity that contains the Kinds
	 */
	private static final String ENTITY_KINDS = "__kind__";

	/**
	 * Name of the System entity that contains the Properties of a Kind
	 */
	private static final String ENTITY_PROPERTIES = "__property__";

	/**
	 * Reference to the entity manager that created this object
	 */
	private DefaultEntityManager entityManager;

	/**
	 * Creates a new instance of <code>DefaultDatastoreMetadata</code>.
	 * 
	 * @param entityManager
	 *            the entity manager that created this metadata object.
	 */
	public DefaultDatastoreMetadata(DefaultEntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<String> getNamespaces() {
		return getNamespaces(0).getResults();

	}

	@Override
	public QueryResponse<String> getNamespaces(int limit) {
		return getNamespaces(new DefaultDatastoreCursor(null), limit);
	}

	@Override
	public QueryResponse<String> getNamespaces(DatastoreCursor fromCursor, int limit) {
		try {
			Datastore datastore = entityManager.getDatastore();
			String query = "SELECT __key__ FROM " + ENTITY_NAMESPACES + " ORDER BY __key__";
			if (limit > 0) {
				query += " LIMIT @Limit";
			}
			query += " OFFSET @Offset";
			GqlQuery.Builder<Key> gqlQueryBuilder = Query.newGqlQueryBuilder(ResultType.KEY, query);
			if (limit > 0) {
				gqlQueryBuilder.setBinding("Limit", limit);
			}
			gqlQueryBuilder.setBinding("Offset", Cursor.fromUrlSafe(fromCursor.getEncoded()));
			GqlQuery<Key> gqlQuery = gqlQueryBuilder.build();
			QueryResults<Key> results = datastore.run(gqlQuery);
			DefaultQueryResponse<String> response = new DefaultQueryResponse<>();
			List<String> namespaces = new ArrayList<>(Math.max(limit, 50));
			response.setStartCursor(new DefaultDatastoreCursor(results.getCursorAfter().toUrlSafe()));
			while (results.hasNext()) {
				Key key = results.next();
				String name = key.getName();
				namespaces.add(name == null ? "" : name);
			}
			response.setResults(namespaces);
			response.setEndCursor(new DefaultDatastoreCursor(results.getCursorAfter().toUrlSafe()));
			return response;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public List<String> getKinds() {
		return getKinds(false);
	}

	@Override
	public List<String> getKinds(boolean excludeSystemKinds) {
		try {
			String query = "SELECT __key__ FROM " + ENTITY_KINDS + " ORDER BY __key__";
			GqlQuery.Builder<Key> gqlQueryBuilder = Query.newGqlQueryBuilder(ResultType.KEY, query);
			gqlQueryBuilder.setNamespace(entityManager.getEffectiveNamespace());
			GqlQuery<Key> gqlQuery = gqlQueryBuilder.build();
			QueryResults<Key> results = entityManager.getDatastore().run(gqlQuery);
			List<String> kinds = new ArrayList<>(50);
			while (results.hasNext()) {
				Key key = results.next();
				String kind = key.getName();
				if (excludeSystemKinds && kind.startsWith("__")) {
					continue;
				}
				kinds.add(key.getName());
			}
			return kinds;
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public List<DatastoreProperty> getProperties(String kind) {
		try {
			Key nativeKey = entityManager.newNativeKeyFactory().setKind(ENTITY_KINDS).newKey(kind);
			DefaultDatastoreKey key = new DefaultDatastoreKey(nativeKey);
			String query = "SELECT * FROM " + ENTITY_PROPERTIES + " WHERE __key__ HAS ANCESTOR @1 ORDER BY __key__";
			EntityQueryRequest request = entityManager.createEntityQueryRequest(query);
			request.addPositionalBinding(key);
			QueryResponse<DatastoreProperty> response = entityManager.executeEntityQueryRequest(DatastoreProperty.class,
					request);
			return response.getResults();
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

}
