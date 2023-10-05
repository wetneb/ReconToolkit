
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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.delpeuch.antonin.recontoolkit.model.Entity;
import eu.delpeuch.antonin.recontoolkit.model.Type;
import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * A reconciliation candidate, returned in the response to a {@link ReconQuery}.
 * 
 * @author antonin
 *
 */
public class ReconCandidate {

    private final String id;
    private final String name;
    private final String description;
    private final List<Type> types;
    private final double score;
    private final Boolean match;
    private final List<Feature> features;

    /**
     * Constructs a reconciliation candidate
     * 
     * @param entity
     *            the entity represented by this candidate
     * @param score
     *            the matching score of this entity with the query
     * @param match
     *            whether the match is considered reliable (as defined by the service)
     * @param features
     *            a list of service-defined matching features
     */
    public ReconCandidate(
            Entity entity, double score, Boolean match, List<Feature> features) {
        super();
        Validate.notNull(entity);
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.types = entity.getTypes();
        this.score = score;
        this.match = match;
        this.features = features;
    }

    /**
     * Constructor for JSON deserialization.
     */
    @JsonCreator
    protected ReconCandidate(
            @JsonProperty("id") String id,
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("type") List<Type> types,
            @JsonProperty("score") double score,
            @JsonProperty("match") Boolean match,
            @JsonProperty("features") List<Feature> features) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.types = types == null ? Collections.emptyList() : types;
        this.score = score;
        this.match = match;
        this.features = features == null ? Collections.emptyList() : features;
    }

    /**
     * @return the entity corresponding to this candidate
     */
    @JsonIgnore
    public Entity getEntity() {
        return new Entity(id, name, description, types);
    }

    /**
     * @return the entity identifier
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * @return the entity name
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * @return the entity description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * @return the list of entity types
     */
    @JsonProperty("type")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<Type> getTypes() {
        return types;
    }

    /**
     * @return the matching score (as defined by the service)
     */
    @JsonProperty("score")
    public double getScore() {
        return score;
    }

    /**
     * @return whether the match is considered safe (service-defined)
     */
    @JsonProperty("match")
    public Boolean getMatch() {
        return match;
    }

    /**
     * @return the list of matching features computed by the service
     */
    @JsonProperty("features")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public List<Feature> getFeatures() {
        return features;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, features, id, name, score, match, types);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ReconCandidate other = (ReconCandidate) obj;
        return Objects.equals(description, other.description) && Objects.equals(features, other.features) && Objects.equals(id, other.id)
                && Objects.equals(name, other.name) && Double.doubleToLongBits(score) == Double.doubleToLongBits(other.score)
                && match == other.match
                && Objects.equals(types, other.types);
    }

    @Override
    public String toString() {
        return "ReconCandidate [id=" + id + ", name=" + name + ", description=" + description + ", types=" + types + ", score=" + score
                + ", match=" + match + ", features=" + features + "]";
    }
}
