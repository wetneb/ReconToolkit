
package eu.delpeuch.antonin.recontoolkit.model;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.util.Collections;

import org.testng.annotations.Test;

public class PropertyValueTest {

    StringValue stringValue = new StringValue("foo");
    LongValue longValue = new LongValue(1234L);
    DoubleValue doubleValue = new DoubleValue(12.34);
    BooleanValue booleanValue = new BooleanValue(true);
    Entity entityValue = new Entity("my id", "my name", "my description", Collections.emptyList());

    PropertyValue.Visitor<PropertyValue> identityVisitor = new PropertyValue.Visitor<PropertyValue>() {

        @Override
        public PropertyValue visit(StringValue stringValue) {
            return stringValue;
        }

        @Override
        public PropertyValue visit(LongValue longValue) {
            return longValue;
        }

        @Override
        public PropertyValue visit(DoubleValue doubleValue) {
            return doubleValue;
        }

        @Override
        public PropertyValue visit(BooleanValue booleanValue) {
            return booleanValue;
        }

        @Override
        public PropertyValue visit(Entity entity) {
            return entity;
        }
    };

    @Test
    public void testIdentityVisitor() {
        assertEquals(stringValue.accept(identityVisitor), stringValue);
        assertEquals(longValue.accept(identityVisitor), longValue);
        assertEquals(booleanValue.accept(identityVisitor), booleanValue);
        assertEquals(doubleValue.accept(identityVisitor), doubleValue);
        assertEquals(entityValue.accept(identityVisitor), entityValue);
    }

    @Test
    public void testToString() {
        assertEquals(stringValue.toString(), "StringValue [value=foo]");
        assertEquals(longValue.toString(), "LongValue [value=1234]");
        assertEquals(booleanValue.toString(), "BooleanValue [value=true]");
        assertEquals(doubleValue.toString(), "DoubleValue [value=12.34]");
        assertEquals(entityValue.toString(), "Entity [id=my id, name=my name, description=my description, types=[]]");
    }

    @Test
    public void testEquals() {
        assertNotEquals(stringValue, longValue);
        assertNotEquals(longValue, booleanValue);
        assertNotEquals(booleanValue, doubleValue);
        assertNotEquals(doubleValue, entityValue);
        assertNotEquals(entityValue, stringValue);

        assertEquals(stringValue, new StringValue("foo"));
        assertEquals(longValue, new LongValue(1234L));
        assertEquals(doubleValue, new DoubleValue(12.34));
        assertEquals(booleanValue, new BooleanValue(true));
        assertEquals(entityValue, new Entity("my id", "my name", "my description", Collections.emptyList()));
    }
}
