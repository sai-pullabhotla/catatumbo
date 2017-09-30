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
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Annotation for automatically setting the entity's modification timestamp. This annotation can be
 * applied to a field of an {@link Entity} or {@link MappedSuperClass}. Only one field can have this
 * annotation in a given Entity hierarchy.
 * 
 * <p>
 * Valid data types for the field include -
 * </p>
 * <ul>
 * <li>{@link Date}</li>
 * <li>{@link Calendar}</li>
 * <li>{@link Long}</li>
 * <li>long</li>
 * <li>{@link OffsetDateTime}}</li>
 * <li>{@link ZonedDateTime}}</li>
 * </ul>
 * 
 * <p>
 * The framework sets the field with this annotation during the following operations:
 * </p>
 * 
 * <ul>
 * <li>INSERT - Any variation of the insert methods automatically set the field with this annotation
 * to the current JVM's timestamp. Furthermore, if the entity also has a field with
 * {@link CreatedTimestamp} annotation, the framework ensures that both creation and modification
 * timestamp are exactly equal.</li>
 * <li>UPDATE - Any variation of the update methods set this field to the current timestamp of the
 * JVM.</li>
 * <li>UPSERT - Any variation of the upsert methods set this field to the current timestamp of the
 * JVM.</li>
 * </ul>
 *
 * <p>
 * The field with this annotation is no different than any other property of the entity. This means,
 * this field could also have the {@link Property} annotation and specify the property name to use
 * in the Datastore as well as whether or not the property should be indexed. The field will be
 * mapped using the specified {@link Mapper}, if a {@link PropertyMapper} is specified, otherwise a
 * default mapper for the data type is used.
 * </p>
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UpdatedTimestamp {
  // Marker
}
