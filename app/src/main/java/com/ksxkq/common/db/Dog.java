package com.ksxkq.common.db;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * OnePiece
 * Created by xukq on 8/26/16.
 */
@Table(database = AppDatabase.class)
public class Dog extends BaseModel {
    @PrimaryKey(autoincrement = true)
    long id;
    @Column
    String name;
    @Column
    double test;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTest() {
        return test;
    }

    public void setTest(double test) {
        this.test = test;
    }
}
