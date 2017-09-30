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

package com.jmethods.catatumbo.entities;

import java.math.BigDecimal;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class BigDecimalField {

  @Identifier
  private long id;

  private BigDecimal value;

  public BigDecimalField() {

  }

  public BigDecimalField(BigDecimal value) {
    this.value = value;
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @return the value
   */
  public BigDecimal getValue() {
    return value;
  }

  /**
   * @param value
   *          the value to set
   */
  public void setValue(BigDecimal value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || !(obj instanceof BigDecimalField)) {
      return false;
    }
    BigDecimalField that = (BigDecimalField) obj;
    if (this.id != that.id) {
      return false;
    }
    if (this.value == null && that.value == null) {
      return true;
    }
    if (this.value == null || that.value == null) {
      return false;
    }
    return this.value.compareTo(that.value) == 0;
  }

}
