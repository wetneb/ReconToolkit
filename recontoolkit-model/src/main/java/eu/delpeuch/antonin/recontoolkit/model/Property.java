
package eu.delpeuch.antonin.recontoolkit.model;

import java.util.Objects;

import org.apache.commons.lang3.Validate;

/**
 * A property offered by a reconciliation service.
 * 
 * @author antonin
 *
 */
public class Property {

    private final String id;
    private final String name;
    private final String description;

    /**
     * Builds a property.
     * 
     * @param id
     *            the identifier of the property (not null)
     * @param name
     *            the name of the property
     * @param description
     *            the description of the property
     */
    public Property(String id, String name, String description) {
        Validate.notNull(id);
        this.id = id;
        this.name = name;
        this.description = description;
    }

    /**
     * @return the identifier of the property
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name of the property
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description of the property
     */
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Property [id=" + id + ", name=" + name + ", description=" + description + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Property other = (Property) obj;
        return Objects.equals(description, other.description) && Objects.equals(id, other.id) && Objects.equals(name, other.name);
    }

}
