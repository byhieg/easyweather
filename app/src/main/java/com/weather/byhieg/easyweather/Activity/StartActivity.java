package com.weather.byhieg.easyweather.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Service.BackGroundService;

public class StartActivity extends Activity {

    private static final String ACTION_ADD_CITY = "com.weather.byhieg.easyweather.Activity.action.addCity";
    private static final String ACTION_GET_WEATHER = "com.weather.byhieg.easyweather.Activity.action.getWeather";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        startGetWeatherService();
        startAddCityService();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        },3500);

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

    public static String getActionAddCity() {
        return ACTION_ADD_CITY;
    }

    public static String getActionGetWeather() {
        return ACTION_GET_WEATHER;
    }
}
