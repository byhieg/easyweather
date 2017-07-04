package com.weather.byhieg.easyweather.city;

import com.weather.byhieg.easyweather.data.source.CityRepository;
import com.weather.byhieg.easyweather.data.source.local.entity.CityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.ProvinceEntity;

import java.util.List;

/**
 * Created by byhieg on 17/5/30.
 * Contact with byhieg@gmail.com
 */

public class ProvincePresenter implements CityContract.ProvincePresenter{


    private CityRepository mRepository;
    private CityContract.ProvinceView mView;


    public ProvincePresenter(CityContract.ProvinceView view) {
        mView = view;
        mRepository = CityRepository.getInstance();
        mView.setPresenter(this);
    }


    public void start() {
        loadData();
    }

    @Override
    public void loadData() {

        List<ProvinceEntity> provinces = mRepository.getAllProvince();
        mView.showProvinceData(provinces);

    }

}
