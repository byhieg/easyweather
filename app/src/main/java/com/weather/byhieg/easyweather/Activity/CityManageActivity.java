package com.weather.byhieg.easyweather.Activity;

import android.annotation.SuppressLint;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.byhieglibrary.Activity.BaseActivity;
import com.example.byhieglibrary.Utils.LogUtils;
import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.Db.LoveCity;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Tools.HandleDaoData;
import com.weather.byhieg.easyweather.Tools.MyJson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

public class CityManageActivity extends BaseActivity {

    @Bind(R.id.city_toolbar)
    public Toolbar toolbar;
    @Bind(R.id.city_name)
    public TextView cityName;
    @Bind(R.id.weather_icon)
    public ImageView weatherIcon;
    @Bind(R.id.weather)
    public TextView weatherTemp;
    @Bind(R.id.updateTime)
    public TextView updateTime;


    private List<LoveCity> loveCities = new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.activity_city_manage;
    }

    @Override
    public void initData() {
        toolbar.setTitle("城市管理");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loveCities = HandleDaoData.getLoveCity();

    }

    @Override
    public void initView() {
        //// TODO: 2016/9/12 根据返回的城市列表，查询天气，放入Card
        for(int i = 0 ;i < loveCities.size();i++) {
            try {
                putDataInCard(loveCities.get(i).getCitynName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 把城市数据放入CardView
     * @param name  城市名字
     * @throws Exception
     */
    @SuppressLint("SimpleDateFormat")
    private void putDataInCard(String name) throws Exception {
        WeatherBean weatherBean = HandleDaoData.getWeatherBean(name);
        cityName.setText(name);
        Map<String, String> params = new HashMap<>();
        String imageUrl = MyApplication.getWeatherCodeUrl() + MyJson.getWeather(weatherBean).getNow().getCond().getCode() + ".png";
        LogUtils.e("url",imageUrl);
        Glide.with(this).
                load(imageUrl).
                into(weatherIcon);

        weatherTemp.setText(MyJson.getWeather(weatherBean).getNow().getTmp());
        updateTime.setText(new SimpleDateFormat("MM-dd hh:mm:ss").
                format(HandleDaoData.getCityWeather(name).
                        getUpdateTime()));
    }


}
