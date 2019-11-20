package com.example.firstday;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    public static Context context;
    public static int DATABASE_VERSION=1;

    public MyApplication(){
        context=this;
    }

}
