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

import java.io.FileNotFoundException;
import java.util.Random;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.http.HttpTransportOptions;
import com.jmethods.catatumbo.impl.DefaultEntityManager;

/**
 * @author Sai Pullabhotla
 *
 */
public class TestUtils {

	private static final String ENV_PREFIX = "CATATUMBO_";
	public static final String ENV_SERVICE_URL = ENV_PREFIX + "SERVICE_URL";
	public static final String ENV_PROJECT_ID = ENV_PREFIX + "PROJECT_ID";
	public static final String ENV_NAMESPACE = ENV_PREFIX + "NAMESPACE";
	public static final String ENV_CREDENTIALS = ENV_PREFIX + "CREDENTIALS";
	public static final String ENV_CONNECTION_TIMEOUT = ENV_PREFIX + "CONNECTION_TIMEOUT";
	public static final String ENV_READ_TIMEOUT = ENV_PREFIX + "READ_TIMEOUT";

	public static EntityManager getEntityManager() throws FileNotFoundException {
		ConnectionParameters parameters = new ConnectionParameters();
		parameters.setServiceURL(System.getenv(ENV_SERVICE_URL));
		parameters.setProjectId(System.getenv(ENV_PROJECT_ID));
		parameters.setNamespace(System.getenv(ENV_NAMESPACE));
		String jsonCredentialsPath = System.getenv(ENV_CREDENTIALS);
		if (!Utility.isNullOrEmpty(jsonCredentialsPath)) {
			parameters.setJsonCredentialsFile(jsonCredentialsPath);
		}
		String connectionTimeout = System.getenv(ENV_CONNECTION_TIMEOUT);
		if (!Utility.isNullOrEmpty(connectionTimeout)) {
			parameters.setConnectionTimeout(Integer.parseInt(connectionTimeout));
		}
		String readTimeout = System.getenv(ENV_READ_TIMEOUT);
		if (!Utility.isNullOrEmpty(readTimeout)) {
			parameters.setReadTimeout(Integer.parseInt(readTimeout));
		}
		System.out.println(parameters);
		EntityManager em = EntityManagerFactory.getInstance().createEntityManager(parameters);
		Datastore ds = ((DefaultEntityManager) em).getDatastore();
		DatastoreOptions options = ds.getOptions();
		HttpTransportOptions httpTransportOptions = (HttpTransportOptions) options.getTransportOptions();
		System.out.println("***************************");
		System.out.println("DATASTORE INFORMATION");
		System.out.printf("Host: %s%n", options.getHost());
		System.out.printf("Project Id: %s%n", options.getProjectId());
		System.out.printf("Namespace: %s%n", options.getNamespace());
		System.out.printf("Credentials: %s%n", options.getCredentials());
		System.out.printf("Connection Timeout: %d%n", httpTransportOptions.getConnectTimeout());
		System.out.printf("Read Timeout: %d%n", httpTransportOptions.getReadTimeout());
		System.out.println("***************************");
		return em;

	}

	public static String getRandomString(int length) {
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			char c = (char) ('a' + random.nextInt(26));
			buffer.append(c);
		}
		return buffer.toString();

	}

	public static byte[] getRandomBytes(int length) {
		Random random = new Random();
		byte[] output = new byte[length];
		random.nextBytes(output);
		return output;
	}

}
