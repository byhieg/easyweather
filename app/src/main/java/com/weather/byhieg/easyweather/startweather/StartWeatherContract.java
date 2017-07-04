package com.weather.byhieg.easyweather.startweather;

import com.weather.byhieg.easyweather.BasePresenter;
import com.weather.byhieg.easyweather.BaseView;

/**
 * Created by byhieg on 17/5/22.
 * Contact with byhieg@gmail.com
 */

public interface StartWeatherContract {

    interface View extends BaseView<Presenter>{

        void startService();
    }


    interface Presenter extends BasePresenter{

    }
}
