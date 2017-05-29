package com.weather.byhieg.easyweather.home;

import com.weather.byhieg.easyweather.BasePresenter;
import com.weather.byhieg.easyweather.BaseView;
import com.weather.byhieg.easyweather.data.bean.HWeather;

/**
 * Created by byhieg on 17/5/27.
 * Contact with byhieg@gmail.com
 */

public interface HomeContract {

    interface View extends BaseView<Presenter>{

        void updateView(HWeather weather);

        void showNoData();

        void showDetail();

        void showPopupWindow();

        void setNetWork();

        void registerBroadCast();
    }


    interface Presenter extends BasePresenter{

        void doBaiduLocation();

        void loadWeather();

        String getShowCity();

        void doRefreshInNoData();

        void refreshData();

        void generateDataInPopView();

    }
}

