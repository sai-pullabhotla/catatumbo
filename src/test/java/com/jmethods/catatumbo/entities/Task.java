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

package com.jmethods.catatumbo.entities;

import java.util.Date;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class Task {

  @Identifier
  private long id;

  private String name;

  private int priority;

  private Date completionDate;

  private boolean complete;

  public Task() {

  }

  public Task(long id, String name, int priority, boolean complete) {
    this.id = id;
    this.name = name;
    this.priority = priority;
    this.complete = complete;
  }

  /**
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * @param id
   *          the id to set
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the priority
   */
  public int getPriority() {
    return priority;
  }

  /**
   * @param priority
   *          the priority to set
   */
  public void setPriority(int priority) {
    this.priority = priority;
  }

  /**
   * @return the completionDate
   */
  public Date getCompletionDate() {
    return completionDate;
  }

  /**
   * @param completionDate
   *          the completionDate to set
   */
  public void setCompletionDate(Date completionDate) {
    this.completionDate = completionDate;
  }

  /**
   * @return the complete
   */
  public boolean isComplete() {
    return complete;
  }

  /**
   * @param complete
   *          the complete to set
   */
  public void setComplete(boolean complete) {
    this.complete = complete;
  }

}
