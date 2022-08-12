
package eu.delpeuch.antonin.recontoolkit.protocol;

import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

/**
 * A reconciliation query.
 * 
 * @author antonin
 *
 */
public class ReconQuery {

    public static enum TypeStrict {
        SHOULD, ALL, ANY
    };

    private final String query;
    @SerializedName("type")
    private final List<String> typeIds;
    private final Integer limit;
    private final List<PropertyMapping> properties;
    @SerializedName("type_strict")
    private final TypeStrict typeStrict;

    /**
     * Builds a reconciliation query.
     * 
     * @param query
     *            the name of the entity to look for (can be null)
     * @param typeIds
     *            an optional list of types to restrict the matching to
     * @param limit
     *            an optional (set to null if not present) limit on the number of candidates to return
     * @param properties
     *            an optional list of property mappings to refine the search
     * @param typeStrict
     *            a parameter whose meaning is unclear
     */
    public ReconQuery(String query, List<String> typeIds, Integer limit, List<PropertyMapping> properties, TypeStrict typeStrict) {
        this.query = query;
        this.typeIds = typeIds;
        this.limit = limit;
        this.properties = properties;
        this.typeStrict = typeStrict;
    }

    /**
     * @return the name of the entity to look for (can be null)
     */
    public String getQuery() {
        return query;
    }

    /**
     * @return the list of type ids to restrict the matching to (can be null)
     */
    public List<String> getTypeIds() {
        return typeIds;
    }

    /**
     * @return the maximum number of reconciliation candidates to return (can be null)
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * @return any property mapping to refine the search (can be null)
     */
    public List<PropertyMapping> getProperties() {
        return properties;
    }

    /**
     * @return a parameter whose meaning is unclear (can be null)
     */
    public TypeStrict getTypeStrict() {
        return typeStrict;
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit, properties, query, typeIds, typeStrict);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ReconQuery other = (ReconQuery) obj;
        return Objects.equals(limit, other.limit) && Objects.equals(properties, other.properties) && Objects.equals(query, other.query)
                && Objects.equals(typeIds, other.typeIds) && typeStrict == other.typeStrict;
    }

    @Override
    public String toString() {
        return "ReconQuery [query=" + query + ", type=" + typeIds + ", limit=" + limit + ", properties=" + properties + ", typeStrict="
                + typeStrict + "]";
    }

}
