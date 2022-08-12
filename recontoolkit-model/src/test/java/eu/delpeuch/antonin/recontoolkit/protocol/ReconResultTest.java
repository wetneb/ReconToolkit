
package eu.delpeuch.antonin.recontoolkit.protocol;

import java.util.Collections;

import org.testng.annotations.Test;

import eu.delpeuch.antonin.recontoolkit.model.Entity;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;

public class ReconResultTest {

    @Test
    public void testSerialization() {
        ReconCandidate candidate = new ReconCandidate(
                new Entity("123", "some name", "some description", Collections.emptyList()),
                12.43, true, Collections.emptyList());
        ReconResult result = new ReconResult(Collections.singletonList(candidate));

        String json = "{"
                + "\"result\":[{"
                + "   \"id\":\"123\","
                + "   \"name\":\"some name\","
                + "   \"description\":\"some description\","
                + "   \"types\":[],"
                + "   \"score\":12.43,"
                + "   \"match\":true,"
                + "   \"features\":[]"
                + " }"
                + "]}";
        TestUtils.assertCanonicalSerialization(result, json, ReconResult.class);
    }
}
