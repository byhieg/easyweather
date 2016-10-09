package com.weather.byhieg.easyweather.Tools;

import com.weather.byhieg.easyweather.Bean.Weather;
import com.weather.byhieg.easyweather.Bean.WeatherBean;

/**
 * Created by byhieg on 16-8-9.
 */
public class MyJson {

    public static Weather getWeather(WeatherBean weatherBean){
        if (weatherBean.getHeWeatherdataservice30().get(0) == null) {
            return null;
        }
        return weatherBean.getHeWeatherdataservice30().get(0);
    }
}
