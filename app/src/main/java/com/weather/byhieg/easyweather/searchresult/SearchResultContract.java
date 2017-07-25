package com.weather.byhieg.easyweather.searchresult;

import com.weather.byhieg.easyweather.BasePresenter;
import com.weather.byhieg.easyweather.BaseView;
import com.weather.byhieg.easyweather.data.bean.CityContext;
import com.weather.byhieg.easyweather.data.source.local.entity.CityEntity;

import java.util.List;

/**
 * Created by byhieg on 17/5/30.
 * Contact with byhieg@gmail.com
 */

public interface SearchResultContract {

    interface Presenter extends BasePresenter {

        void loadCities(String name);

        void insertLoveCity(String cityName);

        void cancelCity(String cityName);

        void getCityWeather(String cityName);

        boolean isExist(String cityName);
    }

    interface View extends BaseView<Presenter> {
        void showNoData();

        void showQueryData(List<CityContext> data);

        void cancelRefresh();

        void setNetWork();

    }
}
