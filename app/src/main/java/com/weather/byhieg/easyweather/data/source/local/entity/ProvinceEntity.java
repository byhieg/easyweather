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

//@Entity(tableName = "province")
//public class ProvinceEntity {
//
//    @PrimaryKey(autoGenerate = true)
//    private int id;
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
//    public String getProvinceName() {
//        return provinceName;
//    }
//
//    public void setProvinceName(String provinceName) {
//        this.provinceName = provinceName;
//    }
//}


@Entity
public class ProvinceEntity {

    @Id(autoincrement = true)
    private Long id;

    private String provinceName;

    @Generated(hash = 1618707584)
    public ProvinceEntity(Long id, String provinceName) {
        this.id = id;
        this.provinceName = provinceName;
    }

    @Generated(hash = 1419486807)
    public ProvinceEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }


}

