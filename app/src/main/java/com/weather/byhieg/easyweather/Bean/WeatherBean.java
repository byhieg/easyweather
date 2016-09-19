package com.weather.byhieg.easyweather.Bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by byhieg on 16-5-10.
 */
public class WeatherBean implements Serializable{

    private List<Weather> HeWeatherdataservice30;

    public List<Weather> getHeWeatherdataservice30() {
        return HeWeatherdataservice30;
    }

    public void setHeWeatherdataservice30(List<Weather> HeWeatherdataservice30) {
        this.HeWeatherdataservice30 = HeWeatherdataservice30;
    }
    
    
}
