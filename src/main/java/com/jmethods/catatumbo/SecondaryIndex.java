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
 * This annotation can be specified on any simple field of an {@link Entity},
 * {@link MappedSuperClass} or {@link Embeddable}. The framework creates an
 * additional property in the Datastore to index the annotated field/property.
 * This is useful to create indexes on String types with a consistent case
 * (UPPER or lower). The framework provides indexers for String types to create
 * indexes in either lower case or upper case. Custom Indexers can be specified
 * using the {@link PropertyIndexer} annotation.
 * 
 * <p>
 * <strong>Note: </strong> The indexing happens after a field is mapped to the
 * Datastore using the {@link Mapper} associated to the field. The output
 * produced by the {@link Mapper} is then fed to the {@link Indexer}.
 * </p>
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SecondaryIndex {

	/**
	 * Name of the secondary index. If not specified, the framework assigns a
	 * name by prefixing the original property name with a $ symbol. For example
	 * -
	 * 
	 * <pre>
	 * &#64;SecondaryIndex
	 * private String lastName;
	 * </pre>
	 * 
	 * This will create two properties in the Cloud Datastore - lastName and
	 * $lastName. The $lastName will have the last names in lower case to
	 * support case insensitive searching/sorting.
	 * 
	 * <pre>
	 * &#64;SecondaryIndex
	 * &#64;Property(name = "lname")
	 * private String lastName;
	 * </pre>
	 * 
	 * The above code will create two properties - lname and $lname.
	 * 
	 * @return the name for the secondary index.
	 */
	String name() default "";

}
