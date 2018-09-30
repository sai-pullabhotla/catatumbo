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
 * Used to implement optimistic locking to prevent updates on a stale entity. This annotation can be
 * applied to a single field in an entity hierarchy (Entity class and any MappedSuperClasses). The
 * field type must be primitive <code>long</code>. Each time the entity is updated, the version
 * property will be incremented by 1.
 * 
 * <h1>Non-transactional Updates</h1>
 * <p>
 * When an entity containing a property with this annotation is updated (without using a
 * transaction), the EntityManager first starts a new transaction, fetches the current entity from
 * the Datastore, makes sure the version is same as the one being updated. If they defer,
 * {@link OptimisticLockException} will be thrown. Otherwise, the the entity will be updated by
 * incrementing the version and the transaction is committed.
 * </p>
 * 
 * <h1>Transactional updates</h1>
 * <p>
 * When an entity containing a property with this annotation is updated using a transaction, the
 * existing transaction is used to fetch the current entity from the Datastore, make sure the
 * version is same as the one being updated. If they defer, {@link OptimisticLockException} will be
 * thrown. Otherwise, the the entity will be updated by incrementing the version. The framwork does
 * not commit the transaction, but will leave it upto the caller who created the transaction.
 * </p>
 * 
 * <p>
 * Entities that have a field with {@link Version} annotation must have a corresponding getter and
 * setter method for the field. However, applications should never call the setter method. The field
 * may also have {@link Property} annotation to specify the property name in the Cloud Datastore and
 * whether or not the property should be indexed.
 * </p>
 * 
 * <p>
 * When an entity containing this annotation is inserted into the Datastore, the initialized value
 * is used as the initial version. For example, if you declare <code>private long version;</code> in
 * your model class, when this entity is inserted, the version property is set to zero in the
 * Datastore. If you need a different starting version, initialize the version field accordingly.
 * For example, <code>private long version=10L;</code>. This will set the version property to 10
 * when inserting an entity. Subsequent updates to the entity increment the version by 1.
 * </p>
 * 
 * <p style="color: red">
 * <strong>Note that the optimistic lock check and version increment only happens when the
 * {@link DatastoreAccess#update(Object)} and {@link DatastoreAccess#update(java.util.List)} is
 * called. Other methods that may potentially update the entities (e.g. UPSERT) will NOT perform the
 * optimistic lock check or increment the version</strong>.
 * </p>
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Version {
  // Marker
}
