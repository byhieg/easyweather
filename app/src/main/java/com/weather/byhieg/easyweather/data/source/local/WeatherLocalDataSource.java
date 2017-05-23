package com.weather.byhieg.easyweather.data.source.local;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.Bean.UrlCity;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.data.HWeather;
import com.weather.byhieg.easyweather.data.source.CityDataSource;
import com.weather.byhieg.easyweather.data.source.WeatherDataSource;
import com.weather.byhieg.easyweather.data.source.local.dao.CityEntityDao;
import com.weather.byhieg.easyweather.data.source.local.dao.LoveCityEntityDao;
import com.weather.byhieg.easyweather.data.source.local.dao.ProvinceEntityDao;
import com.weather.byhieg.easyweather.data.source.local.entity.CityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.LoveCityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.ProvinceEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.weather.byhieg.easyweather.tools.Knife.isListEmpty;


/**
 * Created by byhieg on 17/5/21.
 * Contact with byhieg@gmail.com
 */

public class WeatherLocalDataSource implements WeatherDataSource ,CityDataSource{

    private final static String TAG = "WeatherLocalDataSource";

    private ExecutorService mService = Executors.newFixedThreadPool(4);
    private ProvinceEntityDao mProvinceDao;
    private CityEntityDao mCityDao;
    private LoveCityEntityDao mLoveCityDao;

    public WeatherLocalDataSource() {
        mProvinceDao = MyApplication.getDaoSession().getProvinceEntityDao();
        mCityDao = MyApplication.getDaoSession().getCityEntityDao();
        mLoveCityDao = MyApplication.getDaoSession().getLoveCityEntityDao();

    }

    @Override
    public void getWeatherData() {

    }

    @Override
    public void getWeatherDataFromCity(String cityName, GetWeatherCallBack callBack) throws Exception {

    }

    @Override
    public void updateCityWeather(String cityName, GetWeatherCallBack callBack) throws Exception {

    }

    @Override
    public void refreshWeather() {

    }

    @Override
    public HWeather getWeatherDataFromCity(String cityName) throws Exception {
        return null;
    }

    public void saveWeather(HWeather weather) {

    }


    @Override
    public void addCities(UrlCity city) {
        for (int i = 0; i < city.getCity_info().size(); i++) {
            CityEntity entity = new CityEntity();
            entity.setProvinceName(city.getCity_info().get(i).getProv());
            entity.setCityName(city.getCity_info().get(i).getCity());
            addCity(entity);
        }
    }

    @Override
    public void addCity(CityEntity city) {
        mCityDao.insert(city);
    }

    @Override
    public boolean isExistInCity() {
        CityEntity tempCity = mCityDao.loadByRowId(1);
        return tempCity != null;
    }

    @Override
    public void getCityFromProvince(String provinceName, GetCityCallBack callBack) {
        List<CityEntity> res = mCityDao.queryBuilder().
                where(CityEntityDao.Properties.ProvinceName.like(provinceName)).
                list();
        if (isListEmpty(res)) {
            callBack.onFailure("该省份下没有城市");
        }else{
            callBack.onSuccess(res);
        }
    }

    @Override
    public void getAllCity(GetCityCallBack callBack) {
        List<CityEntity> res = mCityDao.loadAll();
        if (isListEmpty(res)) {
            callBack.onFailure("该省份下没有城市");
        }else{
            callBack.onSuccess(res);
        }
    }

    @Override
    public void getCities(String name, GetCityCallBack callBack) {
        List<CityEntity> res = mCityDao.queryBuilder().
                where(CityEntityDao.Properties.CityName.like("%" + name + "%")).
                list();

        if (isListEmpty(res)) {
            callBack.onFailure("该省份下没有城市");
        }else{
            callBack.onSuccess(res);
        }
    }

    @Override
    public boolean isExistInCity(String name) {
        List<CityEntity> res = mCityDao.queryBuilder().
                where(CityEntityDao.Properties.CityName.eq(name)).
                list();
        if (isListEmpty(res)) {
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void addProvince() {
        final String[] provinces = {"北京", "天津", "河北", "山西", "山东", "辽宁", "吉林", "黑龙江",
                "上海", "江苏", "浙江", "安徽", "福建", "江西", "河南", "湖北", "湖南", "广东", "广西",
                "海南", "重庆", "四川", "贵州", "云南", "陕西", "甘肃", "青海", "内蒙古", "西藏", "宁夏",
                "新疆", "香港", "澳门", "台湾"};

        if (!isExistInProvince()) {
            for (int i = 0; i < provinces.length; i++) {
                ProvinceEntity entity = new ProvinceEntity(null,provinces[i]);
                addProvince(entity);
            }
        }

    }

    @Override
    public void addProvince(ProvinceEntity province) {
        mProvinceDao.insert(province);
    }

    @Override
    public boolean isExistInProvince() {
        ProvinceEntity tempProvince = mProvinceDao.loadByRowId(1);
        return tempProvince != null;
    }

    @Override
    public void getAllProvince(final GetProvinceCallBack callBack) {
        Log.e(TAG, "getAllProvince已经执行");
        List<ProvinceEntity> res = mProvinceDao.loadAll();
        if (isListEmpty(res)) {
            callBack.onFailure("数据为空");
        } else {
            callBack.onSuccess(res);

        }
    }

    @Override
    public void getProvince(String name, GetProvinceCallBack callBack) {
        List<ProvinceEntity> res = mProvinceDao.queryBuilder().
                where(ProvinceEntityDao.Properties.ProvinceName.like("%" + name + "%")).
                list();

        if (isListEmpty(res)) {
            callBack.onFailure("没有含有该名字省份");
        }else{
            callBack.onSuccess(res);
        }
    }


    @Override
    public void getLoveCity(String cityName, GetLoveCityCallBack callBack) {
        List<LoveCityEntity> res = mLoveCityDao.queryBuilder().
                where(LoveCityEntityDao.Properties.CityName.like(cityName)).
                list();

        if (isListEmpty(res)) {
            callBack.onFailure("没有喜欢的城市");
        }else{
            callBack.onSuccess(res);
        }
    }

    @Override
    public void getLoveCity(int order, GetLoveCityCallBack callBack) {
        List<LoveCityEntity> res = mLoveCityDao.queryBuilder().
                where(LoveCityEntityDao.Properties.Order.eq(order)).list();

        if (isListEmpty(res)){
            callBack.onFailure("没有喜欢的城市");
        }else{
            callBack.onSuccess(res);
        }
    }

    @Override
    public void getLoveCity(GetLoveCityCallBack callBack) {
        List<LoveCityEntity> res = mLoveCityDao.queryBuilder().
                orderAsc(LoveCityEntityDao.Properties.Order).
                list();

        if (isListEmpty(res)){
            callBack.onFailure("没有喜欢的城市");
        }else{
            callBack.onSuccess(res);
        }
    }

    @Override
    public void addLoveCity(LoveCityEntity loveCity) {
        mLoveCityDao.insert(loveCity);
    }

    @Override
    public boolean isExistInLoveCity(String cityName) {
        List<LoveCityEntity> res = mLoveCityDao.queryBuilder().
                where(LoveCityEntityDao.Properties.CityName.like(cityName)).
                list();

        if (isListEmpty(res)) {
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void updateCityOrder(String cityName, final int order) {
        getLoveCity(cityName, new GetLoveCityCallBack() {
            @Override
            public void onSuccess(List<LoveCityEntity> loveCities) {
                LoveCityEntity tmp = loveCities.get(0);
                tmp.setOrder(order);
                mLoveCityDao.update(tmp);
            }

            @Override
            public void onFailure(String failureMessage) {
                Logger.e("没有要更新的城市数据");
            }
        });
    }

    @Override
    public void deleteCity(String cityName) {
        getLoveCity(cityName, new GetLoveCityCallBack() {
            @Override
            public void onSuccess(List<LoveCityEntity> loveCities) {
                mLoveCityDao.delete(loveCities.get(0));
            }

            @Override
            public void onFailure(String failureMessage) {
                Logger.e("没有要删除的城市数据");
            }
        });
    }



}
