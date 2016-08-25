package com.ksxkq.net;

import rx.Subscriber;

/**
 * OnePiece
 * Created by xukq on 8/24/16.
 */

public abstract class SimpleSubscribe<T> extends Subscriber<T> {

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
        onFinish();
    }

    @Override
    public void onError(Throwable e) {
        _onError(e);
        onFinish();
    }

    @Override
    public void onCompleted() {

    }

    public void onFinish() {

    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(Throwable e);

}
