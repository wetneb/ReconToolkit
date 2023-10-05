
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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import eu.delpeuch.antonin.recontoolkit.model.PropertyValue;
import org.apache.commons.lang3.Validate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @JsonCreator
    public PropertyMapping(
            @JsonProperty("pid") String pid,
            @JsonProperty("v") List<PropertyValue> v) {
        super();
        Validate.notNull(pid);
        this.pid = pid;
        this.v = v == null ? Collections.emptyList() : v;
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
        this(pid, v == null ? Collections.emptyList() : Collections.singletonList(v));
    }

    /**
     * @return the property id of this mapping
     */
    @JsonProperty("pid")
    public String getPid() {
        return pid;
    }

    /**
     * @return the list of values of this mapping
     */
    @JsonIgnore
    public List<PropertyValue> getValues() {
        return v;
    }

    @JsonProperty("v")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    protected List<Object> getJsonValues() {
        return v.stream().map(PropertyValue::simpleJsonSerialization).collect(Collectors.toList());
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
