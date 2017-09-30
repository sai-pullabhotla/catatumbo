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
 * Specifies a persistent property of an entity whose value is an instance of an embeddable class.
 * The embeddable class must be annotated as {@link Embeddable}.
 * 
 * <p>
 * Embedded objects in an entity can be stored using two strategies:
 * </p>
 * <ul>
 * <li><strong>EXPLODED</strong> - Each primitive field (processed recursively, if the embedded
 * object contains nested embedded objects) in the embedded object is created as a separate property
 * in the Cloud Datastore.</li>
 * <li><strong>IMPLODED</strong> - The embedded object and any nested embedded objects will be
 * stored as a single property in the Cloud Datastore. The type of the property in the Datastore
 * will be Embedded Entity.</li>
 * </ul>
 * 
 * <p>
 * By default, Embedded objects are stored using the EXPLODED strategy. To change the strategy to
 * IMPLODED, simply add the {@link Imploded} annotation to the Embedded field. You may also annotate
 * the field explicitly with the {@link Exploded} annotation.
 * </p>
 * 
 * <p>
 * The class that has an Embedded field, must have a corresponding accessor methods. For example, if
 * <code>homeAddress</code> is of type <code>Address</code> and is a field defined in
 * <code>Person</code> class, then the <code>Person</code> class must have
 * <code>setHomeAddress(Address homeAddress)</code> and <code>Address getHomeAddress()</code>
 * methods.
 * </p>
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Embedded {

  /**
   * Property name to use for this embedded in the Cloud Datastore. This is only used if the
   * embedded object is stored using the {@link Imploded} strategy.
   * 
   * @return the property name.
   */
  String name() default "";

  /**
   * Whether or not to index this property. This is only used if the embedded object is stored using
   * the {@link Imploded} strategy.
   * 
   * @return Whether or not to index this property.
   */
  boolean indexed() default true;

  /**
   * Whether or not the corresponding field is optional. For fields declared as optional, the
   * property will be omitted when the field has a <code>null</code> value. This is only valid/used
   * if the embedded object is stored using {@link Imploded} strategy.
   * 
   * @return whether or not the corresponding embedded field is optional.
   */
  boolean optional() default false;
}
