package com.example.byhieglibrary.Utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by byhieg on 16-9-10.
 * Mail byhieg@gmail.com
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    /**
     *
     * @param newDate 新的时间
     * @param oldDate 旧的时间
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


    /**
     * 输入一个日期，返回该日期以及接下来6天的星期数。
     * @param date 一个日期
     * @return 返回星期数
     */
    public static String[] getNextWeek(Date date) {
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        String[] myWeek = new String[7];
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int weekIndex = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (weekIndex < 0) {
            weekIndex = 0;
        }
        for(int i = 0 ;i < 7;i++) {
            if (i == 0) {
                myWeek[i] = new SimpleDateFormat("MM/dd").format(date);
            }else{
                myWeek[i] = weeks[(weekIndex + i) % 7];

            }
        }
        return myWeek;
    }

    /**
     *
     * @param fDate 旧的时间
     * @param oDate 新的时间
     * @return
     */

    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;

    }


}
