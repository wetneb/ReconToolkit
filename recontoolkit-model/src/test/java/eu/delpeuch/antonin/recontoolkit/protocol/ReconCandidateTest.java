
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

import eu.delpeuch.antonin.recontoolkit.model.Entity;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ReconCandidateTest {

    @Test
    public void testSerialize() {
        ReconCandidate candidate = new ReconCandidate(
                new Entity("134", "some name", "some description", Collections.emptyList()),
                89.2, true, Collections.singletonList(new DoubleFeature("tfidf", 23.3)));
        String json = "{\"id\":\"134\",\"name\":\"some name\",\"description\":\"some description\","
                + "\"score\":89.2,\"match\":true,\"features\":[{\"id\":\"tfidf\",\"value\":23.3}]}";

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
