package com.weather.byhieg.easyweather.home;


import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.MyApplication;


import org.greenrobot.eventbus.EventBus;



public class MyLocationListener implements BDLocationListener {


    @Override
    public void onReceiveLocation(final BDLocation location) {
        String city = location.getCity();
        if (city != null) {
            final String name = city.substring(0, city.length() - 1);
            EventBus.getDefault().post(new CityNameMessage(name));
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

            Toast.makeText(MyApplication.getAppContext(), "网络不同导致定位失败，请检查网络是否通畅", Toast.LENGTH_LONG).show();
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

            Toast.makeText(MyApplication.getAppContext(), "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机", Toast.LENGTH_LONG).show();
        }

        MyApplication.getmLocationClient().stop();

    }


}
