
package eu.delpeuch.antonin.recontoolkit.protocol;

import java.util.Objects;

/**
 * A numerical matching feature.
 * 
 * @author antonin
 *
 */
public class DoubleFeature implements Feature {

    private final String id;
    private final double value;

    /**
     * Builds a numerical matching feature.
     * 
     * @param id
     *            the service-defined identifier of the feature
     * @param value
     *            the value of the feature in the given context
     */
    public DoubleFeature(String id, double value) {
        super();
        this.id = id;
        this.value = value;
    }

    /**
     * @return the value of the matching feature
     */
    public double getValue() {
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
        return "DoubleMatchingFeature [id=" + id + ", value=" + value + "]";
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
        DoubleFeature other = (DoubleFeature) obj;
        return Objects.equals(id, other.id) && Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
    }

}
