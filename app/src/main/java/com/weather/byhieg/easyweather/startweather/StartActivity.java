package com.weather.byhieg.easyweather.startweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.home.MainActivity;
import com.weather.byhieg.easyweather.tools.MainThreadAction;

import butterknife.ButterKnife;
import cn.byhieg.monitor.TimeMonitorConfig;
import cn.byhieg.monitor.TimeMonitorManager;

public class StartActivity extends AppCompatActivity {

    private static final String ACTION_ADD_CITY = "com.weather.byhieg.easyweather.startweather" +
            ".Activity.action.addCity";

    private static final String ACTION_GET_WEATHER = "com.weather.byhieg.easyweather.startweather" +
            ".Activity.action.getWeather";

    private static final String ACTION_START_NOTIFICATION = "com.weather.byhieg.easyweather" +
            ".startweather.Activity.action.notification";


    private static final String ACTION_FILE_PROCESS = "com.weather.byhieg.easyweather.startweather" +
            ".Activity.action.fileprocess";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig
                .TIME_MONITOR_ID_APPLICATION_START).recordingTimeTag("StartActivity_create");
        super.onCreate(savedInstanceState);
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig
                .TIME_MONITOR_ID_APPLICATION_START).end
                ("StartActivity_create_setContentView_start");
        setContentView(getLayoutId());
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig
                .TIME_MONITOR_ID_APPLICATION_START).end
                ("StartActivity_create_setContentView_stop");
        ButterKnife.bind(this);
        initEvent();

    }

    @Override
    protected void onStart() {
        super.onStart();
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig
                .TIME_MONITOR_ID_APPLICATION_START).end("StartActivity_start");
    }

    public void initEvent() {
        startService();
        MainThreadAction.getInstance().post(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, MainActivity.class));
                finish();
            }
        },3500);
    }

    public int getLayoutId() {
        return R.layout.activity_start;
    }

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


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
