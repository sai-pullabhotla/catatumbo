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

import java.math.BigDecimal;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jmethods.catatumbo.entities.DecimalFields;

/**
 * @author Sai Pullabhotla
 *
 */
public class DecimalFieldsTest {
	private static EntityManager em;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		em = TestUtils.getEntityManager();
		em.deleteAll(DecimalFields.class);
	}

	@Test
	public void testInsert_n10_1() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("5");
		entity.setN10(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(n, entity.getN10());
	}

	@Test
	public void testInsert_n10_2() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("5.00");
		entity.setN10(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("5"), entity.getN10());
	}

	@Test
	public void testInsert_n10_3() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("-5");
		entity.setN10(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(n, entity.getN10());
	}

	@Test
	public void testInsert_n10_4() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("-5.0000");
		entity.setN10(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("-5"), entity.getN10());
	}

	@Test(expected = MappingException.class)
	public void testInsert_n10_5() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("0.1");
		entity.setN10(n);
		try {
			entity = em.insert(entity);
		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}

	@Test
	public void testInsert_n11_1() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal(".5");
		entity.setN11(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("0.5"), entity.getN11());
	}

	@Test
	public void testInsert_n11_2() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal(".900");
		entity.setN11(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("0.9"), entity.getN11());
	}

	@Test(expected = MappingException.class)
	public void testInsert_n11_3() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("1");
		entity.setN11(n);
		try {
			entity = em.insert(entity);
		} catch (Exception exp) {
			System.err.println(exp);
			throw exp;
		}
	}

	@Test
	public void testInsert_n50_1() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("99999");
		entity.setN50(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("99999"), entity.getN50());
	}

	@Test
	public void testInsert_n50_2() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("99");
		entity.setN50(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("99"), entity.getN50());
	}

	@Test
	public void testInsert_n50_3() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("99.000");
		entity.setN50(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("99"), entity.getN50());
	}

	@Test(expected = MappingException.class)
	public void testInsert_n50_4() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("99.9");
		entity.setN50(n);
		try {
			entity = em.insert(entity);
		} catch (Exception exp) {
			System.err.println(exp);
			throw exp;
		}
	}

	@Test
	public void testInsert_n73_1() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("9999.999");
		entity.setN73(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("9999.999"), entity.getN73());
	}

	@Test
	public void testInsert_n73_2() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("1.999");
		entity.setN73(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("1.999"), entity.getN73());
	}

	@Test
	public void testInsert_n73_3() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("29.95");
		entity.setN73(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("29.950"), entity.getN73());
	}

	@Test
	public void testInsert_n73_4() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("29.95900");
		entity.setN73(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("29.959"), entity.getN73());
	}

	@Test
	public void testInsert_n73_5() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("0.001");
		entity.setN73(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("0.001"), entity.getN73());
	}

	@Test
	public void testInsert_n73_6() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("-0.1");
		entity.setN73(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("-0.100"), entity.getN73());
	}

	@Test
	public void testInsert_n73_7() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("-9999.999");
		entity.setN73(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("-9999.999"), entity.getN73());
	}

	@Test(expected = MappingException.class)
	public void testInsert_n73_8() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("-0.5432");
		entity.setN73(n);
		try {
			entity = em.insert(entity);
		} catch (Exception exp) {
			System.err.println(exp);
			throw exp;
		}
	}

	@Test
	public void testInsert_n180_1() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("999999999999999999");
		entity.setN180(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("999999999999999999"), entity.getN180());
	}

	@Test
	public void testInsert_n180_2() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("999999999999999999.000000000000");
		entity.setN180(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("999999999999999999"), entity.getN180());
	}

	@Test(expected = MappingException.class)
	public void testInsert_n180_3() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("9999999999999999999");
		entity.setN180(n);
		try {
			entity = em.insert(entity);
		} catch (Exception exp) {
			System.err.println(exp);
			throw exp;
		}
	}

	@Test
	public void testInsert_n1818_1() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("0.000000000000000001");
		entity.setN1818(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("0.000000000000000001"), entity.getN1818());
	}

	@Test
	public void testInsert_n1818_2() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("0");
		entity.setN1818(n);
		entity = em.insert(entity);
		entity = em.load(DecimalFields.class, entity.getId());
		assertEquals(new BigDecimal("0.000000000000000000"), entity.getN1818());
	}

	@Test(expected = MappingException.class)
	public void testInsert_n1818_3() {
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("0.0000000000000000002");
		entity.setN1818(n);
		try {
			entity = em.insert(entity);
		} catch (Exception exp) {
			System.err.println(exp);
			throw exp;
		}
	}

	@Test
	public void testQuery_1() {
		// This test may fail at time, because of eventual consistency. Need to
		// figure out a better way.
		DecimalFields entity = new DecimalFields();
		BigDecimal n = new BigDecimal("0.000000000000000001");
		entity.setN1818(n);
		entity = em.insert(entity);
		EntityQueryRequest request = em.createEntityQueryRequest("SELECT * FROM DecimalFields WHERE __key__=@1");
		request.addPositionalBinding(entity.getKey());
		QueryResponse<DecimalFields> response = em.executeEntityQueryRequest(DecimalFields.class, request);
		List<DecimalFields> entities = response.getResults();
		assertEquals(1, entities.size());
		assertEquals(new BigDecimal("0.000000000000000001"), entity.getN1818());

	}

}
