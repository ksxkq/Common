package com.ksxkq.net.result;

import com.ksxkq.net.HttpConfig;

/**
 * OnePiece
 * Created by xukq on 8/24/16.
 */
public class BaseResult<T> {
    public String resultCode;
    public String resultMessage;
    public T data;

    public boolean isSuccess() {
        return HttpConfig.getSuccessModule().isSuccess(resultCode);
    }
}
