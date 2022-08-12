
package eu.delpeuch.antonin.recontoolkit.serialization;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import eu.delpeuch.antonin.recontoolkit.model.BooleanValue;
import eu.delpeuch.antonin.recontoolkit.model.DoubleValue;
import eu.delpeuch.antonin.recontoolkit.model.Entity;
import eu.delpeuch.antonin.recontoolkit.model.LongValue;
import eu.delpeuch.antonin.recontoolkit.model.PropertyValue;
import eu.delpeuch.antonin.recontoolkit.model.StringValue;
import eu.delpeuch.antonin.recontoolkit.protocol.PropertyMapping;

/**
 * Serializes reconciliation queries in a special way: omits the array around the value if there is a single one. This
 * matches the spec versions 0.1 and 0.2.
 * 
 * It also serializes the property values as atomic JSON elements, as required by the same specs.
 * 
 * @author antonin
 *
 */
public class PropertyMappingSerializer implements
        JsonSerializer<PropertyMapping>,
        PropertyValue.Visitor<JsonElement>,
        JsonDeserializer<PropertyMapping> {

    @Override
    public JsonElement serialize(PropertyMapping src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        json.add("pid", new JsonPrimitive(src.getPid()));
        if (src.getV() == null) {
            json.add("v", JsonNull.INSTANCE);
        } else {
            JsonArray serialized = new JsonArray(src.getV().size());
            src.getV().stream()
                    .forEach(v -> serialized.add(v == null ? JsonNull.INSTANCE : v.accept(this)));
            if (serialized.size() == 1) {
                json.add("v", serialized.get(0));
            } else {
                json.add("v", serialized);
            }
        }
        return json;
    }

    @Override
    public PropertyMapping deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            throw new JsonParseException("Expecting a JSON object to specify a property mapping in reconciliation query");
        }
        JsonObject obj = json.getAsJsonObject();

        // Parse pid
        if (!obj.has("pid") || !obj.get("pid").isJsonPrimitive()) {
            throw new JsonParseException("Expecting a string as 'pid' in property mapping of reconciliation query");
        }
        String pid = obj.get("pid").getAsString();

        // Parse value(s)
        List<PropertyValue> parsedValues = new ArrayList<>();
        if (!obj.has("v")) {
            throw new JsonParseException("Expecting one or more values in field 'v' of property mapping in reconciliation query");
        }
        if (obj.get("v").isJsonArray()) {
            for (JsonElement element : obj.get("v").getAsJsonArray()) {
                parsedValues.add(parseValue(element));
            }
        } else {
            // attempt to parse it as a single value
            parsedValues.add(parseValue(obj.get("v")));
        }

        return new PropertyMapping(pid, parsedValues);
    }

    protected PropertyValue parseValue(JsonElement element) throws JsonParseException {
        if (element.isJsonNull()) {
            return null;
        } else if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = element.getAsJsonPrimitive();
            if (primitive.isBoolean()) {
                return new BooleanValue(primitive.getAsBoolean());
            } else if (primitive.isString()) {
                return new StringValue(primitive.getAsString());
            } else if (primitive.isNumber() && primitive.getAsBigDecimal().scale() > 0) {
                return new DoubleValue(primitive.getAsDouble());
            } else if (primitive.isNumber() && primitive.getAsBigDecimal().scale() <= 0) {
                return new LongValue(primitive.getAsLong());
            }
        } else if (element.isJsonObject()) {
            JsonObject object = element.getAsJsonObject();
            if (!object.has("id") || !object.get("id").isJsonPrimitive() || !object.get("id").getAsJsonPrimitive().isString()) {
                throw new JsonParseException("Expecting a string-value field 'id' in property value in reconciliation query");
            }
            String id = object.get("id").getAsString();
            String name = null;
            if (object.has("name") && object.get("name").isJsonPrimitive() && object.get("name").getAsJsonPrimitive().isString()) {
                name = object.get("name").getAsString();
            }
            return new Entity(id, name, null, null);
        }
        throw new JsonParseException("Unexpected property value in reconciliation query");
    }

    @Override
    public JsonElement visit(StringValue stringValue) {
        return new JsonPrimitive(stringValue.getValue());
    }

    @Override
    public JsonElement visit(LongValue longValue) {
        return new JsonPrimitive(longValue.getValue());
    }

    @Override
    public JsonElement visit(DoubleValue doubleValue) {
        return new JsonPrimitive(doubleValue.getValue());
    }

    @Override
    public JsonElement visit(BooleanValue booleanValue) {
        return new JsonPrimitive(booleanValue.getValue());
    }

    @Override
    public JsonElement visit(Entity entity) {
        JsonObject obj = new JsonObject();
        obj.add("id", new JsonPrimitive(entity.getId()));
        if (entity.getName() != null) {
            obj.add("name", new JsonPrimitive(entity.getName()));
        }
        return obj;
    }

}
