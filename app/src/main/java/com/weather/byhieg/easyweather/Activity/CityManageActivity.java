package com.weather.byhieg.easyweather.Activity;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.example.byhieglibrary.Utils.LogUtils;
import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.Db.LoveCity;
import com.weather.byhieg.easyweather.Fragment.CityFragment;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Tools.HandleDaoData;
import com.weather.byhieg.easyweather.Tools.MyJson;
import com.weather.byhieg.easyweather.Tools.WeatherColor;
import com.weather.byhieg.easyweather.View.CardViewGroup;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class CityManageActivity extends BaseActivity {

    @Bind(R.id.city_toolbar)
    public Toolbar toolbar;


    static List<LoveCity> loveCities = new ArrayList<>();
    static List<LoveCity> addCities = new ArrayList<>();
    static TextView cityName;
    static TextView weatherCond;
    static TextView weatherTemp;
    static TextView updateTime;
    static LinearLayout itemCard;
    static TextView wet;
    static CardViewGroup cardViewGroup;


    @Override
    public int getLayoutId() {
        return R.layout.activity_city_manage;
    }

    @Override
    public void initData() {

        loveCities = HandleDaoData.getLoveCity();

    }

    @Override
    public void initView() {
        toolbar.setTitle("城市管理");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        cardViewGroup = (CardViewGroup) findViewById(R.id.card_view_group);
        updateView();
    }


    @Override
    public void initTheme() {

    }

    private static void updateView() {
        if (cardViewGroup.getChildCount() == 0) {
            for (int i = 0; i < loveCities.size(); i++) {
                LogUtils.e("loveCities", loveCities.get(i).getCitynName());
                try {
                    View v = LayoutInflater.from(MyApplication.getAppContext()).inflate(R.layout.item_city_manage, cardViewGroup, false);
                    itemCard = (LinearLayout) v.findViewById(R.id.item_card);
                    cityName = (TextView) v.findViewById(R.id.city_name);
                    weatherCond = (TextView) v.findViewById(R.id.weather_cond);
                    weatherTemp = (TextView) v.findViewById(R.id.weather);
                    updateTime = (TextView) v.findViewById(R.id.updateTime);
                    wet = (TextView) v.findViewById(R.id.wet);
                    if(putDataInCard(loveCities.get(i).getCitynName())){
                        cardViewGroup.addView(v);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            for (int i = 0; i < addCities.size(); i++) {
                try {
                    View v = LayoutInflater.from(MyApplication.getAppContext()).inflate(R.layout.item_city_manage, cardViewGroup, false);
                    itemCard = (LinearLayout) v.findViewById(R.id.item_card);
                    cityName = (TextView) v.findViewById(R.id.city_name);
                    weatherCond = (TextView) v.findViewById(R.id.weather_cond);
                    weatherTemp = (TextView) v.findViewById(R.id.weather);
                    updateTime = (TextView) v.findViewById(R.id.updateTime);
                    wet = (TextView) v.findViewById(R.id.wet);
                    if(putDataInCard(addCities.get(i).getCitynName())){
                        cardViewGroup.addView(v);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.add_city:
                        startActivity(CityActivity.class);
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 把城市数据放入CardView
     *
     * @param name 城市名字
     * @throws Exception
     */
    @SuppressLint("SimpleDateFormat")
    private static boolean putDataInCard(String name) throws Exception {
        WeatherBean weatherBean = HandleDaoData.getWeatherBean(name);
        if (weatherBean != null) {
            cityName.setText(name);
            String weatherCode = MyJson.getWeather(weatherBean).getNow().getCond().getCode();
            int radius = 30;
            float[] outerR = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
            RoundRectShape roundRectShape = new RoundRectShape(outerR, null, null);
            ShapeDrawable shapeDrawable = new ShapeDrawable(roundRectShape);
            shapeDrawable.getPaint().setColor(ContextCompat.getColor(MyApplication.getAppContext(), WeatherColor.getWeatherColor(weatherCode)));
            shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
            itemCard.setBackground(shapeDrawable);
            weatherCond.setText("天气：" + MyJson.getWeather(weatherBean).getNow().getCond().getTxt());
            weatherTemp.setText("气温：" + MyJson.getWeather(weatherBean).getNow().getTmp() + "°");
            updateTime.setText(new SimpleDateFormat("HH:mm").
                    format(HandleDaoData.getCityWeather(name).
                            getUpdateTime()));
            wet.setText("湿度: " + MyJson.getWeather(weatherBean).getNow().getHum() + "%");
            return true;
        }else{
            return false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_city_manage, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public static class MyHandler extends Handler {

        WeakReference<CityManageActivity> weakReference;

        public MyHandler(CityManageActivity cityManageActivity) {
            weakReference = new WeakReference<>(cityManageActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CityFragment.UPDATE_CITY:
                    addCities = HandleDaoData.getLoveCity();
                    for (LoveCity item : loveCities) {
                        if (addCities.contains(item)) {
                            addCities.remove(item);
                        }
                    }
                    updateView();
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MyApplication.nightMode2()){
            initNightView(R.layout.night_mode_overlay);
        }else {
            removeNightView();
        }
    }
}
