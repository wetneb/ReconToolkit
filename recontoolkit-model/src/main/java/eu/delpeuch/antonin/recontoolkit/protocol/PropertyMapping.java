
package eu.delpeuch.antonin.recontoolkit.protocol;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import eu.delpeuch.antonin.recontoolkit.model.PropertyValue;

/**
 * The "properties" field of a reconciliation query, which contains a property identifier and a list of values that
 * should be matched against this property.
 * 
 * @author antonin
 *
 */
public class PropertyMapping {

    private final String pid;
    private final List<PropertyValue> v;

    /**
     * Builds a property mapping.
     * 
     * @param pid
     *            the identifier of the property
     * @param v
     *            the list of property values to match against
     */
    public PropertyMapping(String pid, List<PropertyValue> v) {
        super();
        this.pid = pid;
        this.v = v;
    }

    /**
     * Convenience constructor to build a mapping with a single value.
     * 
     * @param pid
     *            the identifier of the property
     * @param v
     *            the single value to match against
     */
    public PropertyMapping(String pid, PropertyValue v) {
        this.pid = pid;
        this.v = Collections.singletonList(v);
    }

    /**
     * @return the property id of this mapping
     */
    public String getPid() {
        return pid;
    }

    /**
     * @return the list of values of this mapping
     */
    public List<PropertyValue> getV() {
        return v;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, v);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        PropertyMapping other = (PropertyMapping) obj;
        return Objects.equals(pid, other.pid) && Objects.equals(v, other.v);
    }

    @Override
    public String toString() {
        return "PropertyMapping [pid=" + pid + ", v=" + v + "]";
    }
}
