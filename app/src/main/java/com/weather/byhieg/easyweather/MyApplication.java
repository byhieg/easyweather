package com.weather.byhieg.easyweather;

import android.app.Application;
import android.content.Context;

import com.weather.byhieg.easyweather.Db.DaoMaster;
import com.weather.byhieg.easyweather.Db.DaoSession;

public class MyApplication extends Application{

    private static MyApplication mcontext;
    public static DaoSession daoSession;
    public static DaoMaster daoMaster;

    private static final String cityUrl = "https://api.heweather.com/x3/weather";
    private static final String heweatherKey = "93d476b872724a9681a642dce28c6523";


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
        mcontext = this;
    }

    public static Context getAppContext() {
        return mcontext;
    }

    public static String getCityUrl() {
        return cityUrl;
    }

    public static String getHeweatherKey() {
        return heweatherKey;
    }
}
