
package eu.delpeuch.antonin.recontoolkit.protocol;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Collections;

import org.testng.annotations.Test;

import eu.delpeuch.antonin.recontoolkit.model.Entity;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;

public class ReconCandidateTest {

    @Test
    public void testSerialize() {
        ReconCandidate candidate = new ReconCandidate(
                new Entity("134", "some name", "some description", Collections.emptyList()),
                89.2, true, Collections.singletonList(new DoubleFeature("tfidf", 23.3)));
        String json = "{\"id\":\"134\",\"name\":\"some name\",\"description\":\"some description\","
                + "\"types\":[],\"score\":89.2,\"match\":true,\"features\":[{\"id\":\"tfidf\",\"value\":23.3}]}";

        TestUtils.assertCanonicalSerialization(candidate, json, ReconCandidate.class);
    }

    @Test
    public void testGetters() {
        ReconCandidate candidate = new ReconCandidate(
                new Entity("134", "some name", "some description", Collections.emptyList()),
                89.2, true, Collections.singletonList(new DoubleFeature("tfidf", 23.3)));

        assertEquals(candidate.getId(), "134");
        assertEquals(candidate.getName(), "some name");
        assertEquals(candidate.getDescription(), "some description");
        assertEquals(candidate.getTypes(), Collections.emptyList());
        assertEquals(candidate.getScore(), 89.2);
        assertTrue(candidate.getMatch());
        assertEquals(candidate.getFeatures(), Collections.singletonList(new DoubleFeature("tfidf", 23.3)));
    }
}
