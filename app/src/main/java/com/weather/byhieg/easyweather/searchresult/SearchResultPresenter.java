package com.weather.byhieg.easyweather.searchresult;

import com.weather.byhieg.easyweather.data.bean.CityContext;
import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.data.bean.Weather;
import com.weather.byhieg.easyweather.data.source.CityRepository;
import com.weather.byhieg.easyweather.data.source.WeatherDataSource;
import com.weather.byhieg.easyweather.data.source.WeatherRepository;
import com.weather.byhieg.easyweather.data.source.local.entity.CityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.LoveCityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.ProvinceEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by byhieg on 17/5/30.
 * Contact with byhieg@gmail.com
 */

public class SearchResultPresenter implements SearchResultContract.Presenter {


    private CityRepository mRepository;
    private WeatherRepository mWeatherRepository;
    private SearchResultContract.View mView;
    private List<CityContext> cityList;

    public SearchResultPresenter(SearchResultContract.View view) {
        mView = view;
        mRepository = CityRepository.getInstance();
        mWeatherRepository = WeatherRepository.getInstance();
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void loadCities(String name) {
        cityList = new ArrayList<>();
        List<CityEntity> cities = mRepository.getCities(name);
        List<ProvinceEntity> provinces = mRepository.getProvinces(name);
        for (int i = 0; i < cities.size(); i++) {
            CityContext context = new CityContext();
            context.setCityName(cities.get(i).getCityName());
            cityList.add(context);
        }
        for (int i = 0; i < provinces.size(); i++) {
            CityContext context = new CityContext();
            context.setCityName(provinces.get(i).getProvinceName());
            if (!isInCityList(context.getCityName())) {
                cityList.add(context);
            }
        }
        mView.showQueryData(cityList);
    }

    @Override
    public void insertLoveCity(String cityName) {
        int order = mRepository.getAllLoveCities().size();
        LoveCityEntity loveCity = new LoveCityEntity();
        loveCity.setCityName(cityName);
        loveCity.setOrder(++order);
        mRepository.addLoveCity(loveCity);
    }

    @Override
    public void cancelCity(String cityName) {
        mRepository.deleteCity(cityName);
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
        return mRepository.isExistInLoveCity(cityName);
    }


    private boolean isInCityList(String name) {
        List<String> str = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            str.add(cityList.get(i).getCityName());
        }
        return str.contains(name);
    }
}
