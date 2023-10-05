
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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.delpeuch.antonin.recontoolkit.model.PropertyValue;
import eu.delpeuch.antonin.recontoolkit.model.Type;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A response from a reconciliation service to a data extension query.
 */
public class DataExtensionResponse {

    private final Map<String, Map<String, List<PropertyValue>>> rows;
    private final List<PropertyMetadata> propertyMetadata;

    /**
     * Constructor.
     *
     * @param rows
     *            the two-dimensional grid of property values. The first key is the entity id, the second is the
     *            property id.
     * @param propertyMetadata
     *            some additional metadata about the properties fetched
     */
    @JsonCreator
    public DataExtensionResponse(
            @JsonProperty("rows") Map<String, Map<String, List<PropertyValue>>> rows,
            @JsonProperty("meta") List<PropertyMetadata> propertyMetadata) {
        this.rows = rows;
        this.propertyMetadata = propertyMetadata;
    }

    /**
     * The two-dimensional grid of property values. The first key is the entity id, the second is the property id.
     */
    @JsonProperty("rows")
    public Map<String, Map<String, List<PropertyValue>>> getRows() {
        return rows;
    }

    /**
     * Some additional metadata about the properties fetched.
     */
    @JsonProperty("meta")
    public List<PropertyMetadata> getPropertyMetadata() {
        return propertyMetadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataExtensionResponse that = (DataExtensionResponse) o;
        return Objects.equals(rows, that.rows) && Objects.equals(propertyMetadata, that.propertyMetadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rows, propertyMetadata);
    }

    @Override
    public String toString() {
        return "DataExtensionResponse{" +
                "rows=" + rows +
                ", propertyMetadata=" + propertyMetadata +
                '}';
    }

    /**
     * Additional metadata about properties fetched by a data extension query. Beyond the usual fields of a
     * {@link eu.delpeuch.antonin.recontoolkit.model.Property}, this also contains the {@link #getServiceUrl()}, which
     * is used when the property contains references to entities in other reconciliation services.
     */
    public static class PropertyMetadata {

        private final String id;
        private final String name;
        private final Type type;
        private final String serviceUrl;

        /**
         * Constructor.
         *
         * @param id
         *            the id of the property
         * @param name
         *            the name of the property
         * @param type
         *            the expected type of any entities which are values of this property
         * @param serviceUrl
         *            the external reconciliation service within which entities which are values of this property are to
         *            be understood
         */
        @JsonCreator
        public PropertyMetadata(
                @JsonProperty("id") String id,
                @JsonProperty("name") String name,
                @JsonProperty("type") Type type,
                @JsonProperty("serviceUrl") String serviceUrl) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.serviceUrl = serviceUrl;
        }

        /**
         * The id of the property.
         */
        @JsonProperty("id")
        public String getId() {
            return id;
        }

        /**
         * The name of the property.
         */
        @JsonProperty("name")
        public String getName() {
            return name;
        }

        /**
         * The expected type of any entities which are values of this property.
         */
        @JsonProperty("type")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public Type getType() {
            return type;
        }

        /**
         * The external reconciliation service within which entities which are values of this property are to be
         * understood.
         */
        @JsonProperty("serviceUrl")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String getServiceUrl() {
            return serviceUrl;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PropertyMetadata that = (PropertyMetadata) o;
            return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(type, that.type)
                    && Objects.equals(serviceUrl, that.serviceUrl);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, type, serviceUrl);
        }

        @Override
        public String toString() {
            return "PropertyMetadata [" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", type=" + type +
                    ", serviceUrl='" + serviceUrl + '\'' +
                    ']';
        }
    }
}
