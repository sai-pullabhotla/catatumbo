/*
 * Copyright 2017 Sai Pullabhotla.
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

package com.jmethods.catatumbo.impl;

import java.util.List;

import com.jmethods.catatumbo.DatastoreStats;
import com.jmethods.catatumbo.EntityManagerException;
import com.jmethods.catatumbo.EntityQueryRequest;
import com.jmethods.catatumbo.QueryResponse;
import com.jmethods.catatumbo.Tenant;
import com.jmethods.catatumbo.stats.StatCompositeIndex;
import com.jmethods.catatumbo.stats.StatCompositeIndexNs;
import com.jmethods.catatumbo.stats.StatConstants;
import com.jmethods.catatumbo.stats.StatKind;
import com.jmethods.catatumbo.stats.StatKindNs;
import com.jmethods.catatumbo.stats.StatTotal;
import com.jmethods.catatumbo.stats.StatTotalNs;

/**
 * Default implementation of {@link DatastoreStats}.
 * 
 * @author Sai Pullabhotla
 *
 */
public class DefaultDatastoreStats implements DatastoreStats {

  /**
   * Entity manager
   */
  private DefaultEntityManager entityManager;

  /**
   * Creates a new instance of <code>DefaultDatastoreStats</code>.
   * 
   * @param entityManager
   *          the entity manager
   */
  DefaultDatastoreStats(DefaultEntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public StatTotal getSummary() {
    String currentNamespace = Tenant.getNamespace();
    try {
      Tenant.setNamespace("");
      return entityManager.load(StatTotal.class, StatConstants.ID_TOTAL_ENTITY_USAGE);
    } finally {
      Tenant.setNamespace(currentNamespace);
    }
  }

  @Override
  public StatTotalNs getSummaryNs() {
    return entityManager.load(StatTotalNs.class, StatConstants.ID_TOTAL_ENTITY_USAGE);
  }

  @Override
  public StatTotalNs getSummaryNs(String namespace) throws EntityManagerException {
    String currentNamespace = Tenant.getNamespace();
    try {
      Tenant.setNamespace(namespace);
      return entityManager.load(StatTotalNs.class, StatConstants.ID_TOTAL_ENTITY_USAGE);
    } finally {
      Tenant.setNamespace(currentNamespace);
    }
  }

  @Override
  public List<StatKind> getKinds() {
    String currentNamespace = Tenant.getNamespace();
    try {
      Tenant.setNamespace("");
      final String query = "SELECT * FROM " + StatConstants.STAT_KIND;
      EntityQueryRequest queryRequest = entityManager.createEntityQueryRequest(query);
      QueryResponse<StatKind> queryResponse = entityManager
          .executeEntityQueryRequest(StatKind.class, queryRequest);
      return queryResponse.getResults();
    } finally {
      Tenant.setNamespace(currentNamespace);
    }
  }

  @Override
  public StatKind getKind(String kind) {
    String currentNamespace = Tenant.getNamespace();
    try {
      Tenant.setNamespace("");
      final String query = "SELECT * FROM " + StatConstants.STAT_KIND + " WHERE "
          + StatConstants.PROP_KIND_NAME + "=@1";
      EntityQueryRequest queryRequest = entityManager.createEntityQueryRequest(query);
      queryRequest.addPositionalBinding(kind);
      QueryResponse<StatKind> queryResponse = entityManager
          .executeEntityQueryRequest(StatKind.class, queryRequest);
      List<StatKind> entities = queryResponse.getResults();
      if (!entities.isEmpty()) {
        return entities.get(0);
      }
      return null;
    } finally {
      Tenant.setNamespace(currentNamespace);
    }
  }

  @Override
  public List<StatKindNs> getKindsNs() {
    final String query = "SELECT * FROM " + StatConstants.STAT_KIND_NS;
    EntityQueryRequest queryRequest = entityManager.createEntityQueryRequest(query);
    QueryResponse<StatKindNs> queryResponse = entityManager
        .executeEntityQueryRequest(StatKindNs.class, queryRequest);
    return queryResponse.getResults();
  }

  @Override
  public StatKindNs getKindNs(String kind) {
    final String query = "SELECT * FROM " + StatConstants.STAT_KIND_NS + " WHERE "
        + StatConstants.PROP_KIND_NAME + "=@1";
    EntityQueryRequest queryRequest = entityManager.createEntityQueryRequest(query);
    queryRequest.addPositionalBinding(kind);
    QueryResponse<StatKindNs> queryResponse = entityManager
        .executeEntityQueryRequest(StatKindNs.class, queryRequest);
    List<StatKindNs> entities = queryResponse.getResults();
    if (!entities.isEmpty()) {
      return entities.get(0);
    }
    return null;
  }

  @Override
  public StatKindNs getKindNs(String namespace, String kind) {
    String currentNamespace = Tenant.getNamespace();
    try {
      Tenant.setNamespace(namespace);
      return getKindNs(kind);
    } finally {
      Tenant.setNamespace(currentNamespace);
    }
  }

  @Override
  public List<StatCompositeIndex> getCompositeIndexes() {
    final String currentNamespace = Tenant.getNamespace();
    try {
      Tenant.setNamespace("");
      final String query = "SELECT * FROM " + StatConstants.STAT_COMPOSITE_INDEX;
      EntityQueryRequest request = entityManager.createEntityQueryRequest(query);
      QueryResponse<StatCompositeIndex> response = entityManager
          .executeEntityQueryRequest(StatCompositeIndex.class, request);
      return response.getResults();

    } finally {
      Tenant.setNamespace(currentNamespace);
    }
  }

  @Override
  public List<StatCompositeIndexNs> getCompositeIndexesNs() {
    final String query = "SELECT * FROM " + StatConstants.STAT_COMPOSITE_INDEX_NS;
    EntityQueryRequest request = entityManager.createEntityQueryRequest(query);
    QueryResponse<StatCompositeIndexNs> response = entityManager
        .executeEntityQueryRequest(StatCompositeIndexNs.class, request);
    return response.getResults();
  }

  @Override
  public List<StatCompositeIndexNs> getCompositeIndexesNs(String namespace) {
    final String currentNamespace = Tenant.getNamespace();
    try {
      Tenant.setNamespace(namespace);
      return getCompositeIndexesNs();

    } finally {
      Tenant.setNamespace(currentNamespace);
    }
  }

}
