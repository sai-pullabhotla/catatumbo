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
import java.io.IOException;
import java.io.InputStream;

import com.google.auth.Credentials;
import com.google.auth.http.HttpTransportFactory;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.NoCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.http.HttpTransportOptions;
import com.jmethods.catatumbo.impl.DefaultEntityManager;

/**
 * A factory for producing {@link EntityManager}s.
 * 
 * @author Sai Pullabhotla
 *
 */
public class EntityManagerFactory {

	/**
	 * Singleton instance
	 */
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
		ConnectionParameters parameters = new ConnectionParameters();
		parameters.setNamespace(namespace);
		return createEntityManager(parameters);
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
		ConnectionParameters parameters = new ConnectionParameters();
		parameters.setProjectId(projectId);
		parameters.setNamespace(namespace);
		parameters.setJsonCredentialsStream(jsonCredentialsStream);
		return createEntityManager(parameters);
	}

	/**
	 * Creates and returns an {@link EntityManager} that allows working with the
	 * local Datastore (a.k.a Datastore Emulator). The underlying API will
	 * attempt to use the default project ID, if one exists.
	 * 
	 * @param serviceURL
	 *            Service URL for the Datastore Emulator. (e.g.
	 *            http://localhost:9999)
	 * @return an {@link EntityManager} that allows working with the local
	 *         Datastore (a.k.a Datastore Emulator).
	 */
	public EntityManager createLocalEntityManager(String serviceURL) {
		return createLocalEntityManager(serviceURL, null, null);
	}

	/**
	 * Creates and returns an {@link EntityManager} that allows working with the
	 * local Datastore (a.k.a Datastore Emulator). Specified project ID will be
	 * used.
	 * 
	 * @param serviceURL
	 *            Service URL for the Datastore Emulator. (e.g.
	 *            http://localhost:9999)
	 * @param projectId
	 *            the project ID. The specified project need not exist in Google
	 *            Cloud.
	 * @return an {@link EntityManager} that allows working with the local
	 *         Datastore (a.k.a Datastore Emulator).
	 */
	public EntityManager createLocalEntityManager(String serviceURL, String projectId) {
		return createLocalEntityManager(serviceURL, projectId, null);
	}

	/**
	 * Creates and returns an {@link EntityManager} that allows working with the
	 * local Datastore (a.k.a Datastore Emulator). Specified project ID will be
	 * used.
	 * 
	 * @param serviceURL
	 *            Service URL for the Datastore Emulator. (e.g.
	 *            http://localhost:9999)
	 * @param projectId
	 *            the project ID. The specified project need not exist in Google
	 *            Cloud. If <code>null</code>, default project ID is used, if it
	 *            can be determined.
	 * @param namespace
	 *            the namespace (for multi-tenant datastore) to use.
	 * @return an {@link EntityManager} that allows working with the local
	 *         Datastore (a.k.a Datastore Emulator). If <code>null</code>,
	 *         default namespace is used.
	 */
	public EntityManager createLocalEntityManager(String serviceURL, String projectId, String namespace) {
		ConnectionParameters parameters = new ConnectionParameters();
		parameters.setServiceURL(serviceURL);
		parameters.setProjectId(projectId);
		parameters.setNamespace(namespace);
		return createEntityManager(parameters);
	}

	/**
	 * Creates and returns an {@link EntityManager} using the specified
	 * connection parameters.
	 * 
	 * @param parameters
	 *            the connection parameters
	 * @return a new {@link EntityManager} created using the specified
	 *         connection parameters.
	 * @throws EntityManagerException
	 *             if any error occurs while creating the EntityManager.
	 */
	public EntityManager createEntityManager(ConnectionParameters parameters) {
		try {
			DatastoreOptions.Builder datastoreOptionsBuilder = DatastoreOptions.newBuilder();
			datastoreOptionsBuilder.setHost(parameters.getServiceURL());
			datastoreOptionsBuilder.setTransportOptions(getHttpTransportOptions(parameters));
			String projectId = parameters.getProjectId();
			if (!Utility.isNullOrEmpty(projectId)) {
				datastoreOptionsBuilder.setProjectId(projectId);
			}
			String namespace = parameters.getNamespace();
			if (namespace != null) {
				datastoreOptionsBuilder.setNamespace(namespace);
			}
			datastoreOptionsBuilder.setCredentials(getCredentials(parameters));
			Datastore datastore = datastoreOptionsBuilder.build().getService();
			return new DefaultEntityManager(datastore);
		} catch (Exception exp) {
			throw new EntityManagerFactoryException(exp);
		}
	}

	/**
	 * Creates and returns the credentials from the given connection parameters.
	 * 
	 * @param parameters
	 *            the connection parameters
	 * @return the credentials for authenticating with the Datastore service.
	 * @throws IOException
	 *             if any error occurs such as not able to read the credentials
	 *             file.
	 */
	private static Credentials getCredentials(ConnectionParameters parameters) throws IOException {
		if (parameters.isEmulator()) {
			return NoCredentials.getInstance();
		}
		InputStream jsonCredentialsStream = parameters.getJsonCredentialsStream();
		if (jsonCredentialsStream != null) {
			return ServiceAccountCredentials.fromStream(jsonCredentialsStream);
		}
		File jsonCredentialsFile = parameters.getJsonCredentialsFile();
		if (jsonCredentialsFile != null) {
			return ServiceAccountCredentials.fromStream(new FileInputStream(jsonCredentialsFile));
		}
		return ServiceAccountCredentials.getApplicationDefault();
	}

	/**
	 * Creates and returns HttpTransportOptions from the given connection
	 * parameters.
	 * 
	 * @param parameters
	 *            the connection parameters
	 * @return the HttpTransportOptions
	 */
	private static HttpTransportOptions getHttpTransportOptions(ConnectionParameters parameters) {
		HttpTransportOptions.Builder httpOptionsBuilder = HttpTransportOptions.newBuilder();
		httpOptionsBuilder.setConnectTimeout(parameters.getConnectionTimeout());
		httpOptionsBuilder.setReadTimeout(parameters.getReadTimeout());

		HttpTransportFactory httpTransportFactory = parameters.getHttpTransportFactory();
		if (httpTransportFactory != null) {
			httpOptionsBuilder.setHttpTransportFactory(httpTransportFactory);
		}
		return httpOptionsBuilder.build();
	}
}
