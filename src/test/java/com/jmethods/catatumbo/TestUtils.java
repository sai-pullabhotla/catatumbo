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
 * @author Sai Pullabhotla
 *
 */
public class TestUtils {

	public static EntityManager setupEntityManager() {
		EntityManager em = null;
		String jsonCredentialsFile = System.getenv("jsonCredentialsFile");
		String projectId = System.getenv("projectId");
		String namespace = System.getenv("namespace");
		if (jsonCredentialsFile == null) {
			em = EntityManagerFactory.getInstance().createDefaultEntityManager(namespace);
		} else {
			em = EntityManagerFactory.getInstance().createEntityManager(projectId, jsonCredentialsFile, namespace);
		}
		System.out.println(
				String.format("Running tests against project %s with credentials from file %s using namespace %s",
						projectId, jsonCredentialsFile, namespace));
		return em;

	}

}
