package com.ryuta.baking.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/*
Taken from https://stackoverflow.com/questions/17341234/gson-avoid-the-jsonsyntaxexception-return-the-partial-mapping
 */
public class LenientTypeAdapterFactory implements TypeAdapterFactory {
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
        return new TypeAdapter<T>() {

            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            public T read(JsonReader in) throws IOException {
                try {
                    return delegate.read(in);
                } catch (JsonSyntaxException e) {
                    in.skipValue();
                    return null;
                }
            }
        };
    }
}
