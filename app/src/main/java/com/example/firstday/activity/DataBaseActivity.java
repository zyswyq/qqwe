package com.example.firstday.activity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstday.R;
import com.example.firstday.util.DataBaseUtil;

import java.util.HashMap;
import java.util.Map;

public class DataBaseActivity extends AppCompatActivity implements View.OnClickListener {

    private Button createDatabase, createTable, addBtn, delBtn, updateBtn, queryBtn, updateDatabase;
    private TextView showTv;
    private boolean isUpdated=false;

    private DataBaseUtil dataBaseUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        initView();
        initEvent();
        dataBaseUtil = new DataBaseUtil();
    }

    private void initEvent() {
        createDatabase.setVisibility(View.GONE);
        createDatabase.setOnClickListener(this);
        createTable.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        updateDatabase.setOnClickListener(this);
    }

    private void initView() {
        createDatabase = findViewById(R.id.btn_create_database);
        createTable = findViewById(R.id.btn_create_table);
        addBtn = findViewById(R.id.btn_add);
        delBtn = findViewById(R.id.btn_del);
        updateBtn = findViewById(R.id.btn_update);
        queryBtn = findViewById(R.id.btn_query);
        updateDatabase = findViewById(R.id.btn_table_update);
        showTv = findViewById(R.id.tv_show);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        showTv.setText("");
        switch (v.getId()) {
//            case R.id.btn_create_database:
//                dataBaseUtil=new DataBaseUtil();
//                break;
            case R.id.btn_create_table:
                Map map=new HashMap();
                map.put("lottery_id","integer");
                map.put("lottery_key","varchar(20)");
                map.put("ball","char");
                dataBaseUtil.createTable("select_ball_table",map,"lottery_id");
                break;
            case R.id.btn_add:
                ContentValues contentValues=new ContentValues();
                contentValues.put("lottery_key","这是key");
                contentValues.put("ball","这是ball");
                showTv.setText(dataBaseUtil.addData("select_ball_table",contentValues)+"");
                break;
            case R.id.btn_del:
                showTv.setText(dataBaseUtil.delData("select_ball_table"," lottery_id= 1")+"");
                break;
            case R.id.btn_update:
                ContentValues contentValues1=new ContentValues();
                contentValues1.put("lottery_key","这是更新的key");
                contentValues1.put("ball","这是更新的ball");
                showTv.setText(dataBaseUtil.updateData("select_ball_table","",contentValues1)+"");
                break;
            case R.id.btn_query:
                String sql="select * from select_ball_table";
                if (!isUpdated){
                    showTv.setText(dataBaseUtil.queryBySqlSingle(sql,new String[]{},new String[]{"lottery_id","lottery_key","ball"}).toString()+"");
                }else {
                    showTv.setText(dataBaseUtil.queryBySqlSingle(sql,new String[]{},new String[]{"lottery_id","lottery_key","ball","name"}).toString()+"");
                }
                break;
            case R.id.btn_table_update:
                dataBaseUtil.updateDataBase(2);
                isUpdated=true;
                break;
        }
    }
}
