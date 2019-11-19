package com.example.firstday.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.firstday.R;
import com.example.firstday.util.IMyWeb;
import com.example.firstday.view.MyWebView;

public class WebActivity extends AppCompatActivity {

    private TextView title_tv,progress_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
    }

    private void initView() {
        title_tv=findViewById(R.id.tv_title);
        progress_tv=findViewById(R.id.tv_progess);
        MyWebView myWeb = findViewById(R.id.myWebView);
        myWeb.setMyWeb(new IMyWeb() {
            @Override
            public void getTitle(String title) {
                title_tv.setText(title);
            }

            @Override
            public void getProgress(int progress) {
                progress_tv.setText(progress+"%");
            }

            @Override
            public void errorLoad(int errorCode, String description) {
                title_tv.setText(description+"/错误码/"+errorCode);
            }
        });
        myWeb.loadUrl("https://www.so.com/?src=www&fr=360_wsyjsw&ls=sn2321298&lm_extend=ctype%3A4");
    }
}
