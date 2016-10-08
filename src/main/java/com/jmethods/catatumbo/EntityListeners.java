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
 * Specifies the callback listener classes to be used for an entity. This
 * annotation may be applied to:
 * <ul>
 * <li>Entity classes (classes that have an annotation of {@link Entity}).</li>
 * <li>Mapped super classes (classes that have an annotation of
 * {@link MappedSuperClass}).</li>
 * </ul>
 * 
 * <p>
 * The listeners will be invoked in the order they are defined. The order of
 * execution is as follows:
 * </p>
 * <ul>
 * <li><strong>Default Listeners</strong> - Default listeners are
 * {@link EntityListener}s that are registered with the entity manager using
 * {@link EntityManager#setDefaultListeners(Class...)}. Default listeners are
 * invoked for all types of entities that are managed by the
 * {@link EntityManager}.</li>
 * <li><strong>External Listeners</strong> - External listeners are attached to
 * an {@link Entity} or {@link MappedSuperClass} using the
 * {@link EntityListeners} annotation. Super class listeners are executed first,
 * followed by subclass' listeners.</li>
 * <li><strong>Internal Listeners</strong> - Internal listeners are callback
 * methods defined within the {@link Entity} or {@link MappedSuperClass}. The
 * signature of these methods differs slightly from the signature of methods in
 * the {@link EntityListener}. The signature should be of the form
 * <code>public void methodName()</code>. Internal listeners defined in the
 * super class are executed first and then the subclass listeners.</li>
 * </ul>
 * 
 * <p>
 * It is possible to exclude the execution of Default Listeners and listeners
 * defined in the super classes (both external and internal) using the
 * annotations {@link ExcludeDefaultListeners} and
 * {@link ExcludeSuperclassListeners} annotations, respectively.
 * </p>
 * 
 * <p>
 * Callback methods may throw RuntimeExceptions. All RuntimeExceptions will be
 * re-thrown as {@link EntityManagerException} with the original exception set
 * as the cause.
 * </p>
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface EntityListeners {

	/**
	 * The callback listener classes.
	 * 
	 * @return the callback listener classes.
	 */
	Class<?>[] value();

}
