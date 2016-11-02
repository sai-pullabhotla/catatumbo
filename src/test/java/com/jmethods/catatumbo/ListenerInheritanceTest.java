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

import com.jmethods.catatumbo.entities.Animal;
import com.jmethods.catatumbo.entities.Cat;
import com.jmethods.catatumbo.entities.Cow;
import com.jmethods.catatumbo.entities.Dog;
import com.jmethods.catatumbo.entities.FarmAnimal;
import com.jmethods.catatumbo.entities.Lion;
import com.jmethods.catatumbo.entities.Pet;
import com.jmethods.catatumbo.entities.WildAnimal;
import com.jmethods.catatumbo.listeners.AnimalListener;
import com.jmethods.catatumbo.listeners.FarmAnimalListener;

/**
 * @author Sai Pullabhotla
 *
 */
public class ListenerInheritanceTest {
	private static EntityManager em;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		em = TestUtils.getEntityManager();
		em.setDefaultListeners(AnimalListener.class);
		em.deleteAll(Cat.class);
		em.deleteAll(Dog.class);
		em.deleteAll(Lion.class);
		em.deleteAll(Cow.class);

	}

	@Test
	public void testPreInsert_Cat() {
		Cat cat = new Cat();
		cat = em.insert(cat);
		String preInsertClassName = PreInsert.class.getSimpleName();
		String expected = Animal.class.getSimpleName() + "." + preInsertClassName + "->" + Pet.class.getSimpleName()
				+ "." + preInsertClassName + "->" + Cat.class.getSimpleName() + "." + preInsertClassName;
		assertEquals(expected, cat.getValue());
	}

	@Test
	public void testPreAndPostInsert_Dog() {
		Dog dog = new Dog();
		dog = em.insert(dog);
		String preInsertClassName = PreInsert.class.getSimpleName();
		String postInsertClassName = PostInsert.class.getSimpleName();
		String expected = Dog.class.getSimpleName() + "." + preInsertClassName + "->" + Dog.class.getSimpleName() + "."
				+ postInsertClassName;
		assertEquals(expected, dog.getValue());
	}

	@Test
	public void testPreInsert_Lion() {
		Lion lion = new Lion();
		lion = em.insert(lion);
		String preInsertClassName = PreInsert.class.getSimpleName();
		String expected = WildAnimal.class.getSimpleName() + "." + preInsertClassName + "->"
				+ Lion.class.getSimpleName() + "." + preInsertClassName;
		assertEquals(expected, lion.getValue());
	}

	@Test
	public void testPreInsert_Cow() {
		Cow cow = new Cow();
		cow = em.insert(cow);
		String preInsertClassName = PreInsert.class.getSimpleName();
		String expected = AnimalListener.class.getSimpleName() + "." + preInsertClassName + "->"
				+ FarmAnimalListener.class.getSimpleName() + "." + preInsertClassName + "->"
				+ Animal.class.getSimpleName() + "." + preInsertClassName + "->" + FarmAnimal.class.getSimpleName()
				+ "." + preInsertClassName + "->" + Cow.class.getSimpleName() + "." + preInsertClassName;
		assertEquals(expected, cow.getValue());
	}

}
