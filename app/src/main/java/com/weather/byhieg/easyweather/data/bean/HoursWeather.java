package com.weather.byhieg.easyweather.data.bean;

import java.io.Serializable;

/**
 * Created by byhieg on 16-9-26.
 * Mail byhieg@gmail.com
 */

public class HoursWeather implements Serializable{

    private String tmp;
    private String pop;
    private String wind_class;
    private String wind_dir;
    private String wind_deg;
    private String wind_speed;
    private String hum;
    private String update;


    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }


    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }


    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getWind_class() {
        return wind_class;
    }

    public void setWind_class(String wind_class) {
        this.wind_class = wind_class;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public void setWind_dir(String wind_dir) {
        this.wind_dir = wind_dir;
    }

    public String getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(String wind_deg) {
        this.wind_deg = wind_deg;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }
}
