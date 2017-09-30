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
 * Specifies that any default listeners registered with the {@link EntityManager} should be
 * excluded. This annotation can be specified on an {@link Entity} or a {@link MappedSuperClass}.
 * 
 * <p>
 * If this annotation is defined on an {@link Entity}, default listeners are skipped for various
 * life cycle events of that entity. If this annotation is defined on a {@link MappedSuperClass},
 * default listeners are skipped for all entities extending that {@link MappedSuperClass}.
 * </p>
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface ExcludeDefaultListeners {
  // Marker
}
