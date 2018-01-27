package com.weather.byhieg.easyweather.data.source;

import com.weather.byhieg.easyweather.data.bean.UrlCity;
import com.weather.byhieg.easyweather.data.source.local.WeatherLocalDataSource;
import com.weather.byhieg.easyweather.data.source.local.entity.CityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.LoveCityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.ProvinceEntity;

import java.util.List;

/**
 * Created by byhieg on 17/5/23.
 * Contact with byhieg@gmail.com
 */

public class CityRepository implements CityDataSource{

    private static final String TAG = "CityRepository";

    private static CityRepository sInstance = null;

    private final WeatherLocalDataSource mWeatherLocalDataSource;


    private static final Object LOCK = new Object();


    private CityRepository(){
        mWeatherLocalDataSource = new WeatherLocalDataSource();
    }

    public static CityRepository getInstance(){
        if (sInstance == null) {
            sInstance = new CityRepository();
        }
        return sInstance;
    }

    @Override
    public void addCities(UrlCity city) {
        mWeatherLocalDataSource.addCities(city);
    }

    @Override
    public void addCity(CityEntity city) {
        mWeatherLocalDataSource.addCity(city);
    }

    @Override
    public boolean isExistInCity() {
        return mWeatherLocalDataSource.isExistInCity();
    }

    @Override
    public void getCityFromProvince(String provinceName, GetCityCallBack callBack) {
        mWeatherLocalDataSource.getCityFromProvince(provinceName,callBack);
    }

    @Override
    public void getAllCity(GetCityCallBack callBack) {
        mWeatherLocalDataSource.getAllCity(callBack);
    }

    @Override
    public void getCities(String name, GetCityCallBack callBack) {
        mWeatherLocalDataSource.getCities(name,callBack);
    }

    @Override
    public boolean isExistInCity(String name) {
        return mWeatherLocalDataSource.isExistInCity(name);
    }

    @Override
    public List<CityEntity> getAllCities() {
        return mWeatherLocalDataSource.getAllCities();
    }

    @Override
    public List<CityEntity> getCities(String name) {
        return mWeatherLocalDataSource.getCities(name);
    }

    @Override
    public List<ProvinceEntity> getProvinces(String name) {
        return mWeatherLocalDataSource.getProvinces(name);
    }

    @Override
    public void getAllProvince(GetProvinceCallBack callBack) {
        mWeatherLocalDataSource.getAllProvince(callBack);
    }

    @Override
    public void addProvince() {
        mWeatherLocalDataSource.addProvince();
    }

    @Override
    public void addProvince(ProvinceEntity province) {
        mWeatherLocalDataSource.addProvince(province);
    }

    @Override
    public boolean isExistInProvince() {
        return mWeatherLocalDataSource.isExistInProvince();
    }

    @Override
    public void getProvince(String name, GetProvinceCallBack callBack) {
         mWeatherLocalDataSource.getProvince(name,callBack);
    }

    @Override
    public List<ProvinceEntity> getAllProvince() {
        return mWeatherLocalDataSource.getAllProvince();
    }

    @Override
    public List<LoveCityEntity> getAllLoveCities() {
        return mWeatherLocalDataSource.getAllLoveCities();
    }

    @Override
    public void getLoveCity(String cityName, GetLoveCityCallBack callBack) {
        mWeatherLocalDataSource.getLoveCity(cityName,callBack);
    }

    @Override
    public void getLoveCity(int order, GetLoveCityCallBack callBack) {
        mWeatherLocalDataSource.getLoveCity(order,callBack);
    }

    @Override
    public LoveCityEntity getLoveCity(int order) {
        return mWeatherLocalDataSource.getLoveCity(order);
    }

    @Override
    public void getLoveCity(GetLoveCityCallBack callBack) {
        mWeatherLocalDataSource.getLoveCity(callBack);
    }

    @Override
    public void addLoveCity(LoveCityEntity loveCity) {
        mWeatherLocalDataSource.addLoveCity(loveCity);
    }

    @Override
    public boolean isExistInLoveCity(String cityName) {
        return mWeatherLocalDataSource.isExistInLoveCity(cityName);
    }

    @Override
    public void updateCityOrder(String cityName, int order) {
        mWeatherLocalDataSource.updateCityOrder(cityName,order);
    }

    @Override
    public void updateLocaitonCityOrder(String cityName, int order) {
        mWeatherLocalDataSource.updateLocaitonCityOrder(cityName,order);
    }

    @Override
    public void deleteCity(String cityName) {
        mWeatherLocalDataSource.deleteCity(cityName);
    }
}
