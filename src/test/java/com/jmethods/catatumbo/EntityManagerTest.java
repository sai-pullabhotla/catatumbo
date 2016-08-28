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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jmethods.catatumbo.entities.Account;
import com.jmethods.catatumbo.entities.BooleanField;
import com.jmethods.catatumbo.entities.BooleanObject;
import com.jmethods.catatumbo.entities.ByteArrayField;
import com.jmethods.catatumbo.entities.CalendarField;
import com.jmethods.catatumbo.entities.CharArrayField;
import com.jmethods.catatumbo.entities.CharField;
import com.jmethods.catatumbo.entities.CharObject;
import com.jmethods.catatumbo.entities.ChildEntity;
import com.jmethods.catatumbo.entities.Country;
import com.jmethods.catatumbo.entities.Customer;
import com.jmethods.catatumbo.entities.DateField;
import com.jmethods.catatumbo.entities.Department;
import com.jmethods.catatumbo.entities.DoubleField;
import com.jmethods.catatumbo.entities.DoubleObject;
import com.jmethods.catatumbo.entities.Employee;
import com.jmethods.catatumbo.entities.FloatField;
import com.jmethods.catatumbo.entities.FloatObject;
import com.jmethods.catatumbo.entities.GenericListField;
import com.jmethods.catatumbo.entities.GeoLocationField;
import com.jmethods.catatumbo.entities.GrandchildEntity;
import com.jmethods.catatumbo.entities.IgnoreField;
import com.jmethods.catatumbo.entities.IgnoreField2;
import com.jmethods.catatumbo.entities.IntegerField;
import com.jmethods.catatumbo.entities.IntegerObject;
import com.jmethods.catatumbo.entities.KeyListField;
import com.jmethods.catatumbo.entities.LongField;
import com.jmethods.catatumbo.entities.LongId;
import com.jmethods.catatumbo.entities.LongId2;
import com.jmethods.catatumbo.entities.LongListField;
import com.jmethods.catatumbo.entities.LongObject;
import com.jmethods.catatumbo.entities.ParentEntity;
import com.jmethods.catatumbo.entities.ShortField;
import com.jmethods.catatumbo.entities.ShortObject;
import com.jmethods.catatumbo.entities.StringField;
import com.jmethods.catatumbo.entities.StringId;
import com.jmethods.catatumbo.entities.StringId2;
import com.jmethods.catatumbo.entities.StringListField;
import com.jmethods.catatumbo.entities.SubClass1;
import com.jmethods.catatumbo.entities.SubClass2;
import com.jmethods.catatumbo.entities.SubClass3;
import com.jmethods.catatumbo.entities.SubClass4;
import com.jmethods.catatumbo.entities.Tag;
import com.jmethods.catatumbo.entities.Task;
import com.jmethods.catatumbo.entities.TaskName;

/**
 * @author Sai Pullabhotla
 *
 */
public class EntityManagerTest {

	private static EntityManager em;
	private static Random random = new Random();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
		em.deleteAll(LongId.class);
		em.deleteAll(StringId.class);
		em.deleteAll(LongId2.class);
		em.deleteAll(StringId2.class);
		em.deleteAll(BooleanField.class);
		em.deleteAll(BooleanObject.class);
		em.deleteAll(CharField.class);
		em.deleteAll(CharObject.class);
		em.deleteAll(ShortField.class);
		em.deleteAll(ShortObject.class);
		em.deleteAll(IntegerField.class);
		em.deleteAll(IntegerObject.class);
		em.deleteAll(LongField.class);
		em.deleteAll(LongObject.class);
		em.deleteAll(FloatField.class);
		em.deleteAll(FloatObject.class);
		em.deleteAll(DoubleField.class);
		em.deleteAll(DoubleObject.class);
		em.deleteAll(StringField.class);
		em.deleteAll(CalendarField.class);
		em.deleteAll(DateField.class);
		em.deleteAll(ByteArrayField.class);
		em.deleteAll(CharArrayField.class);
		em.deleteAll(StringListField.class);
		em.deleteAll(LongListField.class);
		em.deleteAll(ParentEntity.class);
		em.deleteAll(ChildEntity.class);
		em.deleteAll(GrandchildEntity.class);
		em.deleteAll(GeoLocationField.class);
		em.deleteAll(Task.class);
		em.deleteAll(IgnoreField.class);
		em.deleteAll(Country.class);
		em.deleteAll(Account.class);
		em.deleteAll(Department.class);
		em.deleteAll(Employee.class);
		em.deleteAll(Tag.class);
		em.deleteAll(KeyListField.class);
		em.deleteAll(GenericListField.class);
		em.deleteAll(Customer.class);
		em.deleteAll(SubClass1.class);
		em.deleteAll(SubClass2.class);
		em.deleteAll(SubClass3.class);
		em.deleteAll(SubClass4.class);
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

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInsertLongId_AutoGenerated() {
		LongId entity = new LongId();
		entity.setField1("Hello World!");
		entity = em.insert(entity);
		assertNotEquals(0, entity.getId());
	}

	@Test
	public void testInsertLongId_Preset() {
		LongId entity = new LongId();
		long id = random.nextLong();
		entity.setId(id);
		entity.setField1("Hello World!");
		entity = em.insert(entity);
		assertEquals(id, entity.getId());
	}

	@Test(expected = EntityManagerException.class)
	public void testInsertLongId2() {
		try {
			LongId2 entity = new LongId2();
			entity.setField1("Hello World!");
			entity = em.insert(entity);
		} catch (EntityManagerException exp) {
			exp.printStackTrace();
			throw exp;
		}
	}

	@Test
	public void testInsertStringId_AutoGenerated() {
		StringId entity = new StringId();
		entity.setGreetings("Good Morning!");
		entity = em.insert(entity);
		assertNotNull(entity.getId());
	}

	@Test
	public void testInsertStringId_Preset() {
		StringId entity = new StringId();
		String id = "greeting1";
		entity.setId(id);
		entity.setGreetings("Good Morning!");
		entity = em.insert(entity);
		assertEquals(id, entity.getId());
	}

	@Test(expected = EntityManagerException.class)
	public void testInsertStringId2() {
		StringId2 entity = new StringId2();
		entity = em.insert(entity);
	}

	@Test
	public void testInsertBooleanField_True() {
		BooleanField entity = new BooleanField();
		entity.setAwesome(true);
		entity = em.insert(entity);
		assertTrue(entity.getId() > 0L && entity.isAwesome());
	}

	@Test
	public void testInsertBooleanField_False() {
		BooleanField entity = new BooleanField();
		entity.setAwesome(false);
		entity = em.insert(entity);
		assertTrue(entity.getId() > 0L && !entity.isAwesome());
	}

	@Test
	public void testInsertBooleanObject_True() {
		BooleanObject entity = new BooleanObject();
		entity.setAwesome(true);
		entity = em.insert(entity);
		assertTrue(entity.getId() > 0L && Boolean.TRUE.equals(entity.getAwesome()));
	}

	@Test
	public void testInsertBooleanObject_False() {
		BooleanObject entity = new BooleanObject();
		entity.setAwesome(false);
		entity = em.insert(entity);
		assertTrue(entity.getId() > 0L && Boolean.FALSE.equals(entity.getAwesome()));
	}

	@Test
	public void testInsertBooleanObject_Null() {
		BooleanObject entity = new BooleanObject();
		entity = em.insert(entity);
		assertTrue(entity.getId() > 0L && entity.getAwesome() == null);
	}

	@Test
	public void testInsertCharField() {
		CharField entity = new CharField();
		entity.setSex('M');
		entity = em.insert(entity);
		entity = em.load(CharField.class, entity.getId());
		assertTrue(entity.getId() > 0L && entity.getSex() == 'M');
	}

	@Test
	public void testInsertCharObject() {
		CharObject entity = new CharObject();
		entity.setSex('F');
		entity = em.insert(entity);
		entity = em.load(CharObject.class, entity.getId());
		assertTrue(entity.getId() > 0L && entity.getSex() == 'F');
	}

	@Test
	public void testInsertCharObject_Null() {
		CharObject entity = new CharObject();
		entity = em.insert(entity);
		entity = em.load(CharObject.class, entity.getId());
		assertTrue(entity.getId() > 0L && entity.getSex() == null);
	}

	@Test
	public void testInsertShortField() {
		ShortField entity = new ShortField();
		entity.setAge((short) 25);
		entity = em.insert(entity);
		entity = em.load(ShortField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getAge() == 25);
	}

	@Test
	public void testInsertShortObject() {
		ShortObject entity = new ShortObject();
		entity.setAge(new Short((short) 25));
		entity = em.insert(entity);
		entity = em.load(ShortObject.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getAge() == 25);
	}

	@Test
	public void testInsertShortObject_Null() {
		ShortObject entity = new ShortObject();
		entity = em.insert(entity);
		entity = em.load(ShortObject.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getAge() == null);
	}

	@Test
	public void testInsertIntegerField() {
		IntegerField entity = new IntegerField();
		entity.setCount(3456789);
		entity = em.insert(entity);
		entity = em.load(IntegerField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getCount() == 3456789);
	}

	@Test
	public void testInsertIntegerField_Negative_Value() {
		IntegerField entity = new IntegerField();
		entity.setCount(-3456789);
		entity = em.insert(entity);
		entity = em.load(IntegerField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getCount() == -3456789);
	}

	@Test
	public void testInsertIntegerObject() {
		IntegerObject entity = new IntegerObject();
		entity.setCount(new Integer(3456789));
		entity = em.insert(entity);
		entity = em.load(IntegerObject.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getCount() == 3456789);
	}

	@Test
	public void testInsertIntegerObject_Null() {
		IntegerObject entity = new IntegerObject();
		entity = em.insert(entity);
		entity = em.load(IntegerObject.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getCount() == null);
	}

	@Test
	public void testInsertLongField() {
		LongField entity = new LongField();
		entity.setDistanceFromEarth(2400000000000000L);
		entity = em.insert(entity);
		entity = em.load(LongField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getDistanceFromEarth() == 2400000000000000L);
	}

	@Test
	public void testInsertLongObject() {
		LongObject entity = new LongObject();
		entity.setDistanceFromEarth(2400000000000000L);
		entity = em.insert(entity);
		entity = em.load(LongObject.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getDistanceFromEarth() == 2400000000000000L);
	}

	@Test
	public void testInsertLongObject_Null() {
		LongObject entity = new LongObject();
		entity = em.insert(entity);
		entity = em.load(LongObject.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getDistanceFromEarth() == null);
	}

	@Test
	public void testInsertFloatField() {
		FloatField entity = new FloatField();
		entity.setArea(Float.MAX_VALUE);
		entity = em.insert(entity);
		entity = em.load(FloatField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getArea() == Float.MAX_VALUE);
	}

	@Test
	public void testInsertFloatField_Negative() {
		FloatField entity = new FloatField();
		entity.setArea(Float.MIN_VALUE);
		entity = em.insert(entity);
		entity = em.load(FloatField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getArea() == Float.MIN_VALUE);
	}

	@Test
	public void testInsertFloatObject() {
		FloatObject entity = new FloatObject();
		entity.setArea(Float.valueOf(Float.MAX_VALUE));
		entity = em.insert(entity);
		entity = em.load(FloatObject.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getArea() == Float.MAX_VALUE);
	}

	@Test
	public void testInsertFloatObject_Negative() {
		FloatObject entity = new FloatObject();
		entity.setArea(Float.valueOf(Float.MIN_VALUE));
		entity = em.insert(entity);
		entity = em.load(FloatObject.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getArea() == Float.MIN_VALUE);
	}

	@Test
	public void testInsertFloatObject_Null() {
		FloatObject entity = new FloatObject();
		entity = em.insert(entity);
		entity = em.load(FloatObject.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getArea() == null);
	}

	@Test
	public void testInsertDoubleField() {
		DoubleField entity = new DoubleField();
		entity.setArea(Double.MAX_VALUE);
		entity = em.insert(entity);
		entity = em.load(DoubleField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getArea() == Double.MAX_VALUE);
	}

	@Test
	public void testInsertDoubleField_Negative() {
		DoubleField entity = new DoubleField();
		entity.setArea(Double.MIN_VALUE);
		entity = em.insert(entity);
		entity = em.load(DoubleField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getArea() == Double.MIN_VALUE);
	}

	@Test
	public void testInsertDoubleObject() {
		DoubleObject entity = new DoubleObject();
		entity.setArea(Double.MAX_VALUE);
		entity = em.insert(entity);
		entity = em.load(DoubleObject.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getArea() == Double.MAX_VALUE);
	}

	@Test
	public void testInsertDoubleObject_Negative() {
		DoubleObject entity = new DoubleObject();
		entity.setArea(Double.MIN_VALUE);
		entity = em.insert(entity);
		entity = em.load(DoubleObject.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getArea() == Double.MIN_VALUE);
	}

	@Test
	public void testInsertDoubleObject_Null() {
		DoubleObject entity = new DoubleObject();
		entity = em.insert(entity);
		entity = em.load(DoubleObject.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getArea() == null);
	}

	@Test
	public void testInsertStringField() {
		StringField entity = new StringField();
		String name = "Google Cloud Datastore";
		entity.setName(name);
		entity = em.insert(entity);
		entity = em.load(StringField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getName().equals(name));
	}

	@Test
	public void testInsertStringField_Blank() {
		StringField entity = new StringField();
		String name = "";
		entity.setName(name);
		entity = em.insert(entity);
		entity = em.load(StringField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getName().equals(name));
	}

	@Test
	public void testInsertStringField_Spaces() {
		StringField entity = new StringField();
		String name = "   ";
		entity.setName(name);
		entity = em.insert(entity);
		entity = em.load(StringField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getName().equals(name));
	}

	@Test
	public void testInsertStringField_Null() {
		StringField entity = new StringField();
		entity = em.insert(entity);
		entity = em.load(StringField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getName() == null);
	}

	@Test
	public void testInsertCalendarField() {
		CalendarField entity = new CalendarField();
		Calendar now = Calendar.getInstance();
		entity.setCreationDate(now);
		entity = em.insert(entity);
		entity = em.load(CalendarField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getCreationDate().getTimeInMillis() == now.getTimeInMillis());
	}

	@Test
	public void testInsertCalendarField_GMT() {
		CalendarField entity = new CalendarField();
		Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		entity.setCreationDate(now);
		entity = em.insert(entity);
		entity = em.load(CalendarField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getCreationDate().getTimeInMillis() == now.getTimeInMillis());
	}

	@Test
	public void testInsertCalendarField_Null() {
		CalendarField entity = new CalendarField();
		// Calendar now = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		// entity.setCreationDate(now);
		entity = em.insert(entity);
		entity = em.load(CalendarField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getCreationDate() == null);
	}

	@Test
	public void testInsertDateField() {
		DateField entity = new DateField();
		Date now = new Date();
		entity.setCreationDate(now);
		entity = em.insert(entity);
		entity = em.load(DateField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getCreationDate().getTime() == now.getTime());
	}

	@Test
	public void testInsertDateField_Future() {
		DateField entity = new DateField();
		long currentTime = System.currentTimeMillis();
		long hundredYearsFromNow = currentTime + 100L * 366L * 24L * 60L * 60L * 1000L;
		Date date = new Date(hundredYearsFromNow);
		entity.setCreationDate(date);
		entity = em.insert(entity);
		entity = em.load(DateField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getCreationDate().getTime() == date.getTime());
	}

	@Test
	public void testInsertDateField_Null() {
		DateField entity = new DateField();
		entity = em.insert(entity);
		entity = em.load(DateField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getCreationDate() == null);
	}

	@Test
	public void testInsertByteArrayField() {
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		ByteArrayField entity = new ByteArrayField();
		entity.setSalt(salt);
		entity = em.insert(entity);
		entity = em.load(ByteArrayField.class, entity.getId());
		assertTrue(entity.getId() > 0 && Arrays.equals(entity.getSalt(), salt));
	}

	@Test
	public void testInsertByteArrayField_Null() {
		ByteArrayField entity = new ByteArrayField();
		entity = em.insert(entity);
		entity = em.load(ByteArrayField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getSalt() == null);
	}

	@Test
	public void testInsertCharArrayField() {
		CharArrayField entity = new CharArrayField();
		char[] password = "secret".toCharArray();
		entity.setPassword(password);
		entity = em.insert(entity);
		entity = em.load(CharArrayField.class, entity.getId());
		assertTrue(entity.getId() > 0 && Arrays.equals(password, entity.getPassword()));
	}

	@Test
	public void testInsertCharArrayField_Blank() {
		CharArrayField entity = new CharArrayField();
		char[] password = "".toCharArray();
		entity.setPassword(password);
		entity = em.insert(entity);
		entity = em.load(CharArrayField.class, entity.getId());
		assertTrue(entity.getId() > 0 && Arrays.equals(password, entity.getPassword()));
	}

	@Test
	public void testInsertCharArrayField_Null() {
		CharArrayField entity = new CharArrayField();
		entity = em.insert(entity);
		entity = em.load(CharArrayField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getPassword() == null);
	}

	@Test
	public void testInsertStringListField() {
		List<String> hobbies = new ArrayList<>();
		hobbies.add("Tennis");
		hobbies.add("Stamp Collection");
		hobbies.add("Long Drives");
		StringListField entity = new StringListField();
		entity.setHobbies(hobbies);
		entity = em.insert(entity);
		entity = em.load(StringListField.class, entity.getId());
		assertTrue(entity.getId() > 0 && hobbies.equals(entity.getHobbies()));
	}

	@Test
	public void testInsertStringListField_Null() {
		StringListField entity = new StringListField();
		entity = em.insert(entity);
		entity = em.load(StringListField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getHobbies() == null);
	}

	@Test
	public void testInsertLongListField() {
		List<Long> numbers = new ArrayList<>();
		numbers.add(Long.MIN_VALUE);
		numbers.add(0L);
		numbers.add(Long.MAX_VALUE);
		LongListField entity = new LongListField();
		entity.setNumbers(numbers);
		entity = em.insert(entity);
		entity = em.load(LongListField.class, entity.getId());
		assertTrue(entity.getId() > 0 && numbers.equals(entity.getNumbers()));
	}

	@Test
	public void testInsertKeyListField() {
		List<Tag> tags = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Tag tag = new Tag();
			tag.setName("tag" + i);
			tags.add(tag);
		}
		tags = em.insert(tags);
		KeyListField entity = new KeyListField();
		List<DatastoreKey> keys = new ArrayList<>();
		keys.add(tags.get(1).getKey());
		keys.add(tags.get(4).getKey());
		keys.add(tags.get(3).getKey());
		entity.setTags(keys);
		entity = em.insert(entity);
		entity = em.load(KeyListField.class, entity.getId());
		assertTrue(keys.equals(entity.getTags()));
	}

	@Test
	public void testInsertBooleanListField() {
		GenericListField entity = new GenericListField();
		List<Boolean> booleanList = new ArrayList<>();
		booleanList.add(true);
		booleanList.add(false);
		entity.setItems(booleanList);
		entity = em.insert(entity);
		entity = em.load(GenericListField.class, entity.getId());
		assertTrue(booleanList.equals(entity.getItems()));
	}

	@Test
	public void testInsertDoubleListField() {
		GenericListField entity = new GenericListField();
		List<Double> doubleList = new ArrayList<>();
		doubleList.add(1.5);
		doubleList.add(3.14);
		entity.setItems(doubleList);
		entity = em.insert(entity);
		entity = em.load(GenericListField.class, entity.getId());
		assertTrue(doubleList.equals(entity.getItems()));
	}

	@Test
	public void testInsertMixedListField() {
		Tag tag = new Tag();
		tag.setName("super tag");
		tag = em.insert(tag);

		GenericListField entity = new GenericListField();
		List items = new ArrayList<>();
		items.add("Catatumbo");
		items.add(512L);
		items.add(3.14);
		items.add(false);
		items.add(tag.getKey());
		entity.setItems(items);
		entity = em.insert(entity);
		entity = em.load(GenericListField.class, entity.getId());
		assertTrue(items.equals(entity.getItems()));
	}

	@Test
	public void testInsertKeyReference() {
		Department department = new Department();
		department.setName("Sales");
		department = em.insert(department);

		Country country = new Country();
		country.setName("United States");
		country = em.insert(country);

		Employee employee = new Employee();
		employee.setName("Sai Pullabhotla");
		employee.setDepartment(department.getFullKey());
		employee.setCountry(country.getKey());
		employee = em.insert(employee);
		assertTrue(employee.getId() > 0 && employee.getDepartment().equals(department.getFullKey()));
	}

	@Test
	public void testInsertParentChild() {
		ParentEntity parent = new ParentEntity();
		parent.setField1("I'm parent!");
		parent = em.insert(parent);

		ChildEntity child = new ChildEntity();
		child.setField1("I'm child!");
		child.setParentKey(parent.getKey());
		child = em.insert(child);

		GrandchildEntity grandchild = new GrandchildEntity();
		grandchild.setField1("I'm grangchild");
		grandchild.setParentKey(child.getKey());
		grandchild = em.insert(grandchild);

		assertTrue(child.getParentKey().equals(parent.getKey()) && grandchild.getParentKey().equals(child.getKey()));
	}

	@Test
	public void testInsert_GeoLocationField() {
		GeoLocationField ny = GeoLocationField.NEW_YORK_CITY;
		ny = em.insert(ny);
		GeoLocationField entity = em.load(GeoLocationField.class, ny.getId());
		assertTrue(entity.getId() != 0 && entity.getCity().equals(ny.getCity())
				&& entity.getCoordinates().equals(ny.getCoordinates()));
	}

	@Test
	public void testInsert_GeoLocationField_Null() {
		GeoLocationField entity = new GeoLocationField();
		entity.setCity("NULL CITY");
		entity = em.insert(entity);
		entity = em.load(GeoLocationField.class, entity.getId());
		assertNull(entity.getCoordinates());
	}

	@Test
	public void testInsert_IgnoredField() {
		IgnoreField entity = new IgnoreField();
		entity.setName("John Doe");
		entity.setComputed("This should not be persisted");
		entity = em.insert(entity);
		entity = em.load(IgnoreField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getName().equals("John Doe") && entity.getComputed() == null);
	}

	@Test(expected = EntityManagerException.class)
	public void testUpdateLongId_AutoGenerateTrue() {
		LongId entity = new LongId();
		entity.setField1("Update entity that does not exist");
		em.update(entity);
	}

	@Test(expected = EntityManagerException.class)
	public void testUpdateLongId_AutoGenerateFalse() {
		LongId2 entity = new LongId2();
		entity.setField1("Update entity that does not exist");
		em.update(entity);
	}

	@Test(expected = EntityManagerException.class)
	public void testUpdateStringId_AutoGenerateTrue() {
		StringId entity = new StringId();
		entity.setGreetings("Update entity that does not exist");
		em.update(entity);
	}

	@Test(expected = EntityManagerException.class)
	public void testUpdateStringId_AutoGenerateFalse() {
		StringId2 entity = new StringId2();
		entity.setGreetings("Update entity that does not exist");
		em.update(entity);
	}

	@Test
	public void testUpdateBooleanField() {
		BooleanField entity = new BooleanField();
		entity.setAwesome(false);
		entity = em.insert(entity);
		entity.setAwesome(true);
		entity = em.update(entity);
		entity = em.load(BooleanField.class, entity.getId());
		assertTrue(entity.isAwesome());
	}

	@Test
	public void testUpdateBooleanObject() {
		BooleanObject entity = new BooleanObject();
		entity.setAwesome(Boolean.TRUE);
		entity = em.insert(entity);
		entity.setAwesome(Boolean.FALSE);
		entity = em.update(entity);
		entity = em.load(BooleanObject.class, entity.getId());
		assertTrue(!entity.getAwesome());
	}

	@Test
	public void testUpdateBooleanObject_Null() {
		BooleanObject entity = new BooleanObject();
		entity.setAwesome(Boolean.TRUE);
		entity = em.insert(entity);
		entity.setAwesome(null);
		entity = em.update(entity);
		entity = em.load(BooleanObject.class, entity.getId());
		assertTrue(entity.getAwesome() == null);
	}

	@Test
	public void testUpdateCharField() {
		CharField entity = new CharField();
		entity.setSex('M');
		entity = em.insert(entity);
		entity.setSex('F');
		entity = em.update(entity);
		entity = em.load(CharField.class, entity.getId());
		assertTrue(entity.getSex() == 'F');
	}

	@Test
	public void testUpdateCharObject() {
		CharObject entity = new CharObject();
		entity.setSex('M');
		entity = em.insert(entity);
		entity.setSex('F');
		entity = em.update(entity);
		entity = em.load(CharObject.class, entity.getId());
		assertTrue(entity.getSex() == 'F');
	}

	@Test
	public void testUpdateCharObject_Null() {
		CharObject entity = new CharObject();
		entity.setSex('M');
		entity = em.insert(entity);
		entity.setSex(null);
		entity = em.update(entity);
		entity = em.load(CharObject.class, entity.getId());
		assertTrue(entity.getSex() == null);
	}

	@Test
	public void testUpdateShortField() {
		ShortField entity = new ShortField();
		entity.setAge((short) 13);
		entity = em.insert(entity);
		entity.setAge((short) 30);
		entity = em.update(entity);
		entity = em.load(ShortField.class, entity.getId());
		assertTrue(entity.getAge() == 30);
	}

	@Test
	public void testUpdateShortObject() {
		ShortObject entity = new ShortObject();
		entity.setAge((short) 13);
		entity = em.insert(entity);
		entity.setAge((short) 30);
		entity = em.update(entity);
		entity = em.load(ShortObject.class, entity.getId());
		assertTrue(entity.getAge() == 30);
	}

	@Test
	public void testUpdateShortObject_Null() {
		ShortObject entity = new ShortObject();
		entity.setAge((short) 13);
		entity = em.insert(entity);
		entity.setAge(null);
		entity = em.update(entity);
		entity = em.load(ShortObject.class, entity.getId());
		assertTrue(entity.getAge() == null);
	}

	@Test
	public void testUpdateIntegerField() {
		IntegerField entity = new IntegerField();
		entity.setCount(9876543);
		entity = em.insert(entity);
		entity.setCount(3456789);
		entity = em.update(entity);
		entity = em.load(IntegerField.class, entity.getId());
		assertTrue(entity.getCount() == 3456789);
	}

	@Test
	public void testUpdateIntegerObject() {
		IntegerObject entity = new IntegerObject();
		entity.setCount(9876543);
		entity = em.insert(entity);
		entity.setCount(3456789);
		entity = em.update(entity);
		entity = em.load(IntegerObject.class, entity.getId());
		assertTrue(entity.getCount() == 3456789);
	}

	@Test
	public void testUpdateIntegerObject_Null() {
		IntegerObject entity = new IntegerObject();
		entity.setCount(9876543);
		entity = em.insert(entity);
		entity.setCount(null);
		entity = em.update(entity);
		entity = em.load(IntegerObject.class, entity.getId());
		assertTrue(entity.getCount() == null);
	}

	@Test
	public void testUpdateLongField() {
		LongField entity = new LongField();
		entity.setDistanceFromEarth(9876543210L);
		entity = em.insert(entity);
		entity.setDistanceFromEarth(987654321098765L);
		entity = em.update(entity);
		entity = em.load(LongField.class, entity.getId());
		assertTrue(entity.getDistanceFromEarth() == 987654321098765L);
	}

	@Test
	public void testUpdateLongObject() {
		LongObject entity = new LongObject();
		entity.setDistanceFromEarth(9876543210L);
		entity = em.insert(entity);
		entity.setDistanceFromEarth(987654321098765L);
		entity = em.update(entity);
		entity = em.load(LongObject.class, entity.getId());
		assertTrue(entity.getDistanceFromEarth() == 987654321098765L);
	}

	@Test
	public void testUpdateLongObject_Null() {
		LongObject entity = new LongObject();
		entity.setDistanceFromEarth(9876543210L);
		entity = em.insert(entity);
		entity.setDistanceFromEarth(null);
		entity = em.update(entity);
		entity = em.load(LongObject.class, entity.getId());
		assertTrue(entity.getDistanceFromEarth() == null);
	}

	@Test
	public void testUpdateFloatField() {
		FloatField entity = new FloatField();
		entity.setArea(322.456f);
		entity = em.insert(entity);
		entity.setArea(456.322f);
		entity = em.update(entity);
		entity = em.load(FloatField.class, entity.getId());
		assertTrue(entity.getArea() == 456.322f);
	}

	@Test
	public void testUpdateFloatObject() {
		FloatObject entity = new FloatObject();
		entity.setArea(322.456f);
		entity = em.insert(entity);
		entity.setArea(456.322f);
		entity = em.update(entity);
		entity = em.load(FloatObject.class, entity.getId());
		assertTrue(entity.getArea() == 456.322f);
	}

	@Test
	public void testUpdateFloatObject_Null() {
		FloatObject entity = new FloatObject();
		entity.setArea(322.456f);
		entity = em.insert(entity);
		entity.setArea(null);
		entity = em.update(entity);
		entity = em.load(FloatObject.class, entity.getId());
		assertTrue(entity.getArea() == null);
	}

	@Test
	public void testUpdateDoubleField() {
		DoubleField entity = new DoubleField();
		entity.setArea(Double.MAX_VALUE);
		entity = em.insert(entity);
		entity.setArea(Double.MIN_VALUE);
		entity = em.update(entity);
		entity = em.load(DoubleField.class, entity.getId());
		assertTrue(entity.getArea() == Double.MIN_VALUE);
	}

	@Test
	public void testUpdateDoubleObject() {
		DoubleObject entity = new DoubleObject();
		entity.setArea(Double.MAX_VALUE);
		entity = em.insert(entity);
		entity.setArea(Double.MIN_VALUE);
		entity = em.update(entity);
		entity = em.load(DoubleObject.class, entity.getId());
		assertTrue(entity.getArea() == Double.MIN_VALUE);
	}

	@Test
	public void testUpdateDoubleObject_Null() {
		DoubleObject entity = new DoubleObject();
		entity.setArea(Double.MAX_VALUE);
		entity = em.insert(entity);
		entity.setArea(null);
		entity = em.update(entity);
		entity = em.load(DoubleObject.class, entity.getId());
		assertTrue(entity.getArea() == null);
	}

	@Test
	public void testUpdateStringField() {
		StringField entity = new StringField();
		entity.setName("Omaha");
		entity = em.insert(entity);
		entity.setName("New York");
		entity = em.update(entity);
		entity = em.load(StringField.class, entity.getId());
		assertTrue(entity.getName().equals("New York"));
	}

	@Test
	public void testUpdateStringField_Empty() {
		StringField entity = new StringField();
		entity.setName("Omaha");
		entity = em.insert(entity);
		entity.setName("");
		entity = em.update(entity);
		entity = em.load(StringField.class, entity.getId());
		assertTrue(entity.getName().equals(""));
	}

	@Test
	public void testUpdateStringField_Null() {
		StringField entity = new StringField();
		entity.setName("Omaha");
		entity = em.insert(entity);
		entity.setName(null);
		entity = em.update(entity);
		entity = em.load(StringField.class, entity.getId());
		assertTrue(entity.getName() == null);
	}

	@Test
	public void testUpdateCalendarField() {
		CalendarField entity = new CalendarField();
		Calendar now = Calendar.getInstance();
		entity.setCreationDate(now);
		entity = em.insert(entity);
		now.add(Calendar.DATE, 1);
		entity.setCreationDate(now);
		entity = em.update(entity);
		entity = em.load(CalendarField.class, entity.getId());
		assertTrue(entity.getCreationDate().getTimeInMillis() == now.getTimeInMillis());
	}

	@Test
	public void testUpdateCalendarField_Null() {
		CalendarField entity = new CalendarField();
		Calendar now = Calendar.getInstance();
		entity.setCreationDate(now);
		entity = em.insert(entity);
		entity.setCreationDate(null);
		entity = em.update(entity);
		entity = em.load(CalendarField.class, entity.getId());
		assertTrue(entity.getCreationDate() == null);
	}

	@Test
	public void testUpdateDateField() {
		DateField entity = new DateField();
		Calendar now = Calendar.getInstance();
		entity.setCreationDate(now.getTime());
		entity = em.insert(entity);
		now.add(Calendar.DATE, 1);
		entity.setCreationDate(now.getTime());
		entity = em.update(entity);
		entity = em.load(DateField.class, entity.getId());
		assertTrue(entity.getCreationDate().getTime() == now.getTimeInMillis());
	}

	@Test
	public void testUpdateDateField_Null() {
		DateField entity = new DateField();
		Calendar now = Calendar.getInstance();
		entity.setCreationDate(now.getTime());
		entity = em.insert(entity);
		entity.setCreationDate(null);
		entity = em.update(entity);
		entity = em.load(DateField.class, entity.getId());
		assertTrue(entity.getCreationDate() == null);
	}

	@Test
	public void testUpdateByteArayField() {
		byte[] salt = new byte[64];
		random.nextBytes(salt);
		ByteArrayField entity = new ByteArrayField();
		entity.setSalt(salt);
		entity = em.insert(entity);
		salt = new byte[32];
		random.nextBytes(salt);
		entity.setSalt(salt);
		entity = em.update(entity);
		entity = em.load(ByteArrayField.class, entity.getId());
		assertTrue(Arrays.equals(salt, entity.getSalt()));
	}

	@Test
	public void testUpdateByteArayField_Null() {
		byte[] salt = new byte[64];
		random.nextBytes(salt);
		ByteArrayField entity = new ByteArrayField();
		entity.setSalt(salt);
		entity = em.insert(entity);
		salt = null;
		entity.setSalt(salt);
		entity = em.update(entity);
		entity = em.load(ByteArrayField.class, entity.getId());
		assertTrue(entity.getSalt() == null);
	}

	@Test
	public void testUpdateCharArayField() {
		char[] password = "super secret phrase".toCharArray();
		CharArrayField entity = new CharArrayField();
		entity.setPassword(password);
		entity = em.insert(entity);
		password = "What a lovely day!".toCharArray();
		entity.setPassword(password);
		entity = em.update(entity);
		entity = em.load(CharArrayField.class, entity.getId());
		assertTrue(Arrays.equals(password, entity.getPassword()));
	}

	@Test
	public void testUpdateCharArayField_Null() {
		char[] password = "super secret phrase".toCharArray();
		CharArrayField entity = new CharArrayField();
		entity.setPassword(password);
		entity = em.insert(entity);
		password = null;
		entity.setPassword(password);
		entity = em.update(entity);
		entity = em.load(CharArrayField.class, entity.getId());
		assertTrue(entity.getPassword() == null);
	}

	@Test
	public void testUpdateStringListField() {
		List<String> hobbies = new ArrayList<>();
		hobbies.add("Tennis");
		hobbies.add("Stamp Collection");
		hobbies.add("Long Drives");
		StringListField entity = new StringListField();
		entity.setHobbies(hobbies);
		entity = em.insert(entity);
		hobbies.add("Watching TV");
		hobbies.add("Sleeping");
		hobbies.remove("Stamp Collection");
		entity.setHobbies(hobbies);
		entity = em.update(entity);
		entity = em.load(StringListField.class, entity.getId());
		assertTrue(hobbies.equals(entity.getHobbies()));
	}

	@Test
	public void testUpdateStringListField_Null() {
		List<String> hobbies = new ArrayList<>();
		hobbies.add("Tennis");
		hobbies.add("Stamp Collection");
		hobbies.add("Long Drives");
		StringListField entity = new StringListField();
		entity.setHobbies(hobbies);
		entity = em.insert(entity);
		hobbies = null;
		entity.setHobbies(hobbies);
		entity = em.update(entity);
		entity = em.load(StringListField.class, entity.getId());
		assertTrue(entity.getHobbies() == null);
	}

	@Test
	public void testUpdateLongListField() {
		List<Long> numbers = new ArrayList<>();
		numbers.add(Long.MIN_VALUE);
		numbers.add(0L);
		numbers.add(Long.MAX_VALUE);
		LongListField entity = new LongListField();
		entity.setNumbers(numbers);
		entity = em.insert(entity);
		numbers.add(42L);
		numbers.remove(Long.MIN_VALUE);
		entity.setNumbers(numbers);
		entity = em.update(entity);
		entity = em.load(LongListField.class, entity.getId());
		assertTrue(numbers.equals(entity.getNumbers()));
	}

	@Test
	public void testUpdateLongListField_Null() {
		List<Long> numbers = new ArrayList<>();
		numbers.add(Long.MIN_VALUE);
		numbers.add(0L);
		numbers.add(Long.MAX_VALUE);
		LongListField entity = new LongListField();
		entity.setNumbers(numbers);
		entity = em.insert(entity);
		numbers = null;
		entity.setNumbers(numbers);
		entity = em.update(entity);
		entity = em.load(LongListField.class, entity.getId());
		assertNull(entity.getNumbers());
	}

	@Test(expected = EntityManagerException.class)
	public void testUpdate_ThatDoesNotExist() {
		LongId entity = new LongId();
		entity.setId(100);
		entity.setField1("I'm 100L");
		// In case if it exists, delete it
		em.delete(entity);
		LongId entity2 = em.load(LongId.class, entity.getId());
		if (entity2 == null) {
			em.update(entity);
		}
	}

	@Test
	public void testUpdate_GeoLocationField() {
		GeoLocationField omaha = GeoLocationField.OMAHA;
		GeoLocationField entity = new GeoLocationField(omaha.getCity(), omaha.getCoordinates());
		entity = em.insert(entity);
		GeoLocation newCoordinates = new GeoLocation(10, 10);
		entity.setCoordinates(newCoordinates);
		em.update(entity);
		entity = em.load(GeoLocationField.class, entity.getId());
		assertTrue(entity.getCoordinates().equals(newCoordinates));
	}

	@Test
	public void testUpdate_GeoLocationField_Null() {
		GeoLocationField paris = GeoLocationField.PARIS;
		GeoLocationField entity = new GeoLocationField(paris.getCity(), paris.getCoordinates());
		entity = em.insert(entity);
		entity.setCoordinates(null);
		em.update(entity);
		entity = em.load(GeoLocationField.class, entity.getId());
		assertNull(entity.getCoordinates());
	}

	@Test
	public void testUpdate_IgnoredField() {
		IgnoreField entity = new IgnoreField();
		entity.setName("John Doe");
		entity.setComputed("This should not be persisted");
		entity = em.insert(entity);
		entity = em.load(IgnoreField.class, entity.getId());
		entity.setName("John Smith");
		entity.setComputed("Wassup!");
		em.update(entity);
		entity = em.load(IgnoreField.class, entity.getId());
		assertTrue(entity.getId() > 0 && entity.getName().equals("John Smith") && entity.getComputed() == null);
	}

	@Test
	public void testUpsert_LongId_Insert() {
		final String message = "Upsert created me";
		LongId entity = new LongId();
		entity.setField1(message);
		entity = em.upsert(entity);
		entity = em.load(LongId.class, entity.getId());
		assertTrue(message.equals(entity.getField1()));
	}

	@Test
	public void testUpsert_LongId_Update() {
		final String message = "Upsert created me";
		final String message2 = "Upsert created me, then updated";
		LongId entity = new LongId();
		entity.setField1(message);
		entity = em.upsert(entity);
		entity = em.load(LongId.class, entity.getId());
		entity.setField1(message2);
		em.upsert(entity);
		entity = em.load(LongId.class, entity.getId());
		assertTrue(message2.equals(entity.getField1()));
	}

	@Test
	public void testUpsert_StringId_Insert() {
		final String greetings = "Upsert created me";
		StringId entity = new StringId();
		entity.setGreetings(greetings);
		entity = em.upsert(entity);
		entity = em.load(StringId.class, entity.getId());
		assertTrue(greetings.equals(entity.getGreetings()));

	}

	@Test
	public void testUpsert_StringId_Update() {
		final String greetings1 = "Upsert created me";
		final String greetings2 = "Upsert first created me, then updated";
		StringId entity = new StringId();
		entity.setGreetings(greetings1);
		entity = em.upsert(entity);
		entity = em.load(StringId.class, entity.getId());
		entity.setGreetings(greetings2);
		em.upsert(entity);
		entity = em.load(StringId.class, entity.getId());
		assertTrue(greetings2.equals(entity.getGreetings()));

	}

	@Test
	public void testUpsert_List_Insert() {
		List<StringField> entities = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			StringField entity = new StringField();
			entity.setName("I'm StringField Entity " + i);
			entities.add(entity);
		}
		entities = em.upsert(entities);
		assertTrue(entities.size() == 5 && entities.get(0).getId() != 0);
	}

	@Test(expected = EntityManagerException.class)
	public void testDelete_UnsetId() {
		StringField entity = new StringField();
		em.delete(entity);
	}

	@Test
	public void testDelete_Root() {
		StringField entity = new StringField();
		entity.setName("Hello World!");
		entity = em.insert(entity);
		entity = em.load(StringField.class, entity.getId());
		em.delete(entity);
		StringField entity2 = em.load(StringField.class, entity.getId());
		assertTrue(entity != null && entity2 == null);
	}

	@Test
	public void testDelete_Child() {
		ParentEntity parent = new ParentEntity();
		parent.setField1("Parent for Delete Test!");
		parent = em.insert(parent);

		ChildEntity child = new ChildEntity();
		child.setField1("Child for Delete Test");
		child.setParentKey(parent.getKey());
		child = em.insert(child);

		child = em.load(ChildEntity.class, parent.getKey(), child.getId());
		em.delete(child);
		ChildEntity child2 = em.load(ChildEntity.class, parent.getKey(), child.getId());
		assertTrue(child != null && child2 == null);
	}

	@Test
	public void testDelete_Child_WithNoParent() {
		ParentEntity parent = new ParentEntity();
		parent.setField1("Parent for Delete Test!");
		parent = em.insert(parent);

		ChildEntity child = new ChildEntity();
		child.setField1("Child for Delete Test");
		child.setParentKey(parent.getKey());
		child = em.insert(child);

		// Second Child with the same ID as the first one, but no parent.
		ChildEntity child2 = new ChildEntity();
		child2.setId(child.getId());
		child2.setField1("Child with no parent");
		child2 = em.insert(child2);

		child2 = em.load(ChildEntity.class, child2.getId());
		em.delete(child2);
		ChildEntity child3 = em.load(ChildEntity.class, child2.getId());
		assertTrue(child2 != null && child3 == null);
	}

	@Test
	public void testDelete_Grandchild() {
		ParentEntity parent = new ParentEntity();
		parent.setField1("Parent for Delete Test!");
		parent = em.insert(parent);

		ChildEntity child = new ChildEntity();
		child.setField1("Child for Delete Test");
		child.setParentKey(parent.getKey());
		child = em.insert(child);

		GrandchildEntity grandchild = new GrandchildEntity();
		grandchild.setField1("Grandchild for Delete Test");
		grandchild.setParentKey(child.getKey());
		grandchild = em.insert(grandchild);

		grandchild = em.load(GrandchildEntity.class, child.getKey(), grandchild.getId());
		em.delete(grandchild);
		GrandchildEntity grandchild2 = em.load(GrandchildEntity.class, child.getKey(), grandchild.getId());
		assertTrue(grandchild != null && grandchild2 == null);
	}

	/*
	 * @Test public void testDeleteAll() { List<DeleteAll> entities = new
	 * ArrayList<>(); for (int i = 0; i < 5; i++) { DeleteAll entity = new
	 * DeleteAll(); entity.setField1("Delete All " + i); entities.add(entity); }
	 * em.insert(entities); try { Thread.sleep(5000L); } catch
	 * (InterruptedException e) { e.printStackTrace(); } long deleteCount =
	 * em.deleteAll(DeleteAll.class); assertTrue(deleteCount == 5); }
	 */

	@Test
	public void testLoad_ThatDoesNotExist() {
		long id = random.nextLong();
		StringField entity = new StringField();
		entity.setId(id);
		em.delete(entity);
		StringField entity2 = em.load(StringField.class, id);
		assertNull(entity2);
	}

	@Test
	public void testLoad_Good() {
		long id = random.nextLong();
		StringField entity = new StringField();
		entity.setId(id);
		entity.setName("USA");
		em.delete(entity);
		em.insert(entity);
		StringField entity2 = em.load(StringField.class, entity.getId());
		assertTrue(entity.getId() == entity2.getId());
	}

	@Test
	public void testLoad_ParentChild() {
		ParentEntity parent = new ParentEntity();
		parent.setField1("Parent for load test");
		parent = em.insert(parent);

		ChildEntity child = new ChildEntity();
		child.setField1("Child for load test");
		child.setParentKey(parent.getKey());
		child = em.insert(child);

		GrandchildEntity grandchild = new GrandchildEntity();
		grandchild.setField1("Grandchild for laod test");
		grandchild.setParentKey(child.getKey());
		grandchild = em.insert(grandchild);

		ParentEntity parent2 = em.load(ParentEntity.class, parent.getId());
		ChildEntity child2 = em.load(ChildEntity.class, parent2.getKey(), child.getId());
		GrandchildEntity grandchild2 = em.load(GrandchildEntity.class, child2.getKey(), grandchild.getId());

		assertTrue(grandchild.getId() == grandchild2.getId() && grandchild.getField1().equals(grandchild2.getField1())
				&& grandchild.getParentKey().equals(grandchild2.getParentKey()));
	}

	@Test
	public void testLoad_IgnoredField() {
		IgnoreField2 entity = new IgnoreField2();
		entity.setName("Hello World");
		entity.setComputed("Hola Mundo");
		entity = em.insert(entity);
		entity = em.load(IgnoreField2.class, entity.getId());

		// Load the previously inserted entity using IgnoreFeild.class
		IgnoreField entity2 = em.load(IgnoreField.class, entity.getId());
		assertTrue(entity.getId() == entity2.getId() && entity.getComputed() != null && entity2.getComputed() == null);
	}

	@Test
	public void testLoadById() {
		List<LongId> entities = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			LongId entity = new LongId();
			entity.setField1("Test for Loading Multiple Entities " + i);
			entities.add(entity);
		}
		entities = em.insert(entities);
		List<Long> identifiers = new ArrayList<>();
		for (LongId entity : entities) {
			identifiers.add(entity.getId());
		}
		List<LongId> entities2 = em.loadById(LongId.class, identifiers);
		assertEquals(entities, entities2);
	}

	@Test
	public void testLoadById_MissingKey() {
		List<LongId> entities = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			LongId entity = new LongId();
			entity.setField1("Test for Loading Multiple Entities " + i);
			entities.add(entity);
		}
		entities = em.insert(entities);
		List<Long> identifiers = new ArrayList<>();
		for (LongId entity : entities) {
			identifiers.add(entity.getId());
		}
		identifiers.add(0, -100L);
		identifiers.add(3, -200L);
		List<LongId> entities2 = em.loadById(LongId.class, identifiers);
		assertTrue(entities2.get(0) == null && entities2.get(1) != null && entities2.get(3) == null);
	}

	@Test
	public void testLoadByName() {
		List<StringId> entities = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			StringId entity = new StringId();
			entity.setGreetings("Test for Loading Multiple Entities " + i);
			entities.add(entity);
		}
		entities = em.insert(entities);
		List<String> identifiers = new ArrayList<>();
		for (StringId entity : entities) {
			identifiers.add(entity.getId());
		}
		List<StringId> entities2 = em.loadByName(StringId.class, identifiers);
		assertEquals(entities, entities2);
	}

	@Test
	public void executeTest_SelectAll() {
		EntityQueryRequest request = em.createEntityQueryRequest("SELECT * FROM Task order by __key__");
		QueryResponse<Task> response = em.execute(Task.class, request);
		List<Task> tasks = response.getResults();
		assertTrue(tasks.size() == 50 && tasks.get(0).getId() == 1 && tasks.get(tasks.size() - 1).getId() == 50);
	}

	@Test
	public void rexecuteTest_SelectPriorityFilter() {
		EntityQueryRequest request = em
				.createEntityQueryRequest("SELECT * FROM Task WHERE priority = @1 order by __key__");
		request.addPositionalBinding(0);
		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
		List<Task> tasks = response.getResults();
		assertTrue(tasks.size() == 10 && tasks.get(0).getId() == 5 && tasks.get(tasks.size() - 1).getId() == 50);
	}

	@Test
	public void executeTest_SelectPriorityAndCompleteFilter() {
		EntityQueryRequest request = em
				.createEntityQueryRequest("SELECT * FROM Task WHERE priority = @1 AND complete = @2 order by __key__");
		request.addPositionalBinding(0);
		request.addPositionalBinding(true);
		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
		List<Task> tasks = response.getResults();
		assertTrue(tasks.size() == 5 && tasks.get(0).getId() == 10 && tasks.get(tasks.size() - 1).getId() == 50);
	}

	@Test
	public void executeTest_SelectTaskNameFilter() {
		EntityQueryRequest request = em.createEntityQueryRequest("SELECT * FROM Task WHERE name = @1 order by __key__");
		request.addPositionalBinding("My Task 13");
		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
		List<Task> tasks = response.getResults();
		assertTrue(tasks.size() == 1 && tasks.get(0).getId() == 13);
	}

	@Test
	public void executeTest_SelectTasksDueByTomorrow() {
		Calendar cal = getToday();
		cal.add(Calendar.DATE, 1);
		Date tomorrow = cal.getTime();

		EntityQueryRequest request = em.createEntityQueryRequest("SELECT * FROM Task WHERE completionDate <= @1");
		request.addPositionalBinding(tomorrow);
		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
		List<Task> tasks = response.getResults();
		assertTrue(tasks.size() == 20);
	}

	@Test
	public void executeTest_SelectTasksDueByBetweenTomorrowAndDayAfter_NamedBindings() {
		Calendar cal = getToday();
		cal.add(Calendar.DATE, 1);
		Date tomorrow = cal.getTime();
		cal.add(Calendar.DATE, 1);
		Date dayAfterTomorrow = cal.getTime();
		Map<String, Object> bindings = new HashMap<>();
		bindings.put("Tomorrow", tomorrow);
		bindings.put("DayAfterTomorrow", dayAfterTomorrow);

		EntityQueryRequest request = em.createEntityQueryRequest(
				"SELECT * FROM Task WHERE completionDate >= @Tomorrow AND completionDate <= @DayAfterTomorrow");
		request.setNamedBindings(bindings);
		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
		List<Task> tasks = response.getResults();
		assertTrue(tasks.size() == 20);
	}

	@Test
	public void runQueryTest_Pagination_Forward() {
		final int pageSize = 5;
		String query = "SELECT * FROM Task ORDER BY __key__";
		DatastoreCursor endCursor = null;
		List<Task> tasks = null;
		for (int i = 0; i < 5; i++) {
			String pageQuery = query + " LIMIT @Limit";
			if (i > 0) {
				pageQuery += " OFFSET @Offset";
			}
			EntityQueryRequest request = em.createEntityQueryRequest(pageQuery);
			request.setNamedBinding("Limit", pageSize);
			if (i > 0) {
				request.setNamedBinding("Offset", endCursor);
			}
			QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
			tasks = response.getResults();
			endCursor = response.getEndCursor();
			System.out.println("**** Page " + (i + 1) + "****");
			for (Task task : tasks) {
				System.out.println(task.getId() + " ---> " + task.getName());
			}
		}
		assertTrue(tasks.size() == 5 && tasks.get(0).getId() == 21);

	}

	@Test
	public void testExecute() {
		EntityQueryRequest request = em.createEntityQueryRequest("SELECT * from Task");
		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
		List<Task> tasks = response.getResults();
		for (Task task : tasks) {
			System.out.println(task.getId() + "--->" + task.getName());
		}
		System.out.println("Start Cursor: " + response.getStartCursor());
		System.out.println("End Cursor: " + response.getEndCursor());
		assertTrue(tasks.size() == 50);
	}

	@Test
	public void testExecute_Limit_10() {
		EntityQueryRequest request = em.createEntityQueryRequest("SELECT * from Task LIMIT @1");
		request.addPositionalBinding(10L);
		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
		List<Task> tasks = response.getResults();
		System.out.println("Start Cursor: " + response.getStartCursor());
		System.out.println("End Cursor: " + response.getEndCursor());
		for (Task task : tasks) {
			System.out.println(task.getId() + "--->" + task.getName());
		}
		assertTrue(tasks.size() == 10);
	}

	@Test
	public void testExecute_PositionalBinding() {
		EntityQueryRequest request = em.createEntityQueryRequest("SELECT * from Task WHERE priority=@1");
		request.addPositionalBinding(3);
		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
		List<Task> tasks = response.getResults();
		System.out.println("Start Cursor: " + response.getStartCursor());
		System.out.println("End Cursor: " + response.getEndCursor());
		for (Task task : tasks) {
			System.out.println(task.getId() + "--->" + task.getName());
		}
		assertTrue(tasks.size() == 10);
	}

	@Test
	public void testExecute_PositionalBindings() {
		EntityQueryRequest request = em
				.createEntityQueryRequest("SELECT * from Task WHERE priority=@1 AND complete=@2");
		request.addPositionalBinding(0);
		request.addPositionalBinding(true);

		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
		List<Task> tasks = response.getResults();
		System.out.println("Start Cursor: " + response.getStartCursor());
		System.out.println("End Cursor: " + response.getEndCursor());
		for (Task task : tasks) {
			System.out.println(task.getId() + "--->" + task.getName());
		}
		assertTrue(tasks.size() == 5);
	}

	@Test
	public void testExecute_GeoLocation() {
		em.deleteAll(GeoLocationField.class);
		GeoLocationField jfk = GeoLocationField.NEW_YORK_CITY;
		GeoLocationField oma = GeoLocationField.OMAHA;
		GeoLocationField cdg = GeoLocationField.PARIS;
		em.insert(jfk);
		em.insert(oma);
		em.insert(cdg);
		EntityQueryRequest request = em.createEntityQueryRequest("SELECT * FROM CityCoordinates ORDER BY city");
		QueryResponse<GeoLocationField> response = em.executeEntityQueryRequest(GeoLocationField.class, request);
		List<GeoLocationField> cities = response.getResults();
		// This test fails sometimes if the below assertion is cities.size() ==
		// 3.
		assertTrue(cities.size() > 0 && cities.get(0).getCoordinates().equals(jfk.getCoordinates()));

	}

	@Test
	public void testExecuteProjectionQuery() {
		ProjectionQueryRequest request = em.createProjectionQueryRequest("SELECT name FROM Task");
		QueryResponse<Task> response = em.execute(Task.class, request);
		List<Task> tasks = response.getResults();
		for (Task task : tasks) {
			System.out.printf("Id: %d; Name: %s; Priority: %s; Complete: %s; Completion Date: %s\n", task.getId(),
					task.getName(), task.getPriority(), task.isComplete(), task.getCompletionDate());
		}
		assertTrue(tasks.size() == 50);
	}

	@Test
	public void testExecuteProjectionQuery2() {
		// Uses a different entity - with a subset of properties from the main
		// entity
		ProjectionQueryRequest request = em.createProjectionQueryRequest("SELECT name FROM Task");
		QueryResponse<TaskName> response = em.executeProjectionQueryRequest(TaskName.class, request);
		List<TaskName> tasks = response.getResults();
		for (TaskName task : tasks) {
			System.out.printf("Id: %d; Name: %s; \n", task.getId(), task.getName());
		}
		assertTrue(tasks.size() == 50);
	}

	@Test
	public void testExecuteKeyQuery() {
		KeyQueryRequest request = em.createKeyQueryRequest("SELECT __key__ from Task");
		QueryResponse<DatastoreKey> response = em.executeKeyQueryRequest(request);
		List<DatastoreKey> keys = response.getResults();
		for (DatastoreKey key : keys) {
			System.out.printf("%-10s %20s %s\n", key.kind(), key.nameOrId(), key.getEncoded());
		}
		assertTrue(keys.size() == 50);
	}

	@Test(expected = EntityManagerException.class)
	public void testUpdate_InvalidStringId() {
		StringId entity = new StringId();
		entity.setGreetings("Good Night, this should never show up in the datastore");
		entity = em.update(entity);
	}

	@Test(expected = EntityManagerException.class)
	public void testExecute_WithLiteral_Default() {
		EntityQueryRequest request = em
				.createEntityQueryRequest("SELECT * FROM Task WHERE priority = 0 order by __key__");
		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
	}

	@Test(expected = EntityManagerException.class)
	public void testExecute_WithLiteral_False() {
		EntityQueryRequest request = em
				.createEntityQueryRequest("SELECT * FROM Task WHERE priority = 0 order by __key__");
		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
	}

	@Test
	public void testExecute_WithLiteral_True() {
		EntityQueryRequest request = em
				.createEntityQueryRequest("SELECT * FROM Task WHERE priority = 0 order by __key__");
		request.setAllowLiterals(true);
		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
		List<Task> tasks = response.getResults();
		assertTrue(tasks.size() == 10 && tasks.get(0).getId() == 5 && tasks.get(tasks.size() - 1).getId() == 50);
	}

	@Test(expected = EntityManagerException.class)
	public void testExecute_WithSyntheticLiteral_Default() {
		EntityQueryRequest request = em.createEntityQueryRequest(
				"SELECT * FROM Task where completionDate > datetime('2016-08-10T00:00:00.000000z')");
		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
	}

	@Test
	public void testExecute_WithSyntheticLiteral_True() {
		EntityQueryRequest request = em.createEntityQueryRequest(
				"SELECT * FROM Task where completionDate > datetime('2016-08-10T00:00:00.000000z')");
		request.setAllowLiterals(true);
		QueryResponse<Task> response = em.executeEntityQueryRequest(Task.class, request);
		// Let's not worry about the results
	}

	@Test
	public void testInsert_Embedded() {
		Customer entity = Customer.SAMPLE_CUSTOMER1;
		entity = em.insert(entity);
		Customer entity2 = em.load(Customer.class, entity.getId());
		assertTrue(entity.equals(entity2));
	}

	@Test
	public void testInsert_Embedded_NullAddress() {
		Customer entity = Customer.SAMPLE_CUSTOMER2;
		entity = em.insert(entity);
		Customer entity2 = em.load(Customer.class, entity.getId());
		assertTrue(entity.equals(entity2));
	}

	@Test
	public void testInsert_Embedded_NullZipCode() {
		Customer entity = Customer.SAMPLE_CUSTOMER3;
		entity = em.insert(entity);
		Customer entity2 = em.load(Customer.class, entity.getId());
		assertTrue(entity.equals(entity2));
	}

	@Test
	public void testUpdate_Embedded() {
		Customer entity = Customer.SAMPLE_CUSTOMER1;
		entity = em.insert(entity);
		entity = em.load(Customer.class, entity.getId());
		entity.setName("John Smith");
		entity.getBillingAddress().setCity("Lincoln");
		entity.getShippingAddress().getZipCode().setFiveDigits("65432");
		em.update(entity);
		Customer entity2 = em.load(Customer.class, entity.getId());
		assertTrue(entity.equals(entity2));
	}

	@Test
	public void testUpdate_Embedded_NullAddress() {
		Customer entity = Customer.SAMPLE_CUSTOMER2;
		entity = em.insert(entity);
		entity = em.load(Customer.class, entity.getId());
		entity.setName("Super Customer Updated");
		entity.getBillingAddress().setCity("Lincoln");
		entity.getShippingAddress().getZipCode().setFiveDigits("65432");
		em.update(entity);
		Customer entity2 = em.load(Customer.class, entity.getId());
		assertTrue(entity.equals(entity2));
	}

	@Test
	public void testUpdate_Embedded_NullZipCode() {
		Customer entity = Customer.SAMPLE_CUSTOMER3;
		entity = em.insert(entity);
		entity = em.load(Customer.class, entity.getId());
		entity.setName("Super Customer Updated");
		entity.getShippingAddress().getZipCode().setFiveDigits("65432");
		entity.getBillingAddress().getZipCode().setFiveDigits("65432");
		em.update(entity);
		Customer entity2 = em.load(Customer.class, entity.getId());
		assertTrue(entity.equals(entity2));
	}

	@Test
	public void testDelete_Embedded() {
		Customer entity = Customer.SAMPLE_CUSTOMER1;
		entity = em.insert(entity);
		entity = em.load(Customer.class, entity.getId());
		em.delete(entity);
		Customer entity2 = em.load(Customer.class, entity.getId());
		assertTrue(entity != null && entity2 == null);
	}

	@Test
	public void testExecuteEntityQuery_Embedded() {
		em.deleteAll(Customer.class);
		List<Customer> customers = new ArrayList<>();
		customers.add(Customer.SAMPLE_CUSTOMER1);
		customers.add(Customer.SAMPLE_CUSTOMER2);
		customers.add(Customer.SAMPLE_CUSTOMER3);
		em.insert(customers);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		EntityQueryRequest request = em.createEntityQueryRequest("SELECT * FROM Customer");
		QueryResponse<Customer> response = em.executeEntityQueryRequest(Customer.class, request);
		List<Customer> output = response.getResults();
		assertTrue(output.size() == 3);
	}

	@Test
	public void testExecuteProjectionQuery_Embedded() {
		em.deleteAll(Customer.class);
		List<Customer> customers = new ArrayList<>();
		customers.add(Customer.SAMPLE_CUSTOMER1);
		customers.add(Customer.SAMPLE_CUSTOMER2);
		customers.add(Customer.SAMPLE_CUSTOMER3);
		em.insert(customers);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ProjectionQueryRequest request = em.createProjectionQueryRequest("SELECT name FROM Customer");
		QueryResponse<Customer> response = em.executeProjectionQueryRequest(Customer.class, request);
		List<Customer> output = response.getResults();
		assertTrue(output.size() == 3);
	}

	@Test
	public void testInsert_MappedSupperClass() {
		SubClass1 entity = new SubClass1();
		entity.setName("John Doe");
		entity.setCreatedBy("user1");
		entity.setCreatedOn(new Date());
		entity.setModifiedBy("user2");
		entity.setModifiedOn(new Date());
		entity = em.insert(entity);
		SubClass1 entity2 = em.load(SubClass1.class, entity.getId());
		assertTrue(entity.equals(entity2));
	}

	@Test
	public void testInsert_MappedSupperClass2() {
		SubClass2 entity = new SubClass2();
		entity.setName("John Doe");
		entity.setCreatedBy("user1");
		entity.setCreatedOn(new Date());
		entity.setModifiedBy("user2");
		entity.setModifiedOn(new Date());
		entity = em.insert(entity);
		SubClass2 entity2 = em.load(SubClass2.class, entity.getId());
		assertTrue(entity.equals(entity2));
	}

	@Test
	public void testUpdate_MappedSupperClass2() {
		SubClass2 entity = new SubClass2();
		entity.setName("John Doe");
		entity.setCreatedBy("user1");
		entity.setCreatedOn(new Date());
		entity.setModifiedBy("user2");
		entity.setModifiedOn(new Date());
		entity = em.insert(entity);
		SubClass2 entity2 = em.load(SubClass2.class, entity.getId());
		entity2.setName("John Smith");
		em.update(entity2);
		SubClass2 entity3 = em.load(SubClass2.class, entity2.getId());
		assertTrue(entity3.equals(entity2));
	}

	@Test
	public void testInsert_MappedSupperClass3() {
		SubClass3 entity = new SubClass3();
		entity.setFieldx("I'm super super!");
		entity.setName("John Doe");
		entity.setCreatedBy("user1");
		entity.setCreatedOn(new Date());
		entity.setModifiedBy("user2");
		entity.setModifiedOn(new Date());
		entity = em.insert(entity);
		SubClass3 entity2 = em.load(SubClass3.class, entity.getId());
		assertTrue(entity.equals(entity2) && entity2.getMyKey() != null);
	}

	@Test
	public void testInsert_MappedSupperClass4() {
		SubClass4 entity = new SubClass4();
		entity.setFieldx("I'm super super!");
		entity.setName("John Doe");
		entity.setCreatedBy("user1");
		entity.setCreatedOn(new Date());
		entity.setModifiedBy("user2");
		entity.setModifiedOn(new Date());
		entity = em.insert(entity);
		SubClass4 entity2 = em.load(SubClass4.class, entity.getId());
		assertTrue(entity.equals(entity2) && entity2.getCreatedBy() == null);
	}

	private static Calendar getToday() {
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		return today;
	}

}
