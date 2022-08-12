
package eu.delpeuch.antonin.recontoolkit.model;

import java.util.Objects;

/**
 * The value of a property on an entity, as a boolean value.
 * 
 * @author antonin
 *
 */
public class BooleanValue implements PropertyValue {

    private final boolean value;

    /**
     * Builds a boolean-valued property value, by wrapping a boolean.
     * 
     * @param value
     *            the wrapped value
     */
    public BooleanValue(boolean value) {
        this.value = value;
    }

    /**
     * @return the unwrapped value
     */
    public boolean getValue() {
        return value;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "BooleanValue [value=" + value + "]";
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
        BooleanValue other = (BooleanValue) obj;
        return value == other.value;
    }
}
