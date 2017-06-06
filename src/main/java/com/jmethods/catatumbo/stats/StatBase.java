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

package com.jmethods.catatumbo.stats;

import java.io.Serializable;
import java.util.Date;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.MappedSuperClass;
import com.jmethods.catatumbo.Property;

/**
 * A base class for various statistics entities.
 * 
 * @author Sai Pullabhotla
 *
 */
@MappedSuperClass
public abstract class StatBase implements Serializable {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 6745596308816079953L;

	/**
	 * Key of this Statistic entity
	 */
	@Key
	private DatastoreKey key;

	/**
	 * number of items, typically entities
	 */
	@Property(name = StatConstants.PROP_COUNT)
	private long count;

	/**
	 * Size of items
	 */
	@Property(name = StatConstants.PROP_BYTES)
	private long bytes;

	/**
	 * Timestamp when the statistics were generated
	 */
	@Property(name = StatConstants.PROP_TIMESTAMP)
	private Date timestamp;

	/**
	 * Returns the full key of this Statistic entity.
	 * 
	 * @return the full key of this Statistic entity.
	 */
	public DatastoreKey getKey() {
		return key;
	}

	/**
	 * Sets the full key of this Statistic entity.
	 * 
	 * @param key
	 *            the full key of this Statistic entity.
	 */
	public void setKey(DatastoreKey key) {
		this.key = key;
	}

	/**
	 * Returns the number of items (typically number of entities) considered by
	 * the statistic.
	 * 
	 * @return the count the number of items (typically number of entities)
	 *         considered by the statistic.
	 */
	public long getCount() {
		return count;
	}

	/**
	 * Sets the number of items (typically number of entities) considered by the
	 * statistic.
	 * 
	 * @param count
	 *            the number of items (typically number of entities) considered
	 *            by the statistic.
	 * 
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * Returns the total size of the items for this statistic.
	 * 
	 * @return the total size of the items for this statistic.
	 */
	public long getBytes() {
		return bytes;
	}

	/**
	 * Sets the total size of the items for this statistics.
	 * 
	 * @param bytes
	 *            the total size of the items for this statistic.
	 */
	public void setBytes(long bytes) {
		this.bytes = bytes;
	}

	/**
	 * Returns the time of the most recent update to the statistic.
	 * 
	 * @return the time of the most recent update to the statistic.
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the time of the most recent update to the statistic.
	 * 
	 * @param timestamp
	 *            the time of the most recent update to the statistic.
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
