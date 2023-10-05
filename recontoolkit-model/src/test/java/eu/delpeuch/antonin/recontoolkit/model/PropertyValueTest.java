
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

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.delpeuch.antonin.recontoolkit.utils.JsonUtils;
import eu.delpeuch.antonin.recontoolkit.utils.TestUtils;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.testng.Assert.*;

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
    public void testSerialize() {
        TestUtils.assertCanonicalSerialization(stringValue, "{\"str\":\"foo\"}", PropertyValue.class);
        TestUtils.assertCanonicalSerialization(longValue, "{\"int\":1234}", PropertyValue.class);
        TestUtils.assertCanonicalSerialization(booleanValue, "{\"bool\":true}", PropertyValue.class);
        TestUtils.assertCanonicalSerialization(doubleValue, "{\"float\":12.34}", PropertyValue.class);
        TestUtils.assertCanonicalSerialization(entityValue, "{\"id\":\"my id\",\"name\":\"my name\",\"description\":\"my description\"}",
                PropertyValue.class);
    }

    @Test
    public void testSimpleJsonSerialization() {
        assertEquals(stringValue.simpleJsonSerialization(), "foo");
        assertEquals(longValue.simpleJsonSerialization(), 1234L);
        assertEquals(booleanValue.simpleJsonSerialization(), true);
        assertEquals(doubleValue.simpleJsonSerialization(), 12.34);
        assertEquals(entityValue.simpleJsonSerialization(), entityValue);
    }

    @Test
    public void testSimpleJsonDeserialization() throws JsonProcessingException {
        assertEquals(JsonUtils.mapper.readValue("\"foo\"", PropertyValue.class), stringValue);
        assertEquals(JsonUtils.mapper.readValue("1234", PropertyValue.class), longValue);
        assertEquals(JsonUtils.mapper.readValue("true", PropertyValue.class), booleanValue);
        assertEquals(JsonUtils.mapper.readValue("12.34", PropertyValue.class), doubleValue);
        assertThrows(JsonProcessingException.class,
                () -> JsonUtils.mapper.readValue("{\"foo\":\"bar\"}", PropertyValue.class));
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

    @Test
    public void testHashCode() {
        assertEquals(stringValue.hashCode(), new StringValue("foo").hashCode());
        assertEquals(longValue.hashCode(), new LongValue(1234L).hashCode());
        assertEquals(doubleValue.hashCode(), new DoubleValue(12.34).hashCode());
        assertEquals(booleanValue.hashCode(), new BooleanValue(true).hashCode());
        assertEquals(entityValue.hashCode(), new Entity("my id", "my name", "my description", Collections.emptyList()).hashCode());
    }
}
