/*
 * Copyright 2018 Sai Pullabhotla.
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

import com.google.datastore.v1.TransactionOptions;
import com.google.datastore.v1.TransactionOptions.ReadOnly;
import com.google.datastore.v1.TransactionOptions.ReadWrite;

/**
 * Supported Transaction modes.
 * 
 * @author Sai Pullabhotla
 *
 */
public enum TransactionMode {

  /**
   * Read-only
   */
  READ_ONLY {
    @Override
    public TransactionOptions getNativeTransactionOptions() {
      TransactionOptions.Builder builder = TransactionOptions.newBuilder();
      return builder.setReadOnly(ReadOnly.getDefaultInstance()).build();
    }
  },

  /**
   * Read-write
   */
  READ_WRITE {
    @Override
    public TransactionOptions getNativeTransactionOptions() {
      TransactionOptions.Builder builder = TransactionOptions.newBuilder();
      return builder.setReadWrite(ReadWrite.getDefaultInstance()).build();
    }
  };

  /**
   * Returns the native transaction options that are equivalent to this {@link TransactionMode}.
   * 
   * @return the native transaction options that are equivalent to this {@link TransactionMode}.
   */
  public TransactionOptions getNativeTransactionOptions() {
    return null;
  }

}
