package com.ksxkq.net;

import com.ksxkq.net.result.BaseResult;

import org.json.JSONException;
import org.json.JSONObject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * OnePiece
 * Created by xukq on 8/24/16.
 */
public class RxHelper {

    /**
     * 对结果进行预处理
     *
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<BaseResult<T>, T> handleBaseResult() {
        return new Observable.Transformer<BaseResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseResult<T>> tObservable) {
                return tObservable.flatMap(new Func1<BaseResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseResult<T> result) {
                        if (result.isSuccess()) {
                            return createData(result.data);
                        } else {
                            Throwable errorThrowable;
                            try {
                                JSONObject error = new JSONObject();
                                error.put("resultCode", result.resultCode);
                                error.put("message", result.resultMessage);
                                errorThrowable = new Throwable(error.toString());
                            } catch (JSONException e) {
                                errorThrowable = new Throwable(e);
                            }
                            return Observable.error(errorThrowable);
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.just(data);
    }
}
