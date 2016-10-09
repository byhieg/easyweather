package com.weather.byhieg.easyweather.Activity;

import android.content.Intent;
import android.os.Handler;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Service.BackGroundService;

public class StartActivity extends BaseActivity {

    private static final String ACTION_ADD_CITY = "com.weather.byhieg.easyweather.Activity.action.addCity";
    private static final String ACTION_GET_WEATHER = "com.weather.byhieg.easyweather.Activity.action.getWeather";
    private static final String ACTION_START_NOTIFICATION = "com.weather.byhieg.easyweather.Activity.action.notification";
    private static final String ACTION_ADD_VIEWSPOT = "com.weather.byhieg.easyweather.Activity.action.viewspot";

    @Override
    public void initData() {
        startGetWeatherService();
        startAddCityService();
        startNotificationService();
    }

    @Override
    public void initEvent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(MainActivity.class);
                finish();
            }
        },3500);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initTheme() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_start;
    }

    private void startAddCityService(){
        Intent intent = new Intent(this, BackGroundService.class);
        intent.setAction(ACTION_ADD_CITY);
        startService(intent);
    }

    private void startGetWeatherService(){
        Intent intent = new Intent(this, BackGroundService.class);
        intent.setAction(ACTION_GET_WEATHER);
        startService(intent);
    }

    private void startNotificationService(){
        Intent intent = new Intent(this, BackGroundService.class);
        intent.setAction(ACTION_START_NOTIFICATION);
        startService(intent);
    }

    private void startGetViewSpotService(){
        Intent intent = new Intent(this, BackGroundService.class);
        intent.setAction(ACTION_ADD_VIEWSPOT);
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

    public static String getActionAddViewspot() {
        return ACTION_ADD_VIEWSPOT;
    }


}
