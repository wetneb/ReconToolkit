
package eu.delpeuch.antonin.recontoolkit.model;

import java.util.Objects;

/**
 * The value of a property on an entity, as a floating-point number.
 * 
 * @author antonin
 *
 */
public class DoubleValue implements PropertyValue {

    private final double value;

    /**
     * Builds a floating-point number-valued property value, by wrapping a double.
     * 
     * @param value
     *            the wrapped value
     */
    public DoubleValue(double value) {
        this.value = value;
    }

    /**
     * @return the unwrapped value
     */
    public double getValue() {
        return value;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "DoubleValue [value=" + value + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DoubleValue other = (DoubleValue) obj;
        return value == other.value;
    }
}
