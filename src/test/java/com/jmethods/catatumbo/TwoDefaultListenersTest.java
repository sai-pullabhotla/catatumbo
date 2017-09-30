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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jmethods.catatumbo.entities.GlobalCalculatorEntity;
import com.jmethods.catatumbo.listeners.Adder;
import com.jmethods.catatumbo.listeners.Multiplier;

/**
 * @author Sai Pullabhotla
 *
 */
public class TwoDefaultListenersTest {

  private static EntityManager em;
  private static Random random = new Random();

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    em = TestUtils.getEntityManager();
    em.setDefaultListeners(Adder.class, Multiplier.class);
  }

  @Test
  public void testPreAndPostInsert() {
    GlobalCalculatorEntity entity = new GlobalCalculatorEntity(7, 5);
    entity = em.insert(entity);
    assertTrue(entity.getSum() == 12);
    assertTrue(entity.getSum2() == 24);
    assertTrue(entity.getProduct() == 35);
    assertTrue(entity.getProduct2() == 70);
  }

  @Test
  public void testPreAndPostInsert_List() {
    List<GlobalCalculatorEntity> entities = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      GlobalCalculatorEntity entity = new GlobalCalculatorEntity(i + 2, i + 4);
      entities.add(entity);
    }
    entities = em.insert(entities);
    assertTrue(entities.get(0).getSum() == 6);
    assertTrue(entities.get(0).getSum2() == 12);
    assertTrue(entities.get(0).getProduct() == 8);
    assertTrue(entities.get(0).getProduct2() == 16);
    assertTrue(entities.get(1).getSum() == 8);
    assertTrue(entities.get(1).getSum2() == 16);
    assertTrue(entities.get(1).getProduct() == 15);
    assertTrue(entities.get(1).getProduct2() == 30);
  }

  @Test
  public void testPreAndPostUpdate() {
    GlobalCalculatorEntity entity = new GlobalCalculatorEntity();
    entity = em.insert(entity);
    entity.setOperand1(9);
    entity.setOperand2(2);
    entity = em.update(entity);
    assertTrue(entity.getSum() == 11);
    assertTrue(entity.getSum2() == 33);
    assertTrue(entity.getProduct() == 18);
    assertTrue(entity.getProduct2() == 54);
  }

  @Test
  public void testPreAndPost_Update_List() {
    List<GlobalCalculatorEntity> entities = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      GlobalCalculatorEntity entity = new GlobalCalculatorEntity(i, i + 1);
      entities.add(entity);
    }
    entities = em.insert(entities);
    entities = em.update(entities);
    assertTrue(entities.get(0).getSum() == 1);
    assertTrue(entities.get(0).getSum2() == 3);
    assertTrue(entities.get(0).getProduct() == 0);
    assertTrue(entities.get(0).getProduct2() == 0);
    assertTrue(entities.get(1).getSum() == 3);
    assertTrue(entities.get(1).getSum2() == 9);
    assertTrue(entities.get(1).getProduct() == 2);
    assertTrue(entities.get(1).getProduct2() == 6);
  }

  @Test
  public void testPreAndPostUpsert() {
    GlobalCalculatorEntity entity = new GlobalCalculatorEntity(8, 8);
    entity = em.upsert(entity);
    assertTrue(entity.getSum() == 16);
    assertTrue(entity.getSum2() == 64);
    assertTrue(entity.getProduct() == 64);
    assertTrue(entity.getProduct2() == 256);
  }

  @Test
  public void testPreAndPostUpsert_List() {
    List<GlobalCalculatorEntity> entities = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      GlobalCalculatorEntity entity = new GlobalCalculatorEntity(i + 6, i + 2);
      entities.add(entity);
    }
    entities = em.upsert(entities);
    assertTrue(entities.get(0).getSum() == 8);
    assertTrue(entities.get(0).getSum2() == 32);
    assertTrue(entities.get(0).getProduct() == 12);
    assertTrue(entities.get(0).getProduct2() == 48);
    assertTrue(entities.get(1).getSum() == 10);
    assertTrue(entities.get(1).getSum2() == 40);
    assertTrue(entities.get(1).getProduct() == 21);
    assertTrue(entities.get(1).getProduct2() == 84);
  }

  @Test
  public void testPreAndPostDelete() {
    GlobalCalculatorEntity entity = new GlobalCalculatorEntity();
    entity = em.insert(entity);
    entity.setOperand1(1);
    entity.setOperand2(2);
    em.delete(entity);
    assertTrue(entity.getSum() == 3);
    assertTrue(entity.getSum2() == 15);
    assertTrue(entity.getProduct() == 2);
    assertTrue(entity.getProduct2() == 10);
  }

  @Test
  public void testPreAndPostDelete_List() {
    List<GlobalCalculatorEntity> entities = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      GlobalCalculatorEntity entity = new GlobalCalculatorEntity(i + 1, i + 1);
      entities.add(entity);
    }
    entities = em.insert(entities);
    em.delete(entities);
    assertTrue(entities.get(0).getSum() == 2);
    assertTrue(entities.get(0).getSum2() == 10);
    assertTrue(entities.get(0).getProduct() == 1);
    assertTrue(entities.get(0).getProduct2() == 5);
    assertTrue(entities.get(1).getSum() == 4);
    assertTrue(entities.get(1).getSum2() == 20);
    assertTrue(entities.get(1).getProduct() == 4);
    assertTrue(entities.get(1).getProduct2() == 20);
  }

  @Test
  public void testPostLoad() {
    GlobalCalculatorEntity entity = new GlobalCalculatorEntity(6, 9);
    entity = em.insert(entity);
    entity = em.load(GlobalCalculatorEntity.class, entity.getId());
    assertTrue(entity.getSum() == 15);
    assertTrue(entity.getSum2() == 90);
    assertTrue(entity.getProduct() == 54);
    assertTrue(entity.getProduct2() == 54 * 6);
  }

  @Test
  public void testPreAndPostLoad_List() {
    List<GlobalCalculatorEntity> entities = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      GlobalCalculatorEntity entity = new GlobalCalculatorEntity(i + 5, i + 6);
      entities.add(entity);
    }
    entities = em.insert(entities);
    List<Long> ids = new ArrayList<>();
    for (GlobalCalculatorEntity entity : entities) {
      ids.add(entity.getId());
    }
    entities = em.loadById(GlobalCalculatorEntity.class, ids);
    assertTrue(entities.get(0).getSum() == 11);
    assertTrue(entities.get(0).getSum2() == 66);
    assertTrue(entities.get(0).getProduct() == 30);
    assertTrue(entities.get(0).getProduct2() == 180);
    assertTrue(entities.get(1).getSum() == 13);
    assertTrue(entities.get(1).getSum2() == 78);
    assertTrue(entities.get(1).getProduct() == 42);
    assertTrue(entities.get(1).getProduct2() == 42 * 6);
  }

  @Test
  public void testPreAndPostLoad_Query() {
    em.deleteAll(GlobalCalculatorEntity.class);
    GlobalCalculatorEntity entity = new GlobalCalculatorEntity(8, 9);
    entity = em.insert(entity);
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    EntityQueryRequest request = em.createEntityQueryRequest(String.format(
        "SELECT * FROM %s WHERE __key__ = @1", GlobalCalculatorEntity.class.getSimpleName()));
    request.addPositionalBinding(entity.getKey());
    QueryResponse<GlobalCalculatorEntity> response = em
        .executeEntityQueryRequest(GlobalCalculatorEntity.class, request);
    List<GlobalCalculatorEntity> entities = response.getResults();
    assertTrue(entities.get(0).getSum() == 17);
    assertTrue(entities.get(0).getSum2() == 17 * 6);
    assertTrue(entities.get(0).getProduct() == 72);
    assertTrue(entities.get(0).getProduct2() == 72 * 6);
  }

}
