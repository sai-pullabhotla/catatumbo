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

package com.jmethods.catatumbo.impl;

import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.cloud.datastore.Entity;
import com.jmethods.catatumbo.TestUtils;
import com.jmethods.catatumbo.entities.LongId;
import com.jmethods.catatumbo.entities.LongObjectId;
import com.jmethods.catatumbo.entities.StringId;
import com.jmethods.catatumbo.entities.WrappedLongIdEntity;
import com.jmethods.catatumbo.entities.WrappedLongObjectIdEntity;
import com.jmethods.catatumbo.entities.WrappedStringIdEntity;
import com.jmethods.catatumbo.impl.Marshaller.Intent;

/**
 * @author Sai Pullabhotla
 *
 */
public class UnmarshallerTest {
  private static DefaultEntityManager em;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    em = (DefaultEntityManager) TestUtils.getEntityManager();
  }

  @Test
  public void testUnmarshal_StringId() {
    StringId entity = new StringId();
    entity.setGreetings("Hello");
    entity.setId("myid");
    Entity nativeEntity = (Entity) Marshaller.marshal(em, entity, Intent.INSERT);
    StringId entity2 = Unmarshaller.unmarshal(nativeEntity, StringId.class);
    assertTrue(entity.equals(entity2));
  }

  @Test
  public void testUnmarshal_LongObjectId() {
    LongObjectId entity = new LongObjectId();
    entity.setComment("Hello");
    entity.setId(900001L);
    Entity nativeEntity = (Entity) Marshaller.marshal(em, entity, Intent.INSERT);
    LongObjectId entity2 = Unmarshaller.unmarshal(nativeEntity, LongObjectId.class);
    assertTrue(entity.equals(entity2));
  }

  @Test
  public void testUnmarshal_LongId() {
    LongId entity = new LongId();
    entity.setField1("Hello");
    entity.setId(90000L);
    Entity nativeEntity = (Entity) Marshaller.marshal(em, entity, Intent.INSERT);
    LongId entity2 = Unmarshaller.unmarshal(nativeEntity, LongId.class);
    assertTrue(entity.equals(entity2));
  }

  @Test
  public void testUnmarshal_WrappedLongId() {
    WrappedLongIdEntity entity = WrappedLongIdEntity.getSample3();
    Entity nativeEntity = (Entity) Marshaller.marshal(em, entity, Intent.INSERT);
    WrappedLongIdEntity entity2 = Unmarshaller.unmarshal(nativeEntity, WrappedLongIdEntity.class);
    assertTrue(entity.equals(entity2));
  }

  @Test
  public void testUnmarshal_WrappedLongObjectId() {
    WrappedLongObjectIdEntity entity = WrappedLongObjectIdEntity.getSample4();
    Entity nativeEntity = (Entity) Marshaller.marshal(em, entity, Intent.UPDATE);
    WrappedLongObjectIdEntity entity2 = Unmarshaller.unmarshal(nativeEntity,
        WrappedLongObjectIdEntity.class);
    assertTrue(entity.equals(entity2));
  }

  @Test
  public void testUnmarshal_WrappedStringId() {
    WrappedStringIdEntity entity = WrappedStringIdEntity.getSample4();
    Entity nativeEntity = (Entity) Marshaller.marshal(em, entity, Intent.UPDATE);
    WrappedStringIdEntity entity2 = Unmarshaller.unmarshal(nativeEntity,
        WrappedStringIdEntity.class);
    assertTrue(entity.equals(entity2));
  }

}
