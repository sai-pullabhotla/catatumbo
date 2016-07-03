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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jmethods.catatumbo.impl.DataType;

/**
 * Specifies that a class is an entity. Classes with this annotation can be
 * managed by the {@link EntityManager} to save objects to the Cloud Datastore
 * and load objects from the Cloud Datastore.
 * 
 * <p>
 * Classes annotated with this annotation must adhere to the following rules:
 * </p>
 * <ul>
 * <li>Must have a public no-arg (zero arguments) constructor.</li>
 * <li>Must have exactly one field annotated with @{@link Identifier}
 * annotation.</li>
 * <li>The identifier field must be a primitive <code>long</code>, {@link Long}
 * or {@link String}.
 * <li>May contain zero or more fields. All fields that need to be persisted to
 * the Cloud Datastore must have public accessor methods.</li>
 * <li>If a field is to be skipped by the EntityManager, it needs to have @
 * {@link Ignore} annotation.</li>
 * <li>May have no more than one field with @{@link Key} annotation which will
 * hold the full key to the entity.</li>
 * <li>Field annotated with @{@link Key} must have a data type of
 * {@link DatastoreKey} .</li>
 * <li>May have no more than one field with @{@link ParentKey} annotation which
 * will hold the full key to the parent entity.</li>
 * <li>Field annotated with @{@link ParentKey} must have a data type of
 * {@link DatastoreKey}.</li>
 * <li>All other fields can be any of the types defined in the {@link DataType}
 * enum and may have @{@link Property} annotation.</li>
 * </ul>
 *
 * @author Sai Pullabhotla
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {

	/**
	 * Specifies the entity kind (equivalent to table name). If not specified,
	 * the kind defaults to unqualified name of the entity class.
	 *
	 * @return the entity kind
	 */
	String kind() default "";

}
