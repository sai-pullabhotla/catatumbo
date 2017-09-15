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
 * Designates a class whose mapping information is applied to the entities that
 * inherit from it. A class designated with the MappedSuperclass annotation can
 * be mapped in the same way as an entity except that the mappings will apply
 * only to its subclasses. This means, a MappedSuperClass may contain fields
 * with {@link Identifier}, {@link Key}, {@link ParentKey}, {@link Property},
 * {@link Embedded} and {@link Ignore} annotations. The mapping information
 * defined in the MappedSuperClass can be overridden by the Entity using the
 * {@link PropertyOverrides} and {@link PropertyOverride} annotations. This
 * allows an entity to use a different property name or turn on/off the indexing
 * of a property.
 * 
 * <p>
 * It is legal for a MappedSuperClass extend from another MappedSuperClass.
 * </p>
 * 
 * <p>
 * Like {@link Entity entities}, the MappedSuperClass may also use either the
 * classic Java Beans pattern or the Builder pattern. Subclasses of
 * MappedSuperClass must use the same design pattern.
 * </p>
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface MappedSuperClass {

}
