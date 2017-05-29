package com.weather.byhieg.easyweather.data.bean;

import java.io.Serializable;

/**
 * Created by byhieg on 16-10-7.
 * Mail byhieg@gmail.com
 */

public class CityManageContext implements Serializable{

    private String cityName;
    private String cond;
    private String tmp;
    private String hum;
    private String time;
    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
