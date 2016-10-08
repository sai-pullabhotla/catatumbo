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

import com.jmethods.catatumbo.DatastoreKey;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.Ignore;
import com.jmethods.catatumbo.Key;
import com.jmethods.catatumbo.MappedSuperClass;

/**
 * @author Sai Pullabhotla
 *
 */
@MappedSuperClass
public class CalculatorEntity {

	@Identifier
	private long id;

	@Key
	private DatastoreKey key;

	private long operand1;

	private long operand2;

	private long sum;

	@Ignore
	private long sum2;

	private long product;

	@Ignore
	private long product2;

	private long difference;

	@Ignore
	private long difference2;

	public CalculatorEntity() {

	}

	/**
	 * @param operand1
	 * @param operand2
	 */
	public CalculatorEntity(long operand1, long operand2) {
		super();
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

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
	 * @return the operand1
	 */
	public long getOperand1() {
		return operand1;
	}

	/**
	 * @param operand1
	 *            the operand1 to set
	 */
	public void setOperand1(long operand1) {
		this.operand1 = operand1;
	}

	/**
	 * @return the operand2
	 */
	public long getOperand2() {
		return operand2;
	}

	/**
	 * @param operand2
	 *            the operand2 to set
	 */
	public void setOperand2(long operand2) {
		this.operand2 = operand2;
	}

	/**
	 * @return the result
	 */
	public long getSum() {
		return sum;
	}

	/**
	 * @param sum
	 *            the result to set
	 */
	public void setSum(long sum) {
		this.sum = sum;
	}

	/**
	 * @return the result2
	 */
	public long getSum2() {
		return sum2;
	}

	/**
	 * @param sum2
	 *            the result2 to set
	 */
	public void setSum2(long sum2) {
		this.sum2 = sum2;
	}

	/**
	 * @return the product
	 */
	public long getProduct() {
		return product;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public void setProduct(long product) {
		this.product = product;
	}

	/**
	 * @return the product2
	 */
	public long getProduct2() {
		return product2;
	}

	/**
	 * @param product2
	 *            the product2 to set
	 */
	public void setProduct2(long product2) {
		this.product2 = product2;
	}

	/**
	 * @return the difference
	 */
	public long getDifference() {
		return difference;
	}

	/**
	 * @param difference
	 *            the difference to set
	 */
	public void setDifference(long difference) {
		this.difference = difference;
	}

	/**
	 * @return the difference2
	 */
	public long getDifference2() {
		return difference2;
	}

	/**
	 * @param difference2
	 *            the difference2 to set
	 */
	public void setDifference2(long difference2) {
		this.difference2 = difference2;
	}

}
