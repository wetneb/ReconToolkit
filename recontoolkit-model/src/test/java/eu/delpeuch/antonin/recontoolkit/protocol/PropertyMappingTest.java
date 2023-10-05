
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
import eu.delpeuch.antonin.recontoolkit.model.BooleanValue;
import eu.delpeuch.antonin.recontoolkit.model.Entity;
import eu.delpeuch.antonin.recontoolkit.model.LongValue;
import eu.delpeuch.antonin.recontoolkit.model.StringValue;
import eu.delpeuch.antonin.recontoolkit.utils.JsonUtils;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.testng.Assert.assertEquals;

public class PropertyMappingTest {

    @Test
    public void testSerializationSingleValue() throws JsonProcessingException {
        String jsonString = "{\"pid\":\"123\",\"v\":\"some string\"}";
        PropertyMapping mapping = new PropertyMapping("123",
                Collections.singletonList(new StringValue("some string")));

        PropertyMapping deserialized = JsonUtils.mapper.readValue(jsonString, PropertyMapping.class);
        assertEquals(deserialized, mapping);
    }

    @Test
    public void testSerializationSingleEntity() throws JsonProcessingException {
        String jsonString = "{\"pid\":\"123\",\"v\":{\"id\":\"456\",\"name\":\"some string\"}}";
        PropertyMapping mapping = new PropertyMapping("123",
                Collections.singletonList(new Entity("456", "some string", null, null)));

        PropertyMapping deserialized = JsonUtils.mapper.readValue(jsonString, PropertyMapping.class);
        assertEquals(deserialized, mapping);
    }

    @Test
    public void testSerializationNull() throws JsonProcessingException {
        String jsonString = "{\"pid\":\"123\",\"v\":null}";
        PropertyMapping mapping = new PropertyMapping("123",
                Collections.emptyList());

        PropertyMapping deserialized = JsonUtils.mapper.readValue(jsonString, PropertyMapping.class);
        assertEquals(deserialized, mapping);
    }

    @Test
    public void testMultipleValues() {
        String json = "{\"pid\":\"123\", \"v\":[true,382,{\"id\":\"foo\",\"name\":\"bar\"}]}";
        PropertyMapping mapping = new PropertyMapping("123",
                Arrays.asList(
                        new BooleanValue(true),
                        new LongValue(382L),
                        new Entity("foo", "bar", null, null)));
        TestUtils.assertCanonicalSerialization(mapping, json, PropertyMapping.class);
    }

    @Test(expectedExceptions = JsonProcessingException.class)
    public void testNoPid() throws JsonProcessingException {
        JsonUtils.mapper.readValue("{\"v\":456}", PropertyMapping.class);
    }

    @Test(expectedExceptions = JsonProcessingException.class)
    public void testArray() throws JsonProcessingException {
        JsonUtils.mapper.readValue("[]", PropertyMapping.class);
    }

    @Test(expectedExceptions = JsonProcessingException.class)
    public void testInvalidId() throws JsonProcessingException {
        JsonUtils.mapper.readValue("{\"id\":123,\"v\":456}", PropertyMapping.class);
    }
}
