
package eu.delpeuch.antonin.recontoolkit.protocol;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import eu.delpeuch.antonin.recontoolkit.utils.JsonUtils;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;

public class BooleanFeatureTest {

    @Test
    public void testSerialize() {
        String json = "{\"id\":\"looks_good\",\"value\":true}";
        Feature feature = new BooleanFeature("looks_good", true);
        TestUtils.assertCanonicalSerialization(feature, json, Feature.class);
    }

    @Test
    public void testSerializeList() {
        List<Feature> features = new ArrayList<>();
        features.add(new BooleanFeature("seems_fine", false));
        TestUtils.assertEqualsAsJson(JsonUtils.gson.toJson(features), "[{\"id\":\"seems_fine\",\"value\":false}]");
    }
}
