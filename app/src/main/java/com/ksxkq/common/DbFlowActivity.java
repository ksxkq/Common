package com.ksxkq.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ksxkq.common.db.Dog;

/**
 * OnePiece
 * Created by xukq on 9/23/16.
 * 注意:使用前需要在 Application 中初始化
 */

public class DbFlowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 5; i++) {
            Dog dog = new Dog();
            dog.setName("dog " + i);
            dog.setTest(i);
            dog.save();
        }
    }
}
