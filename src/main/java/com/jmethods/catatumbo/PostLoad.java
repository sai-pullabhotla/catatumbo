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
 * Used for annotating a method as an entity listener method. The annotated
 * method will be invoked after loading an entity. This annotation can be
 * applied to a method within an {@link Entity}, {@link MappedSuperClass} or an
 * {@link EntityListener}.
 * 
 * <p>
 * Note that PostLoad callback is NOT invoked for projection queries.
 * </p>
 * 
 * @see EntityListeners
 * @see EntityListener
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface PostLoad {

}
