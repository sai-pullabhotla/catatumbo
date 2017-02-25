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
import java.util.Calendar;
import java.util.Date;

/**
 * Annotation for automatically setting the entity's creation timestamp. This
 * annotation can be applied to a field of an {@link Entity} or
 * {@link MappedSuperClass}. Only one field can have this annotation in a given
 * Entity hierarchy.
 * 
 * <p>
 * Valid data types for the field include -
 * <ul>
 * <li>{@link Date} - Value will be set to new Date()</li>
 * <li>{@link Calendar} - Value will be set to Calendar.getInstance()</li>
 * <li>{@link Long} - Value will be set to System.currentTimeMillis()</li>
 * <li>long - Value will be set to System.currentTimeMillis()</li>
 * </p>
 *
 * <p>
 * When inserting entities (any variation of insert methods) that have a field
 * with this annotation, the framework sets the corresponding Datastore property
 * to the current timestamp as returned by the host JVM. Any value set in the
 * entity will be ignored/overwritten.
 * </p>
 * 
 * <p>
 * Update and Upsert operations ignore this annotation, meaning the field does
 * not get any special treatment during UPSERT or UPDATE operations. However,
 * any value set in the entity (may be null) will be persisted to the Datastore.
 * </p>
 * 
 * <p>
 * The field with this annotation is no different than any other property of the
 * entity. This means, this field could also have the {@link Property}
 * annotation and specify the property name to use in the Datastore as well as
 * whether or not the property should be indexed. The field will be mapped using
 * the specified {@link Mapper}, if a {@link PropertyMapper} is specified,
 * otherwise a default mapper for the data type is used.
 * </p>
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface CreatedTimestamp {

}
