
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
import com.fasterxml.jackson.core.type.TypeReference;
import eu.delpeuch.antonin.recontoolkit.model.Entity;
import eu.delpeuch.antonin.recontoolkit.utils.JsonUtils;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;
import org.testng.annotations.Test;

import java.lang.reflect.Type;
import java.util.Arrays;

import static org.testng.Assert.assertEquals;

public class SuggestResponseTest {

    SuggestResponse<Entity> entities = new SuggestResponse<>(Arrays.asList(
            new Entity("123", "some entity", null, null)));

    @Test
    public void testSerialization() throws JsonProcessingException {
        String json = "{\"result\":[{\"id\":\"123\",\"name\":\"some entity\"}]}";

        // test serialization
        TestUtils.assertEqualsAsJson(JsonUtils.mapper.writeValueAsString(entities), json);
        // test deserialization
        assertEquals(JsonUtils.mapper.readValue(json, new TypeReference<SuggestResponse<Entity>>() {
        }), entities);
    }

    @Test
    public void testHashCode() {
        assertEquals(entities.hashCode(), new SuggestResponse<Entity>(entities.getResults()).hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(entities.toString(), "SuggestResponse{results=[Entity [id=123, name=some entity, description=null, types=[]]]}");
    }
}
