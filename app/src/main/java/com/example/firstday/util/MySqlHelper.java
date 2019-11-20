package com.example.firstday.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.firstday.MyApplication;

public class MySqlHelper extends SQLiteOpenHelper {
    private static MySqlHelper sqlHelper;


    public MySqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="alter table select_ball_table add name varchar";
        db.execSQL(sql);
    }
}
