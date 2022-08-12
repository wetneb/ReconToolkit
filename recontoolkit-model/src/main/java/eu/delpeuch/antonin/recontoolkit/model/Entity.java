
package eu.delpeuch.antonin.recontoolkit.model;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.Validate;

/**
 * An entity offered by a reconciliation service.
 * 
 * @author antonin
 *
 */
public class Entity implements PropertyValue {

    private final String id;
    private final String name;
    private final String description;
    private final List<Type> types;

    /**
     * Builds an entity.
     * 
     * @param id
     *            the entity identifier (not null)
     * @param name
     *            the name of the entity
     * @param description
     *            the description of the entity
     * @param types
     *            the list of types of the entity
     */
    public Entity(String id, String name, String description, List<Type> types) {
        Validate.notNull(id);
        this.id = id;
        this.name = name;
        this.description = description;
        this.types = types;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Type> getTypes() {
        return types;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Entity [id=" + id + ", name=" + name + ", description=" + description + ", types=" + types + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, id, name, types);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Entity other = (Entity) obj;
        return Objects.equals(description, other.description) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
                && Objects.equals(types, other.types);
    }

}
