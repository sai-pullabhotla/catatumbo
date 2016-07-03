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
 * Specifies the mapped property for a persistable field. It is not necessary to
 * have this annotation on every field of the entity. By default, all field
 * declared in the entity are treated as persistable, unless they are marked
 * with {@link Ignore} annotation.
 *
 * @author Sai Pullabhotla
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Property {

    /**
     * Specifies the name of the property in the Cloud Datastore. If not
     * specified, the field name is used as the property name.
     *
     * @return the property name
     */
    String name() default "";

    /**
     * Specifies if the property should be indexed or not.
     *
     * @return if the property should be indexed or not.
     */
    boolean indexed() default true;

}
