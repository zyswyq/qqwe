package com.example.firstday.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.firstday.R;
import com.example.firstday.view.MyView;

public class MainActivity extends AppCompatActivity {

    private MyView myView;
    private Class[] jumpContext=new Class[]{WebActivity.class,FileActivity.class,DataBaseActivity.class};
    private int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initEvent() {
        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,jumpContext[i%3]);
                i++;
                startActivity(intent);
            }
        });
    }

    private void initView() {
        myView=findViewById(R.id.myView);
        myView.setMyTextStr("代码中修改");
        myView.setMyShape(MyView.Shape.Square);
        myView.setMyTextSize(35);
        myView.setMyViewColor(R.color.colorAccent);
    }
}
