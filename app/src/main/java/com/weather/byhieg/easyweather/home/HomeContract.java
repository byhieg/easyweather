package com.weather.byhieg.easyweather.home;

import android.content.Context;
import android.view.View;

import com.weather.byhieg.easyweather.BasePresenter;
import com.weather.byhieg.easyweather.BaseView;
import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.data.bean.WeekWeather;

import java.util.List;

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

        void generateTextView(android.view.View v);

        void updateWeeksView(List<WeekWeather> weathers, String[] weeks,List<Integer> lists);
    }


    interface Presenter extends BasePresenter{

        void doBaiduLocation();

        void loadWeather();

        String getShowCity();

        void doRefreshInNoData();

        void refreshData();

        void generateDataInPopView();

        void getNewShowWeather();

        void showDialog(String name,Context context);

        void updateDataInWeeks();

    }
}

