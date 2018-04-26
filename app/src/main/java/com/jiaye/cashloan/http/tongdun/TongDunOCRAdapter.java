package com.jiaye.cashloan.http.tongdun;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.jiaye.cashloan.http.base.NetworkException;

import java.io.IOException;

/**
 * TongDunOCRAdapter
 *
 * @author 贾博瑄
 */
public class TongDunOCRAdapter extends TypeAdapter<TongDunFace> {

    @Override
    public void write(JsonWriter out, TongDunFace value) throws IOException {

    }

    @Override
    public TongDunFace read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.STRING) {
            throw new NetworkException("", in.nextString());
        }
        return new Gson().fromJson(in, TongDunFace.class);
    }
}
