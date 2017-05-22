package com.weather.byhieg.easyweather.data.source.local;

import android.util.Log;

import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.data.HWeather;
import com.weather.byhieg.easyweather.data.source.WeatherDataSource;
import com.weather.byhieg.easyweather.data.source.local.dao.CityEntityDao;
import com.weather.byhieg.easyweather.data.source.local.dao.LoveCityEntityDao;
import com.weather.byhieg.easyweather.data.source.local.dao.ProvinceEntityDao;
import com.weather.byhieg.easyweather.data.source.local.entity.ProvinceEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * Created by byhieg on 17/5/21.
 * Contact with byhieg@gmail.com
 */

public class WeatherLocalDataSource implements WeatherDataSource {

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
    public void getAllProvince(final GetProvinceCallBack callBack) {
        Log.e(TAG, "getAllProvince已经执行");
        List<ProvinceEntity> provinces = mProvinceDao.loadAll();
        if (provinces.size() != 0) {
            callBack.onSuccess(provinces);
        } else {
            callBack.onFailure("数据为空");
        }
    }

    @Override
    public void getAllCity() {

    }

    @Override
    public void addCity() {

    }

    @Override
    public void addProvince() {
        final String[] provinces = {"北京", "天津", "河北", "山西", "山东", "辽宁", "吉林", "黑龙江",
                "上海", "江苏", "浙江", "安徽", "福建", "江西", "河南", "湖北", "湖南", "广东", "广西",
                "海南", "重庆", "四川", "贵州", "云南", "陕西", "甘肃", "青海", "内蒙古", "西藏", "宁夏",
                "新疆", "香港", "澳门", "台湾"};

        Log.e(TAG, "执行了");
        for (int i = 0; i < provinces.length; i++) {
            ProvinceEntity entity = new ProvinceEntity(null,provinces[i]);
            mProvinceDao.insert(entity);
            Log.e(TAG, entity.getId() + "");
        }


    }

    public void saveWeather(HWeather weather) {

    }


}
