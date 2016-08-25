package com.ksxkq.net.factory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * OnePiece
 * Created by xukq on 8/25/16.
 */
public class JSONResponseBodyConverters {
    private JSONResponseBodyConverters() {}

    static final class JSONObjectResponseBodyConverter implements Converter<ResponseBody, JSONObject> {
        static final JSONObjectResponseBodyConverter INSTANCE = new JSONObjectResponseBodyConverter();

        @Override public JSONObject convert(ResponseBody value) throws IOException {
            try {
                return new JSONObject(value.string());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    static final class JSONArrayResponseBodyConverter implements Converter<ResponseBody, JSONArray> {
        static final JSONArrayResponseBodyConverter INSTANCE = new JSONArrayResponseBodyConverter();

        @Override public JSONArray convert(ResponseBody value) throws IOException {
            try {
                return new JSONArray(value.string());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
