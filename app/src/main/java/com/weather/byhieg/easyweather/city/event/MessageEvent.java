package com.weather.byhieg.easyweather.city.event;

/**
 * Created by byhieg on 17/2/5.
 * Contact with byhieg@gmail.com
 */

public class MessageEvent {

    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
