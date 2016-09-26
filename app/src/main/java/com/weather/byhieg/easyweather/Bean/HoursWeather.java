package com.weather.byhieg.easyweather.Bean;

import java.io.Serializable;

/**
 * Created by byhieg on 16-9-26.
 * Mail byhieg@gmail.com
 */

public class HoursWeather implements Serializable{

    private String tmp;
    private String wind;
    private String pop;
    private String update;


    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
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
}
