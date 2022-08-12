
package eu.delpeuch.antonin.recontoolkit.protocol;

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
        this.id = id;
        this.value = value;
    }

    /**
     * @return the value of the matching feature
     */
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
