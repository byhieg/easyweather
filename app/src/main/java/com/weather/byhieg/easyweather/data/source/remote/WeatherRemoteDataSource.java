package com.weather.byhieg.easyweather.data.source.remote;

import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.data.HWeather;
import com.weather.byhieg.easyweather.data.source.WeatherDataSource;
import com.weather.byhieg.easyweather.data.source.remote.conf.ApiService;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.byhieg.betterload.network.IResponseListener;
import cn.byhieg.betterload.network.NetService;

import retrofit2.Call;

import static com.weather.byhieg.easyweather.tools.Knife.checkNotNull;


/**
 * Created by byhieg on 17/5/21.
 * Contact with byhieg@gmail.com
 */

public class WeatherRemoteDataSource implements WeatherDataSource {
    ApiService mApiService;
    Call<HWeather> mCall;

    {
        mApiService = NetService.getInstance().create(ApiService.class);
    }


    @Override
    public void getWeatherData() {

    }

    @Override
    public void getWeatherDataFromCity(String cityName, final GetWeatherCallBack callBack) throws Exception {
        checkNotNull(cityName);
        Map<String, String> maps = new LinkedHashMap<>();
        maps.put("key", MyApplication.getHeweatherKey());
        maps.put("city", cityName);
        mCall = mApiService.getWeather(maps);
        NetService.getInstance().asynRequest(mCall, new IResponseListener<HWeather>() {
            @Override
            public void onSuccess(HWeather response) {
                callBack.onSuccess(response);
            }

            @Override
            public void onFailure(String message) {
                callBack.onFailure(message);
            }
        });
    }

    @Override
    public void updateCityWeather(String cityName, GetWeatherCallBack callBack) throws Exception {
        checkNotNull(cityName);
        getWeatherDataFromCity(cityName,callBack);
    }

    @Override
    public void refreshWeather() {

    }

    @Override
    public void getAllProvince(GetProvinceCallBack callBack) {

    }


    @Override
    public void getAllCity() {

    }

    @Override
    public void addCity() {

    }

    @Override
    public void addProvince() {

    }


}
