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

import java.math.BigDecimal;

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Decimal;
import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Key;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class DecimalFields {

	@Identifier
	private long id;

	@Key
	private DatastoreKey key;

	@Decimal(precision = 1, scale = 0)
	private BigDecimal n10;

	@Decimal(precision = 1, scale = 1)
	private BigDecimal n11;

	@Decimal(precision = 5, scale = 0)
	private BigDecimal n50;

	@Decimal(precision = 7, scale = 3)
	private BigDecimal n73;

	@Decimal(precision = 18, scale = 0)
	private BigDecimal n180;

	@Decimal(precision = 18, scale = 18)
	private BigDecimal n1818;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the key
	 */
	public DatastoreKey getKey() {
		return key;
	}

	/**
	 * @param key
	 *            the key to set
	 */
	public void setKey(DatastoreKey key) {
		this.key = key;
	}

	/**
	 * @return the n10
	 */
	public BigDecimal getN10() {
		return n10;
	}

	/**
	 * @param n10
	 *            the n10 to set
	 */
	public void setN10(BigDecimal n10) {
		this.n10 = n10;
	}

	/**
	 * @return the n11
	 */
	public BigDecimal getN11() {
		return n11;
	}

	/**
	 * @param n11
	 *            the n11 to set
	 */
	public void setN11(BigDecimal n11) {
		this.n11 = n11;
	}

	/**
	 * @return the n50
	 */
	public BigDecimal getN50() {
		return n50;
	}

	/**
	 * @param n50
	 *            the n50 to set
	 */
	public void setN50(BigDecimal n50) {
		this.n50 = n50;
	}

	/**
	 * @return the n73
	 */
	public BigDecimal getN73() {
		return n73;
	}

	/**
	 * @param n73
	 *            the n73 to set
	 */
	public void setN73(BigDecimal n73) {
		this.n73 = n73;
	}

	/**
	 * @return the n180
	 */
	public BigDecimal getN180() {
		return n180;
	}

	/**
	 * @param n180
	 *            the n180 to set
	 */
	public void setN180(BigDecimal n180) {
		this.n180 = n180;
	}

	/**
	 * @return the n1818
	 */
	public BigDecimal getN1818() {
		return n1818;
	}

	/**
	 * @param n1818
	 *            the n1818 to set
	 */
	public void setN1818(BigDecimal n1818) {
		this.n1818 = n1818;
	}

}
