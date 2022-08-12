
package eu.delpeuch.antonin.recontoolkit.model;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

/**
 * A type offered by a reconciliation service.
 * 
 * @author antonin
 *
 */
public class Type {

    private final String id;
    private final String name;
    private final String description;
    private final List<Type> broader;

    /**
     * Build a type.
     * 
     * @param id
     *            the identifier of the type (not null)
     * @param name
     *            the name of the type
     * @param description
     *            the description of the type
     * @param broader
     *            the list of types that are broader than this type (since 0.2)
     */
    public Type(String id, String name, String description, List<Type> broader) {
        Validate.notNull(id);
        this.id = id;
        this.name = name;
        this.description = description;
        this.broader = broader;
    }

    /**
     * @return the identifier of the type
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name of the type
     */
    public String getName() {
        return name;
    }

    /**
     * @return the description of the type
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the list of types that are broader than this type (since 0.2)
     */
    public List<Type> getBroader() {
        return broader;
    }

    @Override
    public int hashCode() {
        return Objects.hash(broader, description, id, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Type other = (Type) obj;
        return Objects.equals(broader, other.broader) && Objects.equals(description, other.description) && Objects.equals(id, other.id)
                && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "Type [id=" + id + ", name=" + name + ", description=" + description + ", broader=" + broader + "]";
    }

}
