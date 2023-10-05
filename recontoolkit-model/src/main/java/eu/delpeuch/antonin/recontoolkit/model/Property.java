
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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;

import java.util.Objects;

/**
 * A property offered by a reconciliation service.
 * 
 * @author antonin
 *
 */
public class Property {

    private final String id;
    private final String name;
    private final String description;

    /**
     * Builds a property.
     * 
     * @param id
     *            the identifier of the property (not null)
     * @param name
     *            the name of the property
     * @param description
     *            the description of the property
     */
    @JsonCreator
    public Property(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description) {
        Validate.notNull(id);
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * @return the identifier of the property
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * @return the name of the property
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @return the description of the property
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Property [id=" + id + ", name=" + name + ", description=" + description + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Property other = (Property) obj;
        return Objects.equals(description, other.description) && Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

}
