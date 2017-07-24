package com.weather.byhieg.easyweather.tools;

import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.R;

import java.util.Calendar;

/**
 * Created by byhieg on 16-10-7.
 * Mail byhieg@gmail.com
 */

public class WeatherIcon {

    public static int getWeatherImage(String weatherCode) {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int code = Integer.parseInt(weatherCode);
        if (hour > 18 || hour < 6) {
            if (code == 100) {
                return R.mipmap.sunny_night;
            }else if(code == 101){
                return R.mipmap.cloudy4_night;
            } else if (code == 102) {
                return R.mipmap.cloudy1_night;
            }else if (code == 103) {
                return R.mipmap.cloudy3_night;
            }else if(code == 104){
                return R.mipmap.sunny_night;
            } else if (code >= 200 && code < 300) {
                return R.mipmap.fog_night;
            }else if (code == 300){
                return R.mipmap.light_rain;
            }else if (code > 300 && code < 400) {
                if(code == 301 || code == 303 || code == 306 || code == 307 || code >= 310){
                    return R.mipmap.shower3;
                }else{
                    return R.mipmap.shower1_night;
                }
            } else if (code == 400 || code == 407) {
                return R.mipmap.snow1_night;
            }else if(code == 401){
                return R.mipmap.snow2_night;
            }else if (code == 402) {
                return R.mipmap.snow3_night;
            }else if (code == 403) {
                return R.mipmap.snow4;
            } else if (code == 404 || code == 405 || code == 406) {
                return R.mipmap.sleet;
            } else if (code >= 500 && code <= 600) {
                return R.mipmap.mist_night;
            }
        }else{
            if (code == 100) {
                return R.mipmap.sunny;
            }else if(code == 101){
                return R.mipmap.cloudy4;
            } else if (code == 102) {
                return R.mipmap.cloudy1;
            }else if (code == 103) {
                return R.mipmap.cloudy3;
            }else if(code == 104){
                return R.mipmap.sunny;
            } else if (code >= 200 && code < 300) {
                return R.mipmap.fog;
            }else if (code == 300){
                return R.mipmap.light_rain;
            }else if (code > 300 && code < 400) {
                if(code == 301 || code == 303 || code == 306 || code == 307 || code >= 310){
                    return R.mipmap.shower3;
                }else{
                    return R.mipmap.shower1;
                }
            } else if (code == 400 || code == 407) {
                return R.mipmap.snow1;
            }else if(code == 401){
                return R.mipmap.snow2;
            }else if (code == 402) {
                return R.mipmap.snow3;
            }else if (code == 403) {
                return R.mipmap.snow4;
            } else if (code == 404 || code == 405 || code == 406) {
                return R.mipmap.sleet;
            } else if (code >= 500 && code <= 600) {
                return R.mipmap.mist;
            }
        }
        return R.mipmap.sunny;
    }
}
