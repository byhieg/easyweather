package com.weather.byhieg.easyweather.data.source;

import com.weather.byhieg.easyweather.data.HWeather;
import com.weather.byhieg.easyweather.data.source.local.entity.ProvinceEntity;

import java.util.List;



/**
 * Created by byhieg on 17/5/21.
 * Contact with byhieg@gmail.com
 */

public interface WeatherDataSource {

    interface GetWeatherCallBack{

        void onSuccess(HWeather weather);

        void onFailure(String failureMessage);
    }


    void getWeatherData();

    void getWeatherDataFromCity(String cityName, GetWeatherCallBack callBack) throws Exception;

    void updateCityWeather(String cityName, GetWeatherCallBack callBack) throws Exception;

    void refreshWeather();

    HWeather getWeatherDataFromCity(String cityName) throws Exception;
}
