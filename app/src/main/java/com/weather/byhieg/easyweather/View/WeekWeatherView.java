package com.weather.byhieg.easyweather.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.byhieglibrary.Utils.IntegerUtils;
import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.Bean.WeekWeather;
import com.weather.byhieg.easyweather.Tools.HandleDaoData;
import com.weather.byhieg.easyweather.Tools.MyJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiqifeng on 2016/9/21.
 * Mail:byhieg@gmail.com
 */

public class WeekWeatherView extends View {

    private Paint baseLinePaint;
    private Paint dashLinePaint;
    private Paint pointPaint;
    private Paint bezierPaint;
    private Path mPath;
    private WeatherBean weatherBean;
    private List<Integer> lists = new ArrayList<>();
    private List<WeekWeather> weekWeathers = new ArrayList<>();

    private int viewHeight,viewWidth;
    private int padding = 20;

    public WeekWeatherView(Context context) {
        super(context);
        init();
    }

    public WeekWeatherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeekWeatherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        try {
            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        baseLinePaint = new Paint();
        baseLinePaint.setColor(Color.BLACK);
        baseLinePaint.setAntiAlias(true);
        baseLinePaint.setStyle(Paint.Style.STROKE);
        baseLinePaint.setStrokeWidth(10);

        dashLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dashLinePaint.setStyle(Paint.Style.STROKE);
        dashLinePaint.setColor(Color.GRAY);
        dashLinePaint.setAntiAlias(true);
        dashLinePaint.setStrokeWidth(2);
//        PathEffect effect = new DashPathEffect(new float[] {1,1}, 0);
//        dashLinePaint.setPathEffect(effect);

        pointPaint = new Paint();
        pointPaint.setColor(Color.BLUE);
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.STROKE);
        pointPaint.setStrokeWidth(5);

        bezierPaint = new Paint();
        bezierPaint.setColor(Color.RED);
        bezierPaint.setStrokeWidth(10);
        bezierPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int result = 0;
        //通过MeasureSpec.getMode与getSize方法获取宽和高的测量方式与测量大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //保存最后的测量值，优化代码的话可以不用这个变量的。
        int width = 0,height = 0;
        //对测量模式进行判断，如果是EXACTLY的话则最后的测量值就是系统帮我们测量的结果。
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        }else{
            //如果是UNSPECIFIED 则使用我们的默认值作为最后的测量值
            result = 300;
            //如果是AT_MOST 则就要用系统测量结果与我们默认结果取最小值来决定最后的测量结果
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(result,widthSize);
            }
        }
        //高度和宽度的过程是一致的。
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        }else {
            result = 300;
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(result, heightSize);
            }
        }
        //把我们最后的宽和高设置进去
        viewHeight = height;
        viewWidth = width;
        setMeasuredDimension(width,height);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(padding,viewHeight - padding,viewWidth - padding,viewHeight - padding, baseLinePaint);
        int translateLength = (viewWidth - 2 * padding) / 7;
        for(int i = 0 ;i < 6 ; i++) {
            canvas.save();
            canvas.translate(translateLength * (i + 1),0);
            canvas.drawLine(padding,viewHeight - padding,padding,0,dashLinePaint);
            canvas.restore();
        }
        int lowestTemp = IntegerUtils.getSmallestNum(lists);
//        int highestTemp = 32;
        float []lowTempX = new float[7];
        float []lowTempY  = new float[7];
        float []highTempX = new float[7];
        float[] highTempY = new float[7];
        Log.e("size", weekWeathers.size() + "");
        for(int i = 0 ;i < weekWeathers.size();i++) {
            int lowDiff = weekWeathers.get(i).getLowTemp() - lowestTemp;
            int diff = weekWeathers.get(i).getHighTemp() - weekWeathers.get(i).getLowTemp();
            canvas.drawCircle(padding + translateLength / 2 + translateLength * i,viewHeight - (10 + lowDiff) * padding,5,pointPaint);
            lowTempX[i] = padding + translateLength / 2 + translateLength * i;
            lowTempY[i] = viewHeight - (10 + lowDiff) * padding;
            canvas.drawCircle((padding + translateLength / 2 + translateLength * i),viewHeight - (10  + lowDiff) * padding - padding * diff ,5,pointPaint);
            highTempX[i] = (padding + translateLength / 2 + translateLength * i);
            highTempY[i] = viewHeight - (10  + lowDiff) * padding - padding * diff;
        }

        for(int i = 0 ; i < 6 ;i++) {
            mPath.reset();
            mPath.moveTo(lowTempX[i],lowTempY[i]);
            mPath.quadTo((lowTempX[i] + lowTempX[i + 1]) /2 , (lowTempY[i] + lowTempY[i + 1]) / 2 - padding * 2,lowTempX[i + 1],lowTempY[i + 1]);
            mPath.rewind();
            mPath.moveTo(highTempX[i],highTempY[i]);
            mPath.quadTo((highTempX[i] + highTempX[i + 1]) /2 , (highTempY[i] + highTempY[i + 1]) / 2 - padding * 2,highTempX[i + 1],highTempY[i + 1]);
            canvas.drawPath(mPath,bezierPaint);
        }

    }


    private void getData() throws Exception {
        weatherBean = HandleDaoData.getWeatherBean(HandleDaoData.getShowCity());
        for(int i = 0 ; i < MyJson.getWeather(weatherBean).getDaily_forecast().size();i++) {
            WeekWeather weekWeather = new WeekWeather();
            weekWeather.setLowTemp(Integer.parseInt(MyJson.getWeather(weatherBean).getDaily_forecast().get(i).getTmp().getMin()));
            lists.add(Integer.parseInt(MyJson.getWeather(weatherBean).getDaily_forecast().get(i).getTmp().getMin()));
            weekWeather.setHighTemp(Integer.parseInt(MyJson.getWeather(weatherBean).getDaily_forecast().get(i).getTmp().getMax()));
            weekWeather.setDate(MyJson.getWeather(weatherBean).getDaily_forecast().get(i).getDate());
            weekWeather.setCond(MyJson.getWeather(weatherBean).getDaily_forecast().get(i).getCond().getTxt_d());
            weekWeathers.add(weekWeather);
        }
    }


}

