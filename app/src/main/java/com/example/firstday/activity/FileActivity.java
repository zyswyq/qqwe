package com.example.firstday.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.firstday.MyApplication;
import com.example.firstday.R;
import com.example.firstday.util.FileUtil;

import java.io.File;
import java.io.IOException;

public class FileActivity extends AppCompatActivity implements View.OnClickListener {
    private String fileName="text.txt";
    private String dicName="/textDic";
//    private String path= MyApplication.context.getFilesDir().getPath();
    private String path= Environment.getExternalStorageDirectory().getPath() + File.separator;
    private Button createDic,createFile,readFile,writeFile,delFile;
    private TextView showTv;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);
        checkPermission();
        initView();
        initEvent();
    }
    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
        }
    }
    private void initEvent() {
        createDic.setOnClickListener(this);
        createFile.setOnClickListener(this);
        readFile.setOnClickListener(this);
        writeFile.setOnClickListener(this);
        delFile.setOnClickListener(this);

    }

    private void initView() {
        createDic=findViewById(R.id.btn_create_dic);
        createFile=findViewById(R.id.btn_create_file);
        readFile=findViewById(R.id.btn_read);
        writeFile=findViewById(R.id.btn_write);
        delFile=findViewById(R.id.btn_delete);
        showTv=findViewById(R.id.tv_show);
        editText=findViewById(R.id.edit);
    }

    @Override
    public void onClick(View v) {
        showTv.setText("");
        int id = v.getId();
        if (id == R.id.btn_create_dic) {
            showTv.setText(FileUtil.createDirectory(path + dicName) + "");
        } else if (id == R.id.btn_create_file) {
            showTv.setText(FileUtil.createFile(path, fileName) + "");
        } else if (id == R.id.btn_delete) {
            FileUtil.deleteDirectory(path + "/" + fileName);
        } else if (id == R.id.btn_read) {
            showTv.setText(FileUtil.readFile(path + "/" + fileName));
        } else if (id == R.id.btn_write) {
            showTv.setText(FileUtil.writeInFile(path + "/" + fileName, editText.getText().toString()) + "");
        }
    }
}
