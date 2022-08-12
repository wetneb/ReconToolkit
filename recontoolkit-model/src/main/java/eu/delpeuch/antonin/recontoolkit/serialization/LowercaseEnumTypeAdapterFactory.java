
package eu.delpeuch.antonin.recontoolkit.serialization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * Adapter to make sure enums are serialized in lowercase.
 * 
 * Taken from Gson's documentation (Copyright 2008 Google, Apache 2.0 license)
 * https://javadoc.io/doc/com.google.code.gson/gson/latest/com.google.gson/com/google/gson/TypeAdapterFactory.html
 */
public class LowercaseEnumTypeAdapterFactory implements TypeAdapterFactory {

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (!rawType.isEnum()) {
            return null;
        }

        final Map<String, T> lowercaseToConstant = new HashMap<String, T>();
        for (T constant : rawType.getEnumConstants()) {
            lowercaseToConstant.put(toLowercase(constant), constant);
        }

        return new TypeAdapter<T>() {

            public void write(JsonWriter out, T value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else {
                    out.value(toLowercase(value));
                }
            }

            public T read(JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    reader.nextNull();
                    return null;
                } else {
                    return lowercaseToConstant.get(reader.nextString());
                }
            }
        };
    }

    private String toLowercase(Object o) {
        return o.toString().toLowerCase(Locale.US);
    }
}
