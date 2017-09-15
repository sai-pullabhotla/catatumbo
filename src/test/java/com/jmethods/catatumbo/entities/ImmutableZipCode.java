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

package com.jmethods.catatumbo.entities;

import java.util.Objects;

import com.jmethods.catatumbo.Embeddable;
import com.jmethods.catatumbo.Property;

/**
 * @author Sai Pullabhotla
 *
 */
@Embeddable
public class ImmutableZipCode {

	private String fiveDigits;

	@Property(optional = true)
	private String fourDgits;

	/**
	 * 
	 */
	public ImmutableZipCode(Builder builder) {
		this.fiveDigits = builder.fiveDigits;
		this.fourDgits = builder.fourDgits;
	}

	/**
	 * @return the fiveDigits
	 */
	public String getFiveDigits() {
		return fiveDigits;
	}

	/**
	 * @return the fourDgits
	 */
	public String getFourDgits() {
		return fourDgits;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("ImmutableZipCode [fiveDigits=").append(fiveDigits).append(", fourDgits=").append(fourDgits)
				.append("]");
		return builder2.toString();
	}

	public static class Builder {
		private String fiveDigits;
		private String fourDgits;

		private Builder() {

		}

		/**
		 * @param fiveDigits
		 *            the fiveDigits to set
		 */
		public void setFiveDigits(String fiveDigits) {
			this.fiveDigits = fiveDigits;
		}

		/**
		 * @param fourDgits
		 *            the fourDgits to set
		 */
		public void setFourDgits(String fourDgits) {
			this.fourDgits = fourDgits;
		}

		public ImmutableZipCode build() {
			return new ImmutableZipCode(this);
		}

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}
		ImmutableZipCode that = (ImmutableZipCode) obj;
		return Objects.equals(this.fiveDigits, that.fiveDigits) && Objects.equals(this.fourDgits, that.fourDgits);
	}
}
