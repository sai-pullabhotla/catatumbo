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

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies a class whose instances are stored as an intrinsic part of an
 * owning entity and share the identity of the entity. Each of the persistent
 * properties or fields of the embedded object are mapped to the owning entity
 * in the Cloud Datastore. An Embeddable can contain other properties (with or
 * without {@link Property} annotation). Further more, the embeddable objects
 * can contain other {@link Embedded} objects.
 * 
 * <p>
 * Embeddable classes must adhere to the following rules:
 * </p>
 * <ul>
 * <li>Must have a public no-arg constructor</li>
 * <li>For each persistable field, there must be corresponding accessor
 * methods</li>
 * <li>Fields that should not be persisted, must be annotated
 * with @{@link Ignore} annotation.</li>
 * <li>Complex objects may be present in the Embeddable class, as long as they
 * have an annotation of @{@link Embedded} or @{@link Ignore}</li>
 * <li>Persistable fields may have @{@link Property} annotation to map the field
 * with a custom name as well as to specify if the property should be indexed.
 * </li>
 * <li>Embeddable objects must not have {@link Identifier}s, {@link Key}s or
 * {@link ParentKey}s. Even if they contain these annotations, they are ignored.
 * </li>
 * 
 * </ul>
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface Embeddable {
	// Just used for tagging a class as Embeddable
}
