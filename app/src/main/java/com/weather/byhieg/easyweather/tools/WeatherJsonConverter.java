package com.weather.byhieg.easyweather.tools;


import com.weather.byhieg.easyweather.data.HWeather;
import com.weather.byhieg.easyweather.data.Weather;

/**
 * Created by byhieg on 16-8-9.
 */
public class WeatherJsonConverter {

    public static Weather getWeather(HWeather hWeather){
        if (hWeather.getHeWeather5().get(0) == null) {
            return null;
        }
        return hWeather.getHeWeather5().get(0);
    }
}
