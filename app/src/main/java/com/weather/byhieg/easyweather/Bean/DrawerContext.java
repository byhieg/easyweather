package com.weather.byhieg.easyweather.Bean;

import java.io.Serializable;

/**
 * Created by byhieg on 16-8-6.
 */
public class DrawerContext implements Serializable{
    public int image;
    public int name;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}
