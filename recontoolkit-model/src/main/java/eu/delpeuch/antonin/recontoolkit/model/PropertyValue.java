
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The value of a property on an entity, which can be either a string, long, double, boolean or an entity.
 * 
 * Because this library is designed to work with Java versions where sum types are not available, this is implemented
 * with a visitor pattern.
 * 
 * @author antonin
 *
 */
@JsonDeserialize(using = PropertyValue.DataExtensionDeserializer.class)
public interface PropertyValue {

    /**
     * Method for the visitor pattern.
     */
    <T> T accept(Visitor<T> visitor);

    /**
     * Returns a Java object whose JSON serialization corresponds to the "simple" serialization of the property value.
     * This is a workaround for the fact that the serialization in reconciliation queries and data extension responses
     * is different.
     */
    @JsonIgnore
    Object simpleJsonSerialization();

    /**
     * A consumer of a property value. This is provided to ensure a closed list of possible subclasses of
     * {@link PropertyValue}.
     * 
     * @author antonin
     *
     * @param <T>
     */
    interface Visitor<T> {

        public T visit(StringValue stringValue);

        public T visit(LongValue longValue);

        public T visit(DoubleValue doubleValue);

        public T visit(BooleanValue booleanValue);

        public T visit(Entity entity);
    }

    /**
     * Deserializer which accepts both the format used in data extension responses and the one used in reconciliation
     * queries. <br>
     * See https://github.com/reconciliation-api/specs/issues/94
     */
    class DataExtensionDeserializer extends StdDeserializer<PropertyValue> {

        public DataExtensionDeserializer() {
            this(null);
        }

        protected DataExtensionDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public PropertyValue deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = parser.getCodec().readTree(parser);
            if (node.isObject()) {
                if (node.has("str")) {
                    return new StringValue(node.get("str").asText());
                } else if (node.has("float")) {
                    return new DoubleValue(node.get("float").asDouble());
                } else if (node.has("int")) {
                    return new LongValue(node.get("int").longValue());
                } else if (node.has("bool")) {
                    return new BooleanValue(node.get("bool").booleanValue());
                } else if (node.has("id")) {
                    JsonNode types = node.get("types");
                    List<Type> parsedTypes = new ArrayList<>();
                    if (types != null && types.isArray()) {
                        for (JsonNode type : types) {
                            parsedTypes.add(parser.getCodec().treeToValue(type, Type.class));
                        }
                    }
                    return new Entity(node.get("id").asText(),
                            node.has("name") ? node.get("name").asText() : null,
                            node.has("description") ? node.get("description").asText() : null,
                            parsedTypes);
                }
            } else if (node.isTextual()) {
                return new StringValue(node.asText());
            } else if (node.isBoolean()) {
                return new BooleanValue(node.asBoolean());
            } else if (node.isFloat() || node.isDouble()) {
                return new DoubleValue(node.asDouble());
            } else if (node.isBigDecimal() || node.isBigInteger() || node.isInt()) {
                return new LongValue(node.longValue());
            }

            throw new JsonMappingException(parser, "invalid property value: " + node);
        }
    }
}
