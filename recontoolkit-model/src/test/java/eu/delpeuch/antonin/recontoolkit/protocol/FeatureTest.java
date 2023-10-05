
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

import static org.testng.Assert.assertEquals;

public class FeatureTest {

    @Test
    public void testSerializeDouble() {
        Feature feature = new DoubleFeature("tfidf", 34.8);
        String json = "{\"id\":\"tfidf\",\"value\":34.8}";

        TestUtils.assertCanonicalSerialization(feature, json, Feature.class);
    }

    @Test
    public void testSerializeBoolean() {
        Feature feature = new BooleanFeature("looks_good", true);
        String json = "{\"id\":\"looks_good\",\"value\":true}";

        TestUtils.assertCanonicalSerialization(feature, json, Feature.class);
    }

    @Test(expectedExceptions = JsonProcessingException.class)
    public void testUnsupportedType() throws JsonProcessingException {
        JsonUtils.mapper.readValue("{\"id\":\"name\",\"value\":\"foo\"}", Feature.class);
    }

    @Test(expectedExceptions = JsonProcessingException.class)
    public void testRawValue() throws JsonProcessingException {
        JsonUtils.mapper.readValue("\"feature\"", Feature.class);
    }

    @Test(expectedExceptions = JsonProcessingException.class)
    public void testMissingId() throws JsonProcessingException {
        JsonUtils.mapper.readValue("{\"value\":true}", Feature.class);
    }

    static class IdVisitor implements Feature.Visitor<String> {

        @Override
        public String visit(BooleanFeature feature) {
            return feature.getId();
        }

        @Override
        public String visit(DoubleFeature feature) {
            return feature.getId();
        }
    };

    @Test
    public void testVisitor() {
        IdVisitor visitor = new IdVisitor();
        assertEquals(new DoubleFeature("double_id", 12.34).accept(visitor), "double_id");
        assertEquals(new BooleanFeature("bool_id", false).accept(visitor), "bool_id");
    }
}
