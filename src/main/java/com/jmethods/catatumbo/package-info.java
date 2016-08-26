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

/**
 * This package contains the public API for mapping and persisting model objects
 * to the Google Cloud Datastore and vice versa. Catatumbo provides the
 * following annotations to take away the boilerplate code that is needed for
 * persisting objects to the Google Cloud Datastore and mapping the Google Cloud
 * Datastore entities to your model objects:
 * 
 * <ul>
 * <li>{@link com.jmethods.catatumbo.Entity}</li>
 * <li>{@link com.jmethods.catatumbo.Identifier}</li>
 * <li>{@link com.jmethods.catatumbo.Property}</li>
 * <li>{@link com.jmethods.catatumbo.Key}</li>
 * <li>{@link com.jmethods.catatumbo.ParentKey}</li>
 * <li>{@link com.jmethods.catatumbo.Embeddable}</li>
 * <li>{@link com.jmethods.catatumbo.Embedded}</li>
 * <li>{@link com.jmethods.catatumbo.PropertyOverrides}</li>
 * <li>{@link com.jmethods.catatumbo.PropertyOverride}</li>
 * <li>{@link com.jmethods.catatumbo.Ignore}</li>
 * </ul>
 * 
 */
package com.jmethods.catatumbo;