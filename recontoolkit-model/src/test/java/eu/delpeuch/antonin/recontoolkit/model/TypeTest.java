
package eu.delpeuch.antonin.recontoolkit.model;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;

import java.util.Collections;

import org.testng.annotations.Test;

import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;

public class TypeTest {

    Type type = new Type("123", "my name", "my description", Collections.emptyList());
    String json = "{\"id\":\"123\",\"name\":\"my name\",\"description\":\"my description\",\"broader\":[]}";

    @Test(expectedExceptions = NullPointerException.class)
    public void testTypeIdNotNull() {
        new Type(null, "my name", "my description", Collections.emptyList());
    }

    @Test
    public void testSerialize() {
        TestUtils.assertCanonicalSerialization(type, json, Type.class);
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
}
