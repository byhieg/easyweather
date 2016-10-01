package com.weather.byhieg.easyweather.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by byhieg on 16-10-1.
 * Mail byhieg@gmail.com
 */

public class ImageUtils {


    public static final int BRIEF = 1;
    public static final int DETAIL = 2;
    public static final int FUTURE = 3;


    public static File drawImage(Context context, int flag) {
        WeatherBean weatherBean = getData();
        Bitmap bitmap = Bitmap.createBitmap(400, 800, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);//设置画笔颜色
        paint.setStrokeWidth(20.0f);// 设置画笔粗细
        String familyName = "宋体";
        Typeface font = Typeface.create(familyName, Typeface.BOLD);
        paint.setTypeface(font);
        paint.setTextSize(25f);//设置文字大小
        paint.setTextAlign(Paint.Align.CENTER);
        File imgFile = null;
        switch (flag) {
            case BRIEF:
                canvas.drawColor(ContextCompat.getColor(context, R.color.dodgerblue));
                Date sqlDate = HandleDaoData.getCityWeather(HandleDaoData.getShowCity()).getUpdateTime();
                String updateTime = new SimpleDateFormat("MM-dd HH:mm:ss").format(sqlDate);
                String city = MyJson.getWeather(weatherBean).getBasic().getCity();
                String tmp = MyJson.getWeather(weatherBean).getNow().getTmp() + "°";
                String cond = MyJson.getWeather(weatherBean).getNow().getCond().getTxt();
                canvas.drawText(updateTime,300,700,paint);
                canvas.drawText(city,300,600,paint);
                canvas.drawText(tmp,100,600,paint);
                canvas.drawText(cond,100,700,paint);
                imgFile = new File(Environment.getExternalStorageDirectory(),
                        "IMG-BRIEF" + System.currentTimeMillis() + ".png");//创建一个文件
                break;
            case DETAIL:
                 imgFile = null;
                 imgFile = new File(Environment.getExternalStorageDirectory(),
                        "IMG-DETAIL" + System.currentTimeMillis() + ".png");//创建一个文件
                break;
            case FUTURE:
                 imgFile = null;
                 imgFile = new File(Environment.getExternalStorageDirectory(),
                        "IMG-FUTURE" + System.currentTimeMillis() + ".png");//创建一个文件
                break;

        }
        try {
            OutputStream os = new FileOutputStream(imgFile);//创建输出流
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);//通过输出流将图片保存
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return imgFile;
    }


    private static WeatherBean getData(){
        try {
            return HandleDaoData.getWeatherBean(HandleDaoData.getShowCity());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
