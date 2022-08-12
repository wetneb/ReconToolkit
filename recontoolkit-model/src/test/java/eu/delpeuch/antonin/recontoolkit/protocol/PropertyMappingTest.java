
package eu.delpeuch.antonin.recontoolkit.protocol;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.testng.annotations.Test;

import com.google.gson.JsonParseException;

import eu.delpeuch.antonin.recontoolkit.model.BooleanValue;
import eu.delpeuch.antonin.recontoolkit.model.Entity;
import eu.delpeuch.antonin.recontoolkit.model.LongValue;
import eu.delpeuch.antonin.recontoolkit.model.StringValue;
import eu.delpeuch.antonin.recontoolkit.utils.JsonUtils;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;

public class PropertyMappingTest {

    @Test
    public void testSerializationSingleValue() {
        String jsonString = "{\"pid\":\"123\",\"v\":\"some string\"}";
        PropertyMapping mapping = new PropertyMapping("123",
                Collections.singletonList(new StringValue("some string")));
        TestUtils.assertCanonicalSerialization(mapping, jsonString, PropertyMapping.class);
    }

    @Test
    public void testSerializationSingleEntity() {
        String jsonString = "{\"pid\":\"123\",\"v\":{\"id\":\"456\",\"name\":\"some string\"}}";
        PropertyMapping mapping = new PropertyMapping("123",
                Collections.singletonList(new Entity("456", "some string", null, null)));
        TestUtils.assertCanonicalSerialization(mapping, jsonString, PropertyMapping.class);
    }

    @Test
    public void testSerializationNull() {
        String jsonString = "{\"pid\":\"123\",\"v\":null}";
        PropertyMapping mapping = new PropertyMapping("123",
                Collections.singletonList(null));
        assertEquals(JsonUtils.gson.fromJson(jsonString, PropertyMapping.class), mapping);
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

    @Test(expectedExceptions = JsonParseException.class)
    public void testNoPid() {
        JsonUtils.gson.fromJson("{\"v\":456}", PropertyMapping.class);
    }

    @Test(expectedExceptions = JsonParseException.class)
    public void testArray() {
        JsonUtils.gson.fromJson("[]", PropertyMapping.class);
    }

    @Test(expectedExceptions = JsonParseException.class)
    public void testInvalidId() {
        JsonUtils.gson.fromJson("{\"id\":123,\"v\":456}", PropertyMapping.class);
    }
}
