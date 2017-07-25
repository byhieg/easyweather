package com.weather.byhieg.easyweather.data.source;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.data.source.local.WeatherLocalDataSource;
import com.weather.byhieg.easyweather.data.source.local.entity.WeatherEntity;
import com.weather.byhieg.easyweather.data.source.remote.WeatherRemoteDataSource;
import com.weather.byhieg.easyweather.tools.LogUtils;

import static com.weather.byhieg.easyweather.tools.Knife.*;

import java.util.Date;


/**
 * Created by byhieg on 17/5/21.
 * Contact with byhieg@gmail.com
 */

public class WeatherRepository implements WeatherDataSource {

    private static final String TAG = "WeatherRepository";

    private static WeatherRepository sInstance = null;

    private final WeatherRemoteDataSource mWeatherRemoteDataSource;
    private final WeatherLocalDataSource mWeatherLocalDataSource;

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
    public String getShowCity() {
        return mWeatherLocalDataSource.getShowCity();
    }

    @Override
    public void addWeather(WeatherEntity entity) {
        mWeatherLocalDataSource.addWeather(entity);
    }

    @Override
    public boolean isExistInCityWeather(String cityName) {
        return mWeatherLocalDataSource.isExistInCityWeather(cityName);
    }

    @Override
    public void getWeatherDataFromCity(String cityName, GetWeatherCallBack callBack) throws Exception {
        mWeatherRemoteDataSource.getWeatherDataFromCity(cityName, callBack);
    }

    @Override
    public void getWeatherEntity(String cityName, GetWeatherEntityCallBack callBack) {
        mWeatherLocalDataSource.getWeatherEntity(cityName, callBack);
    }

    @Override
    public WeatherEntity getWeatherEntity(String cityName) {
        return mWeatherLocalDataSource.getWeatherEntity(cityName);
    }

    @Override
    public void updateCityWeather(final String cityName) throws Exception {
        if (isExistInCityWeather(cityName)) {
            WeatherEntity entity = getWeatherEntity(cityName);
            Date oldTime = entity.getUpdateTime();
            Date nowDate = convertDate(new Date());
            if (isNeedUpdate(oldTime, nowDate)) {
                isCachedDirty = true;
                HWeather weather = getWeatherDataFromCity(cityName);
                saveWeather(weather);
                LogUtils.e(TAG,"save了");
            }
        } else {
            HWeather weather = getWeatherDataFromCity(cityName);
            saveWeather(weather);
        }

    }


    @Override
    public HWeather getLocalWeather(String cityName) {
        return mWeatherLocalDataSource.getLocalWeather(cityName);
    }


    @Override
    public void saveWeather(HWeather weather) {
        mWeatherLocalDataSource.saveWeather(weather);
    }

    @Override
    public HWeather getWeatherDataFromCity(String cityName) throws Exception {
        return mWeatherRemoteDataSource.getWeatherDataFromCity(cityName);
    }

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
