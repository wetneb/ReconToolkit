
package eu.delpeuch.antonin.recontoolkit.model;

import java.util.Objects;

/**
 * The value of a property on an entity, as a string.
 * 
 * @author antonin
 *
 */
public class StringValue implements PropertyValue {

    private final String value;

    /**
     * Builds a string-valued property value, by wrapping a string.
     * 
     * @param value
     *            the wrapped value
     */
    public StringValue(String value) {
        this.value = value;
    }

    /**
     * @return the unwrapped value
     */
    public String getValue() {
        return value;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "StringValue [value=" + value + "]";
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
        StringValue other = (StringValue) obj;
        return Objects.equals(value, other.value);
    }
}
