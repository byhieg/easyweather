package com.example.byhieglibrary.Utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by byhieg on 16-9-10.
 * Mail byhieg@gmail.com
 */
public class DateUtil {

    /**
     *
     * @param newDate 新的时间
     * @param oldDate 旧的事件
     * @return 新旧时间差
     * @throws ParseException
     */
    public static long getDifferenceofDate(Date newDate,Date oldDate) throws ParseException {
        @SuppressLint("SimpleDateFormat")
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date tempNew = df.parse(df.format(newDate));
        Date tempOld = df.parse(df.format(oldDate));
        return tempNew.getTime() - tempOld.getTime();
    }
}
