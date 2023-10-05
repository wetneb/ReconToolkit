
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

import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class DataExtensionQueryTest {

    private final List<String> entityIds = Arrays.asList("Q123", "Q456");
    private final List<PropertyWithSettings> properties = Arrays.asList(new PropertyWithSettings("P789", Collections.emptyMap()),
            new PropertyWithSettings("P364", Collections.emptyMap()));
    DataExtensionQuery query = new DataExtensionQuery(entityIds, properties);

    @Test
    public void testSerialize() {
        String json = "{" +
                "\"ids\":[\"Q123\",\"Q456\"]," +
                "\"properties\":[" +
                "   {\"id\":\"P789\",\"settings\":{}}," +
                "   {\"id\":\"P364\",\"settings\":{}}" +
                "]}";

        TestUtils.assertCanonicalSerialization(query, json, DataExtensionQuery.class);
    }

    @Test
    public void testGetters() {
        assertEquals(query.getEntityIds(), Arrays.asList("Q123", "Q456"));
        assertEquals(query.getProperties(), Arrays.asList(new PropertyWithSettings("P789", Collections.emptyMap()),
                new PropertyWithSettings("P364", Collections.emptyMap())));
    }

    @Test
    public void testToString() {
        assertEquals(query.toString(),
                "DataExtensionQuery [entityIds=[Q123, Q456],"
                        + " properties=[PropertyWithSettings [id=P789, settings={}], PropertyWithSettings [id=P364, settings={}]]]");
    }

    @Test
    public void testEquals() {
        assertEquals(query, new DataExtensionQuery(entityIds, properties));
    }

    @Test
    public void testHashCode() {
        assertEquals(query.hashCode(), new DataExtensionQuery(entityIds, properties).hashCode());
    }
}
