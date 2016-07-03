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
	 * Deletes all entities of given Kind. This is experimental/testing purpose
	 * only and NOT optimized.
	 * 
	 * @param entityClass
	 *            the entity class - The entity Kind will be determined from
	 *            this class.
	 * @throws EntityManagerException
	 *             if any error occurs while inserting.
	 */
	<E> void deleteAll(Class<E> entityClass);

	/**
	 * Returns a new Transaction that can be used to perform a set of
	 * operations.
	 * 
	 * @return a new Transaction that can be used to perform a set of
	 *         operations.
	 */
	DatastoreTransaction newTransaction();

}
