package com.weather.byhieg.easyweather.data.source.local;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.data.bean.UrlCity;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.data.source.CityDataSource;
import com.weather.byhieg.easyweather.data.source.WeatherDataSource;
import com.weather.byhieg.easyweather.data.source.local.dao.CityEntityDao;
import com.weather.byhieg.easyweather.data.source.local.dao.LoveCityEntityDao;
import com.weather.byhieg.easyweather.data.source.local.dao.ProvinceEntityDao;
import com.weather.byhieg.easyweather.data.source.local.dao.WeatherEntityDao;
import com.weather.byhieg.easyweather.data.source.local.entity.CityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.LoveCityEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.ProvinceEntity;
import com.weather.byhieg.easyweather.data.source.local.entity.WeatherEntity;
import com.weather.byhieg.easyweather.tools.WeatherJsonConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.weather.byhieg.easyweather.tools.Knife.checkNotNull;
import static com.weather.byhieg.easyweather.tools.Knife.convertDate;
import static com.weather.byhieg.easyweather.tools.Knife.convertObject;
import static com.weather.byhieg.easyweather.tools.Knife.isListEmpty;


/**
 * Created by byhieg on 17/5/21.
 * Contact with byhieg@gmail.com
 */

public class WeatherLocalDataSource implements WeatherDataSource, CityDataSource {

    private final static String TAG = "WeatherLocalDataSource";
    private ProvinceEntityDao mProvinceDao;
    private CityEntityDao mCityDao;
    private LoveCityEntityDao mLoveCityDao;
    private WeatherEntityDao mWeatherDao;

    public WeatherLocalDataSource() {
        mProvinceDao = MyApplication.getDaoSession().getProvinceEntityDao();
        mCityDao = MyApplication.getDaoSession().getCityEntityDao();
        mLoveCityDao = MyApplication.getDaoSession().getLoveCityEntityDao();
        mWeatherDao = MyApplication.getDaoSession().getWeatherEntityDao();
    }


    @Override
    public String getShowCity() {
        List<LoveCityEntity> entity = mLoveCityDao.queryBuilder().
                where(LoveCityEntityDao.Properties.Order.eq(1)).
                list();
        return entity.get(0).getCityName();
    }

    @Override
    public void addWeather(WeatherEntity entity) {
        mWeatherDao.insert(entity);
    }

    @Override
    public boolean isExistInCityWeather(String cityName) {
        List<WeatherEntity> res = mWeatherDao.queryBuilder().
                where(WeatherEntityDao.Properties.CityName.eq(cityName)).
                limit(1).list();
        return !isListEmpty(res);
    }

    @Override
    public void getWeatherDataFromCity(String cityName, GetWeatherCallBack callBack) throws Exception {
        List<WeatherEntity> res = mWeatherDao.
                queryBuilder().
                where(WeatherEntityDao.Properties.CityName.eq(cityName)).list();
        if (isListEmpty(res)) {
            callBack.onFailure("没有该城市的天气");
        } else {
            byte[] bytes = res.get(res.size() - 1).getWeather();
            callBack.onSuccess(convertObject(bytes, HWeather.class));
        }
    }

    @Override
    public void getWeatherEntity(String cityName, GetWeatherEntityCallBack callBack) {
        List<WeatherEntity> res = mWeatherDao.queryBuilder().
                where(WeatherEntityDao.Properties.CityName.eq(cityName)).list();
        if (isListEmpty(res)) {
            callBack.onFailure("没有该城市的天气数据");
        } else {
            callBack.onSuccess(res.get(res.size() - 1));
        }
    }

    @Override
    public WeatherEntity getWeatherEntity(String cityName) {
        List<WeatherEntity> res = mWeatherDao.queryBuilder().
                where(WeatherEntityDao.Properties.CityName.eq(cityName)).list();
        if (!isListEmpty(res)) {
            return res.get(res.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 该方法由repository实现
     *
     * @param cityName
     * @throws Exception
     */
    @Override
    public void updateCityWeather(String cityName) throws Exception {

    }

    @Override
    public HWeather getLocalWeather(String cityName) {
        WeatherEntity entity = getWeatherEntity(cityName);
        if (entity == null) {
            return null;
        } else {
            return convertObject(entity.getWeather(), HWeather.class);
        }
    }


    @Override
    public void saveWeather(HWeather weather) {
        WeatherEntity entity = new WeatherEntity();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date date = simpleDateFormat.parse(WeatherJsonConverter.getWeather(weather).getBasic().getUpdate().getLoc());
            entity.setCityName(WeatherJsonConverter.getWeather(weather).getBasic().getCity());
            entity.setUpdateTime(date);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(weather);
            objectOutputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            entity.setWeather(bytes);
            addWeather(entity);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 该方法由remoteDataSource实现
     *
     * @param cityName
     * @return
     * @throws Exception
     */
    @Override
    public HWeather getWeatherDataFromCity(String cityName) {
        return null;
    }

    @Override
    public void addCities(UrlCity city) {
        Set<CityEntity> sets = new HashSet<>();
        for (int i = 0; i < city.getCity_info().size(); i++) {
            CityEntity entity = new CityEntity();
            entity.setProvinceName(city.getCity_info().get(i).getProv());
            entity.setCityName(city.getCity_info().get(i).getCity());
            sets.add(entity);
//            addCity(entity);
        }
        mCityDao.insertInTx(sets);
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
        } else {
            callBack.onSuccess(res);
        }
    }

    @Override
    public void getAllCity(GetCityCallBack callBack) {
        List<CityEntity> res = mCityDao.loadAll();
        if (isListEmpty(res)) {
            callBack.onFailure("该省份下没有城市");
        } else {
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
        } else {
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
        } else {
            return true;
        }
    }

    @Override
    public List<CityEntity> getAllCities() {
        return mCityDao.loadAll();
    }

    @Override
    public List<CityEntity> getCities(String name) {
        return mCityDao.queryBuilder().
                where(CityEntityDao.Properties.CityName.like("%" + name + "%")).
                list();
    }

    @Override
    public List<ProvinceEntity> getProvinces(String name) {
        return mProvinceDao.queryBuilder().
                where(ProvinceEntityDao.Properties.ProvinceName.like("%" + name + "%")).
                list();
    }

    @Override
    public void addProvince() {
        final String[] provinces = {"北京", "天津", "河北", "山西", "山东", "辽宁", "吉林", "黑龙江",
                "上海", "江苏", "浙江", "安徽", "福建", "江西", "河南", "湖北", "湖南", "广东", "广西",
                "海南", "重庆", "四川", "贵州", "云南", "陕西", "甘肃", "青海", "内蒙古", "西藏", "宁夏",
                "新疆", "香港", "澳门", "台湾"};

        if (!isExistInProvince()) {
            for (int i = 0; i < provinces.length; i++) {
                ProvinceEntity entity = new ProvinceEntity(null, provinces[i]);
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
        } else {
            callBack.onSuccess(res);
        }
    }

    @Override
    public List<ProvinceEntity> getAllProvince() {
        return mProvinceDao.loadAll();
    }

    @Override
    public List<LoveCityEntity> getAllLoveCities() {
        return mLoveCityDao.loadAll();
    }


    @Override
    public void getLoveCity(String cityName, GetLoveCityCallBack callBack) {
        List<LoveCityEntity> res = mLoveCityDao.queryBuilder().
                where(LoveCityEntityDao.Properties.CityName.like(cityName)).
                list();

        if (isListEmpty(res)) {
            callBack.onFailure("没有喜欢的城市");
        } else {
            callBack.onSuccess(res);
        }
    }

    @Override
    public void getLoveCity(int order, GetLoveCityCallBack callBack) {
        List<LoveCityEntity> res = mLoveCityDao.queryBuilder().
                where(LoveCityEntityDao.Properties.Order.eq(order)).list();

        if (isListEmpty(res)) {
            callBack.onFailure("没有喜欢的城市");
        } else {
            callBack.onSuccess(res);
        }
    }

    @Override
    public LoveCityEntity getLoveCity(int order) {
        List<LoveCityEntity> list = mLoveCityDao.queryBuilder().
                where(LoveCityEntityDao.Properties.Order.eq(order)).list();
        List<LoveCityEntity> tmpList = mLoveCityDao.queryBuilder().list();
        for (int i = 0; i < tmpList.size(); i++) {
            Logger.d(tmpList.get(i).getCityName() + " " + tmpList.get(i).getOrder());
        }
        if (!isListEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }

    }

    @Override
    public void getLoveCity(GetLoveCityCallBack callBack) {
        List<LoveCityEntity> res = mLoveCityDao.queryBuilder().
                orderAsc(LoveCityEntityDao.Properties.Order).
                list();

        if (isListEmpty(res)) {
            callBack.onFailure("没有喜欢的城市");
        } else {
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
        } else {
            return true;
        }
    }

    @Override
    public void updateCityOrder(final String cityName, final int order) {
        getLoveCity(cityName, new GetLoveCityCallBack() {
            @Override
            public void onSuccess(List<LoveCityEntity> loveCities) {
                LoveCityEntity old = getLoveCity(order);
                LoveCityEntity tmp = loveCities.get(0);
                int tmpOrder = tmp.getOrder();
                tmp.setOrder(order);
                old.setOrder(tmpOrder);
                mLoveCityDao.update(tmp);
                mLoveCityDao.update(old);
            }

            @Override
            public void onFailure(String failureMessage) {
                Logger.e("没有要更新的城市数据");
            }
        });
    }

    @Override
    public void updateLocaitonCityOrder(String cityName, final int order) {
        getLoveCity(cityName, new GetLoveCityCallBack() {
            @Override
            public void onSuccess(List<LoveCityEntity> loveCities) {
                LoveCityEntity old = loveCities.get(0);
                old.setOrder(order);
                mLoveCityDao.update(old);
            }

            @Override
            public void onFailure(String failureMessage) {

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
