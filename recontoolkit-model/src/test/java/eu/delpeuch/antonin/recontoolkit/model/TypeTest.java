
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

public class TypeTest {

    Type type = new Type("123", "my name", "my description", Collections.emptyList());
    Type typeWithBroader = new Type("456", "other type", null, Collections.singletonList(type));
    String json = "{\"id\":\"123\",\"name\":\"my name\",\"description\":\"my description\"}";
    String jsonWithBroader = "{\"id\":\"456\",\"name\":\"other type\",\"broader\":[{\"id\":\"123\",\"name\":\"my name\",\"description\":\"my description\"}]}";

    @Test(expectedExceptions = NullPointerException.class)
    public void testTypeIdNotNull() {
        new Type(null, "my name", "my description", Collections.emptyList());
    }

    @Test
    public void testSerialize() {
        TestUtils.assertCanonicalSerialization(type, json, Type.class);
        TestUtils.assertCanonicalSerialization(typeWithBroader, jsonWithBroader, Type.class);
    }

    @Test
    public void testGetters() {
        assertEquals(type.getId(), "123");
        assertEquals(type.getName(), "my name");
        assertEquals(type.getDescription(), "my description");
        assertEquals(type.getBroader(), Collections.emptyList());
    }

    @Test
    public void testToString() {
        assertEquals(type.toString(), "Type [id=123, name=my name, description=my description, broader=[]]");
    }

    @Test
    public void testEquals() {
        assertEquals(type, new Type("123", "my name", "my description", Collections.emptyList()));
        assertFalse(type.equals(null));
        assertNotEquals(type, new Type("456", "my name", "my description", Collections.emptyList()));
        assertNotEquals(type, new Entity("123", "my name", "my description", Collections.emptyList()));
    }

    @Test
    public void testHashCode() {
        assertEquals(type.hashCode(),
                new Type("123", "my name", "my description", Collections.emptyList()).hashCode());
    }
}
