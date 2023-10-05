
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

import eu.delpeuch.antonin.recontoolkit.model.*;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;

public class DataExtensionResponseTest {

    DataExtensionResponse response;

    @BeforeMethod
    public void setUp() {
        Map<String, Map<String, List<PropertyValue>>> rows = new HashMap<>();
        Map<String, List<PropertyValue>> rowA = new HashMap<>();
        rows.put("Q123", rowA);
        List<PropertyValue> rowA1 = new ArrayList<>();
        rowA.put("P123", rowA1);
        rowA1.add(new Entity("Q890", "some entity", "this is an example", null));
        rowA1.add(new Entity("Q078", "some entity", "look, a namesake!", null));
        List<PropertyValue> rowA2 = new ArrayList<>();
        rowA2.add(new StringValue("http://foo.com/"));
        rowA.put("P456", rowA2);

        Map<String, List<PropertyValue>> rowB = new HashMap<>();
        rows.put("Q456", rowB);
        List<PropertyValue> rowB1 = new ArrayList<>();
        rowB.put("P123", rowB1);
        List<PropertyValue> rowB2 = new ArrayList<>();
        rowB2.add(new LongValue(1234L));
        rowB.put("P456", rowB2);

        List<DataExtensionResponse.PropertyMetadata> meta = new ArrayList<>();
        meta.add(new DataExtensionResponse.PropertyMetadata("P123", "parent organization",
                new Type("root", "any entity", "the type of everything", Collections.emptyList()), null));
        meta.add(new DataExtensionResponse.PropertyMetadata("P456", "favourite color",
                null, null));

        response = new DataExtensionResponse(rows, meta);
    }

    @Test
    public void testSerialization() {
        String json = "{" +
                "\"rows\":{" +
                "    \"Q456\":{" +
                "        \"P456\":[{\"int\":1234}]," +
                "        \"P123\":[]" +
                "     }," +
                "     \"Q123\":{" +
                "        \"P456\":[{\"str\":\"http://foo.com/\"}]," +
                "        \"P123\":[" +
                "            {\"id\":\"Q890\",\"name\":\"some entity\",\"description\":\"this is an example\"}," +
                "            {\"id\":\"Q078\",\"name\":\"some entity\",\"description\":\"look, a namesake!\"}]" +
                "    }" +
                "}," +
                "\"meta\":[" +
                "    {" +
                "        \"id\":\"P123\"," +
                "        \"name\":\"parent organization\"," +
                "        \"type\":{\"id\":\"root\",\"name\":\"any entity\",\"description\":\"the type of everything\"}" +
                "    }," +
                "    {" +
                "        \"id\":\"P456\"," +
                "        \"name\":\"favourite color\"" +
                "    }]" +
                "}";

        TestUtils.assertCanonicalSerialization(response, json, DataExtensionResponse.class);
    }

    @Test
    public void testToString() {
        assertEquals(response.toString(), "DataExtensionResponse{rows={Q456={P456=[LongValue [value=1234]], P123=[]},"
                + " Q123={P456=[StringValue [value=http://foo.com/]], P123=[Entity [id=Q890, name=some entity,"
                + " description=this is an example, types=[]], Entity [id=Q078, name=some entity, description=look, a namesake!,"
                + " types=[]]]}}, propertyMetadata=[PropertyMetadata [id='P123', name='parent organization', type=Type [id=root,"
                + " name=any entity, description=the type of everything, broader=[]], serviceUrl='null'], PropertyMetadata"
                + " [id='P456', name='favourite color', type=null, serviceUrl='null']]}");
    }

    @Test
    public void testHashCode() {
        assertEquals(response.hashCode(), new DataExtensionResponse(response.getRows(), response.getPropertyMetadata()).hashCode());
    }
}
