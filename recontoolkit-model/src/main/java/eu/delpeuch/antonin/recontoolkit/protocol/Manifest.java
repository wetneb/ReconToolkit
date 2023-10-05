
package eu.delpeuch.antonin.recontoolkit.protocol;

/*-
 * #%L
 * ReconToolkit data model
 * %%
 * Copyright (C) 2022 - 2023 ReconToolkit Developers
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.delpeuch.antonin.recontoolkit.model.Type;

import java.util.List;
import java.util.Objects;

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
    private final SuggestOptions suggest;
    // extend
    private final int batchSize;
    // authentication

    @JsonCreator
    public Manifest(
            @JsonProperty("versions") List<String> versions,
            @JsonProperty("name") String name,
            @JsonProperty("identifierSpace") String identifierSpace,
            @JsonProperty("schemaSpace") String schemaSpace,
            @JsonProperty("defaultTypes") List<Type> defaultTypes,
            @JsonProperty("documentation") String documentation,
            @JsonProperty("logo") String logo,
            @JsonProperty("serviceVersion") String serviceVersion,
            @JsonProperty("suggest") SuggestOptions suggest,
            @JsonProperty("batchSize") int batchSize) {
        super();
        this.versions = versions;
        this.name = name;
        this.identifierSpace = identifierSpace;
        this.schemaSpace = schemaSpace;
        this.defaultTypes = defaultTypes;
        this.documentation = documentation;
        this.logo = logo;
        this.serviceVersion = serviceVersion;
        this.suggest = suggest;
        this.batchSize = batchSize;
    }

    @JsonProperty("versions")
    public List<String> getVersions() {
        return versions;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("identifierSpace")
    public String getIdentifierSpace() {
        return identifierSpace;
    }

    @JsonProperty("schemaSpace")
    public String getSchemaSpace() {
        return schemaSpace;
    }

    @JsonProperty("defaultTypes")
    public List<Type> getDefaultTypes() {
        return defaultTypes;
    }

    @JsonProperty("documentation")
    public String getDocumentation() {
        return documentation;
    }

    @JsonProperty("logo")
    public String getLogo() {
        return logo;
    }

    @JsonProperty("serviceVersion")
    public String getServiceVersion() {
        return serviceVersion;
    }

    @JsonProperty("suggest")
    public SuggestOptions getSuggestOptions() {
        return suggest;
    }

    @JsonProperty("batchSize")
    public int getBatchSize() {
        return batchSize;
    }

    @Override
    public int hashCode() {
        return Objects.hash(batchSize, defaultTypes, documentation, identifierSpace, logo, name, schemaSpace, serviceVersion, suggest,
                versions);
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
                && Objects.equals(serviceVersion, other.serviceVersion) && Objects.equals(suggest, other.suggest)
                && Objects.equals(versions, other.versions);
    }

    @Override
    public String toString() {
        return "Manifest [versions=" + versions + ", name=" + name + ", identifierSpace=" + identifierSpace + ", schemaSpace=" + schemaSpace
                + ", defaultTypes=" + defaultTypes + ", documentation=" + documentation + ", logo=" + logo + ", serviceVersion="
                + serviceVersion + ", batchSize=" + batchSize + "]";
    }

    /**
     * Location information for a Suggest service.
     */
    public static class SuggestMetadata {

        private final String serviceUrl;
        private final String servicePath;
        private final String flyoutServiceUrl;
        private final String flyoutServicePath;

        @JsonCreator
        public SuggestMetadata(
                @JsonProperty("service_url") String serviceUrl,
                @JsonProperty("service_path") String servicePath,
                @JsonProperty("flyout_service_url") String flyoutServiceUrl,
                @JsonProperty("flyout_service_path") String flyoutServicePath) {
            this.serviceUrl = serviceUrl;
            this.servicePath = servicePath;
            this.flyoutServiceUrl = flyoutServiceUrl;
            this.flyoutServicePath = flyoutServicePath;
        }

        /**
         * @return the base URL of the Suggest service
         */
        @JsonProperty("service_url")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String getServiceUrl() {
            return serviceUrl;
        }

        /**
         * @return the path of the Suggest service, to be concatenated to {@link #getServiceUrl()}
         */
        @JsonProperty("service_path")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String getServicePath() {
            return servicePath;
        }

        /**
         * @return the full URL to use for Suggest queries
         */
        @JsonIgnore
        public String getFullServiceUrl() {
            return serviceUrl + servicePath;
        }

        /**
         * @return the base URL of the Flyout service
         */
        @JsonProperty("flyout_service_url")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String getFlyoutServiceUrl() {
            return flyoutServiceUrl;
        }

        /**
         * @return the path of the Flyout service, to be concatenated to {@link #getFlyoutServiceUrl()}
         */
        @JsonProperty("flyout_service_path")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String getFlyoutServicePath() {
            return flyoutServicePath;
        }

        /**
         * @return the full URL to use for Flyout queries
         */
        @JsonIgnore
        public String getFullFlyoutUrl() {
            return flyoutServiceUrl + flyoutServicePath;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SuggestMetadata that = (SuggestMetadata) o;
            return Objects.equals(serviceUrl, that.serviceUrl) && Objects.equals(servicePath, that.servicePath)
                    && Objects.equals(flyoutServiceUrl, that.flyoutServiceUrl) && Objects.equals(flyoutServicePath, that.flyoutServicePath);
        }

        @Override
        public int hashCode() {
            return Objects.hash(serviceUrl, servicePath, flyoutServiceUrl, flyoutServicePath);
        }

        @Override
        public String toString() {
            return "SuggestMetadata{" +
                    "serviceUrl='" + serviceUrl + '\'' +
                    ", servicePath='" + servicePath + '\'' +
                    ", flyoutServiceUrl='" + flyoutServiceUrl + '\'' +
                    ", flyoutServicePath='" + flyoutServicePath + '\'' +
                    '}';
        }

    }

    /**
     * Metadata associated to the presence of suggest services for entities, properties and types.
     */
    public static class SuggestOptions {

        private final SuggestMetadata entity;
        private final SuggestMetadata property;
        private final SuggestMetadata type;

        @JsonCreator
        public SuggestOptions(
                @JsonProperty("entity") SuggestMetadata entity,
                @JsonProperty("property") SuggestMetadata property,
                @JsonProperty("type") SuggestMetadata type) {
            this.entity = entity;
            this.property = property;
            this.type = type;
        }

        /**
         * @return the Suggest metadata for entities. Returns null if suggesting entities is not supported.
         */
        @JsonProperty("entity")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public SuggestMetadata getEntitySuggestMetadata() {
            return entity;
        }

        /**
         * @return the Suggest metadata for properties. Returns null if suggesting properties is not supported.
         */
        @JsonProperty("property")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public SuggestMetadata getPropertySuggestMetadata() {
            return property;
        }

        /**
         * @return the Suggest metadata for types. Returns null if suggesting types is not supported.
         */
        @JsonProperty("type")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public SuggestMetadata getTypeSuggestMetadata() {
            return type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SuggestOptions that = (SuggestOptions) o;
            return Objects.equals(entity, that.entity) && Objects.equals(property, that.property) && Objects.equals(type, that.type);
        }

        @Override
        public int hashCode() {
            return Objects.hash(entity, property, type);
        }

        @Override
        public String toString() {
            return "SuggestOptions{" +
                    "entity=" + entity +
                    ", property=" + property +
                    ", type=" + type +
                    '}';
        }
    }

}
