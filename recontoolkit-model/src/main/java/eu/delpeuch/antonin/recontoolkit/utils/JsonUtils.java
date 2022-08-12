
package eu.delpeuch.antonin.recontoolkit.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import eu.delpeuch.antonin.recontoolkit.protocol.Feature;
import eu.delpeuch.antonin.recontoolkit.protocol.PropertyMapping;
import eu.delpeuch.antonin.recontoolkit.serialization.FeatureDeserializer;
import eu.delpeuch.antonin.recontoolkit.serialization.LowercaseEnumTypeAdapterFactory;
import eu.delpeuch.antonin.recontoolkit.serialization.PropertyMappingSerializer;

public class JsonUtils {

    public static Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory());
        builder.registerTypeAdapter(PropertyMapping.class, new PropertyMappingSerializer());
        builder.registerTypeAdapter(Feature.class, new FeatureDeserializer());
        gson = builder.create();
    }
}
