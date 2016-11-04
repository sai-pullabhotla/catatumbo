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

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jmethods.catatumbo.entities.CustomTypeEntity;
import com.jmethods.catatumbo.mappers.ByteMapper;

/**
 * @author Sai Pullabhotla
 *
 */

public class CustomTypeTest {

	private static EntityManager em = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MapperFactory.getInstance().setDefaultMapper(byte.class, new ByteMapper());
		em = TestUtils.getEntityManager();
		em.deleteAll(CustomTypeEntity.class);
	}

	@Test
	public void testInsert() {
		CustomTypeEntity entity = new CustomTypeEntity();
		entity.setByteField((byte) 65);
		entity = em.insert(entity);
		assertEquals((byte) 65, entity.getByteField());
	}

}
