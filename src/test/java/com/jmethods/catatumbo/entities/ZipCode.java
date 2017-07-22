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

import java.util.Objects;

import com.jmethods.catatumbo.Embeddable;
import com.jmethods.catatumbo.Embedded;
import com.jmethods.catatumbo.Property;

/**
 * @author Sai Pullabhotla
 *
 */
@Embeddable
public class ZipCode {

	@Property(name = "zip")
	private String fiveDigits;

	@Property(name = "zipx", indexed = false, optional = true)
	private String fourDigits;

	@Embedded(name = "useless", indexed = true)
	private AnotherEmbeddable anotherEmbeddable = new AnotherEmbeddable();

	/**
	 * 
	 */
	public ZipCode() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the fiveDigits
	 */
	public String getFiveDigits() {
		return fiveDigits;
	}

	/**
	 * @param fiveDigits
	 *            the fiveDigits to set
	 */
	public void setFiveDigits(String fiveDigits) {
		this.fiveDigits = fiveDigits;
	}

	/**
	 * @return the fourDigits
	 */
	public String getFourDigits() {
		return fourDigits;
	}

	/**
	 * @param fourDigits
	 *            the fourDigits to set
	 */
	public void setFourDigits(String fourDigits) {
		this.fourDigits = fourDigits;
	}

	@Override
	public String toString() {
		return fiveDigits + "-" + fourDigits;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ZipCode)) {
			return false;
		}
		ZipCode that = (ZipCode) obj;
		return Objects.equals(this.fiveDigits, that.fiveDigits) && Objects.equals(this.fourDigits, that.fourDigits)
				&& Objects.equals(this.anotherEmbeddable, that.anotherEmbeddable);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fiveDigits, fourDigits, anotherEmbeddable);
	}

	/**
	 * @return the anotherEmbeddable
	 */
	public AnotherEmbeddable getAnotherEmbeddable() {
		return anotherEmbeddable;
	}

	/**
	 * @param anotherEmbeddable
	 *            the anotherEmbeddable to set
	 */
	public void setAnotherEmbeddable(AnotherEmbeddable anotherEmbeddable) {
		this.anotherEmbeddable = anotherEmbeddable;
	}

}
