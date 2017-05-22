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

    /**
     * 添加城市进入城市表中
     * @param city 城市数据
     */
    public static void insertCity(City city) {
        MyApplication.getDaoSession().getCityDao().insert(city);
    }

    public static boolean isExistInCity(){
        City tempCity = MyApplication.
                getDaoSession().
                getCityDao().
                loadByRowId(1);

        return tempCity != null;
    }


    public static List<City> getCityFromProvince(String provinceName) {
        return MyApplication.
                getDaoSession().
                getCityDao().
                queryBuilder().
                where(CityDao.Properties.ProvinceName.like(provinceName)).
                list();
    }

    public static List<City> getCities(String name) {
        return MyApplication.
                getDaoSession().
                getCityDao().
                queryBuilder().
                where(CityDao.Properties.CitynName.like("%" + name + "%")).
                list();
    }

    public static boolean isExistinCity(String name) {
        if (getCities(name) == null || getCities(name).size() == 0) {
            return false;
        }
        return true;
    }

    /******************************************************************************************
     * 对ProvinceDao进行的操作
     *******************************************************************************************/


    /******************************************************************************************
     *  对LoveCityDao进行的操作
     *******************************************************************************************/
    /**
     * 重载方法 根据order的值得到喜欢的城市
     * @param order 喜欢城市的顺序
     * @return LoveCity 喜欢的一个城市
     */
    public static LoveCity getLoveCity(Integer order){
        return  MyApplication.
                getDaoSession().
                getLoveCityDao().
                queryBuilder().
                where(LoveCityDao.Properties.Order.eq(order)).list().get(0);
    }

    /**
     * 无参数，得到所有喜欢的城市
     * @return List<LoveCity> 喜欢城市的集合
     */
    public static List<LoveCity> getLoveCity(){

        return MyApplication.
                getDaoSession().
                getLoveCityDao().
                queryBuilder().
                orderAsc(LoveCityDao.Properties.Order).
                list();

    }

    /**
     * 重载方法，根据城市名字，得到该记录
     * @param cityName 城市名字
     * @return 对应的城市记录
     */

    public static LoveCity getLoveCity(String cityName) {
        return MyApplication.
                getDaoSession().
                getLoveCityDao().
                queryBuilder().
                where(LoveCityDao.Properties.CitynName.like(cityName)).
                list().get(0);
    }

    /**
     * 插入喜欢的城市
     * @param loveCity 喜欢的城市
     */
    public static void insertLoveCity(LoveCity loveCity) {
        MyApplication.getDaoSession().getLoveCityDao().insert(loveCity);
    }


    /**
     * 根据城市名字，判断是不是在喜欢的城市的列表中
     * @param cityName 城市名字
     * @return true 代表在列表中，false 表示不在
     */
    public static boolean isExistInLoveCity(String cityName) {
        List<LoveCity> loveCities = MyApplication.
                getDaoSession().
                getLoveCityDao().
                queryBuilder().
                where(LoveCityDao.Properties.CitynName.like(cityName)).
                list();
        if (loveCities != null && loveCities.size() != 0) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据城市名字，更新该记录的天气
     * @param cityName 城市名字
     * @param order 顺序
     */

    public static void updateCityOrder(String cityName,int order) {
        LoveCity temp = getLoveCity(cityName);
        temp.setOrder(order);
        MyApplication.getDaoSession().getLoveCityDao().update(temp);
    }

    /**
     * 根据城市名字，
     * @param cityName 城市名
     */
    public static void deleteCity(String cityName) {
        MyApplication.getDaoSession().getLoveCityDao().delete(getLoveCity(cityName));
    }


    /******************************************************************************************
     * 对ViewSpotDao进行的操作
     *******************************************************************************************/

    public static void insertViewSpot(ViewSpot viewSpot) {
        MyApplication.getDaoSession().getViewSpotDao().insert(viewSpot);
    }

    public static boolean isExistInViewSpot(){
        ViewSpot viewSpot = MyApplication.getDaoSession().getViewSpotDao().loadByRowId(1);
        return viewSpot != null;
    }
}
