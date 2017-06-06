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

/**
 * Constants for System Kinds and Property names in the statistic entities.
 * 
 * @author Sai Pullabhotla
 *
 */
public class StatConstants {

	/**
	 * Creates a new instance of <code>StatConstants</code>.
	 */
	private StatConstants() {
		// Hide the constructor
	}

	/**
	 * Entity name for StatTotal
	 */
	public static final String STAT_TOTAL = "__Stat_Total__";

	/**
	 * Entity name for StatTotal by Namespace
	 */
	public static final String STAT_TOTAL_NS = "__Stat_Ns_Total__";

	/**
	 * Entity name for StatKind
	 */
	public static final String STAT_KIND = "__Stat_Kind__";

	/**
	 * Entity name for StatKind by namespace
	 */
	public static final String STAT_KIND_NS = "__Stat_Ns_Kind__";

	/**
	 * Entity name for StatNamespace
	 */
	public static final String STAT_NAMESPACE = "__Stat_Namespace__";

	/**
	 * Entity name for composite index statistics
	 */
	public static final String STAT_COMPOSITE_INDEX = "__Stat_Kind_CompositeIndex__";

	/**
	 * Entity name for composite index statistics (namespace specific)
	 */
	public static final String STAT_COMPOSITE_INDEX_NS = "__Stat_Ns_Kind_CompositeIndex__";

	/**
	 * Property name that holds the Kind name
	 */
	public static final String PROP_KIND_NAME = "kind_name";

	/**
	 * Property name that holds the total bytes for the statistic
	 */
	public static final String PROP_BYTES = "bytes";

	/**
	 * Property name that holds the total count for the statistic
	 */
	public static final String PROP_COUNT = "count";

	/**
	 * Property name that holds the timestamp of statistic
	 */
	public static final String PROP_TIMESTAMP = "timestamp";

	/**
	 * Property name that holds the builtin index size
	 */
	public static final String PROP_BUILTIN_INDEX_BYTES = "builtin_index_bytes";

	/**
	 * Property name that holds the builtin index count
	 */
	public static final String PROP_BUILTIN_INDEX_COUNT = "builtin_index_count";

	/**
	 * Property name that holds that size of composite indexes
	 */
	public static final String PROP_COMPOSITE_INDEX_BYTES = "composite_index_bytes";

	/**
	 * Property name that holds the composite index count
	 */
	public static final String PROP_COMPOSITE_INDEX_COUNT = "composite_index_count";

	/**
	 * Property name that holds the size of entity
	 */
	public static final String PROP_ENTITY_BYTES = "entity_bytes";

	/**
	 * Property name that holds the subject namespace
	 */
	public static final String PROP_SUBJECT_NAMESPACE = "subject_namespace";

	/**
	 * Property name that holds the index ID.
	 */
	public static final String PROP_INDEX_ID = "index_id";

	/**
	 * ID of StatTotal and StatTotal by namespace
	 */
	public static final String ID_TOTAL_ENTITY_USAGE = "total_entity_usage";

}
