package com.weather.byhieg.easyweather.citymanage;

import com.weather.byhieg.easyweather.BasePresenter;
import com.weather.byhieg.easyweather.BaseView;
import com.weather.byhieg.easyweather.data.source.local.entity.LoveCityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.WeatherEntity;
import com.weather.byhieg.easyweather.home.HomeContract;

import java.util.List;

/**
 * Created by byhieg on 17/5/29.
 * Contact with byhieg@gmail.com
 */

public interface CityManageContract {

    interface View extends BaseView<Presenter>{

        void showNoData();

        void showCities(List<WeatherEntity> cityEntities);
    }

    interface Presenter extends BasePresenter{

        void loadCities();

        void deleteCity(String cityName);

        String getShowCity();

        void updateShowCity(String cityName);
    }
}
