package com.weather.byhieg.easyweather.data.source;

import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.data.source.local.entity.WeatherEntity;


/**
 * Created by byhieg on 17/5/21.
 * Contact with byhieg@gmail.com
 */

public interface WeatherDataSource {

    interface GetWeatherCallBack{

        void onSuccess(HWeather weather);

        void onFailure(String failureMessage);
    }

    interface GetWeatherEntityCallBack{

        void onSuccess(WeatherEntity entity);

        void onFailure(String failureMessage);
    }


    String getShowCity();

    void addWeather(WeatherEntity entity);

    boolean isExistInCityWeather(String cityName);

    void getWeatherDataFromCity(String cityName, GetWeatherCallBack callBack) throws Exception;

    void getWeatherEntity(String cityName,GetWeatherEntityCallBack callBack);


    WeatherEntity getWeatherEntity(String cityName);

    void updateCityWeather(String cityName) throws Exception;

    HWeather getLocalWeather(String cityName);

    void saveWeather(HWeather weather);

    HWeather getWeatherDataFromCity(String cityName) throws Exception;
}
