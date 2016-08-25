package com.ksxkq.net;

/**
 * OnePiece
 * Created by xukq on 8/24/16.
 */
public class HttpConfig {

    private static SuccessModule successModule;

    public static SuccessModule getSuccessModule() {
        if (successModule == null) {
            throw new NullPointerException("You must point a SuccessModule impl");
        }
        return successModule;
    }

    public static void init(SuccessModule successModule) {
        HttpConfig.successModule = successModule;
    }

}

