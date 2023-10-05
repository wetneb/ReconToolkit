
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

import java.util.List;
import java.util.Objects;

public class DataExtensionQuery {

    private final List<String> entityIds;
    private final List<PropertyWithSettings> properties;

    @JsonCreator
    public DataExtensionQuery(
            @JsonProperty("ids") List<String> entityIds,
            @JsonProperty("properties") List<PropertyWithSettings> properties) {
        this.entityIds = entityIds;
        this.properties = properties;
    }

    @JsonProperty("ids")
    public List<String> getEntityIds() {
        return entityIds;
    }

    @JsonProperty("properties")
    public List<PropertyWithSettings> getProperties() {
        return properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataExtensionQuery that = (DataExtensionQuery) o;
        return Objects.equals(entityIds, that.entityIds) && Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityIds, properties);
    }

    @Override
    public String toString() {
        return "DataExtensionQuery [" +
                "entityIds=" + entityIds +
                ", properties=" + properties +
                ']';
    }
}
