package com.weather.byhieg.easyweather.startweather;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import com.example.byhieglibrary.Utils.LogUtils;
import com.weather.byhieg.easyweather.Activity.MainActivity;
import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.tools.HandleDaoData;
import com.weather.byhieg.easyweather.tools.MyJson;
import com.weather.byhieg.easyweather.tools.WeatherIcon;

import java.lang.reflect.Field;

import static com.weather.byhieg.easyweather.tools.Constants.NOTIFICATION_ID;

/**
 * Created by byhieg on 16-10-7.
 * Mail byhieg@gmail.com
 */

public class NotificationService extends Service{

    public WeatherBean notificationWeather;
    public NotificationManager notificationManager;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("notificaiton","启动了");
        try {
            notificationWeather = HandleDaoData.getWeatherBean(HandleDaoData.getShowCity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification();
        notification.icon = R.mipmap.icon;
        notification.tickerText = "天气状况查看";
        notification.when = System.currentTimeMillis();
        notification.flags = Notification.FLAG_NO_CLEAR;// 不能够自动清除
        notification.contentIntent = pendingIntent;

        RemoteViews contentViews = new RemoteViews(getPackageName(), R.layout.layout_notification);
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$id");
            Field field = clazz.getField("icon");
            field.setAccessible(true);
            int idIcon = field.getInt(null);
            contentViews.setImageViewResource(idIcon,R.mipmap.icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
        contentViews.setTextViewText(R.id.city_text,MyJson.getWeather(notificationWeather).getBasic().getCity());
        contentViews.setTextViewText(R.id.weather_text,MyJson.getWeather(notificationWeather).getNow().getCond().getTxt());
        contentViews.setImageViewResource(R.id.weather_image,
                    WeatherIcon.getWeatherImage(MyJson.getWeather(notificationWeather).getNow().getCond().getCode()));
        contentViews.setTextViewText(R.id.temperature_text,MyJson.getWeather(notificationWeather).getNow().getTmp()+"℃");
        String exerciseStr = "运动情况:" + MyJson.getWeather(notificationWeather).getSuggestion().getSport().getBrf();
        contentViews.setTextViewText(R.id.exercise_text,exerciseStr);
        notification.contentView = contentViews;
        notificationManager.notify(NOTIFICATION_ID, notification);
        startForeground(NOTIFICATION_ID, notification);
        LogUtils.e("notificaiton","真没有");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
    }
}
