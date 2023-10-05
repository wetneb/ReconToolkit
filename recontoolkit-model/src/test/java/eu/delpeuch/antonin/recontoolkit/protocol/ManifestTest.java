
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
import eu.delpeuch.antonin.recontoolkit.model.Type;
import eu.delpeuch.antonin.recontoolkit.utils.JsonUtils;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ManifestTest {

    Manifest.SuggestMetadata entitySuggest = new Manifest.SuggestMetadata(
            "http://example.com",
            "/suggest/entity",
            null, null);
    Manifest.SuggestMetadata typeSuggest = new Manifest.SuggestMetadata(
            "http://example.com",
            "/suggest/type",
            null, null);
    Manifest.SuggestOptions suggest = new Manifest.SuggestOptions(entitySuggest, null, typeSuggest);
    Manifest manifest = new Manifest(
            Collections.singletonList("0.2"),
            "My service",
            "https://my.service/entities/",
            "https://my.service/schema/",
            Collections.singletonList(
                    new Type("default_type", "The default type", "This type is really generic", Collections.emptyList())),
            "https://docs.my.service/",
            "https://my.service/logo",
            "1.4.3",
            suggest, 35);

    String json = "{\"versions\":[\"0.2\"],"
            + "\"name\":\"My service\","
            + "\"identifierSpace\":\"https://my.service/entities/\","
            + "\"schemaSpace\":\"https://my.service/schema/\","
            + "\"defaultTypes\":["
            + "   {\"id\":\"default_type\","
            + "   \"name\":\"The default type\","
            + "   \"description\":\"This type is really generic\""
            + "}],"
            + "\"documentation\":\"https://docs.my.service/\","
            + "\"logo\":\"https://my.service/logo\","
            + "\"serviceVersion\":\"1.4.3\","
            + "\"suggest\":{"
            + "   \"entity\":{"
            + "      \"service_url\": \"http://example.com\","
            + "      \"service_path\": \"/suggest/entity\""
            + "   },"
            + "   \"type\":{"
            + "      \"service_url\": \"http://example.com\","
            + "      \"service_path\": \"/suggest/type\""
            + "   }"
            + "},"
            + "\"batchSize\":35}";

    @Test
    public void testSerializeManifest() {
        TestUtils.assertCanonicalSerialization(manifest, json, Manifest.class);
    }

    @Test
    public void testSuggestMetadata() {
        Manifest.SuggestMetadata metadata = new Manifest.SuggestMetadata("http://example.com", "/suggest",
                "http://other.example.com", "/flyout");

        assertEquals(metadata.getFullServiceUrl(), "http://example.com/suggest");
        assertEquals(metadata.getFullFlyoutUrl(), "http://other.example.com/flyout");
    }

    @Test
    public void testHashCode() throws JsonProcessingException {
        Manifest other = JsonUtils.mapper.readValue(json, Manifest.class);
        assertEquals(other.hashCode(), manifest.hashCode());
    }

    @Test
    public void testToString() {
        assertTrue(manifest.toString().contains("My service"));
    }
}
