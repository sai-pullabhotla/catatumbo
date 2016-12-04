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

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jmethods.catatumbo.indexers.LowerCaseStringIndexer;
import com.jmethods.catatumbo.indexers.UpperCaseStringListIndexer;

/**
 * @author Sai Pullabhotla
 *
 */
public class IndexerFactoryTest {

	@Test
	public void testGetIndexer_LowerCaseStringIndexer() {
		Indexer i1 = IndexerFactory.getInstance().getIndexer(LowerCaseStringIndexer.class);
		Indexer i2 = IndexerFactory.getInstance().getIndexer(LowerCaseStringIndexer.class);
		assertTrue(i1 == i2);
	}

	@Test
	public void testGetIndexer_UpperCaseSringListIndexer() {
		Indexer i1 = IndexerFactory.getInstance().getIndexer(UpperCaseStringListIndexer.class);
		Indexer i2 = IndexerFactory.getInstance().getIndexer(UpperCaseStringListIndexer.class);
		assertTrue(i1 == i2);
	}

}
