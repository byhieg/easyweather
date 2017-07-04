package com.weather.byhieg.easyweather.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by byhieg on 17/5/13.
 * Contact with byhieg@gmail.com
 */

public class HWeather implements Parcelable,Serializable {


    private List<Weather> HeWeather5;

    public List<Weather> getHeWeather5() {
        return HeWeather5;
    }

    public void setHeWeather5(List<Weather> HeWeather5) {
        this.HeWeather5 = HeWeather5;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.HeWeather5);
    }

    public HWeather() {
    }

    protected HWeather(Parcel in) {
        this.HeWeather5 = new ArrayList<Weather>();
        in.readList(this.HeWeather5, Weather.class.getClassLoader());
    }

    public static final Creator<HWeather> CREATOR = new Creator<HWeather>() {
        @Override
        public HWeather createFromParcel(Parcel source) {
            return new HWeather(source);
        }

        @Override
        public HWeather[] newArray(int size) {
            return new HWeather[size];
        }
    };
}
