
package eu.delpeuch.antonin.recontoolkit.protocol;

import java.util.Collections;

import org.testng.annotations.Test;

import eu.delpeuch.antonin.recontoolkit.model.StringValue;
import eu.delpeuch.antonin.recontoolkit.protocol.ReconQuery.TypeStrict;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;

public class ReconQueryTest {

    @Test
    public void testSerialize() {
        String json = "{"
                + "\"query\":\"some name\","
                + "\"properties\":[{\"pid\":\"123\",\"v\":\"abc\"}],"
                + "\"limit\":456"
                + "}";
        ReconQuery query = new ReconQuery(
                "some name",
                null,
                456,
                Collections.singletonList(new PropertyMapping("123", new StringValue("abc"))),
                null);
        TestUtils.assertCanonicalSerialization(query, json, ReconQuery.class);
    }

    @Test
    public void testSerializeTypeStrict() {
        String json = "{"
                + "\"query\":\"some name\","
                + "\"type_strict\":\"should\","
                + "\"type\":[\"Q3874\"]"
                + "}";
        ReconQuery query = new ReconQuery(
                "some name",
                Collections.singletonList("Q3874"),
                null,
                null,
                TypeStrict.SHOULD);
        TestUtils.assertCanonicalSerialization(query, json, ReconQuery.class);
    }
}
