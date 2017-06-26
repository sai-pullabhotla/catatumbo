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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.GqlQuery;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.jmethods.catatumbo.DatastoreBatch;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.DatastoreMetadata;
import com.jmethods.catatumbo.DatastoreStats;
import com.jmethods.catatumbo.DatastoreTransaction;
import com.jmethods.catatumbo.EntityManager;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.EntityQueryRequest;
import com.jmethods.catatumbo.KeyQueryRequest;
import com.jmethods.catatumbo.ProjectionQueryRequest;
import com.jmethods.catatumbo.QueryResponse;
import com.jmethods.catatumbo.Tenant;
import com.jmethods.catatumbo.TransactionalTask;
import com.jmethods.catatumbo.impl.IdentifierMetadata.DataType;

/**
 * Default implementation of {@link EntityManager} interface. Manages entities
 * in the Cloud Datastore such as inserting entities, updating, deleting,
 * retrieving, etc. In addition to the standard CRUD operations, the
 * EntityManager allows running GQL queries to retrieve multiple entities that
 * match the specified criteria.
 * 
 * @author Sai Pullabhotla
 */
public class DefaultEntityManager implements EntityManager {

	/**
	 * Batch size for sending delete requests when using the deleteAll method
	 */
	private static final int DEFAULT_DELETE_ALL_BATCH_SIZE = 100;

	/**
	 * Reference to the native Datastore object
	 */
	private Datastore datastore;

	/**
	 * Datastore writer
	 */
	private DefaultDatastoreWriter writer;

	/**
	 * Datastore reader
	 */
	private DefaultDatastoreReader reader;

	/**
	 * Metadata of global callbacks
	 */
	private Map<CallbackType, List<CallbackMetadata>> globalCallbacks;

	/**
	 * Creates a new instance of <code>DefaultEntityManager</code>.
	 * 
	 * @param datastore
	 *            the Datastore object
	 */
	public DefaultEntityManager(Datastore datastore) {
		this.datastore = datastore;
		writer = new DefaultDatastoreWriter(this);
		reader = new DefaultDatastoreReader(this);
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
	public <E> long deleteAll(Class<E> entityClass) {
		EntityMetadata entityMetadata = EntityIntrospector.introspect(entityClass);
		return deleteAll(entityMetadata.getKind());
	}

	@Override
	public long deleteAll(String kind) {
		String query = "SELECT __key__ FROM " + kind;
		try {
			GqlQuery<Key> gqlQuery = Query.newGqlQueryBuilder(Query.ResultType.KEY, query)
					.setNamespace(getEffectiveNamespace()).build();
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
		return new DefaultDatastoreTransaction(this);
	}

	@Override
	public DatastoreBatch newBatch() {
		return new DefaultDatastoreBatch(this);
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
			if (transaction != null) {
				transaction.rollback();
			}
			throw new EntityManagerException(exp);
		} finally {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
		}
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
		return writer.updateWithOptimisticLock(entities);
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
	public <E> E load(Class<E> entityClass, long id) {
		return reader.load(entityClass, id);
	}

	@Override
	public <E> List<E> loadById(Class<E> entityClass, List<Long> identifiers) {
		return reader.loadById(entityClass, identifiers);
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
	public <E> E load(Class<E> entityClass, DatastoreKey parentKey, long id) {
		return reader.load(entityClass, parentKey, id);
	}

	@Override
	public <E> E load(Class<E> entityClass, DatastoreKey parentKey, String id) {
		return reader.load(entityClass, parentKey, id);
	}

	@Override
	public <E> E load(Class<E> entityClass, DatastoreKey key) {
		return reader.load(entityClass, key);
	}

	@Override
	public <E> List<E> loadByKey(Class<E> entityClass, List<DatastoreKey> keys) {
		return reader.loadByKey(entityClass, keys);
	}

	@Override
	public EntityQueryRequest createEntityQueryRequest(String query) {
		return reader.createEntityQueryRequest(query);
	}

	@Override
	public ProjectionQueryRequest createProjectionQueryRequest(String query) {
		return reader.createProjectionQueryRequest(query);
	}

	@Override
	public KeyQueryRequest createKeyQueryRequest(String query) {
		return reader.createKeyQueryRequest(query);
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

	@Override
	public DatastoreMetadata getDatastoreMetadata() {
		return new DefaultDatastoreMetadata(this);
	}

	@Override
	public DatastoreStats getDatastoreStats() {
		return new DefaultDatastoreStats(this);
	}

	@Override
	public DatastoreKey allocateId(Object entity) {
		List<DatastoreKey> keys = allocateId(Arrays.asList(new Object[] { entity }));
		return keys.get(0);
	}

	@Override
	public List<DatastoreKey> allocateId(List<Object> entities) {
		for (Object entity : entities) {
			IdentifierMetadata identifierMetadata = EntityIntrospector.getIdentifierMetadata(entity);
			if (DataType.STRING == identifierMetadata.getDataType()) {
				throw new IllegalArgumentException("ID allocation is only valid for entities with numeric identifiers");
			}
			Object id = IntrospectionUtils.getFieldValue(identifierMetadata, entity);
			if (!(id == null || ((long) id) == 0)) {
				throw new IllegalArgumentException("ID allocation is only valid for entities with a null or zero ID");
			}
		}
		IncompleteKey[] incompleteKeys = new IncompleteKey[entities.size()];
		int i = 0;
		for (Object entity : entities) {
			incompleteKeys[i++] = getIncompleteKey(entity);
		}
		List<Key> nativeKeys = datastore.allocateId(incompleteKeys);
		return DatastoreUtils.toDatastoreKeys(nativeKeys);
	}

	/**
	 * Returns an IncompleteKey of the given entity.
	 * 
	 * @param entity
	 *            the entity
	 * @return the incomplete key
	 */
	private IncompleteKey getIncompleteKey(Object entity) {
		EntityMetadata entityMetadata = EntityIntrospector.introspect(entity.getClass());
		String kind = entityMetadata.getKind();
		ParentKeyMetadata parentKeyMetadata = entityMetadata.getParentKeyMetadata();
		DatastoreKey parentKey = null;
		IncompleteKey incompleteKey = null;
		if (parentKeyMetadata != null) {
			parentKey = (DatastoreKey) IntrospectionUtils.getFieldValue(parentKeyMetadata, entity);
		}
		if (parentKey != null) {
			incompleteKey = IncompleteKey.newBuilder(parentKey.nativeKey(), kind).build();
		} else {
			incompleteKey = IncompleteKey.newBuilder(datastore.getOptions().getProjectId(), kind)
					.setNamespace(getEffectiveNamespace()).build();
		}
		return incompleteKey;
	}

	@Override
	public void setDefaultListeners(Class<?>... entityListeners) {
		globalCallbacks = new EnumMap<>(CallbackType.class);
		for (Class<?> listenerClass : entityListeners) {
			ExternalListenerMetadata listenerMetadata = ExternalListenerIntrospector.introspect(listenerClass);
			Map<CallbackType, Method> callbacks = listenerMetadata.getCallbacks();
			if (callbacks != null) {
				for (Map.Entry<CallbackType, Method> entry : callbacks.entrySet()) {
					CallbackType callbackType = entry.getKey();
					Method callbackMethod = entry.getValue();
					CallbackMetadata callbackMetadata = new CallbackMetadata(EntityListenerType.DEFAULT, callbackType,
							callbackMethod);
					putDefaultCallback(callbackType, callbackMetadata);
				}
			}
		}
	}

	/**
	 * Puts/adds the given callback type and its metadata to the list of default
	 * listeners.
	 * 
	 * @param callbackType
	 *            the event type
	 * @param metadata
	 *            the callback metadata
	 */
	private void putDefaultCallback(CallbackType callbackType, CallbackMetadata metadata) {
		List<CallbackMetadata> metadataList = globalCallbacks.get(callbackType);
		if (metadataList == null) {
			metadataList = new ArrayList<>();
			globalCallbacks.put(callbackType, metadataList);
		}
		metadataList.add(metadata);
	}

	/**
	 * Executes the entity listeners associated with the given entity.
	 * 
	 * @param callbackType
	 *            the event type
	 * @param entity
	 *            the entity that prodeced the event
	 */
	public void executeEntityListeners(CallbackType callbackType, Object entity) {
		// We may get null entities here. For example loading a nonexistent ID
		// or IDs.
		if (entity == null) {
			return;
		}
		EntityListenersMetadata entityListenersMetadata = EntityIntrospector.getEntityListenersMetadata(entity);
		List<CallbackMetadata> callbacks = entityListenersMetadata.getCallbacks(callbackType);
		if (!entityListenersMetadata.isExcludeDefaultListeners()) {
			executeGlobalListeners(callbackType, entity);
		}
		if (callbacks == null) {
			return;
		}
		for (CallbackMetadata callback : callbacks) {
			switch (callback.getListenerType()) {
			case EXTERNAL:
				Object listener = ListenerFactory.getInstance().getListener(callback.getListenerClass());
				invokeCallbackMethod(callback.getCallbackMethod(), listener, entity);
				break;
			case INTERNAL:
				invokeCallbackMethod(callback.getCallbackMethod(), entity);
				break;
			default:
				String message = String.format("Unknown or unimplemented callback listener type: %s",
						callback.getListenerType());
				throw new EntityManagerException(message);
			}
		}
	}

	/**
	 * Executes the entity listeners associated with the given list of entities.
	 * 
	 * @param callbackType
	 *            the callback type
	 * @param entities
	 *            the entities
	 */
	public void executeEntityListeners(CallbackType callbackType, List<?> entities) {
		for (Object entity : entities) {
			executeEntityListeners(callbackType, entity);
		}
	}

	/**
	 * Executes the global listeners for the given event type for the given
	 * entity.
	 * 
	 * @param callbackType
	 *            the event type
	 * @param entity
	 *            the entity
	 */
	private void executeGlobalListeners(CallbackType callbackType, Object entity) {
		if (globalCallbacks == null) {
			return;
		}
		List<CallbackMetadata> callbacks = globalCallbacks.get(callbackType);
		if (callbacks == null) {
			return;
		}
		for (CallbackMetadata callback : callbacks) {
			Object listener = ListenerFactory.getInstance().getListener(callback.getListenerClass());
			invokeCallbackMethod(callback.getCallbackMethod(), listener, entity);
		}
	}

	/**
	 * Invokes the given callback method on the given target object.
	 * 
	 * @param callbackMethod
	 *            the callback method
	 * @param listener
	 *            the listener object on which to invoke the method
	 * @param entity
	 *            the entity for which the callback is being invoked.
	 */
	private static void invokeCallbackMethod(Method callbackMethod, Object listener, Object entity) {
		try {
			callbackMethod.invoke(listener, entity);
		} catch (Exception exp) {
			String message = String.format("Failed to execute callback method %s of class %s", callbackMethod.getName(),
					callbackMethod.getDeclaringClass().getName());
			throw new EntityManagerException(message, exp);
		}

	}

	/**
	 * Invokes the given callback method on the given target object.
	 * 
	 * @param callbackMethod
	 *            the callback method
	 * @param entity
	 *            the entity for which the callback is being invoked
	 */
	private static void invokeCallbackMethod(Method callbackMethod, Object entity) {
		try {
			callbackMethod.invoke(entity);
		} catch (Exception exp) {
			String message = String.format("Failed to execute callback method %s of class %s", callbackMethod.getName(),
					callbackMethod.getDeclaringClass().getName());
			throw new EntityManagerException(message, exp);
		}

	}

	/**
	 * Creates and returns a new native KeyFactory. If a namespace was specified
	 * using {@link Tenant}, the returned KeyFactory will have the specified
	 * namespace.
	 * 
	 * @return a {@link KeyFactory}
	 */
	KeyFactory newNativeKeyFactory() {
		KeyFactory keyFactory = datastore.newKeyFactory();
		String namespace = Tenant.getNamespace();
		if (namespace != null) {
			keyFactory.setNamespace(namespace);
		}
		return keyFactory;
	}

	/**
	 * Returns the effective namespace. If a namespace was specified using
	 * {@link Tenant}, it will be returned. Otherwise, the namespace of this
	 * EntityManager is returned.
	 * 
	 * @return the effective namespace.
	 */
	String getEffectiveNamespace() {
		String namespace = Tenant.getNamespace();
		if (namespace == null) {
			namespace = datastore.getOptions().getNamespace();
		}
		return namespace;
	}

}
