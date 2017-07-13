package com.weather.byhieg.easyweather.city;

import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.data.source.CityDataSource;
import com.weather.byhieg.easyweather.data.source.CityRepository;
import com.weather.byhieg.easyweather.data.source.WeatherDataSource;
import com.weather.byhieg.easyweather.data.source.WeatherRepository;
import com.weather.byhieg.easyweather.data.source.local.entity.CityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.LoveCityEntity;

import java.util.List;

/**
 * Created by byhieg on 17/5/30.
 * Contact with byhieg@gmail.com
 */

public class CityPresenter implements CityContract.CityPresenter {


    private CityContract.CityView mView;
    private CityRepository mCityRepository;
    private WeatherRepository mWeatherRepository;

    public CityPresenter(CityContract.CityView view){
        mView = view;
        mView.setPresenter(this);
        mCityRepository = CityRepository.getInstance();
        mWeatherRepository = WeatherRepository.getInstance();
    }

    @Override
    public void start() {

    }

    @Override
    public void loadCityData(String provinceName) {
        mCityRepository.getCityFromProvince(provinceName, new CityDataSource.GetCityCallBack() {
            @Override
            public void onSuccess(List<CityEntity> cities) {
                mView.showCityData(cities);
            }

            @Override
            public void onFailure(String failureMessage) {
                mView.showNoData();
            }
        });
    }

    @Override
    public void insertLoveCity(String cityName) {
        int order = mCityRepository.getAllLoveCities().size();
        LoveCityEntity loveCity = new LoveCityEntity();
        loveCity.setCityName(cityName);
        loveCity.setOrder(++order);
        mCityRepository.addLoveCity(loveCity);
    }

    @Override
    public void cancelCity(String cityName) {
        mCityRepository.deleteCity(cityName);
    }

    @Override
    public void getCityWeather(String cityName) {
        try {
            mWeatherRepository.getWeatherDataFromCity(cityName, new WeatherDataSource.GetWeatherCallBack() {
                @Override
                public void onSuccess(HWeather weather) {
                    mWeatherRepository.saveWeather(weather);
                    mView.cancelRefresh();
                }

                @Override
                public void onFailure(String failureMessage) {

                }
            });
        } catch (Exception e) {
            mView.setNetWork();
            mView.cancelRefresh();

        }
    }

    @Override
    public boolean isExist(String cityName) {
        return mCityRepository.isExistInLoveCity(cityName);
    }
}
