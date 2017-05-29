package com.weather.byhieg.easyweather.tools;


import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.data.bean.Weather;

/**
 * Created by byhieg on 16-8-9.
 */
public class WeatherJsonConverter {

    public static Weather getWeather(HWeather hWeather){
        return hWeather.getHeWeather5().get(0);
    }
}
