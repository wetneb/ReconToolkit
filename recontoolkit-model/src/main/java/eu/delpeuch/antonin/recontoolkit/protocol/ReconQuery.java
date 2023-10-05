
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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * A reconciliation query.
 * 
 * @author antonin
 *
 */
public class ReconQuery {

    public static enum TypeStrict {
        @JsonProperty("should")
        SHOULD, @JsonProperty("all")
        ALL, @JsonProperty("any")
        ANY
    };

    private final String query;
    private final List<String> typeIds;
    private final Integer limit;
    private final List<PropertyMapping> properties;
    private final TypeStrict typeStrict;

    /**
     * Builds a reconciliation query.
     * 
     * @param query
     *            the name of the entity to look for (can be null)
     * @param typeIds
     *            an optional list of types to restrict the matching to
     * @param limit
     *            an optional (set to null if not present) limit on the number of candidates to return
     * @param properties
     *            an optional list of property mappings to refine the search
     * @param typeStrict
     *            a parameter whose meaning is unclear
     */
    @JsonCreator
    public ReconQuery(
            @JsonProperty("query") String query,
            @JsonProperty("types") List<String> typeIds,
            @JsonProperty("limit") Integer limit,
            @JsonProperty("properties") List<PropertyMapping> properties,
            @JsonProperty("type_strict") TypeStrict typeStrict) {
        this.query = query;
        this.typeIds = typeIds;
        this.limit = limit;
        this.properties = properties;
        this.typeStrict = typeStrict;
    }

    /**
     * @return the name of the entity to look for (can be null)
     */
    @JsonProperty("query")
    public String getQuery() {
        return query;
    }

    /**
     * @return the list of type ids to restrict the matching to (can be null)
     */
    @JsonProperty("type")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<String> getTypeIds() {
        return typeIds;
    }

    /**
     * @return the maximum number of reconciliation candidates to return (can be null)
     */
    @JsonProperty("limit")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getLimit() {
        return limit;
    }

    /**
     * @return any property mapping to refine the search (can be null)
     */
    @JsonProperty("properties")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<PropertyMapping> getProperties() {
        return properties;
    }

    /**
     * @return a parameter whose meaning is unclear (can be null)
     */
    @JsonProperty("type_strict")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public TypeStrict getTypeStrict() {
        return typeStrict;
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit, properties, query, typeIds, typeStrict);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ReconQuery other = (ReconQuery) obj;
        return Objects.equals(limit, other.limit) && Objects.equals(properties, other.properties) && Objects.equals(query, other.query)
                && Objects.equals(typeIds, other.typeIds) && typeStrict == other.typeStrict;
    }

    @Override
    public String toString() {
        return "ReconQuery [query=" + query + ", type=" + typeIds + ", limit=" + limit + ", properties=" + properties + ", typeStrict="
                + typeStrict + "]";
    }

    public static final class Builder {

        private String query;
        private List<String> typeIds;
        private Integer limit;
        private List<PropertyMapping> properties;
        private TypeStrict typeStrict;

        private Builder() {
        }

        public static Builder aReconQuery() {
            return new Builder();
        }

        public Builder withQuery(String query) {
            this.query = query;
            return this;
        }

        public Builder withTypeIds(List<String> typeIds) {
            this.typeIds = typeIds;
            return this;
        }

        public Builder withLimit(Integer limit) {
            this.limit = limit;
            return this;
        }

        public Builder withProperties(List<PropertyMapping> properties) {
            this.properties = properties;
            return this;
        }

        public Builder withTypeStrict(TypeStrict typeStrict) {
            this.typeStrict = typeStrict;
            return this;
        }

        public ReconQuery build() {
            return new ReconQuery(query, typeIds, limit, properties, typeStrict);
        }
    }
}
