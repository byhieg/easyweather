package cn.byhieg.betterload.network;

import java.io.Serializable;

/**
 * Created by byhieg on 17/5/11.
 * Contact with byhieg@gmail.com
 */

public class BaseResponseEntity<T> implements Serializable{

    public String result;

    public int code;

    public T data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
