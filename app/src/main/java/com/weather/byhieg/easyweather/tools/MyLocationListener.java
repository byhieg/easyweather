package com.weather.byhieg.easyweather.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;

/**
 * Created by wy on 2016/9/28.
 */

public class MyLocationListener implements BDLocationListener {

    private Context context;
    private AlertDialog.Builder dialogBuilder;

    public MyLocationListener() {
    }

    public MyLocationListener(Context context) {
        this.context = context;
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        String city = location.getCity();
        if(city != null){
            final String name = city.substring(0,city.length() - 1);
            try {
                if (MyApplication.nightMode2()){
                    dialogBuilder  = new AlertDialog.Builder(context, R.style.NightDialog);
                }else{
                    dialogBuilder = new AlertDialog.Builder(context);
                }
                dialogBuilder.setTitle("系统提示").setMessage("当前定位到的城市为：" + name + ",是否将该城市设为首页").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (HandleDaoData.isExistInLoveCity(name)) {
                            Toast.makeText(context, "该城市已经是喜欢城市", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!HandleDaoData.isExistinCity(name)) {
                                Toast.makeText(context, "暂无该城市的天气，期待你的反馈", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            LoveCity loveCity = HandleDaoData.getLoveCity(1);
                            HandleDaoData.updateCityOrder(loveCity.getCitynName(), HandleDaoData.getLoveCity().size() + 1);
                            if (!HandleDaoData.isExistInLoveCity(name)) {
                                LoveCity newLoveCity = new LoveCity();
                                newLoveCity.setCitynName(name);
                                newLoveCity.setOrder(1);
                                HandleDaoData.insertLoveCity(newLoveCity);
                            } else {
                                HandleDaoData.updateCityOrder(name, 1);
                            }
                            Intent intent = new Intent("com.weather.byhieg.easyweather.Activity.LOCAL_BROADCAST");
                            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
                            localBroadcastManager.sendBroadcast(intent);
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

        }else if (location.getLocType() == BDLocation.TypeNetWorkException) {

            Toast.makeText(context,"网络不同导致定位失败，请检查网络是否通畅",Toast.LENGTH_LONG).show();
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

            Toast.makeText(context,"无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机",Toast.LENGTH_LONG).show();
        }

        MyApplication.getmLocationClient().stop();

    }
}
