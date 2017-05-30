package com.weather.byhieg.easyweather.data.source.local.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by byhieg on 17/5/23.
 * Contact with byhieg@gmail.com
 */
@Entity
public class WeatherEntity {
    @Id(autoincrement = true)
    private Long id;

    private String cityName;
    /**
     * HWeather对象转成二进制流
     */
    private byte[] weather;
    private Date updateTime;

    @Generated(hash = 1331303326)
    public WeatherEntity(Long id, String cityName, byte[] weather,
                         Date updateTime) {
        this.id = id;
        this.cityName = cityName;
        this.weather = weather;
        this.updateTime = updateTime;
    }

    @Generated(hash = 1598697471)
    public WeatherEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public byte[] getWeather() {
        return this.weather;
    }

    public void setWeather(byte[] weather) {
        this.weather = weather;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
