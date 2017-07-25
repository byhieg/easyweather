package com.weather.byhieg.easyweather.startweather;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.weather.byhieg.easyweather.base.BaseActivity;
import com.weather.byhieg.easyweather.home.MainActivity;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.tools.MainThreadAction;

import butterknife.ButterKnife;
import cn.byhieg.monitor.TimeMonitorConfig;
import cn.byhieg.monitor.TimeMonitorManager;

public class StartActivity extends AppCompatActivity implements StartWeatherContract.View {

    private static final String ACTION_ADD_CITY = "com.weather.byhieg.easyweather.startweather" +
            ".Activity.action.addCity";

    private static final String ACTION_GET_WEATHER = "com.weather.byhieg.easyweather.startweather" +
            ".Activity.action.getWeather";

    private static final String ACTION_START_NOTIFICATION = "com.weather.byhieg.easyweather" +
            ".startweather.Activity.action.notification";


    private static final String ACTION_FILE_PROCESS = "com.weather.byhieg.easyweather.startweather" +
            ".Activity.action.fileprocess";

    private StartWeatherContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig
                .TIME_MONITOR_ID_APPLICATION_START).recordingTimeTag("StartActivity_create");
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(getLayoutId());
        initData();
        initView();
        initEvent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig
                .TIME_MONITOR_ID_APPLICATION_START).end("StartActivity_start");
    }

    public void initData() {
        ButterKnife.bind(this);
        mPresenter = new StartWeatherPresenter(this);
        mPresenter.start();
    }

    public void initEvent() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                startActivity(MainActivity.class);
//                finish();
//            }
//        }, 3500);
        MainThreadAction.getInstance().post(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
            }
        },3500);
    }

    public void initView() {

    }

    public void initTheme() {

    }

    public int getLayoutId() {
        return R.layout.activity_start;
    }


    @Override
    public void setPresenter(StartWeatherContract.Presenter presenter) {

    }

    @Override
    public void startService() {
        startAddCityService();
        startGetWeatherService();
        startNotificationService();
        startFileProcessService();
    }

    private void startAddCityService() {
        Intent intent = new Intent(this, BackGroundService.class);
        intent.setAction(ACTION_ADD_CITY);
        startService(intent);
    }

    private void startGetWeatherService() {
        Intent intent = new Intent(this, BackGroundService.class);
        intent.setAction(ACTION_GET_WEATHER);
        startService(intent);
    }

    private void startNotificationService() {
        Intent intent = new Intent(this, BackGroundService.class);
        intent.setAction(ACTION_START_NOTIFICATION);
        startService(intent);
    }


    private void startFileProcessService() {
        Intent intent = new Intent(this, BackGroundService.class);
        intent.setAction(ACTION_FILE_PROCESS);
        startService(intent);
    }

    public static String getActionAddCity() {
        return ACTION_ADD_CITY;
    }

    public static String getActionStartNotification() {
        return ACTION_START_NOTIFICATION;
    }

    public static String getActionGetWeather() {
        return ACTION_GET_WEATHER;
    }



    public static String getActionFileProcess() {
        return ACTION_FILE_PROCESS;
    }


}
