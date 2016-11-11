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
import java.util.Objects;

import com.jmethods.catatumbo.Entity;
import com.jmethods.catatumbo.Identifier;
import com.jmethods.catatumbo.PropertyMapper;
import com.jmethods.catatumbo.custommappers.CurrencyMapper;

/**
 * @author Sai Pullabhotla
 *
 */
@Entity
public class Item {

	@Identifier
	private long id;

	private String name;

	@PropertyMapper(CurrencyMapper.class)
	private BigDecimal price;

	@PropertyMapper(CurrencyMapper.class)
	private BigDecimal discount;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price
	 *            the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * @return the discount
	 */
	public BigDecimal getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Item)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Item that = (Item) obj;
		return this.id == that.id && Objects.equals(this.name, that.name) && Objects.equals(this.price, that.price);
	}

}
