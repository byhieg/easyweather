package com.weather.byhieg.easyweather.startweather;

/**
 * Created by byhieg on 17/5/22.
 * Contact with byhieg@gmail.com
 */

public class StartWeatherPresenter implements StartWeatherContract.Presenter {

    private StartWeatherContract.View mView;

    public StartWeatherPresenter(StartWeatherContract.View view){
        mView = view;
    }


    @Override
    public void start() {
        mView.startService();
    }
}
