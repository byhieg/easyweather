package com.weather.byhieg.easyweather.citymanage;

import com.weather.byhieg.easyweather.data.source.CityRepository;
import com.weather.byhieg.easyweather.data.source.WeatherRepository;
import com.weather.byhieg.easyweather.data.source.local.entity.LoveCityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.WeatherEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by byhieg on 17/5/29.
 * Contact with byhieg@gmail.com
 */

public class CityManagePresenter implements CityManageContract.Presenter {


    private CityManageContract.View mView;
    private CityRepository mCityRepository;
    private WeatherRepository mWeatherRepository;

    public CityManagePresenter(CityManageContract.View view){
        this.mView = view;
        mCityRepository = CityRepository.getInstance();
        mWeatherRepository = WeatherRepository.getInstance();
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        loadCities();
    }

    @Override
    public void loadCities() {
        List<LoveCityEntity> cities = mCityRepository.getAllLoveCities();
        List<WeatherEntity> weathers = new ArrayList<>();
        Collections.sort(cities, new Comparator<LoveCityEntity>() {
            @Override
            public int compare(LoveCityEntity o1, LoveCityEntity o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });
        for (LoveCityEntity entity : cities) {
            weathers.add(mWeatherRepository.getWeatherEntity(entity.getCityName()));
        }
        mView.showCities(weathers);
    }

    @Override
    public void deleteCity(String cityName) {
        mCityRepository.deleteCity(cityName);
        loadCities();
    }

    @Override
    public String getShowCity() {
        return mWeatherRepository.getShowCity();
    }

    @Override
    public void updateShowCity(String cityName) {
        mCityRepository.updateCityOrder(cityName,1);
        loadCities();
    }
}
