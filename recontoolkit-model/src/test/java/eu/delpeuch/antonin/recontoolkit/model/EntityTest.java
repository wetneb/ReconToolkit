
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

public class EntityTest {

    Type type = new Type("T123", "The Type", null, null);
    Entity entity = new Entity("123", "my name", "my description", Collections.emptyList());
    Entity entityWithType = new Entity("123", "my name", null, Collections.singletonList(type));
    String json = "{\"id\":\"123\",\"name\":\"my name\",\"description\":\"my description\"}";
    String jsonWithType = "{\"id\":\"123\",\"name\":\"my name\",\"types\":[{\"id\":\"T123\",\"name\":\"The Type\"}]}";

    @Test(expectedExceptions = NullPointerException.class)
    public void testEntityIdNotNull() {
        new Entity(null, "name", "description", Collections.emptyList());
    }

    @Test
    public void testSerialize() {
        TestUtils.assertCanonicalSerialization(entity, json, Entity.class);
        TestUtils.assertCanonicalSerialization(entityWithType, jsonWithType, Entity.class);
    }

    @Test
    public void testGetters() {
        assertEquals(entity.getId(), "123");
        assertEquals(entity.getName(), "my name");
        assertEquals(entity.getDescription(), "my description");
        assertEquals(entity.getTypes(), Collections.emptyList());

        assertNull(entityWithType.getDescription());
        assertEquals(entityWithType.getTypes(), Collections.singletonList(type));
    }

    @Test
    public void testToString() {
        assertEquals(entity.toString(),
                "Entity [id=123, name=my name, description=my description, types=[]]");
    }

    @Test
    public void testEquals() {
        assertEquals(entity, new Entity("123", "my name", "my description", Collections.emptyList()));
        assertFalse(entity.equals(null));
        assertNotEquals(entity, new Entity("456", "my name", "my description", Collections.emptyList()));
        assertNotEquals(entity, new Type("123", "my name", "my description", Collections.emptyList()));
    }

    @Test
    public void testHashCode() {
        assertEquals(entity.hashCode(),
                new Entity("123", "my name", "my description", Collections.emptyList()).hashCode());
    }
}
