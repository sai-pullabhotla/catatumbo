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

/**
 * Specifies that a class is an entity. Classes with this annotation can be
 * managed by the {@link EntityManager} to save objects to the Cloud Datastore
 * and load objects from the Cloud Datastore.
 *
 * <p>
 * Entity Classes and other persistence classes ({@link Embeddable},
 * {@link MappedSuperClass}) may either use classic Java Beans pattern or the
 * Builder pattern. Builder Pattern is recommended when persistence objects that
 * need to be immutable.
 * </p>
 * 
 * <p>
 * Classes annotated with this annotation must adhere to the following rules:
 * </p>
 * <ul>
 * <li>If using the classic Java Beans pattern, the class must have a public
 * no-arg (zero arguments) constructor. Absence of a public no-arg constructor
 * assumes that the class is using the Builder pattern.</li>
 * <li>For Builder pattern, the class must have a public static
 * {@code newBuilder} or {@code builder} method that returns an instance of
 * corresponding Builder class.</li>
 * <li>When using the Builder pattern, the Builder class must have a public
 * {@code build} method.</li>
 * <li>Must have exactly one field annotated with @{@link Identifier}
 * annotation.</li>
 * <li>The type of identifier field must be a supported type as defined in the
 * {@link Identifier} documentation.</li>
 * <li>May contain zero or more fields. All fields that need to be persisted to
 * the Cloud Datastore must have public accessor methods.</li>
 * <li>For classic Java Beans pattern, all persistable fields must also have
 * their corresponding mutator methods.</li>
 * <li>For the Builder pattern, all persistable fields must have their
 * corresponding mutator methods in the Builder class. The mutator methods may
 * use any of the below naming conventions:
 * <ul>
 * <li>setXXX (e.g. {@code setFirstName})</li>
 * <li>withXXX (e.g. {@code withFirstName})</li>
 * <li>xxx (e.g. {@code firstName})</li>
 * </ul>
 * </li>
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
 * <li>May have zero or more embedded objects with an annotation
 * of @{@link Embedded}.
 * <li>All other fields can be any of the types for which a {@link Mapper}
 * exists. The framework provides Mappers for various commonly used types.
 * CustomMappers can be specified using
 * {@link MapperFactory#setDefaultMapper(java.lang.reflect.Type, Mapper)} or
 * {@link PropertyMapper} annotation.</li>
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
