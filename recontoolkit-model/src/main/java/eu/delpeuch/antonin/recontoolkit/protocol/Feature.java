
package eu.delpeuch.antonin.recontoolkit.protocol;

/**
 * Represents the value of a service-defined matching feature for a given reconciliation query.
 * 
 * This is a sum type, implemented with a visitor pattern.
 * 
 * @author antonin
 *
 */
public interface Feature {

    /**
     * @return the service-defined identifier of the feature
     */
    String getId();

    /**
     * A method to consume a matching feature (visitor pattern)
     * 
     * @param <T>
     * @param visitor
     * @return
     */
    <T> T accept(Visitor<T> visitor);

    /**
     * A visitor for {@link Feature}.
     * 
     * @author antonin
     *
     * @param <T>
     */
    public interface Visitor<T> {

        T visit(BooleanFeature feature);

        T visit(DoubleFeature feature);
    }
}
