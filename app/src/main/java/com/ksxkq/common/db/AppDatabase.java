package com.ksxkq.common.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * OnePiece
 * Created by xukq on 8/26/16.
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    //数据库名称
    public static final String NAME = "AppDatabase";
    //数据库版本号
    public static final int VERSION = 1;
}
