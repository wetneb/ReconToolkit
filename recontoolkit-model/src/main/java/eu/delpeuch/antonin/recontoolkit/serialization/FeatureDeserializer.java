
package eu.delpeuch.antonin.recontoolkit.serialization;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import eu.delpeuch.antonin.recontoolkit.protocol.BooleanFeature;
import eu.delpeuch.antonin.recontoolkit.protocol.DoubleFeature;
import eu.delpeuch.antonin.recontoolkit.protocol.Feature;

/**
 * Custom deserializer for {@link Feature}s.
 * 
 * @author antonin
 *
 */
public class FeatureDeserializer implements JsonDeserializer<Feature>, JsonSerializer<Feature>, Feature.Visitor<JsonElement> {

    @Override
    public Feature deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            throw new JsonParseException("Expecting a JSON object to deserialize a matching feature");
        }
        JsonObject object = json.getAsJsonObject();
        if (!object.has("id") || !object.get("id").isJsonPrimitive() || !object.get("id").getAsJsonPrimitive().isString()) {
            throw new JsonParseException("Expecting a string-valued 'id' field in matching feature");
        }
        String id = object.get("id").getAsJsonPrimitive().getAsString();
        if (!object.has("value") || !object.get("value").isJsonPrimitive()) {
            throw new JsonParseException("Expecting boolean or numerical 'value' field in matching feature");
        }
        JsonPrimitive primitive = object.get("value").getAsJsonPrimitive();
        if (primitive.isBoolean()) {
            return new BooleanFeature(id, primitive.getAsBoolean());
        } else if (primitive.isNumber()) {
            return new DoubleFeature(id, primitive.getAsDouble());
        } else {
            throw new JsonParseException("Unsupported matching feature type: " + primitive.getClass().getName());
        }
    }

    /**
     * Because of https://github.com/google/gson/issues/2032 we also need to implement custom serialization
     */
    @Override
    public JsonElement serialize(Feature src, Type typeOfSrc, JsonSerializationContext context) {
        return src.accept(this);
    }

    @Override
    public JsonElement visit(BooleanFeature feature) {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", feature.getId());
        obj.addProperty("value", feature.getValue());
        return obj;
    }

    @Override
    public JsonElement visit(DoubleFeature feature) {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", feature.getId());
        obj.addProperty("value", feature.getValue());
        return obj;
    }

}
