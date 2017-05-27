package com.weather.byhieg.easyweather.home;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.customview.MyToast;
import com.weather.byhieg.easyweather.data.HWeather;
import com.weather.byhieg.easyweather.data.source.CityDataSource;
import com.weather.byhieg.easyweather.data.source.CityRepository;
import com.weather.byhieg.easyweather.data.source.WeatherRepository;
import com.weather.byhieg.easyweather.data.source.local.entity.LoveCityEntity;
import com.weather.byhieg.easyweather.startweather.BackGroundService;
import com.weather.byhieg.easyweather.tools.Constants;
import com.weather.byhieg.easyweather.tools.MainThreadAction;
import com.weather.byhieg.easyweather.tools.MyLocationListener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by byhieg on 17/5/27.
 * Contact with byhieg@gmail.com
 */

public class HomePresenter implements HomeContract.Presenter {
    private CityRepository mCityRepository;
    private WeatherRepository mWeatherRepository;
    private HomeContract.View mView;
    private AtomicInteger mCount = new AtomicInteger(0);

    public HomePresenter(HomeContract.View view) {
        mView = view;
        mCityRepository = CityRepository.getInstance();
        mWeatherRepository = WeatherRepository.getInstance();
//        myListener = new MyLocationListener(MyApplication.getAppContext());
    }

    @Override
    public void start() {
//        mLocalBroadcastManager = LocalBroadcastManager.getInstance(MyApplication.getAppContext());
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        intentFilter.addAction("com.weather.byhieg.easyweather.Activity.LOCAL_BROADCAST");
//        localReceiver = new LocalReceiver();
//        networkChangeReceiver = new NetworkChangeReceiver();
//        registerReceiver(networkChangeReceiver, intentFilter);
//        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
//        getHoursData();
//        MyApplication.getmLocationClient().registerLocationListener(myListener);
        loadWeather();
        initLocation();
    }

    @Override
    public void doBaiduLocation() {
        MyApplication.getmLocationClient().start();
    }

    @Override
    public void loadWeather() {
        String showCity = getShowCity();
        HWeather weather = mWeatherRepository.getLocalWeather(showCity);
        if (weather == null) {
            if(3 == mCount.getAndIncrement()){
                return;
            }
            doRefreshInNoData();
        } else {
            mView.updateView(weather);
        }
    }

    @Override
    public String getShowCity() {
        return mWeatherRepository.getShowCity();
    }

    @Override
    public void updateWeather() {

    }

    @Override
    public void doRefreshInNoData() {
        loadWeather();
    }

    @Override
    public void refreshData() {
        mCityRepository.getLoveCity(new CityDataSource.GetLoveCityCallBack() {
            @Override
            public void onSuccess(final List<LoveCityEntity> loveCities) {
                ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
                for (int i = 0; i < loveCities.size(); i++) {
                    final int index = i;
                    singleThreadExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mWeatherRepository.updateCityWeather(loveCities.get(index).getCityName());
                            } catch (Exception e) {
                                mView.setNetWork();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(String failureMessage) {
                LoveCityEntity entity = new LoveCityEntity(null, "成都", 1);
                mCityRepository.addLoveCity(entity);
                refreshData();
            }
        });
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        MyApplication.getmLocationClient().setLocOption(option);
    }

}
