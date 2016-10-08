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

package com.jmethods.catatumbo.listeners;

import com.jmethods.catatumbo.EntityListener;
import com.jmethods.catatumbo.PostDelete;
import com.jmethods.catatumbo.PostInsert;
import com.jmethods.catatumbo.PostLoad;
import com.jmethods.catatumbo.PostUpdate;
import com.jmethods.catatumbo.PostUpsert;
import com.jmethods.catatumbo.PreDelete;
import com.jmethods.catatumbo.PreInsert;
import com.jmethods.catatumbo.PreUpdate;
import com.jmethods.catatumbo.PreUpsert;
import com.jmethods.catatumbo.entities.CalculatorEntity;

/**
 * @author Sai Pullabhotla
 *
 */
@EntityListener
public class Multiplier {

	@PreInsert
	public void beforeInsert(CalculatorEntity entity) {
		entity.setProduct(entity.getOperand1() * entity.getOperand2());
	}

	@PreUpdate
	public void beforeUpdate(CalculatorEntity entity) {
		entity.setProduct(entity.getOperand1() * entity.getOperand2());
	}

	@PreUpsert
	public void beforeUpsert(CalculatorEntity entity) {
		entity.setProduct(entity.getOperand1() * entity.getOperand2());
	}

	@PreDelete
	public void beforeDelete(CalculatorEntity entity) {
		entity.setProduct(entity.getOperand1() * entity.getOperand2());
	}

	@PostInsert
	public void afterInsert(CalculatorEntity entity) {
		entity.setProduct2(entity.getProduct() * 2);
	}

	@PostUpdate
	public void afterUpdate(CalculatorEntity entity) {
		entity.setProduct2(entity.getProduct() * 3);
	}

	@PostUpsert
	public void afterUpsert(CalculatorEntity entity) {
		entity.setProduct2(entity.getProduct() * 4);
	}

	@PostDelete
	public void afterDelete(CalculatorEntity entity) {
		entity.setProduct2(entity.getProduct() * 5);
	}

	@PostLoad
	public void afterLoad(CalculatorEntity entity) {
		entity.setProduct(entity.getOperand1() * entity.getOperand2());
		entity.setProduct2(entity.getProduct() * 6);
	}

}
