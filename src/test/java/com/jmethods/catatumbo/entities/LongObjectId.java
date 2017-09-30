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

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class LongObjectId {

  @Identifier
  private Long id;

  private String comment;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the comment
   */
  public String getComment() {
    return comment;
  }

  /**
   * @param comment
   *          the comment to set
   */
  public void setComment(String comment) {
    this.comment = comment;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("LongObjectId [id=").append(id).append(", comment=").append(comment).append("]");
    return builder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || !this.getClass().equals(obj.getClass())) {
      return false;
    }
    LongObjectId that = (LongObjectId) obj;
    return Objects.equals(this.id, that.id) && Objects.equals(this.comment, that.comment);
  }

}
