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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base implementation of {@link QueryRequest} interface.
 * 
 * @author Sai Pullabhotla
 *
 */
public abstract class BaseQueryRequest implements QueryRequest {

  /**
   * GQL Query
   */
  protected String query = null;

  /**
   * Named bindings
   */
  protected Map<String, Object> namedBindings = null;

  /**
   * Positional bindings
   */
  protected List<Object> positionalBindings = null;

  /**
   * Whether or not to allow literals in the query
   */
  protected boolean allowLiterals = false;

  /**
   * Creates a new instance of <code>BaseQueryRequest</code>.
   * 
   * @param query
   *          the GQL query string
   */
  public BaseQueryRequest(String query) {
    this.query = query;
    this.allowLiterals = false;
    // Initialize the named and positional bindings.
    namedBindings = new HashMap<>();
    positionalBindings = new ArrayList<>();
  }

  @Override
  public String getQuery() {
    return query;
  }

  /**
   * Sets the GQL query to the given value.
   * 
   * @param query
   *          the GQL query
   */
  public void setQuery(String query) {
    this.query = query;
  }

  @Override
  public Map<String, Object> getNamedBindings() {
    return namedBindings;
  }

  /**
   * Sets the named bindings that are needed for any named parameters in the GQL query.
   * 
   * @param namedBindings
   *          the named bindings.
   * @throws NullPointerException
   *           if the <code>namedBindings</code> argument is <code>null</code>.
   */
  public void setNamedBindings(Map<String, Object> namedBindings) {
    if (namedBindings == null) {
      throw new NullPointerException();
    }
    this.namedBindings = namedBindings;
  }

  @Override
  public List<Object> getPositionalBindings() {
    return positionalBindings;
  }

  /**
   * Sets the positional bindings that are needed for any positional parameters in the GQL Query.
   * 
   * @param positionalBindings
   *          the positional bindings.
   * @throws NullPointerException
   *           if the <code>positionalArguments</code> is <code>null</code>.
   */
  public void setPositionalBindings(List<Object> positionalBindings) {
    if (positionalBindings == null) {
      throw new NullPointerException();
    }
    this.positionalBindings = positionalBindings;
  }

  /**
   * Adds or replaces the given named binding to the list of named bindings.
   * 
   * @param bindingName
   *          the binding name
   * @param bindingValue
   *          the binding value
   */
  public void setNamedBinding(String bindingName, Object bindingValue) {
    namedBindings.put(bindingName, bindingValue);
  }

  /**
   * Adds the given value to the list of positional bindings.
   * 
   * @param bindingValue
   *          the binding value
   */
  public void addPositionalBinding(Object bindingValue) {
    positionalBindings.add(bindingValue);
  }

  /**
   * Adds the positional bindings that are needed for any positional parameters in the GQL query.
   * 
   * @param first
   *          the first positional binding
   * @param others
   *          subsequent positional bindings, if any
   */
  public void addPositionalBindings(Object first, Object... others) {
    positionalBindings.add(first);
    positionalBindings.addAll(Arrays.asList(others));
  }

  /**
   * Clears the positional bindings, if any.
   */
  public void clearPositionalBindings() {
    positionalBindings.clear();
  }

  /**
   * Clears the named bindings, if any.
   */
  public void clearNamedBindings() {
    namedBindings.clear();
  }

  /**
   * Clears all bindings (positional and named), if any.
   */
  public void clearBindings() {
    clearPositionalBindings();
    clearNamedBindings();
  }

  @Override
  public boolean isAllowLiterals() {
    return allowLiterals;
  }

  /**
   * Allows or disallows presence of any literals (constants or synthetic literals) in the query.
   * 
   * @param allowLiterals
   *          whether or not to allow literals in the query.
   */
  public void setAllowLiterals(boolean allowLiterals) {
    this.allowLiterals = allowLiterals;
  }

}
