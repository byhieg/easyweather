package com.weather.byhieg.easyweather.Tools;

import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.Db.CityWeather;
import com.weather.byhieg.easyweather.Db.CityWeatherDao;
import com.weather.byhieg.easyweather.Db.LoveCity;
import com.weather.byhieg.easyweather.Db.LoveCityDao;
import com.weather.byhieg.easyweather.MyApplication;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

/**
 * Created by byhieg on 16-8-11.
 */
public class HandleDaoData {

    public static WeatherBean getWeatherBean(String city) throws Exception{
        List<CityWeather> cityWeather = MyApplication.
                                  getDaoSession().
                                  getCityWeatherDao().
                                  queryBuilder().
                                  where(CityWeatherDao.Properties.CityName.like(city)).limit(1).list();

        if (cityWeather != null && cityWeather.size() != 0) {
            byte[] javaObjBytes = cityWeather.get(0).getWeatherBean();
            ByteArrayInputStream bai = new ByteArrayInputStream(javaObjBytes);
            WeatherBean wb = (WeatherBean) new ObjectInputStream(bai).readObject();
            return wb;
        }else{
            return null;
        }
    }

    public static String getShowCity() {
        LoveCity city = MyApplication.
                        getDaoSession().
                        getLoveCityDao().
                        queryBuilder().
                        where(LoveCityDao.Properties.Order.eq(1)).list().get(0);

        if (city != null) {
            return city.getCitynName();
        }else{
            return null;
        }
    }
}
