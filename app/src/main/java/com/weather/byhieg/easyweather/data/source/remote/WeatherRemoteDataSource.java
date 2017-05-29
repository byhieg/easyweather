package com.weather.byhieg.easyweather.data.source.remote;

import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.data.source.WeatherDataSource;
import com.weather.byhieg.easyweather.data.source.local.entity.WeatherEntity;
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


    /**
     * 该方法由localDataSource实现
     * @return
     */
    @Override
    public String getShowCity() {
        return null;
    }

    /**
     * 该方法由localDataSource实现
     * @return
     */
    @Override
    public void addWeather(WeatherEntity entity) {

    }

    /**
     * 该方法由localDataSource实现
     * @return
     */
    @Override
    public boolean isExistInCityWeather(String cityName) {
        return false;
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

    /**
     * 该方法由localDataSource实现
     * @return
     */
    @Override
    public void getWeatherEntity(String cityName, GetWeatherEntityCallBack callBack) {

    }

    /**
     * 该方法由localDataSource实现
     * @return
     */
    @Override
    public WeatherEntity getWeatherEntity(String cityName) {
        return null;
    }

    /**
     * 该方法由repository实现
     * @param cityName
     * @throws Exception
     */
    @Override
    public void updateCityWeather(String cityName) throws Exception {

    }

    /**
     * 该方法由localDataSource实现
     * @return
     */
    @Override
    public HWeather getLocalWeather(String cityName) {
        return null;
    }


    /**
     * 该方法由localDataSource实现
     * @return
     */
    @Override
    public void saveWeather(HWeather weather) {

    }

    @Override
    public HWeather getWeatherDataFromCity(String cityName){
        checkNotNull(cityName);
        Map<String, String> maps = new LinkedHashMap<>();
        maps.put("key", MyApplication.getHeweatherKey());
        maps.put("city", cityName);
        mCall = mApiService.getWeather(maps);
        HWeather weather = NetService.getInstance().syncRequest(mCall);
        return weather;
    }


}
