
package eu.delpeuch.antonin.recontoolkit.model;

import java.util.Objects;

/**
 * The value of a property on an entity, as an integer.
 * 
 * @author antonin
 *
 */
public class LongValue implements PropertyValue {

    private final long value;

    /**
     * Builds an integer-valued property value, by wrapping a long.
     * 
     * @param value
     *            the wrapped value
     */
    public LongValue(long value) {
        this.value = value;
    }

    /**
     * @return the unwrapped value
     */
    public long getValue() {
        return value;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "LongValue [value=" + value + "]";
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
        LongValue other = (LongValue) obj;
        return value == other.value;
    }
}
