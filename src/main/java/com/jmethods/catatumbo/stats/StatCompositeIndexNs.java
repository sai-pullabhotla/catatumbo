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

import com.jmethods.catatumbo.Entity;

/**
 * Statistic entity for namespace specific composite index.
 * 
 * @author Sai Pullabhotla
 *
 */
@Entity(kind = StatConstants.STAT_COMPOSITE_INDEX_NS)
public class StatCompositeIndexNs extends StatCompositeIndexBase implements Serializable {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = -2593351832953284028L;

}
