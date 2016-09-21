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
 * Interface for Callbacks to run Datastore operations within a Transaction.
 * 
 * @author Sai Pullabhotla
 * @param <T>
 * @see EntityManager#executeInTransaction(TransactionalTask)
 *
 */
public interface TransactionalTask<T> {

	/**
	 * Executes the task. After the execute method finishes normally, the
	 * transaction will be committed by the {@link EntityManager}. If the
	 * execute method throws any exception, the transaction will be rolled back.
	 * 
	 * @param transaction
	 *            the transaction to read from/write to the Cloud Datastore.
	 * @return the result of execution
	 */
	T execute(DatastoreTransaction transaction);

}
