package com.weather.byhieg.easyweather.Service;

import android.app.IntentService;
import android.content.Intent;

import com.example.byhieglibrary.Net.HttpUtils;
import com.example.byhieglibrary.Utils.StringUtils;
import com.google.gson.Gson;
import com.weather.byhieg.easyweather.Activity.StartActivity;
import com.weather.byhieg.easyweather.Bean.UrlCity;
import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.Db.City;
import com.weather.byhieg.easyweather.Db.CityWeather;
import com.weather.byhieg.easyweather.Db.CityWeatherDao;
import com.weather.byhieg.easyweather.Db.LoveCity;
import com.weather.byhieg.easyweather.Db.LoveCityDao;
import com.weather.byhieg.easyweather.Db.Province;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Tools.PutDaoData;
import com.weather.byhieg.easyweather.View.MyToast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;


public class BackGroundService extends IntentService {


    public BackGroundService() {
        super("BackGroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (StartActivity.getActionGetWeather().equals(action)) {
                getWeatherData();
            } else if (StartActivity.getActionAddCity().equals(action)) {
                try {
                    addCityData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将所有城市放入数据库
     */
    private void addCityData() throws Exception {
        String[] provinces = {"北京", "天津", "河北", "山西", "山东", "辽宁", "吉林", "黑龙江", "上海", "江苏", "浙江", "安徽", "福建", "江西",
                "河南", "湖北", "湖南", "广东", "广西", "海南", "重庆", "四川", "贵州", "云南", "陕西", "甘肃", "青海", "内蒙古",
                "西藏", "宁夏", "新疆", "香港", "澳门", "台湾"};
        Province tempProvince = MyApplication.
                getDaoSession(this).
                getProvinceDao().
                loadByRowId(1);

        if (tempProvince == null) {
            for (String item : provinces) {
                Province province = new Province();
                province.setProvinceName(item);
                MyApplication.getDaoSession(this).getProvinceDao().insert(province);
            }
        }

        City tempCity = MyApplication.
                getDaoSession(this).
                getCityDao().
                loadByRowId(1);

        if (tempCity == null) {
            Gson gson = new Gson();
            InputStream inputStream = getResources().openRawResource(R.raw.city);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            UrlCity urlCity = gson.fromJson(sb.toString(), UrlCity.class);
            for (int i = 0; i < urlCity.getCity_info().size(); i++) {
                City city = new City();
                city.setProvinceName(urlCity.getCity_info().get(i).getProv());
                city.setCitynName( urlCity.getCity_info().get(i).getCity());
                city.setLove("no");
                MyApplication.getDaoSession(this).getCityDao().insert(city);
            }
        }
    }

    /**
     * 获取天气
     * <p>
     * 先检测喜欢城市的数据库是否为空，是的话，添加默认城市成都。
     * 查询天气数据库，必要时进行网络请求
     */
    private void getWeatherData() {
        List<LoveCity> city = MyApplication.
                getDaoSession(this).
                getLoveCityDao().
                loadAll();
        if (city != null) {
            if (city.size() == 0) {
                LoveCity chengdu = new LoveCity();
                chengdu.setCitynName("成都");
                chengdu.setOrder(1);
                MyApplication.getDaoSession(this).getLoveCityDao().insert(chengdu);
            }
        }
        LoveCity firstCity = MyApplication.
                getDaoSession(this).
                getLoveCityDao().
                queryBuilder().
                where(LoveCityDao.Properties.Order.eq(1)).list().get(0);

        if (firstCity != null) {
            List<CityWeather> weather = MyApplication.
                    getDaoSession(this).
                    getCityWeatherDao().
                    queryBuilder().
                    where(CityWeatherDao.Properties.CityName.eq(firstCity)).list();

            if (weather != null && weather.size() == 0) {
                try {
                    doNetWeather(firstCity.getCitynName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 从网络获取order为1的城市的天气，并把天气放入数据库中
     *
     * @param cityName 根据城市名字进行网络请求
     * @throws Exception
     */
    private void doNetWeather(String cityName) throws Exception {
        Gson gson = new Gson();
        int[] pos = {11, 15, 22, 23};
        Map<String, String> params = new HashMap<>();
        params.put("city", cityName);
        params.put("key", MyApplication.getHeweatherKey());
        String url = HttpUtils.url(MyApplication.getCityUrl(), null, params);
        Response netResponse = HttpUtils.getAsyn(url);
        if (netResponse != null) {
            String response = StringUtils.delPosOfString(netResponse.body().string(), pos);
            WeatherBean weatherBean = gson.fromJson(response, WeatherBean.class);
            MyApplication.getDaoSession(this).getCityWeatherDao().insert(PutDaoData.putWeatherData(weatherBean));
        } else {
            MyToast myToast = MyToast.createMyToast();
            myToast.ToastShow(this, "网络异常,请检查网络");
        }

    }


}
