
package eu.delpeuch.antonin.recontoolkit.utils;

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

import com.fasterxml.jackson.databind.JsonNode;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UncheckedIOException;

import static org.testng.Assert.assertEquals;

public class TestUtils {

    /**
     * Asserts that two JSON blobs are equal as JSON objects (the order of keys and the whitespace do not matter).
     * 
     * @param actual
     *            the actual JSON string
     * @param expected
     *            the expected JSON string
     */
    public static void assertEqualsAsJson(String actual, String expected) {
        try {
            JsonNode actualJson = JsonUtils.mapper.readTree(actual);
            JsonNode expectedJson = JsonUtils.mapper.readTree(expected);
            if (!actualJson.equals(expectedJson)) {
                assertEquals(actual, expected);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Checks that the serialization of an object is as given, and that deserializing this JSON gives the same object.
     * 
     * @param obj
     *            the object to serialize
     * @param json
     *            the expected serialization
     * @param clazz
     *            the type to deserialize to
     */
    public static void assertCanonicalSerialization(Object obj, String json, Class<?> clazz) {
        try {
            // test serialization
            assertEqualsAsJson(JsonUtils.mapper.writeValueAsString(obj), json);
            // test deserialization
            assertEquals(JsonUtils.mapper.readValue(json, clazz), obj);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    public void testAssertEqualsAsJson() {
        assertEqualsAsJson("{\"foo\":12}", "  { \"foo\": 12 } ");
    }
}
