package com.weather.byhieg.easyweather;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.baidu.location.LocationClient;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.weather.byhieg.easyweather.data.source.local.dao.DaoMaster;
import com.weather.byhieg.easyweather.data.source.local.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

import cn.byhieg.betterload.network.NetService;
import cn.byhieg.monitor.TimeMonitorConfig;
import cn.byhieg.monitor.TimeMonitorManager;

public class MyApplication extends Application{

    private static MyApplication mcontext;
    public static DaoMaster daoMaster;
    public static DaoSession daoSession;
    public static LocationClient mLocationClient;


    private static final String cityUrl = "https://free-api.heweather.com/";
    private static final String heweatherKey = "93d476b872724a9681a642dce28c6523";

    public static final String shareFilename1 ="nightMode";
    public static final String shareFilename2 ="nightMode2";
    public static final String logFilename = "log";
    public static final String notificationname = "notification";
    public static final String widgetname = "widget";
    public static final String cachename = "cache";
//    public static RefWatcher mRefWatcher;
    public static boolean isNewDay = false;
    public static final boolean ENCRYPTED = false;

    public static DaoSession getDaoSession() {
        if (daoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getAppContext(), ENCRYPTED ?
                    "weather-db-encrypted" : "weather-db");
            Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
            daoMaster = new DaoMaster(db);
            daoSession = new DaoMaster(db).newSession();
        }
        return daoMaster.newSession();
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        TimeMonitorManager.getInstance().resetTimeMonitor(TimeMonitorConfig.TIME_MONITOR_ID_APPLICATION_START);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mcontext = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetService.getInstance().init(cityUrl);
                Logger.addLogAdapter(new AndroidLogAdapter());
            }
        }).start();

//        Stetho.initializeWithDefaults(this);
//        mRefWatcher = LeakCanary.install(mcontext);
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig
                .TIME_MONITOR_ID_APPLICATION_START).recordingTimeTag("ApplicationCreated");

//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getAppContext(),
//                "weather-db", null);
//        daoMaster = new DaoMaster(helper.getWritableDatabase());


//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(this);
//        crashHandler.sendPreviousReportsToServer();
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


    public static boolean nightMode(){
        SharedPreferences share = mcontext.getSharedPreferences(shareFilename1, MODE_PRIVATE);
        boolean ischecked=share.getBoolean("ischecked",false);
        return ischecked;
    }

    public static boolean nightMode2(){
        SharedPreferences share = mcontext.getSharedPreferences(shareFilename2, MODE_PRIVATE);
        boolean ischecked = share.getBoolean("ischecked",false);
        return ischecked;
    }


    public static boolean log(){
        SharedPreferences share = mcontext.getSharedPreferences(logFilename, MODE_PRIVATE);
        boolean ischecked = share.getBoolean("ischecked",true);
        return ischecked;
    }


    public static boolean notification(){
        SharedPreferences share = mcontext.getSharedPreferences(notificationname, MODE_PRIVATE);
        boolean ischecked = share.getBoolean("ischecked",true);
        return ischecked;
    }

    public static boolean widget(){
        SharedPreferences share = mcontext.getSharedPreferences(widgetname, MODE_PRIVATE);
        boolean ischecked = share.getBoolean("ischecked",true);
        return ischecked;
    }

    public static boolean cache(){
        SharedPreferences share = mcontext.getSharedPreferences(cachename, MODE_PRIVATE);
        boolean ischecked = share.getBoolean("ischecked",true);
        return ischecked;
    }

    public static LocationClient getmLocationClient() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(mcontext);

        }
        return mLocationClient;
    }

    public static boolean isNewDay() {
        return isNewDay;
    }
}
