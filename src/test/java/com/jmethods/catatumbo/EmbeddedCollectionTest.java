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

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jmethods.catatumbo.entities.Address;
import com.jmethods.catatumbo.entities.AddressMap;
import com.jmethods.catatumbo.entities.PhoneList;
import com.jmethods.catatumbo.entities.PhoneNumber;

/**
 * @author Sai Pullabhotla
 *
 */
public class EmbeddedCollectionTest {
  private static EntityManager em;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    em = TestUtils.getEntityManager();
  }

  @Test
  public void testInsert_PhoneList() {
    PhoneList entity = PhoneList.getSample1();
    entity = em.insert(entity);
    PhoneList loadedEntity = em.load(PhoneList.class, entity.getId());
    assertTrue(entity.equals(loadedEntity));
  }

  @Test
  public void testInsert_PhoneList_Null() {
    PhoneList entity = new PhoneList();
    entity = em.insert(entity);
    PhoneList loadedEntity = em.load(PhoneList.class, entity.getId());
    assertTrue(entity.equals(loadedEntity));
  }

  @Test
  public void testUpdate_PhoneList() {
    PhoneList entity = PhoneList.getSample1();
    entity = em.insert(entity);
    entity = em.load(PhoneList.class, entity.getId());
    entity.getLandLineNumbers().add(PhoneNumber.getSample1());
    entity = em.update(entity);
    PhoneList loadedEntity = em.load(PhoneList.class, entity.getId());
    assertTrue(entity.equals(loadedEntity));
  }

  @Test
  public void testInsert_AddressMap() {
    AddressMap entity = new AddressMap();
    Map<String, Address> addresses = new HashMap<>();
    addresses.put("home", Address.getSample1());
    addresses.put("work", Address.getSample2());
    entity.setAddresses(addresses);
    entity = em.insert(entity);
    AddressMap loadedEntity = em.load(AddressMap.class, entity.getId());
    assertTrue(entity.equals(loadedEntity));
  }

  @Test
  public void testInsert_AddressMap_Null() {
    AddressMap entity = AddressMap.getSample1();
    entity = em.insert(entity);
    AddressMap loadedEntity = em.load(AddressMap.class, entity.getId());
    assertTrue(entity.equals(loadedEntity));
  }

  @Test
  public void testInsert_AddressMap_NullZip() {
    AddressMap entity = AddressMap.getSample2();
    entity = em.insert(entity);
    AddressMap loadedEntity = em.load(AddressMap.class, entity.getId());
    assertTrue(entity.equals(loadedEntity));
  }

  @Test
  public void testUpdate_AddressMap() {
    AddressMap entity = AddressMap.getSample1();
    entity = em.insert(entity);
    entity = em.load(AddressMap.class, entity.getId());
    entity.getAddresses().get("work").setStreet2("Suite 2");
    entity = em.update(entity);
    AddressMap loadedEntity = em.load(AddressMap.class, entity.getId());
    assertTrue(entity.equals(loadedEntity));
  }

}
