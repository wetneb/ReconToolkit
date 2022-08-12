
package eu.delpeuch.antonin.recontoolkit.protocol;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

import eu.delpeuch.antonin.recontoolkit.model.Entity;
import eu.delpeuch.antonin.recontoolkit.model.Type;

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
    public ReconCandidate(Entity entity, double score, Boolean match, List<Feature> features) {
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
     * @return the entity corresponding to this candidate
     */
    public Entity getEntity() {
        return new Entity(id, name, description, types);
    }

    /**
     * @return the entity identifier
     */
    public String getId() {
        return id;
    }

    /**
     * @return the entity name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the entity description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the list of entity types
     */
    public List<Type> getTypes() {
        return types;
    }

    /**
     * @return the matching score (as defined by the service)
     */
    public double getScore() {
        return score;
    }

    /**
     * @return whether the match is considered safe (service-defined)
     */
    public Boolean getMatch() {
        return match;
    }

    /**
     * @return the list of matching features computed by the service
     */
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
