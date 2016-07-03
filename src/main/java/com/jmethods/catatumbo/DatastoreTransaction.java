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

import com.google.cloud.datastore.DatastoreException;

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
	 * Tells if this DatastoreTransaction is still active.
	 * 
	 * @return <code>true</code>, if this DatastoreTransaction is still active;
	 *         <code>false</code>, otherwise.
	 */
	boolean isActive();

	/**
	 * Commits changes made within this transaction.
	 * 
	 * @throws EntityManagerException
	 *             if the commit fails.
	 */
	void commit() throws EntityManagerException;

	/**
	 * Rolls back the changes made in this transaction.
	 * 
	 * @throws DatastoreException
	 *             if this transaction was already committed.
	 */
	void rollback() throws DatastoreException;

}
