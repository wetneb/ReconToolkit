
package eu.delpeuch.antonin.recontoolkit.utils;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.google.gson.JsonElement;

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
        JsonElement actualJson = JsonUtils.gson.fromJson(actual, JsonElement.class);
        JsonElement expectedJson = JsonUtils.gson.fromJson(expected, JsonElement.class);
        assertEquals(actualJson, expectedJson);
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
        // test serialization
        assertEqualsAsJson(JsonUtils.gson.toJson(obj), json);
        // test deserialization
        assertEquals(JsonUtils.gson.fromJson(json, clazz), obj);
    }

    @Test
    public void testAssertEqualsAsJson() {
        assertEqualsAsJson("{\"foo\":12}", "  {'foo': 12 } ");
    }
}
