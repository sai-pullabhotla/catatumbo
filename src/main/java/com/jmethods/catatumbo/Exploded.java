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

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicates that the {@link Embedded} object of an entity should be stored using the Exploded
 * strategy. With Exploded strategy, the {@link Embedded} object, and nested embedded objects, if
 * any, are exploded into separate properties when stored into the Datastore. This annotation can
 * exist on any {@link Embedded} field of an {@link Entity} or {@link MappedSuperClass}. Annotating
 * a nested Embedded field with this annotation will not have any effect as the storage strategy is
 * enforced at the Entity level.
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Exploded {
  // Marker
}
