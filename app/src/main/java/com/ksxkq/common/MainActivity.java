package com.ksxkq.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ksxkq.common.http.Api;
import com.ksxkq.net.RxHelper;
import com.ksxkq.net.SimpleSubscribe;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void testRetrofit() {
        Api.getDefault()
                .test2()
                .compose(RxHelper.<JSONObject>applySchedulers())
                .subscribe(new SimpleSubscribe<JSONObject>() {
                    @Override
                    protected void _onNext(JSONObject s) {
                        List<String> list = new ArrayList<String>();
                        Log.d("kkk", "");
                    }

                    @Override
                    protected void _onError(Throwable e) {
                        Log.d("kkk", "");
                    }
                });
    }
}
