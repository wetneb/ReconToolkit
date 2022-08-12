
package eu.delpeuch.antonin.recontoolkit.model;

/**
 * The value of a property on an entity, which can be either a string, long, double, boolean or an entity.
 * 
 * Because this library is designed to work with Java versions where sum types are not available, this is implemented
 * with a visitor pattern.
 * 
 * @author antonin
 *
 */
public interface PropertyValue {

    public <T> T accept(Visitor<T> visitor);

    /**
     * A consumer of a property value. This is provided to ensure a closed list of possible subclasses of
     * {@link PropertyValue}.
     * 
     * @author antonin
     *
     * @param <T>
     */
    public interface Visitor<T> {

        public T visit(StringValue stringValue);

        public T visit(LongValue longValue);

        public T visit(DoubleValue doubleValue);

        public T visit(BooleanValue booleanValue);

        public T visit(Entity entity);
    }

}
