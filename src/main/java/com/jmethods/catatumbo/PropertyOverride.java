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
 * Used to override mapping of a property. This annotation must be specified on
 * the top-level entity to override any properties of {@link Embedded} objects
 * or a {@link MappedSuperClass}.
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface PropertyOverride {

	/**
	 * The name of the property whose mapping is to be overridden.
	 * 
	 * @return name of the property whose mapping is to be overridden.
	 */
	String name();

	/**
	 * The override mapping information.
	 * 
	 * @return override mapping information.
	 */
	Property property();

}
