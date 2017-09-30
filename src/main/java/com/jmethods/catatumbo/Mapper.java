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

import com.google.cloud.datastore.Value;
import com.google.cloud.datastore.ValueBuilder;

/**
 * Contract for mapping model values to/from Cloud Datastore.
 * 
 * @author Sai Pullabhotla
 *
 */
public interface Mapper {

  /**
   * Maps the given Model object to native Cloud Datastore value.
   * 
   * @param input
   *          the input to map
   * @return the equivalent native value
   * @throws MappingException
   *           if the input is not compatible.
   */
  public ValueBuilder<?, ?, ?> toDatastore(Object input);

  /**
   * Maps the given native Cloud Datastore value to equivalent model object.
   * 
   * @param input
   *          the native Cloud Datastore value
   * @return the equivalent model object
   * @throws MappingException
   *           if the input is not compatible.
   */
  public Object toModel(Value<?> input);

}
