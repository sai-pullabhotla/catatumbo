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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * @author Sai Pullabhotla
 *
 */
public class TenantTest {

  @Test
  public void test1() {
    assertNull(Tenant.getNamespace());
  }

  @Test
  public void test2() {
    String oldNamespace = Tenant.getNamespace();
    Tenant.setNamespace(Thread.currentThread().getName());
    assertEquals(Thread.currentThread().getName(), Tenant.getNamespace());
    Tenant.setNamespace(oldNamespace);
  }

}
