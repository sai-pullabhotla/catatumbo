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
 * Interface that provides the metadata of the Datastore, such as the namespaces
 * in the Datastore, the various Kinds in a namespace and the Properties of a
 * Kind.
 * 
 * Objects of this class may be obtained from the {@link EntityManager} by
 * calling the {@link EntityManager#getDatastoreMetadata()}.
 * 
 * @author Sai Pullabhotla
 *
 */
public interface DatastoreMetadata {

	/**
	 * Returns all the namespaces in the Datastore. The results will be ordered
	 * by name in ascending order. Exercise caution when using this method on a
	 * Datastore that has many many namespaces. Use one of the overloaded
	 * methods to get a page of namespaces.
	 * 
	 * @return the list of namespaces. The default namespace, if exists, would
	 *         be returned as an empty string, typically the first namespace in
	 *         the returned list.
	 * @throws EntityManagerException
	 *             if any error occurs while accessing the Datastore.
	 */
	List<String> getNamespaces();

	/**
	 * Returns the namespaces, up to the specified limit. The returned results
	 * will be ordered by name in ascending order.
	 * 
	 * @param limit
	 *            the maximum number of namespaces to return. A limit of zero or
	 *            less is interpreted as no limit and returns all results.
	 * @return the namespaces, up to the specified limit. The default namespace,
	 *         if exists, would be returned as an empty string.
	 * @throws EntityManagerException
	 *             if any error occurs while accessing the Datastore.
	 */
	QueryResponse<String> getNamespaces(int limit);

	/**
	 * Returns the namespaces, up to the specified limit, from the specified
	 * cursor location. The results will be ordered by name in ascending order.
	 * 
	 * @param fromCursor
	 *            the cursor location from which the results should start.
	 * @param limit
	 *            the maximum number of namespaces to return
	 * @return the namespaces from the specified cursor location. The default
	 *         namespace, if exists, would be returned as an empty string.
	 * @throws EntityManagerException
	 *             if any error occurs while accessing the Datastore.
	 */
	QueryResponse<String> getNamespaces(DatastoreCursor fromCursor, int limit);

	/**
	 * Returns the available Kinds from the current namespace, where current
	 * namespace is the the namespace set using {@link Tenant} or the namespace
	 * with which the {@link EntityManager} was created.
	 * 
	 * @return the Kinds in the current namespace. The returned results will be
	 *         order by Kind's name in ascending order.
	 * @throws EntityManagerException
	 *             if any error occurs while accessing the Datastore.
	 */
	List<String> getKinds();

	/**
	 * Returns the list of Properties in the specified Kind. The returned list
	 * only contains indexed properties. If the {@link Tenant} has a namespace
	 * set, it will be used. Otherwise, the namespace with which the
	 * {@link EntityManager} was created. The returned list is order by property
	 * name in ascending order.
	 * 
	 * @param kind
	 *            the Kind
	 * @return the indexed properties in the given Kind.
	 * @throws EntityManagerException
	 *             if any error occurs while accessing the Datastore.
	 */
	List<DatastoreProperty> getProperties(String kind);

}
