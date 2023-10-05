
package eu.delpeuch.antonin.recontoolkit.protocol;

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
import org.apache.commons.lang3.Validate;

import java.util.Objects;

/**
 * A boolean matching feature.
 * 
 * @author antonin
 *
 */
public class BooleanFeature implements Feature {

    private final String id;
    private final boolean value;

    /**
     * Builds a boolean matching feature.
     * 
     * @param id
     *            the service-defined identifier of the feature
     * @param value
     *            the value of the feature in the given context
     */
    public BooleanFeature(String id, boolean value) {
        super();
        Validate.notNull(id, "no id provided for reconciliation feature");
        this.id = id;
        this.value = value;
    }

    /**
     * @return the value of the matching feature
     */
    @JsonProperty("value")
    public boolean getValue() {
        return value;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "BooleanMatchingFeature [id=" + id + ", value=" + value + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        BooleanFeature other = (BooleanFeature) obj;
        return Objects.equals(id, other.id) && value == other.value;
    }

}
