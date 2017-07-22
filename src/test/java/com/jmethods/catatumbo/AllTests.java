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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.jmethods.catatumbo.entities.Task;

/**
 * @author Sai Pullabhotla
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ CustomTypeTest.class, DatastoreBatchTest.class, DatastoreMetadataTest.class, DatastoreStatsTest.class,
		DatastoreTransactionTest.class, DecimalFieldsTest.class, DefaultAndExternalListenersTest.class,
		DefaultListenerTest.class, EmbeddedCollectionTest.class, EntityManagerFactoryTest.class,
		EntityManagerTest.class, ExcludeDefaultListenerTest.class, ExternalListenerTest.class, IndexerFactoryTest.class,
		InternalListenerTest.class, ListenerInheritanceTest.class, MapperFactoryTest.class, OptionalFieldsTest.class,
		SecondaryIndexTest.class, TenantTest.class, TwoDefaultListenersTest.class, TwoExternalListenersTest.class })

public class AllTests {

	private static EntityManager em;

	@BeforeClass
	public static void clearDatastore() throws Exception {
		em = TestUtils.getEntityManager();
		em.getDatastoreMetadata().getKinds(true).forEach(em::deleteAll);
		populateTasks();
	}

	private static void populateTasks() {
		List<Task> tasks = new ArrayList<>(50);
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		for (int i = 1; i <= 50; i++) {
			Task task = new Task();
			task.setId(i);
			task.setName("My Task " + i);
			task.setPriority(i % 5);
			task.setComplete(i % 10 == 0);
			Calendar cal = (Calendar) today.clone();
			cal.add(Calendar.DATE, i % 5);
			task.setCompletionDate(cal.getTime());
			tasks.add(task);
		}
		em.insert(tasks);
	}

}
