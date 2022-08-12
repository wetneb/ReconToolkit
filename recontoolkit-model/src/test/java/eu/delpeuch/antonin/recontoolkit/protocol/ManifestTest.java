
package eu.delpeuch.antonin.recontoolkit.protocol;

import java.util.Collections;

import org.testng.annotations.Test;

import eu.delpeuch.antonin.recontoolkit.model.Type;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;

public class ManifestTest {

    @Test
    public void testSerializeManifest() {
        /*
         * List<String> versions, String name, String identifierSpace, String schemaSpace, List<Type> defaultTypes,
         * String documentation, String logo, String serviceVersion, int batchSize
         */
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
                35);

        String json = "{\"versions\":[\"0.2\"],"
                + "\"name\":\"My service\","
                + "\"identifierSpace\":\"https://my.service/entities/\","
                + "\"schemaSpace\":\"https://my.service/schema/\","
                + "\"defaultTypes\":["
                + "   {\"id\":\"default_type\","
                + "   \"name\":\"The default type\","
                + "   \"description\":\"This type is really generic\","
                + "   \"broader\":[]"
                + "}],"
                + "\"documentation\":\"https://docs.my.service/\","
                + "\"logo\":\"https://my.service/logo\","
                + "\"serviceVersion\":\"1.4.3\","
                + "\"batchSize\":35}";

        TestUtils.assertCanonicalSerialization(manifest, json, Manifest.class);
    }
}
