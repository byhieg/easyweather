package com.weather.byhieg.easyweather.tools;

import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.MyApplication;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

public class HandleDaoData {

    /**
     * 根据城市，得到城市天气表中的天气数据
     * @param city city
     * @return WeatherBean对象的天气数据
     * @throws Exception
     */

    public static WeatherBean getWeatherBean(String city) throws Exception{
        List<CityWeather> cityWeather = MyApplication.
                                  getDaoSession().
                                  getCityWeatherDao().
                                  queryBuilder().
                                  where(CityWeatherDao.Properties.CityName.like(city)).limit(1).list();

        if (cityWeather != null && cityWeather.size() != 0) {
            byte[] javaObjBytes = cityWeather.get(0).getWeatherBean();
            ByteArrayInputStream bai = new ByteArrayInputStream(javaObjBytes);
            return (WeatherBean) new ObjectInputStream(bai).readObject();
        }else{
            return null;
        }
    }

    /**
     *
     * @return 要展示的城市的名字
     */

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

    /******************************************************************************************
     *  对CityWeatherDao进行的操作
     *******************************************************************************************/

    /**
     * 插入城市天气
     * @param cityWeather 要插入的城市天气
     */
    public static void insertCityWeather(CityWeather cityWeather){
        MyApplication.getDaoSession().getCityWeatherDao().insert(cityWeather);
    }

    /**
     * 查询城市天气表中　该城市的天气是否存在
     * @param city 城市名字
     * @return true 存在
     */

    public static boolean isExistInCityWeather(String city) {
        List<CityWeather> cityWeathers = MyApplication.
                                         getDaoSession().
                                         getCityWeatherDao().
                                         queryBuilder().
                                         where(CityWeatherDao.Properties.CityName.eq(city)).
                                         limit(1).list();

        return cityWeathers != null && cityWeathers.size() != 0;
    }


    /**
     * 根据天气数据 更新表中内容
     * @param cityWeather 天气数据
     */
    public static void updateCityWeather(CityWeather cityWeather) {
        String name = cityWeather.getCityName();
        CityWeather temp = getCityWeather(name);
        temp.setWeatherBean(cityWeather.getWeatherBean());
        temp.setUpdateTime(cityWeather.getUpdateTime());
        MyApplication.getDaoSession().getCityWeatherDao().update(temp);
    }


    public static CityWeather getCityWeather(String cityName){
        List<CityWeather> cityWeathers = MyApplication.
                                  getDaoSession().
                                  getCityWeatherDao().
                                  queryBuilder().
                                  where(CityWeatherDao.Properties.CityName.eq(cityName)).list();
        if (cityWeathers.size() != 0) {
            return cityWeathers.get(cityWeathers.size() - 1);
        }else {
            return null;
        }

    }

    /******************************************************************************************
     * 对CityDao进行的操作
     *******************************************************************************************/


    /******************************************************************************************
     * 对ProvinceDao进行的操作
     *******************************************************************************************/


    /******************************************************************************************
     *  对LoveCityDao进行的操作
     *******************************************************************************************/

}
