package com.weather.byhieg.easyweather.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.baidu.location.LocationClientOption;
import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.data.bean.WeekWeather;
import com.weather.byhieg.easyweather.data.source.CityDataSource;
import com.weather.byhieg.easyweather.data.source.CityRepository;
import com.weather.byhieg.easyweather.data.source.WeatherRepository;
import com.weather.byhieg.easyweather.data.source.local.entity.LoveCityEntity;
import com.weather.byhieg.easyweather.tools.DateUtil;
import com.weather.byhieg.easyweather.tools.MainThreadAction;
import com.weather.byhieg.easyweather.tools.MessageEvent;
import com.weather.byhieg.easyweather.tools.WeatherJsonConverter;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.atomic.AtomicInteger;

import static com.weather.byhieg.easyweather.tools.Constants.UPDATE_WEATHER;

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
        mView.setPresenter(this);
    }

    @Override
    public void start() {
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

        final HWeather weather = mWeatherRepository.getLocalWeather(showCity);
        if (weather == null) {
            doRefreshInNoData();
        } else {
            MainThreadAction.getInstance().post(new Runnable() {
                @Override
                public void run() {
                    mView.updateView(weather);
                }
            });
        }

    }

    @Override
    public String getShowCity() {
        return mWeatherRepository.getShowCity();
    }


    @Override
    public void doRefreshInNoData() {
        refreshData();
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
                                if (index == 0) {
                                    loadWeather();
                                    updateDataInWeeks();
                                }
                            } catch (Exception e) {
                                MainThreadAction.getInstance().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mView.setNetWork();
                                    }
                                });
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

    @Override
    public void generateDataInPopView() {
        mView.showPopupWindow();
    }

    @Override
    public void getNewShowWeather() {
        String showCity = getShowCity();
        HWeather weather = mWeatherRepository.getLocalWeather(showCity);
        mView.updateView(weather);
    }

    @Override
    public void showDialog(final String name, final Context context) {
        AlertDialog.Builder dialogBuilder;
        try {
            if (MyApplication.nightMode2()) {
                dialogBuilder = new AlertDialog.Builder(context, R.style.NightDialog);
            } else {
                dialogBuilder = new AlertDialog.Builder(context);
            }
            dialogBuilder.setTitle("系统提示").setMessage("当前定位到的城市为：" + name + ",是否将该城市设为首页").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (mCityRepository.isExistInLoveCity(name)) {
                        Toast.makeText(context, "该城市已经是喜欢城市", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!mCityRepository.isExistInCity(name)) {
                            Toast.makeText(context, "暂无该城市的天气，期待你的反馈", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mCityRepository.getLoveCity(1, new CityDataSource.GetLoveCityCallBack() {
                            @Override
                            public void onSuccess(List<LoveCityEntity> loveCities) {
                                Logger.d(loveCities.get(0).getCityName() + "  "+ loveCities.get(0).getOrder());
                                mCityRepository.updateLocaitonCityOrder(loveCities.get(0).getCityName(),
                                        mCityRepository.getAllLoveCities().size()+1);
                                LoveCityEntity newLoveCity = new LoveCityEntity();
                                newLoveCity.setCityName(name);
                                newLoveCity.setOrder(1);
                                mCityRepository.addLoveCity(newLoveCity);

                            }

                            @Override
                            public void onFailure(String failureMessage) {
                                Logger.e(failureMessage);
                            }
                        });

                        getNewShowWeather();

                    }
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDataInWeeks() {
        String showCity = getShowCity();
        final HWeather weather = mWeatherRepository.getLocalWeather(showCity);
        final List<WeekWeather> weekWeathers = new ArrayList<>();
        String[] weeks = new String[7];
        final List<Integer> lists = new ArrayList<>();
        if (weather == null) return;
        for (int i = 0; i < WeatherJsonConverter.getWeather(weather).getDaily_forecast().size(); i++) {
            WeekWeather weekWeather = new WeekWeather();
            String weatherCond = WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getCond().getTxt_d();
            weekWeather.setLowTemp(Integer.parseInt(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getTmp().getMin()));
            lists.add(Integer.parseInt(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getTmp().getMin()));
            weekWeather.setHighTemp(Integer.parseInt(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getTmp().getMax()));
            weekWeather.setDate(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getDate());
            if (weatherCond.contains("/")) {
                weekWeather.setCond(weatherCond.split("/")[1]);
            } else {
                weekWeather.setCond(weatherCond);
            }
            weekWeathers.add(weekWeather);
        }
        try {
            weeks = DateUtil.
                    getNextWeek(new SimpleDateFormat("yyyy-MM-dd").
                            parse(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(0).getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String[] finalWeeks = weeks;

        mView.updateWeeksView(weekWeathers, finalWeeks, lists);


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
