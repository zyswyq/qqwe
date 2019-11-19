package com.example.firstday.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstday.R;
import com.example.firstday.util.DataBaseUtil;

public class DataBaseActivity extends AppCompatActivity implements View.OnClickListener {

    private Button createDatabase,createTable,addBtn,delBtn,updateBtn,queryBtn,updateDatabase;
    private TextView showTv;

    private DataBaseUtil dataBaseUtil;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        initView();
        initEvent();
        dataBaseUtil=new DataBaseUtil(this);
    }

    private void initEvent() {
        createDatabase.setOnClickListener(this);
        createTable.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        delBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        updateDatabase.setOnClickListener(this);
    }

    private void initView() {
        createDatabase=findViewById(R.id.btn_create_database);
        createTable=findViewById(R.id.btn_create_table);
        addBtn=findViewById(R.id.btn_add);
        delBtn=findViewById(R.id.btn_del);
        updateBtn=findViewById(R.id.btn_update);
        queryBtn=findViewById(R.id.btn_query);
        updateDatabase=findViewById(R.id.btn_table_update);
        showTv=findViewById(R.id.tv_show);
    }

    @Override
    public void onClick(View v) {
        showTv.setText("");
        switch (v.getId()) {
            case R.id.btn_create_database:
                dataBaseUtil.createDatabase();
                break;
            case R.id.btn_create_table:
//                dataBaseUtil.createTable();
                break;
            case R.id.btn_add:
//                dataBaseUtil.addData();
                break;
            case R.id.btn_del:
//                dataBaseUtil.delData()
                break;
            case R.id.btn_update:
//                dataBaseUtil.updateData()
                break;
            case R.id.btn_query:
//                dataBaseUtil.queryBySqlSingle("")
                break;
            case R.id.btn_table_update:
                dataBaseUtil.updateDataBase();
                break;
        }
    }
}
