package com.weather.byhieg.easyweather.Activity;

import android.content.Intent;
import android.os.Handler;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Service.BackGroundService;

public class StartActivity extends BaseActivity {

    private static final String ACTION_ADD_CITY = "com.weather.byhieg.easyweather.Activity.action.addCity";
    private static final String ACTION_GET_WEATHER = "com.weather.byhieg.easyweather.Activity.action.getWeather";


    @Override
    public void initData() {
        startGetWeatherService();
        startAddCityService();
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

    public static String getActionAddCity() {

        return ACTION_ADD_CITY;
    }

    public static String getActionGetWeather() {
        return ACTION_GET_WEATHER;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MyApplication.nightMode2()){
            initNightView(R.layout.night_mode_overlay);
        }else {
            removeNightView();
        }
    }
}
