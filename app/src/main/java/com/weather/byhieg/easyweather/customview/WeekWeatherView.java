package com.weather.byhieg.easyweather.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Typeface;
import android.os.Debug;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.tools.DateUtil;
import com.weather.byhieg.easyweather.tools.IntegerUtils;
import com.weather.byhieg.easyweather.data.bean.WeekWeather;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.tools.WeatherJsonConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by shiqifeng on 2016/9/21.
 * Mail:byhieg@gmail.com
 */

public class WeekWeatherView extends View {

    private Paint baseLinePaint;
    private Paint verticalLinePaint;
    private Paint highPointPaint;
    private Paint lowPointPaint;
    private Paint highLinePaint;
    private Paint lowLinePaint;
    private Paint textPaint;
    private Paint lowTempPaint;
    private Paint highTempPaint;
    private Path mPath;
    private Path dPath;
    private List<Integer> lists = new ArrayList<>();
    private List<WeekWeather> weekWeathers = new ArrayList<>();
    private float[] lowTempX = new float[7];
    private float[] lowTempY = new float[7];
    private float[] highTempX = new float[7];
    private float[] highTempY = new float[7];
    private String[] weeks = new String[7];
    private int viewHeight, viewWidth;
    private int padding = 20;
    private int tempPadding = 15;
    private int textPadding = 20;

    public WeekWeatherView(Context context) {
        super(context);
        init(context);
    }

    public WeekWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WeekWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        baseLinePaint = new Paint();
        baseLinePaint.setColor(ContextCompat.getColor(context, R.color.maincolor));
        baseLinePaint.setAntiAlias(true);
        baseLinePaint.setStyle(Paint.Style.FILL);
        baseLinePaint.setStrokeWidth(10);

        verticalLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        verticalLinePaint.setStyle(Paint.Style.STROKE);
        verticalLinePaint.setColor(ContextCompat.getColor(context, R.color.lightgray));
        verticalLinePaint.setAntiAlias(true);
        verticalLinePaint.setStrokeWidth(5);
        verticalLinePaint.setStrokeCap(Paint.Cap.ROUND);
        PathEffect effect = new DashPathEffect(new float[]{1, 2, 4, 8}, 1);
        verticalLinePaint.setPathEffect(effect);
        verticalLinePaint.setStrokeJoin(Paint.Join.ROUND);

        highPointPaint = new Paint();
        highPointPaint.setColor(ContextCompat.getColor(context, R.color.red));
        highPointPaint.setAntiAlias(true);
        highPointPaint.setStyle(Paint.Style.STROKE);
        highPointPaint.setStrokeWidth(5);

        lowPointPaint = new Paint();
        lowPointPaint.setColor(ContextCompat.getColor(context, R.color.maincolor));
        lowPointPaint.setAntiAlias(true);
        lowPointPaint.setStyle(Paint.Style.STROKE);
        lowPointPaint.setStrokeWidth(5);

        highLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        highLinePaint.setColor(ContextCompat.getColor(context, R.color.red));
        highLinePaint.setAntiAlias(true);
        highLinePaint.setStrokeWidth(5);
        highLinePaint.setStrokeCap(Paint.Cap.ROUND);
        highLinePaint.setStrokeJoin(Paint.Join.ROUND);
        highLinePaint.setStyle(Paint.Style.STROKE);

        lowLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lowLinePaint.setColor(ContextCompat.getColor(context, R.color.cadetblue));
        lowLinePaint.setAntiAlias(true);
        lowLinePaint.setStrokeWidth(5);
        lowLinePaint.setStrokeCap(Paint.Cap.ROUND);
        lowLinePaint.setStrokeJoin(Paint.Join.ROUND);
        lowLinePaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
        dPath = new Path();

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(ContextCompat.getColor(context, R.color.black));
        String familyName = "宋体";
        Typeface font = Typeface.create(familyName, Typeface.BOLD);
        textPaint.setTypeface(font);
        textPaint.setTextAlign(Paint.Align.CENTER);

        lowTempPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        lowTempPaint.setColor(ContextCompat.getColor(context, R.color.cadetblue));
        lowTempPaint.setTypeface(font);
        lowTempPaint.setTextAlign(Paint.Align.CENTER);

        highTempPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        highTempPaint.setColor(ContextCompat.getColor(context, R.color.red));
        highTempPaint.setTypeface(font);
        highTempPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int result;
        //通过MeasureSpec.getMode与getSize方法获取宽和高的测量方式与测量大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //保存最后的测量值，优化代码的话可以不用这个变量的。
        int width = 0, height = 0;
        //对测量模式进行判断，如果是EXACTLY的话则最后的测量值就是系统帮我们测量的结果。
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            //如果是UNSPECIFIED 则使用我们的默认值作为最后的测量值
            result = 300;
            //如果是AT_MOST 则就要用系统测量结果与我们默认结果取最小值来决定最后的测量结果
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(result, widthSize);
            }
        }
        //高度和宽度的过程是一致的。
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            result = 300;
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(result, heightSize);
            }
        }
        //把我们最后的宽和高设置进去
        viewHeight = height;
        viewWidth = width;
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int translateLength = (viewWidth - 2 * padding) / 7;
        textPaint.setTextSize(translateLength / 4);
        lowTempPaint.setTextSize(translateLength / 4);
        highTempPaint.setTextSize(translateLength / 4);

        //画水平基准线
        canvas.drawLine(padding,
                viewHeight - padding,
                viewWidth - padding,
                viewHeight - padding,
                baseLinePaint);

        //画竖直基准线
        for (int i = 1; i < 7; i++) {
            canvas.save();
            canvas.translate(translateLength * i, 0);
            dPath.moveTo(padding,
                    viewHeight - padding - 10);
            dPath.lineTo(padding,
                    padding);
            canvas.drawPath(dPath, verticalLinePaint);
            canvas.restore();
        }
        if (weeks[0] == null) return;
        //画日期
        for (int i = 0; i < weeks.length; i++) {
            canvas.save();
            canvas.translate(translateLength * i, 0);
            canvas.drawText(weeks[i], padding + translateLength / 2, 4 * textPadding, textPaint);
            canvas.drawText(weekWeathers.get(i).getCond(), padding + translateLength / 2, 6 * textPadding,textPaint);
            canvas.restore();
        }


        int lowestTemp = IntegerUtils.getSmallestNum(lists);

        for (int i = 0; i < weekWeathers.size(); i++) {
            int lowDiff = weekWeathers.get(i).getLowTemp() - lowestTemp;
            int diff = weekWeathers.get(i).getHighTemp() - weekWeathers.get(i).getLowTemp();
            lowTempX[i] = padding + translateLength / 2 + translateLength * i;
            lowTempY[i] = viewHeight - (15 + lowDiff) * tempPadding;
            canvas.drawCircle(lowTempX[i],
                    lowTempY[i],
                    8,
                    lowPointPaint);
            canvas.drawText(weekWeathers.get(i).getLowTemp() + "℃",
                    lowTempX[i],
                    lowTempY[i] + 2 * textPadding,
                    lowTempPaint);

            highTempX[i] = (padding + translateLength / 2 + translateLength * i);
            highTempY[i] = viewHeight - (15 + lowDiff) * tempPadding - tempPadding * diff;
            canvas.drawCircle(highTempX[i],
                    highTempY[i],
                    8,
                    highPointPaint);
            canvas.drawText(weekWeathers.get(i).getHighTemp() + "℃",
                    highTempX[i],
                    highTempY[i] - textPadding,
                    highTempPaint);
        }
        for (int i = 0; i < 6; i++) {
            mPath.reset();
            mPath.moveTo(lowTempX[i] + 8, lowTempY[i]);
            mPath.lineTo(lowTempX[i + 1] - 8, lowTempY[i + 1]);
            canvas.drawPath(mPath, lowLinePaint);
            mPath.rewind();
            mPath.moveTo(highTempX[i] + 8, highTempY[i]);
            mPath.lineTo(highTempX[i + 1] - 8, highTempY[i + 1]);
            canvas.drawPath(mPath, highLinePaint);
        }
    }


//    @SuppressLint("SimpleDateFormat")
//    private void getData() throws Exception {
//        weekWeathers.clear();
//        if(weather == null) return;
//        for (int i = 0; i < WeatherJsonConverter.getWeather(weather).getDaily_forecast().size(); i++) {
//            WeekWeather weekWeather = new WeekWeather();
//            String weatherCond = WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getCond().getTxt_d();
//            weekWeather.setLowTemp(Integer.parseInt(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getTmp().getMin()));
//            lists.add(Integer.parseInt(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getTmp().getMin()));
//            weekWeather.setHighTemp(Integer.parseInt(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getTmp().getMax()));
//            weekWeather.setDate(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(i).getDate());
//            if (weatherCond.contains("/")) {
//                weekWeather.setCond(weatherCond.split("/")[1]);
//            }else{
//                weekWeather.setCond(weatherCond);
//            }
//            weekWeathers.add(weekWeather);
//        }
//        weeks = DateUtil.
//                getNextWeek(new SimpleDateFormat("yyyy-MM-dd").
//                        parse(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(0).getDate()));
//
//    }

    public void setData(List<WeekWeather> weathers,String[] weeks,List<Integer> lists){

        this.weekWeathers = weathers;
        this.weeks = weeks;
        this.lists = lists;
    }

    public void notifyDateChanged(){
        postInvalidate();
        Debug.stopMethodTracing();
    }

}

