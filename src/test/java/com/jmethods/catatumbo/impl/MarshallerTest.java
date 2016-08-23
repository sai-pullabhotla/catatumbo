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

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.cloud.AuthCredentials;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.FullEntity;
import com.jmethods.catatumbo.entities.Customer;

/**
 * @author Sai Pullabhotla
 *
 */
public class MarshallerTest {

	private static Datastore datastore;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		AuthCredentials authCredentials = AuthCredentials.createApplicationDefaults();
		DatastoreOptions.Builder datastoreOptionsBuilder = DatastoreOptions.builder().authCredentials(authCredentials);
		datastore = datastoreOptionsBuilder.build().service();
	}

	@Test
	public void testMarshal_Embedded() {
		Customer customer = Customer.SAMPLE_CUSTOMER2;
		FullEntity<?> entity = (FullEntity<?>) Marshaller.marshal(datastore, customer);
		System.out.println(entity);
	}

}
