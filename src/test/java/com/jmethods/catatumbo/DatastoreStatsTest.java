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

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;
import com.jmethods.catatumbo.impl.DefaultEntityManager;
import com.jmethods.catatumbo.stats.StatCompositeIndex;
import com.jmethods.catatumbo.stats.StatCompositeIndexBase;
import com.jmethods.catatumbo.stats.StatCompositeIndexNs;
import com.jmethods.catatumbo.stats.StatConstants;
import com.jmethods.catatumbo.stats.StatKind;
import com.jmethods.catatumbo.stats.StatKindBase;
import com.jmethods.catatumbo.stats.StatKindNs;
import com.jmethods.catatumbo.stats.StatTotal;
import com.jmethods.catatumbo.stats.StatTotalBase;
import com.jmethods.catatumbo.stats.StatTotalNs;

/**
 * @author Sai Pullabhotla
 *
 */
public class DatastoreStatsTest {
	private static EntityManager em = null;
	private static DatastoreStats stats = null;
	private static Datastore datastore = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		em = TestUtils.getEntityManager();
		stats = em.getDatastoreStats();
		datastore = ((DefaultEntityManager) em).getDatastore();
	}

	@Test
	public void testGetSummary() {
		StatTotal stat = stats.getSummary();
		com.google.cloud.datastore.Key key = datastore.newKeyFactory().setNamespace("")
				.setKind(StatConstants.STAT_TOTAL).newKey(StatConstants.ID_TOTAL_ENTITY_USAGE);
		com.google.cloud.datastore.Entity nativeEntity = datastore.get(key);
		assertTrue(equals(stat, nativeEntity));
	}

	@Test
	public void testGetSummaryNs() {
		StatTotalNs stat = stats.getSummaryNs();
		com.google.cloud.datastore.Key key = datastore.newKeyFactory().setKind(StatConstants.STAT_TOTAL_NS)
				.newKey(StatConstants.ID_TOTAL_ENTITY_USAGE);
		com.google.cloud.datastore.Entity nativeEntity = datastore.get(key);
		assertTrue(equals(stat, nativeEntity));
	}

	@Test
	public void testGetSummaryNs_ValidNamespace() {
		final String namespace = "junit";
		StatTotalNs stat = stats.getSummaryNs(namespace);
		com.google.cloud.datastore.Key key = datastore.newKeyFactory().setNamespace(namespace)
				.setKind(StatConstants.STAT_TOTAL_NS).newKey(StatConstants.ID_TOTAL_ENTITY_USAGE);
		com.google.cloud.datastore.Entity nativeEntity = datastore.get(key);
		assertTrue(equals(stat, nativeEntity));
	}

	@Test
	public void testGetSummaryNs_InvalidNamespace() {
		final String namespace = "junit9999999999";
		StatTotalNs stat = stats.getSummaryNs(namespace);
		com.google.cloud.datastore.Key key = datastore.newKeyFactory().setNamespace(namespace)
				.setKind(StatConstants.STAT_TOTAL_NS).newKey(StatConstants.ID_TOTAL_ENTITY_USAGE);
		com.google.cloud.datastore.Entity nativeEntity = datastore.get(key);
		assertNull(stat);
		assertNull(nativeEntity);
		assertTrue(equals(stat, nativeEntity));
	}

	@Test
	public void testGetKinds() {
		List<StatKind> statEntities = stats.getKinds();
		Query query = Query.newEntityQueryBuilder().setNamespace("").setKind(StatConstants.STAT_KIND).build();
		QueryResults<com.google.cloud.datastore.Entity> results = datastore.run(query);
		int i = 0;
		while (results.hasNext()) {
			StatKind statEntity = statEntities.get(i);
			com.google.cloud.datastore.Entity nativeEntity = results.next();
			assertTrue(equals(statEntity, nativeEntity));
			i++;
		}
	}

	@Test
	public void testGetKind() {
		final String kindName = "StringField";
		StatKind statEntity = stats.getKind(kindName);
		Query query = Query.newEntityQueryBuilder().setNamespace("").setKind(StatConstants.STAT_KIND)
				.setFilter(StructuredQuery.PropertyFilter.eq(StatConstants.PROP_KIND_NAME, kindName)).build();
		QueryResults<com.google.cloud.datastore.Entity> results = datastore.run(query);
		com.google.cloud.datastore.Entity nativeEntity = null;
		if (results.hasNext()) {
			nativeEntity = results.next();
		}
		assertTrue(equals(statEntity, nativeEntity));

	}

	@Test
	public void testGetKindsNs() {
		List<StatKindNs> statEntities = stats.getKindsNs();
		Query query = Query.newEntityQueryBuilder().setKind(StatConstants.STAT_KIND_NS).build();
		QueryResults<com.google.cloud.datastore.Entity> results = datastore.run(query);
		int i = 0;
		while (results.hasNext()) {
			StatKindNs statEntity = statEntities.get(i);
			com.google.cloud.datastore.Entity nativeEntity = results.next();
			assertTrue(equals(statEntity, nativeEntity));
			i++;
		}
	}

	@Test
	public void testGetKindNs_KindName() {
		final String kindName = "StringField";
		StatKindNs statEntity = stats.getKindNs(kindName);
		Query query = Query.newEntityQueryBuilder().setKind(StatConstants.STAT_KIND_NS)
				.setFilter(StructuredQuery.PropertyFilter.eq(StatConstants.PROP_KIND_NAME, kindName)).build();
		QueryResults<com.google.cloud.datastore.Entity> results = datastore.run(query);
		com.google.cloud.datastore.Entity nativeEntity = null;
		if (results.hasNext()) {
			nativeEntity = results.next();
		}
		assertTrue(equals(statEntity, nativeEntity));
	}

	@Test
	public void testGetKindNs_Namespace_KindName() {
		final String namespace = "junit";
		final String kindName = "StringField";
		StatKindNs statEntity = stats.getKindNs(namespace, kindName);
		Query query = Query.newEntityQueryBuilder().setNamespace(namespace).setKind(StatConstants.STAT_KIND_NS)
				.setFilter(StructuredQuery.PropertyFilter.eq(StatConstants.PROP_KIND_NAME, kindName)).build();
		QueryResults<com.google.cloud.datastore.Entity> results = datastore.run(query);
		com.google.cloud.datastore.Entity nativeEntity = null;
		if (results.hasNext()) {
			nativeEntity = results.next();
		}
		assertTrue(equals(statEntity, nativeEntity));
	}

	@Test
	public void testGetCompositeIndexes() {
		List<StatCompositeIndex> statEntities = stats.getCompositeIndexes();
		Query query = Query.newEntityQueryBuilder().setNamespace("").setKind(StatConstants.STAT_COMPOSITE_INDEX)
				.build();
		QueryResults<com.google.cloud.datastore.Entity> results = datastore.run(query);
		int i = 0;
		while (results.hasNext()) {
			StatCompositeIndex statEntity = statEntities.get(i);
			com.google.cloud.datastore.Entity nativeEntity = results.next();
			assertTrue(equals(statEntity, nativeEntity));
			i++;
		}
	}

	@Test
	public void testGetCompositeIndexesNs() {
		List<StatCompositeIndexNs> statEntities = stats.getCompositeIndexesNs();
		Query query = Query.newEntityQueryBuilder().setKind(StatConstants.STAT_COMPOSITE_INDEX_NS).build();
		QueryResults<com.google.cloud.datastore.Entity> results = datastore.run(query);
		int i = 0;
		while (results.hasNext()) {
			StatCompositeIndexNs statEntity = statEntities.get(i);
			com.google.cloud.datastore.Entity nativeEntity = results.next();
			assertTrue(equals(statEntity, nativeEntity));
			i++;
		}
	}

	@Test
	public void testGetCompositeIndexesNs_Namespace() {
		final String namespace = "junit";
		List<StatCompositeIndexNs> statEntities = stats.getCompositeIndexesNs(namespace);
		Query query = Query.newEntityQueryBuilder().setNamespace(namespace)
				.setKind(StatConstants.STAT_COMPOSITE_INDEX_NS).build();
		QueryResults<com.google.cloud.datastore.Entity> results = datastore.run(query);
		int i = 0;
		while (results.hasNext()) {
			StatCompositeIndexNs statEntity = statEntities.get(i);
			com.google.cloud.datastore.Entity nativeEntity = results.next();
			assertTrue(equals(statEntity, nativeEntity));
			i++;
		}
	}

	private static boolean equals(StatTotalBase stat, com.google.cloud.datastore.Entity entity) {
		if (stat != null && entity != null) {
			return stat.getBuiltinIndexBytes() == entity.getLong(StatConstants.PROP_BUILTIN_INDEX_BYTES)
					&& stat.getBuiltinIndexCount() == entity.getLong(StatConstants.PROP_BUILTIN_INDEX_COUNT)
					&& stat.getBytes() == entity.getLong(StatConstants.PROP_BYTES)
					&& stat.getCompositeIndexBytes() == entity.getLong(StatConstants.PROP_COMPOSITE_INDEX_BYTES)
					&& stat.getCompositeIndexCount() == entity.getLong(StatConstants.PROP_COMPOSITE_INDEX_COUNT)
					&& stat.getCount() == entity.getLong(StatConstants.PROP_COUNT)
					&& stat.getEntityBytes() == entity.getLong(StatConstants.PROP_ENTITY_BYTES)
					&& equals(stat.getTimestamp(), entity.getTimestamp(StatConstants.PROP_TIMESTAMP));
		}
		return stat == null && entity == null;
	}

	private static boolean equals(StatKindBase stat, com.google.cloud.datastore.Entity entity) {
		if (stat != null && entity != null) {
			return stat.getBuiltinIndexBytes() == entity.getLong(StatConstants.PROP_BUILTIN_INDEX_BYTES)
					&& stat.getBuiltinIndexCount() == entity.getLong(StatConstants.PROP_BUILTIN_INDEX_COUNT)
					&& stat.getBytes() == entity.getLong(StatConstants.PROP_BYTES)
					&& stat.getCompositeIndexBytes() == entity.getLong(StatConstants.PROP_COMPOSITE_INDEX_BYTES)
					&& stat.getCompositeIndexCount() == entity.getLong(StatConstants.PROP_COMPOSITE_INDEX_COUNT)
					&& stat.getCount() == entity.getLong(StatConstants.PROP_COUNT)
					&& stat.getEntityBytes() == entity.getLong(StatConstants.PROP_ENTITY_BYTES)
					&& stat.getKindName().equals(entity.getString(StatConstants.PROP_KIND_NAME))
					&& equals(stat.getTimestamp(), entity.getTimestamp(StatConstants.PROP_TIMESTAMP));
		}
		return stat == null && entity == null;
	}

	private static boolean equals(StatCompositeIndexBase stat, com.google.cloud.datastore.Entity entity) {
		if (stat != null && entity != null) {
			return stat.getBytes() == entity.getLong(StatConstants.PROP_BYTES)
					&& stat.getCount() == entity.getLong(StatConstants.PROP_COUNT)
					&& stat.getIndexId() == entity.getLong(StatConstants.PROP_INDEX_ID)
					&& stat.getKindName().equals(entity.getString(StatConstants.PROP_KIND_NAME))
					&& stat.getKindName().equals(entity.getString(StatConstants.PROP_KIND_NAME))
					&& equals(stat.getTimestamp(), entity.getTimestamp(StatConstants.PROP_TIMESTAMP));

		}
		return stat == null && entity == null;
	}

	private static boolean equals(Date date, Timestamp timestamp) {
		long ms1 = date.getTime();
		long ms2 = TimeUnit.SECONDS.toMillis(timestamp.getSeconds())
				+ TimeUnit.NANOSECONDS.toMillis(timestamp.getNanos());
		return ms1 == ms2;
	}

}
