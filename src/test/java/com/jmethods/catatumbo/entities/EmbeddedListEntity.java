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

package com.jmethods.catatumbo.entities;

import java.util.Objects;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;

@Entity
public class EmbeddedListEntity {
  @Identifier
  private long id;
  private EmbeddedList embeddedList;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public EmbeddedList getEmbeddedList() {
    return embeddedList;
  }

  public void setEmbeddedList(EmbeddedList embeddedList) {
    this.embeddedList = embeddedList;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!this.getClass().equals(obj.getClass())) {
      return false;
    }
    EmbeddedListEntity that = (EmbeddedListEntity) obj;
    return this.id == that.id && Objects.equals(this.embeddedList, that.embeddedList);
  }

  public static EmbeddedListEntity getSample1() {
    EmbeddedListEntity entity = new EmbeddedListEntity();
    entity.setEmbeddedList(EmbeddedList.getSample1());
    return entity;
  }

}
