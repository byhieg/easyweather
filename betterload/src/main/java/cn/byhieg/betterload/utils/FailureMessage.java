package cn.byhieg.betterload.utils;

import java.io.Serializable;

/**
 * Created by byhieg on 17/3/3.
 * Contact with byhieg@gmail.com
 */

public class FailureMessage implements Serializable{

    private int resultCode;
    private String failureMessage;
    private String other;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public void clear(){
        if (resultCode != 0 || failureMessage != null) {
            resultCode = 0;
            failureMessage = "";
            other = "";
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=====================\n");
        sb.append(":::请求失败，状态如下\n");
        sb.append(":::状态码：").append(resultCode).append("\n");
        sb.append(":::失败信息：").append(failureMessage).append("\n");
        sb.append("======================\n");
        return sb.toString();
    }
}
