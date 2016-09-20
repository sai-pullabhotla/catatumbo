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

import static com.jmethods.catatumbo.impl.DatastoreUtils.*;

import java.util.List;

import com.google.cloud.datastore.DatastoreException;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.Transaction;
import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.DatastoreTransaction;
import com.jmethods.catatumbo.EntityManagerException;

/**
 * Default implementation of the {@link DatastoreTransaction} interface.
 * 
 * @author Sai Pullabhotla
 *
 */
public class DefaultDatastoreTransaction extends BaseDatastoreAccess implements DatastoreTransaction {

	/**
	 * Native transaction
	 */
	private Transaction nativeTransaction;

	/**
	 * Creates a new instance of <code>DatastoreTransaction</code>.
	 * 
	 * @param nativeTransaction
	 *            native transaction
	 */
	public DefaultDatastoreTransaction(Transaction nativeTransaction) {
		super(nativeTransaction);
		this.nativeTransaction = nativeTransaction;
	}

	/**
	 * Returns the native transaction.
	 * 
	 * @return the native transaction.
	 */
	public Transaction getNativeTransaction() {
		return nativeTransaction;
	}

	@Override
	public <E> void insertWithDeferredIdAllocation(E entity) {
		try {
			DatastoreUtils.validateDeferredIdAllocation(entity);
			FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(datastore, entity);
			nativeTransaction.addWithDeferredIdAllocation(nativeEntity);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}

	}

	@Override
	public <E> void insertWithDeferredIdAllocation(List<E> entities) {
		if (entities == null || entities.isEmpty()) {
			return;
		}
		try {
			DatastoreUtils.validateDeferredIdAllocation(entities.get(0));
			FullEntity<?>[] nativeEntities = toNativeFullEntities(entities, datastore);
			nativeTransaction.addWithDeferredIdAllocation(nativeEntities);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public <E> void upsertWithDeferredIdAllocation(E entity) {
		try {
			DatastoreUtils.validateDeferredIdAllocation(entity);
			FullEntity<?> nativeEntity = (FullEntity<?>) Marshaller.marshal(datastore, entity);
			nativeTransaction.putWithDeferredIdAllocation(nativeEntity);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}

	}

	@Override
	public <E> void upsertWithDeferredIdAllocation(List<E> entities) {
		if (entities == null || entities.isEmpty()) {
			return;
		}
		try {
			DatastoreUtils.validateDeferredIdAllocation(entities.get(0));
			FullEntity<?>[] nativeEntities = toNativeFullEntities(entities, datastore);
			nativeTransaction.putWithDeferredIdAllocation(nativeEntities);
		} catch (DatastoreException exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public boolean isActive() {
		try {
			return nativeTransaction.active();
		} catch (Exception exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public Response commit() {
		try {
			Transaction.Response nativeResponse = nativeTransaction.commit();
			return new DefaultResponse(nativeResponse);
		} catch (Exception exp) {
			throw new EntityManagerException(exp);
		}
	}

	@Override
	public void rollback() {
		try {
			nativeTransaction.rollback();
		} catch (Exception exp) {
			throw new EntityManagerException(exp);
		}
	}

	static class DefaultResponse implements Response {

		private final Transaction.Response nativeResponse;

		public DefaultResponse(Transaction.Response nativeResponse) {
			this.nativeResponse = nativeResponse;
		}

		@Override
		public List<DatastoreKey> getGneratedKeys() {
			return DatastoreUtils.toDatastoreKeys(nativeResponse.generatedKeys());
		}

	}

}
