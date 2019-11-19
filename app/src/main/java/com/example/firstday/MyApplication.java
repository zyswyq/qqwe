package com.example.firstday;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    public static Context context;

    public MyApplication(){
        context=this;
    }

}
