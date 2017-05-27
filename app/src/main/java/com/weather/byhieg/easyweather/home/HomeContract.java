package com.weather.byhieg.easyweather.home;

import com.weather.byhieg.easyweather.BasePresenter;
import com.weather.byhieg.easyweather.BaseView;
import com.weather.byhieg.easyweather.data.HWeather;

/**
 * Created by byhieg on 17/5/27.
 * Contact with byhieg@gmail.com
 */

public interface HomeContract {

    interface View extends BaseView<Presenter>{

        void updateView(HWeather weather);

        void showNoData();

        void showErrdata();

        void showLoading();

        void showDetail(HWeather weather);

        void showPopupWindow(HWeather weather);

        void setNetWork();

        void registerBroadCast();
    }


    interface Presenter extends BasePresenter{

        void doBaiduLocation();

        void loadWeather();

        String getShowCity();

        void doRefreshInNoData();

        void refreshData();

    }
}

