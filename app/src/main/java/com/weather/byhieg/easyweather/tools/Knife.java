package com.weather.byhieg.easyweather.tools;

import android.provider.ContactsContract;

import com.weather.byhieg.easyweather.data.HWeather;
import com.weather.byhieg.easyweather.data.source.local.entity.WeatherEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by byhieg on 17/5/21.
 * Contact with byhieg@gmail.com
 */

public class Knife {

    static final long UPDATE_TIME = 60 * 60 * 1000;
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale
            .getDefault());

    public static <T> T checkNotNull(T ref) {
        if (ref == null) {
            throw new NullPointerException();
        }else{
            return ref;
        }
    }

    public static boolean isListEmpty(List<?> list) {
        if (list == null) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }

        return false;
    }

    public static Date convertDate(Date date){
        try {
            return simpleDateFormat.parse(simpleDateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date convertDate(String date){
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isNeedUpdate(Date oldTime,Date newTime){
        long oT = oldTime.getTime();
        long nT = newTime.getTime();

        if (nT - oT >= UPDATE_TIME) {
            return true;
        }else{
            return false;
        }
    }


    public static <T>  T convertObject(byte[] bytes, Class<T> clz) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            Object object = new ObjectInputStream(bais).readObject();
            T t = clz.cast(object);
            return t;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static WeatherEntity convertHWeather(HWeather weather) {

        try {
            WeatherEntity entity = new WeatherEntity();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date date = simpleDateFormat.parse(WeatherJsonConverter.getWeather(weather).getBasic().getUpdate().getLoc());
            entity.setUpdateTime(date);
            entity.setCityName(WeatherJsonConverter.getWeather(weather).getBasic().getCity());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(weather);
            objectOutputStream.flush();
            byte[] bytes = byteArrayOutputStream.toByteArray();
            entity.setWeather(bytes);
            return entity;
        } catch (Exception e) {

        }

        return null;
    }
}
