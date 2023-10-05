
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
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Objects;

/**
 * A property in a {@link DataExtensionQuery}, consisting of the property identifier and some optional settings to
 * configure how the property is retrieved.
 */
public class PropertyWithSettings {

    private final String id;
    private final Map<String, Object> settings;

    @JsonCreator
    public PropertyWithSettings(
            @JsonProperty("id") String id,
            @JsonProperty("settings") Map<String, Object> settings) {
        this.id = id;
        this.settings = settings;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("settings")
    public Map<String, Object> getSettings() {
        return settings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyWithSettings that = (PropertyWithSettings) o;
        return Objects.equals(id, that.id) && Objects.equals(settings, that.settings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, settings);
    }

    @Override
    public String toString() {
        return "PropertyWithSettings [" +
                "id=" + id +
                ", settings=" + settings +
                ']';
    }
}
