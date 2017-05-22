package com.weather.byhieg.easyweather.tools;

/**
 * Created by byhieg on 17/5/21.
 * Contact with byhieg@gmail.com
 */

public class Knife {

    public static <T> T checkNotNull(T ref) {
        if (ref == null) {
            throw new NullPointerException();
        }else{
            return ref;
        }
    }
}
