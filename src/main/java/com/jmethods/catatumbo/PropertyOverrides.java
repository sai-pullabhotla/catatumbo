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

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Used to override mappings of properties. Typically used if the entity contains {@link Embedded}
 * objects or inherits a {@link MappedSuperClass}. This annotation must be applied to an
 * {@link Entity} only.
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface PropertyOverrides {

  /**
   * One or more property overrides.
   * 
   * @return the property overrides
   */
  PropertyOverride[] value();

}
