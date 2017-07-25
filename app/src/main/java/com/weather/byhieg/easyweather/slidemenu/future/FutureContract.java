package com.weather.byhieg.easyweather.slidemenu.future;

import com.weather.byhieg.easyweather.BasePresenter;
import com.weather.byhieg.easyweather.BaseView;
import com.weather.byhieg.easyweather.data.bean.FutureContext;

import java.util.List;

/**
 * Created by byhieg on 17/5/30.
 * Contact with byhieg@gmail.com
 */

public interface FutureContract {

    interface Presenter extends BasePresenter {

        void loadData();
    }

    interface View extends BaseView<Presenter> {

        void showListView(List<FutureContext> list);
    }

}
