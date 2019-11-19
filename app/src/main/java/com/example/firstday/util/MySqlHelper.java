package com.example.firstday.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqlHelper extends SQLiteOpenHelper {

    public MySqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("", "onUpgrade: "+newVersion);
        Log.e("", "onUpgrade: " );
        if (db != null) {
            db=this.getWritableDatabase();

        }
    }
}
