
package eu.delpeuch.antonin.recontoolkit.model;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;

import java.util.Collections;

import org.testng.annotations.Test;

import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;

public class EntityTest {

    Entity entity = new Entity("123", "my name", "my description", Collections.emptyList());
    String json = "{\"id\":\"123\",\"name\":\"my name\",\"description\":\"my description\",\"types\":[]}";

    @Test(expectedExceptions = NullPointerException.class)
    public void testEntityIdNotNull() {
        new Entity(null, "name", "description", Collections.emptyList());
    }

    @Test
    public void testSerialize() {
        TestUtils.assertCanonicalSerialization(entity, json, Entity.class);
    }

    @Test
    public void testGetters() {
        assertEquals(entity.getId(), "123");
        assertEquals(entity.getName(), "my name");
        assertEquals(entity.getDescription(), "my description");
        assertEquals(entity.getTypes(), Collections.emptyList());
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
}
