package com.example.firstday.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private Context context;
    private Cursor mCursor;

    private String  DATABASE_NAME="TEST";

    public DataBaseUtil(Context context){
        this.context=context;
    }

    public void createDatabase(){
        mSQLiteOpenHelper =new MySqlHelper(context,DATABASE_NAME,null,1);
    };

    public void  updateDataBase(){
        mSQLiteOpenHelper.close();
        mSQLiteOpenHelper =new MySqlHelper(context,DATABASE_NAME,null,2);
    }

    public void createTable(String tableName , Map<String ,Object> setList,String primary){
        StringBuilder sql=new StringBuilder();
        sql.append("create table "+tableName+"{");
        for ( Map.Entry<String, Object>  m: setList.entrySet()) {
            if (primary.equals(m.getKey())){
                sql.append(m.getKey()).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
            }else {
                sql.append(m.getKey()).append(m.getValue()).append(",");
            }
        }
        sql.deleteCharAt(sql.length()-1).append("}");
        mSQLiteDatabase=mSQLiteOpenHelper.getWritableDatabase();
        mSQLiteDatabase.execSQL(sql.toString());
        closeAll();
    };

    public boolean addData(String tableName,ContentValues data){
        mSQLiteDatabase=mSQLiteOpenHelper.getWritableDatabase();
        long i=mSQLiteDatabase.insert(tableName,null,data);
        closeAll();
        return i==1?true:false;
    }
    public boolean delData(String tableName,String causeBy){
        mSQLiteDatabase=mSQLiteOpenHelper.getWritableDatabase();
        long i=mSQLiteDatabase.delete(tableName,causeBy,null);
        closeAll();
        return i==1?true:false;
    }
    public boolean updateData(String tableName,String causeBy,ContentValues contentValues){
        mSQLiteDatabase=mSQLiteOpenHelper.getWritableDatabase();
        int i= (int) mSQLiteDatabase.update(tableName,contentValues,causeBy,null);
        closeAll();
        return i==1?true:false;
    }

    public Map queryBySqlSingle(String sql, String[] params, String[] indexs) {
        mSQLiteDatabase = mSQLiteOpenHelper.getWritableDatabase();
        mCursor = mSQLiteDatabase.rawQuery(sql, params);
        List<Map> list = new ArrayList<>();
        while (mCursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < indexs.length; i++) {
                //通过列名得到下标
                int index = mCursor.getColumnIndex(indexs[i]);
                //通过下标得到查询内容
                String result = mCursor.getString(index);
                if ("null".equals(result)){
                    result="";
                }
                map.put(indexs[i], result);
            }
            list.add(map);
        }
        closeAll();
        return list!=null&&list.size()>0?list.get(0):null;
    }

    /**
     * 关闭全部
     */
    public void closeAll() {
        if (mCursor != null && !mCursor.isClosed()) {
            mCursor.close();
        } else {
        }
        if (mSQLiteOpenHelper != null) {
            mSQLiteOpenHelper.close();
        } else {
        }
        if (mSQLiteDatabase != null && mSQLiteDatabase.isOpen()) {
            mSQLiteDatabase.close();
        } else {
        }
    }
}
