
package eu.delpeuch.antonin.recontoolkit.model;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;

import java.util.Collections;

import org.testng.annotations.Test;

import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;

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
}
