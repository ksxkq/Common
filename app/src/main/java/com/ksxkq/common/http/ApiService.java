package com.ksxkq.common.http;

import org.json.JSONObject;

import retrofit2.http.GET;
import rx.Observable;

/**
 * OnePiece
 * Created by xukq on 8/24/16.
 */
public interface ApiService {

    @GET("version_config.txt")
    Observable<JSONObject> test2();
}
