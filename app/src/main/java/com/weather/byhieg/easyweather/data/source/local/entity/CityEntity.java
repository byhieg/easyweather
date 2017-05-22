package com.weather.byhieg.easyweather.data.source.local.entity;

//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by byhieg on 17/5/22.
 * Contact with byhieg@gmail.com
 */

//@Entity(tableName = "city")
//public class CityEntity {
//
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//
//    private String cityName;
//
//    private String provinceName;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getCityName() {
//        return cityName;
//    }
//
//    public void setCityName(String cityName) {
//        this.cityName = cityName;
//    }
//
//    public String getProvinceName() {
//        return provinceName;
//    }
//
//    public void setProvinceName(String provinceName) {
//        this.provinceName = provinceName;
//    }
//}

@Entity
public class CityEntity{

    @Id(autoincrement = true)
    private Long id;

    private String cityName;

    private String provinceName;

    @Generated(hash = 1398242605)
    public CityEntity(Long id, String cityName, String provinceName) {
        this.id = id;
        this.cityName = cityName;
        this.provinceName = provinceName;
    }

    @Generated(hash = 2001321047)
    public CityEntity() {
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

    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

}