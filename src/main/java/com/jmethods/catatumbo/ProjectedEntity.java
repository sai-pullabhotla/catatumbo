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

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Specifies that a class is a Projected Entity. ProjectedEntity is similar to a
 * regular {@link Entity}, but typically contains a subset of fields of an
 * entity. Projected Entities are useful for mapping the results of a projection
 * query.
 * <p>
 * Note that results of a projection query can be mapped to an {@link Entity},
 * but having a {@link ProjectedEntity} helps accidental data loss (e.g.
 * persisting an entity mapped from a projection query). The EntityManager
 * restricts persistence of ProjectedEntities. To clarify this further, INSERT,
 * UPDATE and UPSERT operations are not allowed on ProjectedEntities. DELETE
 * operations are still valid on Projected Entities.
 * </p>
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface ProjectedEntity {

	/**
	 * The entity Kind.
	 * 
	 * @return the entity Kind.
	 */
	String kind();

}
