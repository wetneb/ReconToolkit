
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
 * An entity offered by a reconciliation service.
 * 
 * @author antonin
 *
 */
public class Entity implements PropertyValue {

    private final String id;
    private final String name;
    private final String description;
    private final List<Type> types;

    /**
     * Builds an entity.
     * 
     * @param id
     *            the entity identifier (not null)
     * @param name
     *            the name of the entity
     * @param description
     *            the description of the entity
     * @param types
     *            the list of types of the entity
     */
    @JsonCreator
    public Entity(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("types") List<Type> types) {
        Validate.notNull(id);
        this.id = id;
        this.name = name;
        this.description = description;
        this.types = types != null ? types : Collections.emptyList();
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("description")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getDescription() {
        return description;
    }

    @JsonProperty("types")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<Type> getTypes() {
        return types;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public Object simpleJsonSerialization() {
        return this;
    }

    @Override
    public String toString() {
        return "Entity [id=" + id + ", name=" + name + ", description=" + description + ", types=" + types + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, name, types);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Entity other = (Entity) obj;
        return Objects.equals(description, other.description) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
                && Objects.equals(types, other.types);
    }

}
