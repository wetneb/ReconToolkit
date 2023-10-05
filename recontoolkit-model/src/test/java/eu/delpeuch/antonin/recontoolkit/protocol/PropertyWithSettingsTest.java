
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

import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.testng.Assert.assertEquals;

public class PropertyWithSettingsTest {

    @Test
    public void testSerialize() {
        String json = "{\"id\":\"P123\",\"settings\":{\"foo\":true}}";
        PropertyWithSettings property = new PropertyWithSettings(
                "P123",
                Collections.singletonMap("foo", true));
        TestUtils.assertCanonicalSerialization(property, json, PropertyWithSettings.class);
    }

    @Test
    public void testGetters() {
        PropertyWithSettings property = new PropertyWithSettings(
                "P123",
                Collections.singletonMap("foo", true));

        assertEquals(property.getId(), "P123");
        assertEquals(property.getSettings(), Collections.singletonMap("foo", true));
    }
}
