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

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.PreDelete;
import com.jmethods.catatumbo.PreInsert;
import com.jmethods.catatumbo.PreUpdate;
import com.jmethods.catatumbo.PreUpsert;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class InternalCalculatorEntity extends CalculatorEntity {

  /**
   * 
   */
  public InternalCalculatorEntity() {
    super();
  }

  /**
   * @param operand1
   * @param operand2
   */
  public InternalCalculatorEntity(long operand1, long operand2) {
    super(operand1, operand2);
  }

  @PreInsert
  @PreUpdate
  @PreUpsert
  @PreDelete
  public void beforeEvent() {
    setSum(getOperand1() + getOperand2());
    setProduct(getOperand1() * getOperand2());
    setDifference(getOperand1() - getOperand2());
  }

}
