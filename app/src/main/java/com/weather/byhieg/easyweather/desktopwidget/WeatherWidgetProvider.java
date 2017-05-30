package com.weather.byhieg.easyweather.desktopwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.data.source.WeatherRepository;
import com.weather.byhieg.easyweather.tools.WeatherJsonConverter;
import com.weather.byhieg.easyweather.tools.WeatherIcon;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wy on 2016/9/25.
 */

public class WeatherWidgetProvider extends AppWidgetProvider {


    // 保存 widget 的id的HashSet，每新建一个 widget 都会为该 widget 分配一个 id。
    private static Set idsSet = new HashSet();
    private WeatherRepository mRepository = WeatherRepository.getInstance();

    // onUpdate() 在更新 widget 时，被执行，
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        // 每次 widget 被创建时，对应的将widget的id添加到set中
        for (int appWidgetId : appWidgetIds) {
            idsSet.add(Integer.valueOf(appWidgetId));
        }

        updateAllAppWidgets(context,appWidgetManager,idsSet);
    }

    // 第一个widget被创建时调用
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        // 在第一个 widget 被创建时，开启服务
        context.startService(new Intent(context,WeatherWidgetService.class));
    }

    // 最后一个widget被删除时调用
    @Override
    public void onDisabled(Context context) {
        // 在最后一个 widget 被删除时，终止服务
        context.stopService(new Intent(context,WeatherWidgetService.class));
        super.onDisabled(context);
    }


    //widget被删除时调用
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // 当 widget 被删除时，对应的删除set中保存的widget的id
        for (int appWidgetId : appWidgetIds) {
            idsSet.remove(Integer.valueOf(appWidgetId));
        }
        super.onDeleted(context, appWidgetIds);
    }

    // 接收广播的回调函数
    @Override
    public void onReceive(Context context, Intent intent) {

        // “更新”广播
        updateAllAppWidgets(context, AppWidgetManager.getInstance(context), idsSet);

        super.onReceive(context, intent);
    }

    // 更新所有的 widget
    private void updateAllAppWidgets(Context context, AppWidgetManager appWidgetManager, Set set) {

        // widget 的id
        int appID;
        // 迭代器，用于遍历所有保存的widget的id
        Iterator it = set.iterator();

        while (it.hasNext()) {
            appID = ((Integer) it.next()).intValue();
            // 获取 example_appwidget.xml 对应的RemoteViews
            String cityName = WeatherJsonConverter.getWeather(getCityData()).getBasic().getCity();
            String temp = WeatherJsonConverter.getWeather(getCityData()).getNow().getTmp();
            String cond = WeatherJsonConverter.getWeather(getCityData()).getNow().getCond().getTxt();
            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.weather_appwidget);
            remoteView.setTextViewText(R.id.widget_city,cityName);
            remoteView.setTextViewText(R.id.widget_temperature,temp+"℃");
            remoteView.setImageViewResource(R.id.widget_weather_img, WeatherIcon.getWeatherImage(WeatherJsonConverter.getWeather(getCityData()).getNow().getCond().getCode()));
            remoteView.setTextViewText(R.id.widget_weather_text,cond);
            // 更新 widget
            if (MyApplication.widget()) {
                appWidgetManager.updateAppWidget(appID, remoteView);
            }
        }
    }
    private HWeather getCityData(){
        String cityName = mRepository.getShowCity();
        try {
            return mRepository.getLocalWeather(cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
