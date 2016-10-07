package com.weather.byhieg.easyweather.Tools;

import com.example.byhieglibrary.Net.HttpUtils;
import com.example.byhieglibrary.Utils.StringUtils;
import com.google.gson.Gson;
import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Response;

/**
 * Created by byhieg on 16-8-11.
 */
public class NetTool {

    private final static int UPDATERATE = 60;
    /**
     * 从网络获取order为1的城市的天气，并把天气放入数据库中
     *
     * @param cityName 根据城市名字进行网络请求
     */
    public static synchronized void doNetWeather(String cityName) throws Exception {
        Gson gson = new Gson();
        int[] pos = {11, 15, 22, 23};
        Map<String, String> params = new HashMap<>();
        params.put("city", cityName);
        params.put("key", MyApplication.getHeweatherKey());
        String url = HttpUtils.url(MyApplication.getCityUrl(), null, params);

        if(!HandleDaoData.isExistInCityWeather(cityName)){
            Response netResponse = HttpUtils.getAsyn(url);
            String response = StringUtils.delPosOfString(netResponse.body().string(), pos);
            WeatherBean weatherBean = gson.fromJson(response, WeatherBean.class);
            HandleDaoData.insertCityWeather(PutDaoData.putWeatherData(weatherBean));
        }else{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date nowDate = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            if((nowDate.getTime() - (HandleDaoData.getCityWeather(cityName)).getUpdateTime().getTime()) > 1000 * 60 * UPDATERATE){
                Response netResponse = HttpUtils.getAsyn(url);
                String response = StringUtils.delPosOfString(netResponse.body().string(), pos);
                WeatherBean weatherBean = gson.fromJson(response, WeatherBean.class);
                HandleDaoData.updateCityWeather(PutDaoData.putWeatherData(weatherBean));
            }
        }
    }
}
