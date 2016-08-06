package com.example.byhieglibrary.Utils;

/**
 * Created by byhieg on 16-6-28.
 */
public class StringUtils {
    //根据输入的指定的字符,分割字符串
    public static String[] splitString(String str,String regex) {
        String [] result = str.split(regex);
        return result;
    }

    //删除指定位置的字符串
    public static String delPosOfString(String str, int[] pos) {
        StringBuffer sb = new StringBuffer(str);
        for(int i = 0 ; i < pos.length;i++) {
            sb.deleteCharAt(pos[i]);
        }
        return sb.toString();
    }
}
