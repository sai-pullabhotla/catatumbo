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
import com.jmethods.catatumbo.ExcludeDefaultListeners;
import com.jmethods.catatumbo.ExcludeSuperclassListeners;
import com.jmethods.catatumbo.PostInsert;
import com.jmethods.catatumbo.PreInsert;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
@ExcludeSuperclassListeners
@ExcludeDefaultListeners
public class Dog extends Pet {

  @PreInsert
  public void insertingDog() {
    if (value.trim().length() > 0) {
      value += "->";
    }
    value += Dog.class.getSimpleName() + "." + PreInsert.class.getSimpleName();
  }

  @PostInsert
  public void dogInserted() {
    if (value.trim().length() > 0) {
      value += "->";
    }
    value += Dog.class.getSimpleName() + "." + PostInsert.class.getSimpleName();
  }

}
