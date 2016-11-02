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

import java.util.Random;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.jmethods.catatumbo.impl.DefaultEntityManager;

/**
 * @author Sai Pullabhotla
 *
 */
public class TestUtils {

	private static final String ENV_PREFIX = "CATATUMBO_";
	private static final String ENV_TARGET = ENV_PREFIX + "TARGET";
	private static final String ENV_HOST = ENV_PREFIX + "HOST";
	private static final String ENV_PROJECT_ID = ENV_PREFIX + "PROJECT_ID";
	private static final String ENV_NAMESPACE = ENV_PREFIX + "NAMESPACE";
	private static final String ENV_CREDENTIALS = ENV_PREFIX + "CREDENTIALS";

	public static EntityManager getEntityManager() {
		String target = System.getenv(ENV_TARGET);
		EntityManager em;
		if ("remote".equals(target)) {
			em = setupRemoteEntityManager();
		} else {
			em = setupLocalEntityManager();
		}
		Datastore ds = ((DefaultEntityManager) em).getDatastore();
		DatastoreOptions options = ds.getOptions();
		System.out.println("***************************");
		System.out.println("DATASTORE INFORMATION");
		System.out.printf("Host: %s%n", options.getHost());
		System.out.printf("Project Id: %s%n", options.getProjectId());
		System.out.printf("Namespace: %s%n", options.getNamespace());
		System.out.println("***************************");
		return em;

	}

	public static EntityManager setupLocalEntityManager() {
		String host = System.getenv(ENV_HOST);
		String projectId = System.getenv(ENV_PROJECT_ID);
		String namespace = System.getenv(ENV_NAMESPACE);
		if (host == null) {
			host = "localhost:9999";
		}
		return EntityManagerFactory.getInstance().createLocalEntityManager(host, projectId, namespace);
	}

	public static EntityManager setupRemoteEntityManager() {
		EntityManager em = null;
		String jsonCredentialsFile = System.getenv(ENV_CREDENTIALS);
		String projectId = System.getenv(ENV_PROJECT_ID);
		String namespace = System.getenv(ENV_NAMESPACE);
		if (jsonCredentialsFile == null) {
			em = EntityManagerFactory.getInstance().createDefaultEntityManager(namespace);
		} else {
			em = EntityManagerFactory.getInstance().createEntityManager(projectId, jsonCredentialsFile, namespace);
		}
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
