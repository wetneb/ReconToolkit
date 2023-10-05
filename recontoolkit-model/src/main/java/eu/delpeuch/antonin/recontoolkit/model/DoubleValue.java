
package eu.delpeuch.antonin.recontoolkit.model;

/*-
 * #%L
 * ReconToolkit data model
 * %%
 * Copyright (C) 2022 - 2023 ReconToolkit Developers
 * %%
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
 * #L%
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * The value of a property on an entity, as a floating-point number.
 * 
 * @author antonin
 *
 */
public class DoubleValue implements PropertyValue {

    private final double value;

    /**
     * Builds a floating-point number-valued property value, by wrapping a double.
     * 
     * @param value
     *            the wrapped value
     */
    public DoubleValue(double value) {
        this.value = value;
    }

    /**
     * @return the unwrapped value
     */
    @JsonProperty("float")
    public double getValue() {
        return value;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Object simpleJsonSerialization() {
        return value;
    }

    @Override
    public String toString() {
        return "DoubleValue [value=" + value + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DoubleValue other = (DoubleValue) obj;
        return value == other.value;
    }
}
