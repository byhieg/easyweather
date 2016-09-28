package com.weather.byhieg.easyweather.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;

import java.util.List;

/**
 * Created by wy on 2016/9/28.
 */

public class MyLocationListener implements BDLocationListener {

    private Context context;

    public MyLocationListener() {
    }

    public MyLocationListener(Context context) {
        this.context = context;
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        String city=null;
        city=location.getCity();
        if(city!=null){
            new AlertDialog.Builder(context).setTitle("系统提示").setMessage("当前定位到的城市为："+city+",是否将该城市设为首页").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        }else if (location.getLocType() == BDLocation.TypeNetWorkException) {

            Toast.makeText(context,"网络不同导致定位失败，请检查网络是否通畅",Toast.LENGTH_LONG).show();
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

            Toast.makeText(context,"无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机",Toast.LENGTH_LONG).show();
        }

    }
}
