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
 * Specifies a persistent property of an entity whose value is an instance of an
 * embeddable class. The embeddable class must be annotated as
 * {@link Embeddable}. Each primitive field (processed recursively, if the
 * embedded object contains nested embedded objects) in the embedded object is
 * created as a property in the Cloud Datastore.
 * 
 * The class that has an Embedded field, must have a corresponding accessor
 * methods. For example, if <code>homeAddress</code> is of type
 * <code>Address</code> and is a field defined in <code>Person</code> class,
 * then the <code>Person</code> class must have
 * <code>setHomeAddress(Address homeAddress)</code> and
 * <code>Address getHomeAddress()</code> methods.
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Embedded {
	// No special configuration
}
