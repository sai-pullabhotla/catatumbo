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
 * Used to specify the Indexer to use for a given property/field of an entity.
 * This annotation can be applied to any field of an {@link Entity},
 * {@link MappedSuperClass} or {@link Embeddable}. The specified indexer must be
 * able to handle the indexing of value returned by the field's {@link Mapper}.
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface PropertyIndexer {
	/**
	 * Returns the indexer class. The specified class must have a public default
	 * (or no-argument constructor).
	 * 
	 * @return the indexer class.
	 */
	Class<? extends Indexer> value();

}
