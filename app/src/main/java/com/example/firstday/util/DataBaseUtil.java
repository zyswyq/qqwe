package com.example.firstday.util;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.firstday.MyApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作类
 * 1.创建数据库。
 * 2.新建表。
 * 3.能够对数据库的表进行增删改查操作。
 * 4.数据库升级添加新的一列。
 */
public class DataBaseUtil {
    private SQLiteDatabase mSQLiteDatabase;
    private SQLiteOpenHelper mSQLiteOpenHelper;
    private Cursor mCursor;
    private static String NAME = "wangcai_lottery.db";

    /**
     * 创建数据库
     */
    public DataBaseUtil() {
        mSQLiteOpenHelper = new MySqlHelper(MyApplication.context, NAME, null, MyApplication.DATABASE_VERSION);
    }


//    public void createDatabase() {
//
//        closeAll();
//    }


    /**
     * 更新数据库
     *
     * @param version 版本号
     */
    public void updateDataBase(int version) {
        mSQLiteOpenHelper = new MySqlHelper(MyApplication.context, NAME, null, version);
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        closeAll();
    }

    /**
     * 创建表
     *
     * @param tableName 表名
     * @param setList   列名与类型
     * @param primary   主键的列名
     */
    public void createTable(String tableName, Map<String, Object> setList, String primary) {
        StringBuilder sql = new StringBuilder();
        sql.append("create table if not exists ").append(tableName).append("(");
        for (Map.Entry<String, Object> m : setList.entrySet()) {
            if (primary.equals(m.getKey())) {
                sql.append(m.getKey()).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
//                sql.append(m.getKey()).append(" INTEGER PRIMARY KEY ,");
            } else {
                sql.append(m.getKey()).append(" ").append(m.getValue()).append(",");
            }
        }

        sql.deleteCharAt(sql.length() - 1).append(")");
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mSQLiteDatabase.execSQL(sql.toString());
        closeAll();
    }

    ;

    /**
     * 新增数据
     *
     * @param tableName 表名
     * @param data      新增的数据
     * @return 是否成功
     */
    public boolean addData(String tableName, ContentValues data) {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        long i = mSQLiteDatabase.insert(tableName, null, data);
        closeAll();
        return i >= 1;
    }


    /**
     * 删除数据
     *
     * @param tableName 表名
     * @param causeBy   删除条件
     * @return 是否成功
     */
    public boolean delData(String tableName, String causeBy) {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        long i = mSQLiteDatabase.delete(tableName, causeBy, null);
        closeAll();
        return i == 1;
    }

    /**
     * 更新数据
     *
     * @param tableName     表名
     * @param causeBy       更新条件
     * @param contentValues 更新值
     * @return 是否成功
     */
    public boolean updateData(String tableName, String causeBy, ContentValues contentValues) {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        int i = mSQLiteDatabase.update(tableName, contentValues, causeBy, null);
        closeAll();
        return i >= 1;
    }

    /**
     * 查询语句
     *
     * @param sql    查询sql
     * @param params 查询条件值
     * @param indexs 查询的列名
     * @return 查询到的数据
     */
    public List<Map> queryBySqlSingle(String sql, String[] params, String[] indexs) {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mCursor = mSQLiteDatabase.rawQuery(sql, params);
        List<Map> list = new ArrayList<>();
        while (mCursor.moveToNext()) {
            Map<String, Object> map = new HashMap<>();
            for (String s : indexs) {
                //通过列名得到下标
                int index = mCursor.getColumnIndex(s);
                //通过下标得到查询内容
                String result = mCursor.getString(index);
                if ("null".equals(result)) {
                    result = "";
                }
                map.put(s, result);
            }
            list.add(map);
        }
        closeAll();
        return list;
    }

    /**
     * 关闭
     */
    private void closeAll() {
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        }
        if (mSQLiteDatabase != null && mSQLiteDatabase.isOpen()) {
            mSQLiteDatabase.close();
        }
    }
}
