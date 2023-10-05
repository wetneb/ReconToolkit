
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

import eu.delpeuch.antonin.recontoolkit.model.StringValue;
import eu.delpeuch.antonin.recontoolkit.protocol.ReconQuery.TypeStrict;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.testng.Assert.assertEquals;

public class ReconQueryTest {

    @Test
    public void testSerialize() {
        String json = "{"
                + "\"query\":\"some name\","
                + "\"properties\":[{\"pid\":\"123\",\"v\":[\"abc\"]}],"
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

    @Test
    public void testBuilder() {
        ReconQuery built = ReconQuery.Builder.aReconQuery()
                .withQuery("foo")
                .withLimit(123)
                .withProperties(Collections.emptyList())
                .withTypeIds(Arrays.asList("T123", "T456"))
                .withTypeStrict(TypeStrict.ANY)
                .build();

        ReconQuery expected = new ReconQuery("foo", Arrays.asList("T123", "T456"), 123,
                Collections.emptyList(), TypeStrict.ANY);

        assertEquals(built, expected);
    }
}
