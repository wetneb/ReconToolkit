
package eu.delpeuch.antonin.recontoolkit.protocol;

import java.util.List;
import java.util.Objects;

import eu.delpeuch.antonin.recontoolkit.model.Type;

/**
 * The metadata associated with a reconciliation service, obtained by issuing a GET request to its root endpoint.
 * 
 * @author antonin
 *
 */
public class Manifest {

    private final List<String> versions;
    private final String name;
    private final String identifierSpace;
    private final String schemaSpace;
    private final List<Type> defaultTypes;
    private final String documentation;
    private final String logo;
    private final String serviceVersion;
    // view
    // feature_view
    // preview
    // suggest
    // extend
    private final int batchSize;
    // authentication

    public Manifest(List<String> versions, String name, String identifierSpace, String schemaSpace, List<Type> defaultTypes,
            String documentation, String logo, String serviceVersion, int batchSize) {
        super();
        this.versions = versions;
        this.name = name;
        this.identifierSpace = identifierSpace;
        this.schemaSpace = schemaSpace;
        this.defaultTypes = defaultTypes;
        this.documentation = documentation;
        this.logo = logo;
        this.serviceVersion = serviceVersion;
        this.batchSize = batchSize;
    }

    public List<String> getVersions() {
        return versions;
    }

    public String getName() {
        return name;
    }

    public String getIdentifierSpace() {
        return identifierSpace;
    }

    public String getSchemaSpace() {
        return schemaSpace;
    }

    public List<Type> getDefaultTypes() {
        return defaultTypes;
    }

    public String getDocumentation() {
        return documentation;
    }

    public String getLogo() {
        return logo;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public int getBatchSize() {
        return batchSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchSize, defaultTypes, documentation, identifierSpace, logo, name, schemaSpace, serviceVersion, versions);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Manifest other = (Manifest) obj;
        return batchSize == other.batchSize && Objects.equals(defaultTypes, other.defaultTypes)
                && Objects.equals(documentation, other.documentation) && Objects.equals(identifierSpace, other.identifierSpace)
                && Objects.equals(logo, other.logo) && Objects.equals(name, other.name) && Objects.equals(schemaSpace, other.schemaSpace)
                && Objects.equals(serviceVersion, other.serviceVersion) && Objects.equals(versions, other.versions);
    }

    @Override
    public String toString() {
        return "Manifest [versions=" + versions + ", name=" + name + ", identifierSpace=" + identifierSpace + ", schemaSpace=" + schemaSpace
                + ", defaultTypes=" + defaultTypes + ", documentation=" + documentation + ", logo=" + logo + ", serviceVersion="
                + serviceVersion + ", batchSize=" + batchSize + "]";
    }

}
