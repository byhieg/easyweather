package com.weather.byhieg.easyweather.data.source;

import android.util.Log;

import com.weather.byhieg.easyweather.data.HWeather;
import com.weather.byhieg.easyweather.data.source.local.WeatherLocalDataSource;
import com.weather.byhieg.easyweather.data.source.remote.WeatherRemoteDataSource;


/**
 * Created by byhieg on 17/5/21.
 * Contact with byhieg@gmail.com
 */

public class WeatherRepository implements WeatherDataSource {

    private static final String TAG = "WeatherRepository";

    private static WeatherRepository sInstance = null;

    private final WeatherRemoteDataSource mWeatherRemoteDataSource;
    private final WeatherLocalDataSource mWeatherLocalDataSource;
    private static final Object lock = new Object();
    private MyCallBack mCallBack = new MyCallBack();

    private boolean isCachedDirty = false;

    private WeatherRepository() {
        mWeatherLocalDataSource = new WeatherLocalDataSource();
        mWeatherRemoteDataSource = new WeatherRemoteDataSource();
    }

    public static WeatherRepository getInstance() {
        if (sInstance == null) {
            sInstance = new WeatherRepository();
        }
        return sInstance;
    }

    public static void destoryInstance() {
        sInstance = null;
    }


    @Override
    public void getWeatherData() {
        mWeatherLocalDataSource.getWeatherData();
    }

    @Override
    public void getWeatherDataFromCity(String cityName, GetWeatherCallBack callBack) throws Exception {
//        if (isCachedDirty) {
//            mWeatherRemoteDataSource.getWeatherDataFromCity(cityName, mCallBack);
//        }
//        mWeatherLocalDataSource.getWeatherDataFromCity(cityName, callBack);
        mWeatherRemoteDataSource.getWeatherDataFromCity(cityName,callBack);
    }

    @Override
    public void updateCityWeather(String cityName, GetWeatherCallBack callBack) throws Exception {
        mWeatherRemoteDataSource.updateCityWeather(cityName, mCallBack);

    }

    @Override
    public void refreshWeather() {
        isCachedDirty = true;
    }

//    @Override
//    public void getAllProvince(GetProvinceCallBack callBack) {
//        mWeatherLocalDataSource.getAllProvince(callBack);
//    }
//
//    @Override
//    public void getAllCity() {
//
//    }
//
//    @Override
//    public void addCity() {
//
//    }
//
//    @Override
//    public void addProvinces() {
//        mWeatherLocalDataSource.addProvinces();
//    }


    class MyCallBack implements GetWeatherCallBack {

        @Override
        public void onSuccess(HWeather weather) {
            mWeatherLocalDataSource.saveWeather(weather);
        }

        @Override
        public void onFailure(String failureMessage) {
            Log.e(TAG, failureMessage);
        }
    }

}
