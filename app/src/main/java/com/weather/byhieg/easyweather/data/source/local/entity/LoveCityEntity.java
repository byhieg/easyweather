package com.weather.byhieg.easyweather.data.source.local.entity;

//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;

//import android.arch.persistence.room.PrimaryKey;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by byhieg on 17/5/22.
 * Contact with byhieg@gmail.com
 */
//
//@Entity(tableName = "love")
//public class LoveCityEntity {
//
//    @PrimaryKey(autoGenerate = true)
//    private int id;
//    private String cityName;
//    private int order;
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
//    public int getOrder() {
//        return order;
//    }
//
//    public void setOrder(int order) {
//        this.order = order;
//    }
//}


@Entity
public class LoveCityEntity {

    @Id(autoincrement = true)
    private Long id;
    private String cityName;
    private int order;

    @Generated(hash = 1968781902)
    public LoveCityEntity(Long id, String cityName, int order) {
        this.id = id;
        this.cityName = cityName;
        this.order = order;
    }

    @Generated(hash = 2010273138)
    public LoveCityEntity() {
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

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


}