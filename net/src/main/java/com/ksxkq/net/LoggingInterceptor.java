package com.ksxkq.net;


import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/1/5 下午2:02
 * 描述:
 */
public class LoggingInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long startTime = System.nanoTime();
        Logger.i(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long endTime = System.nanoTime();
        MediaType contentType = response.body().contentType();
        String content = response.body().string();
        Logger.i(String.format("Received response for %s in %.1fms%n%s%n%s", response.request().url(), (endTime - startTime) / 1e6d, response.headers(), content));

        return response.newBuilder().body(ResponseBody.create(contentType, content)).build();
    }
}
