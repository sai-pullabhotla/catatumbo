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

package com.jmethods.catatumbo;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.jmethods.catatumbo.mappers.DecimalMapper;

/**
 * Specifies that the annotated field is of type decimal (aka numeric). This
 * annotation can be applied to any BigDecimal field of an Entity,
 * MappedSuperClass or Embeddable. When this annotation is present on a field,
 * the persistence framework attaches a custom mapper, {@link DecimalMapper}, to
 * map the data between model and Cloud Datastore. The custom mapper stores the
 * data as a 64-bit integer in the Cloud Datastore. When reading data from the
 * Datastore, the mapper converts the 64-bit integer to a BigDecimal based on
 * the specified scale.
 * 
 * <p>
 * <strong> WARNING: <br />
 * DO NOT CHANGE THE SCALE WHEN THERE IS ALREADY DATA IN THE DATASTORE. DOING SO
 * WILL YIELD INCORRECT RESULTS. YOU MAY INCREASE THE PRECISION, BUT DO NOT
 * DECREASE. IF IT IS NECESSARY TO CHANGE THE PRECISION OR SCALE, EXISTING DATA
 * MUST BE UPDATED BEFORE CHANGING YOUR ENTITY. </strong>
 * </p>
 * 
 * @author Sai Pullabhotla
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Decimal {

	/**
	 * The precision for a decimal property. Precision is the total number of
	 * digits before and after the decimal point.
	 * 
	 * @return the precision
	 */
	int precision();

	/**
	 * The scale for a decimal property. Scale is maximum number of digits after
	 * the decimal point.
	 * 
	 * @return the scale
	 */
	int scale();

}
