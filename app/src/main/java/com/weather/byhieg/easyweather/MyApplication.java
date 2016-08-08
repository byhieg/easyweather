package com.weather.byhieg.easyweather;

import android.app.Application;
import android.content.Context;

import com.weather.byhieg.easyweather.Db.DaoMaster;
import com.weather.byhieg.easyweather.Db.DaoSession;

public class MyApplication extends Application{

    private static MyApplication mcontext;
    public static DaoSession daoSession;
    public static DaoMaster daoMaster;


    public static DaoMaster getDaoMaster(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "Weather-db", null);
        daoMaster = new DaoMaster(helper.getWritableDatabase());
        return daoMaster;
    }
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mcontext=this;
    }
    public static Context getAppContext(){
        return mcontext;
    }
}
