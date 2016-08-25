package com.ksxkq.net.factory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * OnePiece
 * Created by xukq on 8/25/16.
 */
public class JSONConverterFactory extends Converter.Factory {
    public static JSONConverterFactory create() {
        return new JSONConverterFactory();
    }

    private JSONConverterFactory() {
    }

    @Override public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                                    Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (type == JSONObject.class
                || type == JSONArray.class) {
            return JSONRequestBodyConverter.INSTANCE;
        }
        return null;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (type == JSONObject.class) {
            return JSONResponseBodyConverters.JSONObjectResponseBodyConverter.INSTANCE;
        }
        if (type == JSONArray.class) {
            return JSONResponseBodyConverters.JSONArrayResponseBodyConverter.INSTANCE;
        }
        return null;
    }
}

