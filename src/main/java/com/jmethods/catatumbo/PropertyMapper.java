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

import com.jmethods.catatumbo.mappers.IntegerMapper;

/**
 * Specifies a custom {@link Mapper} for a field in the model class (Entity,
 * Embedded or MappedSuperClass). Presence of this annotation on a field
 * overrides the default {@link Mapper} used for the field type.
 * 
 * <p>
 * Customer Mappers must conform to the following specifications:
 * </p>
 * <ul>
 * <li>The Customer Mapper class must implement the {@link Mapper}
 * interface</li>
 * <li>Custom Mappers must have a public constructor with a parameter of
 * {@link java.lang.reflect.Field}.</li>
 * </ul>
 * 
 * <p>
 * Note that, unlike the built-in mappers where {@link Mapper} instances are
 * reused where possible, custom mappers are created once for each field. To
 * clarify this further, there is only one instance of {@link IntegerMapper} in
 * the JVM that handles mapping of int/Integer fields in all entities, but
 * that's not the same case with custom mappers.
 * </p>
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface PropertyMapper {

	/**
	 * The class that implements the {@link Mapper} interface.
	 * 
	 * @return the class that implements the {@link Mapper} interface.
	 */
	Class<? extends Mapper> value();

}
