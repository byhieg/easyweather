package com.weather.byhieg.easyweather.Activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.Db.LoveCity;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Tools.HandleDaoData;
import com.weather.byhieg.easyweather.Tools.MyJson;
import com.weather.byhieg.easyweather.Tools.WeatherColor;
import com.weather.byhieg.easyweather.View.CardViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class CityManageActivity extends BaseActivity {

    @Bind(R.id.city_toolbar)
    public Toolbar toolbar;
    @Bind(R.id.card_view_group)
    public CardViewGroup cardViewGroup;


    private List<LoveCity> loveCities = new ArrayList<>();
    private TextView cityName;
    private TextView weatherCond;
    private TextView weatherTemp;
    private TextView updateTime;
    private LinearLayout itemCard;
    private TextView wet;


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
        cardViewGroup.removeAllViews();
        for(int i = 0 ;i < 50;i++) {
            try {
                View v = LayoutInflater.from(this).inflate(R.layout.item_city_manage,cardViewGroup,false);
//                LinearLayout cardView = new LinearLayout(this);
//                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                cardView.setLayoutParams(lp);
                itemCard = (LinearLayout) v.findViewById(R.id.item_card);
                cityName = (TextView)v.findViewById(R.id.city_name);
                weatherCond = (TextView)v.findViewById(R.id.weather_cond);
                weatherTemp = (TextView)v.findViewById(R.id.weather);
                updateTime = (TextView) v.findViewById(R.id.updateTime);
                wet = (TextView) v.findViewById(R.id.wet);
                cardViewGroup.addView(v);
                putDataInCard(loveCities.get(0).getCitynName());
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
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.add_city:
                        break;
                }
                return true;
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
//        Map<String, String> params = new HashMap<>();
//        String imageUrl = MyApplication.getWeatherCodeUrl() + MyJson.getWeather(weatherBean).getNow().getCond().getCode() + ".png";
//        LogUtils.e("url",imageUrl);
        String weatherCode = MyJson.getWeather(weatherBean).getNow().getCond().getCode();
//        LogUtils.e("weatherCode", weatherCode);
        GradientDrawable gradientDrawable = (GradientDrawable)itemCard.getBackground();
        gradientDrawable.setColor(WeatherColor.getWeatherColor(weatherCode));
        weatherCond.setText("天气：" + MyJson.getWeather(weatherBean).getNow().getCond().getTxt());
        weatherTemp.setText("气温：" + MyJson.getWeather(weatherBean).getNow().getTmp() + "°");
        updateTime.setText(new SimpleDateFormat("HH:mm").
                format(HandleDaoData.getCityWeather(name).
                        getUpdateTime()));
        wet.setText("湿度: " + MyJson.getWeather(weatherBean).getNow().getHum() + "%");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_city_manage,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
