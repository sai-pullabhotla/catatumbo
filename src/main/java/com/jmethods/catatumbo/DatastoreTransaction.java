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
 * A Datastore Transaction. This interface extends the {@link DatastoreAccess}.
 * DatastoreTransaction objects are created with a call to
 * {@link EntityManager#newTransaction()}. Once a handle to
 * {@link DatastoreTransaction} is obtained, it can be used to perform various
 * database operations (read/write) within a transaction. Transactions are
 * committed with the call to {@link DatastoreTransaction#commit()} method.
 * Transactions can be rolled back with a call to
 * {@link DatastoreTransaction#rollback()}.
 * 
 * @author Sai Pullabhotla
 *
 */
public interface DatastoreTransaction extends DatastoreAccess {

	/**
	 * Inserts the given entity. ID allocation is deferred to the submit time.
	 * Generated key, if any, can be retrieved by calling
	 * {@link DatastoreTransaction.Response#getGneratedKeys()}.
	 * 
	 * @param entity
	 *            the entity to insert.
	 * @throws EntityManagerException
	 *             if the entity has a String Identifier or any other error
	 *             occurs while accessing the underlying Datastore.
	 */
	<E> void insertWithDeferredIdAllocation(E entity);

	/**
	 * Inserts the given entities. ID allocation is deferred to the submit time.
	 * Generated keys, if any, can be retrieved by calling
	 * {@link DatastoreTransaction.Response#getGneratedKeys()}.
	 * 
	 * @param entities
	 *            the entities to insert
	 * @throws EntityManagerException
	 *             if the entity has a String Identifier or any other error
	 *             occurs while accessing the underlying Datastore.
	 */
	<E> void insertWithDeferredIdAllocation(List<E> entities);

	/**
	 * Updates or inserts the given entity. ID allocation is deferred to the
	 * submit time. Generated key, if any, can be retrieved by calling
	 * {@link DatastoreTransaction.Response#getGneratedKeys()}.
	 * 
	 * 
	 * @param entity
	 *            the entity to update or insert.
	 * @throws EntityManagerException
	 *             if the entity has a String Identifier or any other error
	 *             occurs while accessing the underlying Datastore.
	 */
	<E> void upsertWithDeferredIdAllocation(E entity);

	/**
	 * Updates or Inserts the given entities. ID allocation is deferred to the
	 * submit time. Generated keys, if any, can be retrieved by calling
	 * {@link DatastoreTransaction.Response#getGneratedKeys()}.
	 * 
	 * 
	 * @param entities
	 *            the entities to update or insert
	 * @throws EntityManagerException
	 *             if the entity has a String Identifier or any other error
	 *             occurs while accessing the underlying Datastore.
	 */
	<E> void upsertWithDeferredIdAllocation(List<E> entities);

	/**
	 * Tells if this DatastoreTransaction is still active.
	 * 
	 * @return <code>true</code>, if this DatastoreTransaction is still active;
	 *         <code>false</code>, otherwise.
	 */
	boolean isActive();

	/**
	 * Commits changes made within this transaction.
	 * 
	 * @return Response. The response contains any generated.
	 * 
	 * @throws EntityManagerException
	 *             if the commit fails.
	 */
	Response commit();

	/**
	 * Rolls back the changes made in this transaction.
	 * 
	 * @throws EntityManagerException
	 *             if this transaction was already committed.
	 */
	void rollback();

	/**
	 * Transaction's commit Response. Used for returning generated keys for
	 * entities whose id allocation was deferred until submit/commit time.
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
		List<DatastoreKey> getGneratedKeys();
	}

}
