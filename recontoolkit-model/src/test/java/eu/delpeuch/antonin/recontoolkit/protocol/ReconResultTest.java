
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
import eu.delpeuch.antonin.recontoolkit.model.Type;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;
import org.testng.annotations.Test;

import java.util.Collections;

public class ReconResultTest {

    @Test
    public void testSerialization() {
        ReconCandidate candidate = new ReconCandidate(
                new Entity("123", "some name", "some description",
                        Collections.singletonList(new Type("T123", "type name", "type description", null))),
                12.43, true, Collections.emptyList());
        ReconResult result = new ReconResult(Collections.singletonList(candidate));

        String json = "{"
                + "\"result\":[{"
                + "   \"id\":\"123\","
                + "   \"name\":\"some name\","
                + "   \"description\":\"some description\","
                + "   \"type\":[{\"id\":\"T123\",\"name\":\"type name\",\"description\":\"type description\"}],"
                + "   \"score\":12.43,"
                + "   \"match\":true"
                + " }"
                + "]}";
        TestUtils.assertCanonicalSerialization(result, json, ReconResult.class);
    }
}
