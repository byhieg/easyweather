package com.weather.byhieg.easyweather.data.source;

import com.weather.byhieg.easyweather.Bean.UrlCity;
import com.weather.byhieg.easyweather.data.source.local.entity.CityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.LoveCityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.ProvinceEntity;

import java.util.List;

/**
 * Created by byhieg on 17/5/22.
 * Contact with byhieg@gmail.com
 */

public interface CityDataSource {

    /******************************************************************************************
     * 对CityDao进行的操作
     *******************************************************************************************/
    void addCities(UrlCity city);

    void addCity(CityEntity city);

    boolean isExistInCity();

    void getCityFromProvince(String provinceName,GetCityCallBack callBack);


    void getAllCity(GetCityCallBack callBack);

    void getCities(String name,GetCityCallBack callBack);

    boolean isExistInCity(String name);


    /******************************************************************************************
     * 对ProvinceDao进行的操作
     *******************************************************************************************/


    void getAllProvince(GetProvinceCallBack callBack);

    void addProvince();

    void addProvince(ProvinceEntity province);

    boolean isExistInProvince();

    void getProvince(String name,GetProvinceCallBack callBack);


    /******************************************************************************************
     *  对LoveCityDao进行的操作
     *******************************************************************************************/


    void getLoveCity(String cityName,GetLoveCityCallBack callBack);

    void getLoveCity(int order,GetLoveCityCallBack callBack);

    void getLoveCity(GetLoveCityCallBack callBack);

    void addLoveCity(LoveCityEntity loveCity);

    boolean isExistInLoveCity(String cityName);

    void updateCityOrder(String cityName,int order);

    void deleteCity(String cityName);

    interface GetLoveCityCallBack{
        void onSuccess(List<LoveCityEntity> loveCities);
        void onFailure(String failureMessage);

    }

    interface GetProvinceCallBack{
        void onSuccess(List<ProvinceEntity> provinces);
        void onFailure(String failureMessage);
    }


    interface GetCityCallBack{
        void onSuccess(List<CityEntity> cities);

        void onFailure(String failureMessage);
    }




}
