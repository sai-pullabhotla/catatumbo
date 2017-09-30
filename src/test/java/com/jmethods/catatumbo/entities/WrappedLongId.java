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

/**
 * @author Sai Pullabhotla
 *
 */
public class WrappedLongId {

  private long value;

  public WrappedLongId(long value) {
    this.value = value;
  }

  public long getValue() {
    return value;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("WrappedLongId [value=").append(value).append("]");
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
    WrappedLongId that = (WrappedLongId) obj;
    return this.value == that.value;
  }

}
