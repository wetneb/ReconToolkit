
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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the value of a service-defined matching feature for a given reconciliation query.
 * 
 * This is a sum type, implemented with a visitor pattern.
 * 
 * @author antonin
 *
 */
public interface Feature {

    /**
     * @return the service-defined identifier of the feature
     */
    @JsonProperty("id")
    String getId();

    /**
     * A method to consume a matching feature (visitor pattern)
     * 
     * @param <T>
     * @param visitor
     * @return
     */
    <T> T accept(Visitor<T> visitor);

    /**
     * A visitor for {@link Feature}.
     * 
     * @author antonin
     *
     * @param <T>
     */
    public interface Visitor<T> {

        T visit(BooleanFeature feature);

        T visit(DoubleFeature feature);
    }

    @JsonCreator
    static Feature deserialize(
            @JsonProperty("id") String id,
            @JsonProperty("value") Object value) {
        if (value instanceof Number) {
            return new DoubleFeature(id, ((Number) value).doubleValue());
        } else if (value instanceof Boolean) {
            return new BooleanFeature(id, (Boolean) value);
        } else {
            throw new IllegalArgumentException("Invalid reconciliation feature value: " + value);
        }
    }
}
