package com.weather.byhieg.easyweather.tools;

import java.util.List;

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

    public static boolean isListEmpty(List<?> list) {
        if (list == null) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }

        return false;
    }
}
