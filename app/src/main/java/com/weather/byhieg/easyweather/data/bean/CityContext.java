package com.weather.byhieg.easyweather.data.bean;

import java.io.Serializable;

/**
 * Created by byhieg on 16-9-15.
 * Mail byhieg@gmail.com
 */
public class CityContext implements Serializable{
    private String cityName;



    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
