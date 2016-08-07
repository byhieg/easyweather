package com.weather.byhieg.easyweather;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application{

    private static MyApplication mcontext;
    @Override
    public void onCreate() {
        super.onCreate();
        mcontext=this;
    }
    public static Context getAppContext(){
        return mcontext;
    }
}
