package com.weather.byhieg.easyweather.data.bean;

import java.io.Serializable;

/**
 * Created by shiqifeng on 2016/9/21.
 * Mail:byhieg@gmail.com
 */

public class WeekWeather implements Serializable {

    private int lowTemp;
    private int highTemp;

    private String Date;
    private String cond;

    public int getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(int lowTemp) {
        this.lowTemp = lowTemp;
    }

    public int getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(int highTemp) {
        this.highTemp = highTemp;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }
}
