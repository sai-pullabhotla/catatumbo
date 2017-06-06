/*
 * Copyright 2017 Sai Pullabhotla.
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

import com.jmethods.catatumbo.stats.StatCompositeIndex;
import com.jmethods.catatumbo.stats.StatCompositeIndexNs;
import com.jmethods.catatumbo.stats.StatKind;
import com.jmethods.catatumbo.stats.StatKindNs;
import com.jmethods.catatumbo.stats.StatTotal;
import com.jmethods.catatumbo.stats.StatTotalNs;

/**
 * Provides access to various Datastore statistics. An instance of
 * {@link DatastoreStats} is obtained by calling
 * {@link EntityManager#getDatastoreStats()}.
 * 
 * @author Sai Pullabhotla
 *
 */
public interface DatastoreStats {

	/**
	 * Returns the Summary statistic. This includes the information from all
	 * namespaces.
	 * 
	 * @return the Summary statistic. May be <code>null</code>, if the statistic
	 *         is not available.
	 * @throws EntityManagerException
	 *             if any error occurs while retrieving the statistics.
	 */
	StatTotal getSummary() throws EntityManagerException;

	/**
	 * Returns the summary statistic for the current namespace. If the
	 * {@link Tenant} has a namespace set, it will be used. Otherwise, the
	 * namespace with which the {@link EntityManager} was created.
	 * 
	 * @return the summary statistic for the current namespace. May be
	 *         <code>null</code>, if the statistic is not available.
	 * @throws EntityManagerException
	 *             if any error occurs while retrieving the statistics.
	 */
	StatTotalNs getSummaryNs() throws EntityManagerException;

	/**
	 * Returns the summary statistics for the given namespace.
	 * 
	 * @param namespace
	 *            the namespace
	 * 
	 * @return the summary statistics for the given namespace. May be
	 *         <code>null</code>, if the statistic is not available.
	 * @throws EntityManagerException
	 *             if any error occurs while retrieving the statistics.
	 */
	StatTotalNs getSummaryNs(String namespace) throws EntityManagerException;

	/**
	 * Returns the statistics by Kind, across all namespaces. The returned list
	 * contains one Statistic entity for each Kind.
	 * 
	 * @return the statistics by Kind, across all namespaces. Returns an empty
	 *         list if the statistics are not available.
	 * @throws EntityManagerException
	 *             if any error occurs while retrieving the statistics.
	 */
	List<StatKind> getKinds() throws EntityManagerException;

	/**
	 * Returns the statistics for the given Kind, across all namespaces.
	 * 
	 * @param kind
	 *            the Kind name
	 * 
	 * @return the statistics for the given Kind, across all namespaces. Returns
	 *         <code>null</code>, if the statistic is not available.
	 * @throws EntityManagerException
	 *             if any error occurs while retrieving the statistics.
	 */
	StatKind getKind(String kind) throws EntityManagerException;

	/**
	 * Returns the statistics by Kind, for the current namespace. The returned
	 * list contains one Statistic entity for each Kind. If the {@link Tenant}
	 * has a namespace set, it will be used. Otherwise, the namespace with which
	 * the {@link EntityManager} was created.
	 * 
	 * @return the statistics by Kind, for the current namespace.
	 * @throws EntityManagerException
	 *             if any error occurs while retrieving the statistics.
	 */
	List<StatKindNs> getKindsNs() throws EntityManagerException;

	/**
	 * Returns the statistics for the given Kind in the current namespace. If
	 * the {@link Tenant} has a namespace set, it will be used. Otherwise, the
	 * namespace with which the {@link EntityManager} was created.
	 * 
	 * @param kind
	 *            the Kind name
	 * 
	 * @return the statistics for the given Kind in the current namespace.
	 * @throws EntityManagerException
	 *             if any error occurs while retrieving the statistics.
	 */
	StatKindNs getKindNs(String kind) throws EntityManagerException;

	/**
	 * Returns the statistics for the given Kind and namespace. If the
	 * {@link Tenant} has a namespace set, it will be used. Otherwise, the
	 * namespace with which the {@link EntityManager} was created.
	 * 
	 * @param namespace
	 *            the namespace
	 * @param kind
	 *            the Kind name
	 * 
	 * @return the statistics for the given Kind and namespace.
	 * @throws EntityManagerException
	 *             if any error occurs while retrieving the statistics.
	 */
	StatKindNs getKindNs(String namespace, String kind) throws EntityManagerException;

	/**
	 * Returns the statistics for composite indexes, across all namespaces.
	 * 
	 * @return the statistics for composite indexes, across all namespaces.
	 */
	List<StatCompositeIndex> getCompositeIndexes();

	/**
	 * Returns the statistics for composite indexes for the current namespace.
	 * If the {@link Tenant} has a namespace set, it will be used. Otherwise,
	 * the namespace with which the {@link EntityManager} was created.
	 * 
	 * @return the statistics for composite indexes in the current namespace.
	 */
	List<StatCompositeIndexNs> getCompositeIndexesNs();

	/**
	 * Returns the statistics for composite indexes for the given namespace.
	 * 
	 * @param namespace
	 *            the namespace for which the statistics should be retrieved.
	 * 
	 * @return the statistics for composite indexes for the given namespace.
	 */
	List<StatCompositeIndexNs> getCompositeIndexesNs(String namespace);

}
