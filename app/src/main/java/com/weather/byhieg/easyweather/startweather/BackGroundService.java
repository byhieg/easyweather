package com.weather.byhieg.easyweather.startweather;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.tools.Knife;
import com.weather.byhieg.easyweather.tools.LogUtils;
import com.google.gson.Gson;
import com.weather.byhieg.easyweather.data.bean.UrlCity;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.data.source.CityDataSource;
import com.weather.byhieg.easyweather.data.source.CityRepository;
import com.weather.byhieg.easyweather.data.source.WeatherRepository;
import com.weather.byhieg.easyweather.data.source.local.entity.LoveCityEntity;
import com.weather.byhieg.easyweather.tools.MainThreadAction;
import com.weather.byhieg.easyweather.customview.MyToast;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BackGroundService extends IntentService {

    private CityRepository mCityRepository = CityRepository.getInstance();
    private WeatherRepository mWeatherRepository = WeatherRepository.getInstance();

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
            } else if (StartActivity.getActionStartNotification().equals(action)) {
                if (MyApplication.notification()) {
                    startService(new Intent(this, NotificationService.class));
                }
            } else if (StartActivity.getActionFileProcess().equals(action)) {
                if (MyApplication.cache()) {
                    deleteCacheFile();
                }
            }
        }
    }

    /**
     * 将所有城市放入数据库
     */
    private void addCityData() {
        mCityRepository.addProvince();
        try {
            if (!mCityRepository.isExistInCity()) {
                LoveCityEntity entity = new LoveCityEntity(null, "成都", 1);
                mCityRepository.addLoveCity(entity);
                Gson gson = new Gson();
                InputStream inputStream = getResources().openRawResource(R.raw.city);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                UrlCity urlCity = gson.fromJson(sb.toString(), UrlCity.class);
                mCityRepository.addCities(urlCity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取天气
     * <p/>
     * 先检测喜欢城市的数据库是否为空，是的话，添加默认城市成都。
     * 查询天气数据库，必要时进行网络请求
     */
    private void getWeatherData() {
        final List<LoveCityEntity> loveCities = mCityRepository.getAllLoveCities();
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < loveCities.size(); i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        mWeatherRepository.updateCityWeather(loveCities.get(index).getCityName());
                    } catch (Exception e) {
                        Logger.e(e,"问题");
                        MainThreadAction.getInstance().post(new Runnable() {
                            @Override
                            public void run() {
                                MyToast.createMyToast().showToast(getApplicationContext(),"网络出错");
//                                Toast.makeText(BackGroundService.this,"网络错误",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }

    private void deleteCacheFile() {
        File dir = MyApplication.getAppContext().getExternalFilesDir(null);
        File[] children = dir.listFiles();
        for (int i = 0; i < children.length; i++) {
//            LogUtils.e("children", children[i]);
//            File tmp = new File(children[i]);
            if (children[i].exists() && children[i].isFile()) {
                Logger.d("exist", "文件存在");
                if (children[i].delete()) {
                    Logger.d("delete", "删除成功");
                }
            } else {
                Logger.d("no exist", "文件不存在");
            }


        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}


