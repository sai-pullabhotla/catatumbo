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

/**
 * Manages mapping and persistence of entities. EntityManager objects are
 * created using the {@link EntityManagerFactory}.
 * 
 * @author Sai Pullabhotla
 *
 */
public interface EntityManager extends DatastoreAccess {

	/**
	 * Deletes all entities of given Kind.
	 * 
	 * @param entityClass
	 *            the entity class - The entity Kind will be determined from
	 *            this class.
	 * @return the number of entities that were deleted
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	<E> long deleteAll(Class<E> entityClass);

	/**
	 * Returns a new Transaction that can be used to perform a set of
	 * operations.
	 * 
	 * @return a new Transaction that can be used to perform a set of
	 *         operations.
	 */
	DatastoreTransaction newTransaction();

	/**
	 * Creates and returns a new {@link DatastoreBatch} that can be used for
	 * processing multiple write operations in one request.
	 * 
	 * @return a new <code>DatastoreBatch</code> for processing multiple write
	 *         operations in one request.
	 */
	DatastoreBatch newBatch();

	/**
	 * Runs the given {@link TransactionalTask} in a new transaction. The
	 * {@link TransactionalTask#execute(DatastoreTransaction)} will receive a
	 * reference to the newly created {@link DatastoreTransaction} to perform
	 * reads/writes from/to the Cloud Datastore. When the
	 * {@link TransactionalTask} finishes, the transaction is committed. If any
	 * error occurs during the execution of the {@link TransactionalTask}, the
	 * transaction will be rolled back.
	 * 
	 * @param task
	 *            the task (or call back) to execute
	 * @return the return value from the execution of
	 *         {@link TransactionalTask#execute(DatastoreTransaction)}.
	 * 
	 */
	<T> T executeInTransaction(TransactionalTask<T> task);

	/**
	 * Registers the given entity lifecycle listeners with this entity manager.
	 * 
	 * @param classes
	 *            the classes that should receive entity lifecycle events.
	 *            Lifecycle callbacks are executed for all types of entities
	 *            that are managed by this EntityManager.
	 */
	void setDefaultListeners(Class<?>... classes);

}
