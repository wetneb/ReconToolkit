
package eu.delpeuch.antonin.recontoolkit.protocol;

import org.testng.annotations.Test;

import com.google.gson.JsonParseException;

import eu.delpeuch.antonin.recontoolkit.utils.JsonUtils;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;

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

    @Test(expectedExceptions = JsonParseException.class)
    public void testUnsupportedType() {
        JsonUtils.gson.fromJson("{\"id\":\"name\",\"value\":\"foo\"}", Feature.class);
    }

    @Test(expectedExceptions = JsonParseException.class)
    public void testRawValue() {
        JsonUtils.gson.fromJson("\"feature\"", Feature.class);
    }

    @Test(expectedExceptions = JsonParseException.class)
    public void testMissingId() {
        JsonUtils.gson.fromJson("{\"value\":true}", Feature.class);
    }
}
