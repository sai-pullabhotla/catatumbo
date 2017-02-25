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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.cloud.datastore.FullEntity;
import com.jmethods.catatumbo.TestUtils;
import com.jmethods.catatumbo.entities.Contact;
import com.jmethods.catatumbo.entities.Customer;
import com.jmethods.catatumbo.impl.Marshaller.Intent;

/**
 * @author Sai Pullabhotla
 *
 */
public class MarshallerTest {

	private static DefaultEntityManager em;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		em = (DefaultEntityManager) TestUtils.getEntityManager();
	}

	@Test
	public void testMarshal_Embedded() {
		Customer customer = Customer.createSampleCustomer2();
		FullEntity<?> entity = (FullEntity<?>) Marshaller.marshal(em, customer, Intent.INSERT);
		assertNull(entity.getString("ba_line1"));
		assertNull(entity.getString("ba_line2"));
		assertNull(entity.getString("ba_zip"));
		assertNull(entity.getString("ba_zipx"));
		assertNull(entity.getString("ba_zip"));
	}

	@Test
	public void testMarshal_Embedded_Imploded() {
		Contact contact = Contact.createContact1();
		FullEntity<?> entity = (FullEntity<?>) Marshaller.marshal(em, contact, Intent.INSERT);
		assertNull(entity.getValue("cellNumber").get());
		assertNull(entity.getValue("homeAddress").get());
	}

	@Test
	public void testMarshal_Embedded_Imploded2() {
		Contact contact = Contact.createContact2();
		FullEntity<?> entity = (FullEntity<?>) Marshaller.marshal(em, contact, Intent.INSERT);
		assertEquals("55555", entity.getEntity("homeAddress").getEntity("postal_code").getString("zip"));
	}

}
