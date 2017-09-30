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

package com.jmethods.catatumbo.impl;

import java.lang.annotation.Annotation;

import com.jmethods.catatumbo.PostDelete;
import com.jmethods.catatumbo.PostInsert;
import com.jmethods.catatumbo.PostLoad;
import com.jmethods.catatumbo.PostUpdate;
import com.jmethods.catatumbo.PostUpsert;
import com.jmethods.catatumbo.PreDelete;
import com.jmethods.catatumbo.PreInsert;
import com.jmethods.catatumbo.PreUpdate;
import com.jmethods.catatumbo.PreUpsert;

/**
 * Enumeration of various callback types.
 * 
 * @author Sai Pullabhotla
 *
 */
public enum CallbackType {

  /**
   * Pre insert
   */
  PRE_INSERT(PreInsert.class),

  /**
   * Pre update
   */
  PRE_UPDATE(PreUpdate.class),

  /**
   * Pre upsert
   */
  PRE_UPSERT(PreUpsert.class),

  /**
   * Pre delete
   */
  PRE_DELETE(PreDelete.class),

  /**
   * Post load
   */
  POST_LOAD(PostLoad.class),

  /**
   * Post insert
   */
  POST_INSERT(PostInsert.class),

  /**
   * Post update
   */
  POST_UPDATE(PostUpdate.class),

  /**
   * Post upsert
   */
  POST_UPSERT(PostUpsert.class),

  /**
   * Post delete
   */
  POST_DELETE(PostDelete.class);

  /**
   * Annotation class for this callback type
   */
  private final Class<? extends Annotation> annotationClass;

  /**
   * Creates a new instance of <code>CallbackEventType</code>.
   * 
   * @param annotationClass
   *          the annotation for this callback type
   */
  private CallbackType(Class<? extends Annotation> annotationClass) {
    this.annotationClass = annotationClass;
  }

  /**
   * Returns the annotation class for this callback type.
   * 
   * @return the annotation class for this callback type.
   */
  public Class<? extends Annotation> getAnnotationClass() {
    return annotationClass;
  }

}
