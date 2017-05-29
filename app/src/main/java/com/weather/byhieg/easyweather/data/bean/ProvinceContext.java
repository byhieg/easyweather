package com.weather.byhieg.easyweather.data.bean;

import java.io.Serializable;

/**
 * Created by byhieg on 16-9-15.
 * Mail byhieg@gmail.com
 */
public class ProvinceContext implements Serializable{
    public int image;
    public String name;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
