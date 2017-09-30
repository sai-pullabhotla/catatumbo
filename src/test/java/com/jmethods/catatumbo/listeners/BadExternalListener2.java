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

package com.jmethods.catatumbo.listeners;

import com.jmethods.catatumbo.EntityListener;
import com.jmethods.catatumbo.PostDelete;
import com.jmethods.catatumbo.PostInsert;
import com.jmethods.catatumbo.PostUpdate;

/**
 * @author Sai Pullabhotla
 *
 */
@EntityListener
public class BadExternalListener2 {

  @PostInsert
  public void afterInsert(Object obj) {
    System.out.printf("Object %s of type %s inserted\n", obj, obj.getClass().getName());
  }

  @PostUpdate
  public void afterUpdate(Object obj) {
    System.out.printf("Object %s of type %s updated\n", obj, obj.getClass().getName());
  }

  @PostDelete
  public void afterDelete(Object obj) {
    System.out.printf("Object %s of type %s deleted\n", obj, obj.getClass().getName());
  }

  @PostInsert
  public void afterInsert2(Object obj) {
    System.out.printf("Object %s of type %s inserted\n", obj, obj.getClass().getName());
  }

}
