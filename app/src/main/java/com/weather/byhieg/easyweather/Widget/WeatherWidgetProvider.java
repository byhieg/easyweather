package com.weather.byhieg.easyweather.Widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.weather.byhieg.easyweather.Db.CityWeather;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Tools.HandleDaoData;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wy on 2016/9/25.
 */

public class WeatherWidgetProvider extends AppWidgetProvider {

    // 保存 widget 的id的HashSet，每新建一个 widget 都会为该 widget 分配一个 id。
    private static Set idsSet = new HashSet();


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
            RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.weather_appwidget);
            remoteView.setTextViewText(R.id.widget_city,"成都");
            remoteView.setTextViewText(R.id.widget_temperature,"20℃");
            Bitmap weather= BitmapFactory.decodeResource(context.getResources(),R.mipmap.cloudy2);
            remoteView.setImageViewBitmap(R.id.widget_weather_img,weather);
            remoteView.setTextViewText(R.id.widget_weather_text,"多云");
            // 更新 widget
            appWidgetManager.updateAppWidget(appID, remoteView);
        }
    }

}
