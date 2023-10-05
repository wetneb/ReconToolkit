
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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A type offered by a reconciliation service.
 * 
 * @author antonin
 *
 */
public class Type {

    private final String id;
    private final String name;
    private final String description;
    private final List<Type> broader;

    /**
     * Build a type.
     * 
     * @param id
     *            the identifier of the type (not null)
     * @param name
     *            the name of the type
     * @param description
     *            the description of the type
     * @param broader
     *            the list of types that are broader than this type (since 0.2)
     */
    @JsonCreator
    public Type(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("broader") List<Type> broader) {
        Validate.notNull(id);
        this.id = id;
        this.name = name;
        this.description = description;
        this.broader = broader == null ? Collections.emptyList() : broader;
    }

    /**
     * @return the identifier of the type
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * @return the name of the type
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @return the description of the type
     */
    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() {
        return description;
    }

    /**
     * @return the list of types that are broader than this type (since 0.2)
     */
    @JsonProperty("broader")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<Type> getBroader() {
        return broader;
    }

    @Override
    public int hashCode() {
        return Objects.hash(broader, description, id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Type other = (Type) obj;
        return Objects.equals(broader, other.broader) && Objects.equals(description, other.description) && Objects.equals(id, other.id)
                && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "Type [id=" + id + ", name=" + name + ", description=" + description + ", broader=" + broader + "]";
    }

}
