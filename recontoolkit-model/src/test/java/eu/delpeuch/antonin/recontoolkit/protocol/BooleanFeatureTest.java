
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

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.delpeuch.antonin.recontoolkit.utils.JsonUtils;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public class BooleanFeatureTest {

    Feature feature = new BooleanFeature("looks_good", true);

    @Test
    public void testSerialize() {
        String json = "{\"id\":\"looks_good\",\"value\":true}";
        TestUtils.assertCanonicalSerialization(feature, json, Feature.class);
    }

    @Test
    public void testSerializeList() throws JsonProcessingException {
        List<Feature> features = new ArrayList<>();
        features.add(feature);
        TestUtils.assertEqualsAsJson(JsonUtils.mapper.writeValueAsString(features), "[{\"id\":\"looks_good\",\"value\":true}]");
    }

    @Test
    public void testToString() {
        assertEquals(feature.toString(), "BooleanMatchingFeature [id=looks_good, value=true]");
    }

    @Test
    public void testEquals() {
        assertEquals(feature, new BooleanFeature("looks_good", true));
        assertNotEquals(feature, new BooleanFeature("looks_bad", true));
        assertNotEquals(feature, new BooleanFeature("looks_good", false));
    }

    @Test
    public void testHashCode() {
        assertEquals(feature.hashCode(), new BooleanFeature("looks_good", true).hashCode());
    }
}
