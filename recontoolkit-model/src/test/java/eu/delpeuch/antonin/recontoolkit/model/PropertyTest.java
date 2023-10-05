
package eu.delpeuch.antonin.recontoolkit.model;

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

import java.util.Collections;

import static org.testng.Assert.*;

public class PropertyTest {

    Property property = new Property("123", "my name", "my description");
    String json = "{\"id\":\"123\",\"name\":\"my name\",\"description\":\"my description\"}";

    @Test(expectedExceptions = NullPointerException.class)
    public void testPropertyIdNotNull() {
        new Property(null, "some name", "some description");
    }

    @Test
    public void testSerialize() {
        TestUtils.assertCanonicalSerialization(property, json, Property.class);
    }

    @Test
    public void testGetters() {
        assertEquals(property.getId(), "123");
        assertEquals(property.getName(), "my name");
        assertEquals(property.getDescription(), "my description");
    }

    @Test
    public void testToString() {
        assertEquals(property.toString(),
                "Property [id=123, name=my name, description=my description]");
    }

    @Test
    public void testEquals() {
        assertEquals(property, new Property("123", "my name", "my description"));
        assertFalse(property.equals(null));
        assertNotEquals(property, new Type("123", "my name", "my description", Collections.emptyList()));
    }

    @Test
    public void testHashCode() {
        assertEquals(property.hashCode(), new Property("123", "my name", "my description").hashCode());
    }
}
