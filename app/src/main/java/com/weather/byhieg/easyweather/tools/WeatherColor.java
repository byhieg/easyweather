package com.weather.byhieg.easyweather.tools;

import com.weather.byhieg.easyweather.R;

/**
 * Created by shiqifeng on 2016/9/13.
 * Mail:byhieg@gmail.com
 */
public class WeatherColor {

    public static int getWeatherColor(String weatherCode){
        if(weatherCode == null) return R.color.white;
        int code = Integer.parseInt(weatherCode);
        if (code == 100) {
            return R.color.sunny;
        } else if (code > 100 && code < 200) {
            return R.color.lightskyblue;
        } else if (code >= 200 && code < 300) {
            return R.color.maincolor;
        } else if (code >= 300 && code < 500) {
            return R.color.royalblue;
        } else if (code >= 500 && code < 600) {
            return R.color.lightgrey;
        }else {
            return R.color.violet;
        }
    }
}
