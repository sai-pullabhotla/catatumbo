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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.google.cloud.AuthCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.jmethods.catatumbo.impl.DefaultEntityManager;

/**
 * A factory for producing {@link EntityManager}s.
 * 
 * @author Sai Pullabhotla
 *
 */
public class EntityManagerFactory {

	private static final EntityManagerFactory INSTANCE = new EntityManagerFactory();

	/**
	 * Returns the singleton instance of <code>EntityManagerFactory</code>.
	 * 
	 * @return the instance the singleton instance.
	 */
	public static EntityManagerFactory getInstance() {
		return INSTANCE;
	}

	/**
	 * Creates and returns a new {@link EntityManager} using the default
	 * options.
	 * 
	 * @return a new {@link EntityManager} using the default options.
	 */
	public EntityManager createDefaultEntityManager() {
		return createDefaultEntityManager(null);
	}

	/**
	 * Creates and returns a new {@link EntityManager} using the default
	 * options.
	 * 
	 * @param namespace
	 *            the namespace to work with (for multitenancy)
	 * 
	 * @return a new {@link EntityManager} using the default options.
	 */
	public EntityManager createDefaultEntityManager(String namespace) {
		try {
			AuthCredentials authCredentials = AuthCredentials.createApplicationDefaults();
			DatastoreOptions.Builder datastoreOptionsBuilder = DatastoreOptions.newBuilder()
					.setAuthCredentials(authCredentials);
			if (namespace != null) {
				datastoreOptionsBuilder.namespace(namespace);
			}
			Datastore datastore = datastoreOptionsBuilder.build().getService();
			return new DefaultEntityManager(datastore);
		} catch (Exception exp) {
			throw new EntityManagerFactoryException(exp);
		}
	}

	/**
	 * Creates and return a new {@link EntityManager} using the provided JSON
	 * formatted credentials.
	 * 
	 * @param projectId
	 *            the project ID
	 * 
	 * @param jsonCredentialsFile
	 *            the JSON formatted credentials file for the target Cloud
	 *            project.
	 * @return a new {@link EntityManager}
	 */
	public EntityManager createEntityManager(String projectId, String jsonCredentialsFile) {
		return createEntityManager(projectId, jsonCredentialsFile, null);
	}

	/**
	 * Creates and return a new {@link EntityManager} using the provided JSON
	 * formatted credentials.
	 * 
	 * @param projectId
	 *            the project ID
	 * 
	 * @param jsonCredentialsFile
	 *            the JSON formatted credentials file for the target Cloud
	 *            project.
	 * @return a new {@link EntityManager}
	 */
	public EntityManager createEntityManager(String projectId, File jsonCredentialsFile) {
		return createEntityManager(projectId, jsonCredentialsFile, null);
	}

	/**
	 * Creates and return a new {@link EntityManager} using the provided JSON
	 * formatted credentials.
	 * 
	 * @param projectId
	 *            the project ID
	 * 
	 * @param jsonCredentialsStream
	 *            the JSON formatted credentials for the target Cloud project.
	 * @return a new {@link EntityManager}
	 */
	public EntityManager createEntityManager(String projectId, InputStream jsonCredentialsStream) {
		return createEntityManager(projectId, jsonCredentialsStream, null);
	}

	/**
	 * Creates and return a new {@link EntityManager} using the provided JSON
	 * formatted credentials.
	 * 
	 * @param projectId
	 *            the project ID
	 * 
	 * @param jsonCredentialsFile
	 *            the JSON formatted credentials file for the target Cloud
	 *            project.
	 * @param namespace
	 *            the namespace (for multi-tenant datastore) to use. If
	 *            <code>null</code>, default namespace is used.
	 * @return a new {@link EntityManager}
	 */
	public EntityManager createEntityManager(String projectId, String jsonCredentialsFile, String namespace) {
		return createEntityManager(projectId, new File(jsonCredentialsFile), namespace);
	}

	/**
	 * Creates and return a new {@link EntityManager} using the provided JSON
	 * formatted credentials.
	 * 
	 * @param projectId
	 *            the project ID
	 * 
	 * @param jsonCredentialsFile
	 *            the JSON formatted credentials file for the target Cloud
	 *            project.
	 * @param namespace
	 *            the namespace (for multi-tenant datastore) to use. If
	 *            <code>null</code>, default namespace is used.
	 * @return a new {@link EntityManager}
	 */
	public EntityManager createEntityManager(String projectId, File jsonCredentialsFile, String namespace) {
		try {
			return createEntityManager(projectId, new FileInputStream(jsonCredentialsFile), namespace);
		} catch (Exception exp) {
			throw new EntityManagerFactoryException(exp);
		}
	}

	/**
	 * Creates and return a new {@link EntityManager} using the provided JSON
	 * formatted credentials.
	 * 
	 * @param projectId
	 *            the project ID
	 * 
	 * @param jsonCredentialsStream
	 *            the JSON formatted credentials for the target Cloud project.
	 * @param namespace
	 *            the namespace (for multi-tenant datastore) to use. If
	 *            <code>null</code>, default namespace is used.
	 * @return a new {@link EntityManager}
	 */
	public EntityManager createEntityManager(String projectId, InputStream jsonCredentialsStream, String namespace) {
		try {
			AuthCredentials authCredentials = AuthCredentials.createForJson(jsonCredentialsStream);
			DatastoreOptions.Builder datastoreOptionsBuilder = DatastoreOptions.newBuilder()
					.setAuthCredentials(authCredentials);
			if (projectId != null) {
				datastoreOptionsBuilder.setProjectId(projectId);
			}
			if (namespace != null) {
				datastoreOptionsBuilder.namespace(namespace);
			}
			Datastore datastore = datastoreOptionsBuilder.build().getService();
			return new DefaultEntityManager(datastore);
		} catch (Exception exp) {
			throw new EntityManagerFactoryException(exp);
		} finally {
			Utility.close(jsonCredentialsStream);
		}
	}
}
