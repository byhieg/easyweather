package com.weather.byhieg.easyweather.startweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.weather.byhieg.easyweather.MyApplication;

import java.sql.Time;

import cn.byhieg.monitor.TimeMonitor;
import cn.byhieg.monitor.TimeMonitorConfig;
import cn.byhieg.monitor.TimeMonitorManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig
                .TIME_MONITOR_ID_APPLICATION_START).recordingTimeTag("SplashActivity_create");
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, StartActivity.class));
        TimeMonitorManager.getInstance().getTimeMonitor(TimeMonitorConfig
                .TIME_MONITOR_ID_APPLICATION_START).recordingTimeTag("SplashActivity_create_over");
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
