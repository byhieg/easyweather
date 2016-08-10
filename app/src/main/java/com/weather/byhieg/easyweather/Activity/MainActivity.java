package com.weather.byhieg.easyweather.Activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.weather.byhieg.easyweather.Adapter.DrawerListAdapter;
import com.weather.byhieg.easyweather.Bean.DrawerContext;
import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Tools.HandleDaoData;
import com.weather.byhieg.easyweather.Tools.MyJson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;

public class MainActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    @Bind(R.id.recyclerview)
    public RecyclerView recyclerView;
    @Bind(R.id.view_spot)
    public ViewStub viewStub;
    @Bind(R.id.arrow)
    public ImageView arrow;
    @Bind(R.id.arrow_detail)
    public ImageView arrowDetail;
    @Bind(R.id.expand_view)
    public LinearLayout expandView;
    @Bind(R.id.detail)
    public LinearLayout detail;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.updateTime)
    TextView updateTime;
    @Bind(R.id.temp)
    TextView temp;
    @Bind(R.id.tempImage)
    ImageView tempImage;
    @Bind(R.id.tempHigh)
    TextView tempHigh;
    @Bind(R.id.tempLow)
    TextView tempLow;
    @Bind(R.id.cloth)
    TextView cloth;
    @Bind(R.id.pm)
    TextView pm;
    @Bind(R.id.hum)
    TextView hum;
    @Bind(R.id.wind)
    TextView wind;
    @Bind(R.id.wind_dir)
    TextView windDir;
    @Bind(R.id.to_detail)
    TextView toDetail;
    @Bind(R.id.qlty)
    TextView qlty;
    @Bind(R.id.vis)
    TextView vis;
    @Bind(R.id.pres)
    TextView pres;
    @Bind(R.id.uv)
    TextView uv;
    @Bind(R.id.sunrise)
    TextView sunrise;
    @Bind(R.id.sunset)
    TextView sunset;
    @Bind(R.id.condition)
    TextView condition;
    @Bind(R.id.scrollView)
    ScrollView scrollView;


    private DrawerListAdapter drawerListAdapter;
    private ArrayList<DrawerContext> drawerList = new ArrayList<>();
    private WeatherBean weatherBean;
    private int[] rotateCount = {0, 0};
    private View convertView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        int[] images = {R.mipmap.ic_trending_up_black_24dp,
                R.mipmap.ic_settings_black_24dp,
                R.mipmap.ic_share_black_24dp,
                R.mipmap.ic_help_black_24dp,
                R.mipmap.ic_hourglass_empty_black_24dp,
                R.mipmap.ic_wikipedia,
                R.mipmap.ic_more_black_24dp};

        int[] names = {R.string.future,
                R.string.setting,
                R.string.share,
                R.string.help,
                R.string.laboratory,
                R.string.wiki,
                R.string.about};

        for (int i = 0; i < 7; i++) {
            DrawerContext drawerContext = new DrawerContext();
            drawerContext.setImage(images[i]);
            drawerContext.setName(names[i]);
            drawerList.add(drawerContext);
        }
        drawerListAdapter = new DrawerListAdapter(drawerList);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        date.setText("今天" + simpleDateFormat.format(new Date()));

        try {
            weatherBean = HandleDaoData.getWeatherBean(HandleDaoData.getShowCity());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initView() {
        toolbar.setTitle("成都");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        mDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mDrawerToggle);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(drawerListAdapter);

        if (weatherBean != null) {
            temp.setText(MyJson.getWeather(weatherBean).getNow().getTmp() + "°");
            tempHigh.setText("高 " + MyJson.getWeather(weatherBean).getDaily_forecast().get(0).getTmp().getMax() + "°");
            tempLow.setText("低 " + MyJson.getWeather(weatherBean).getDaily_forecast().get(0).getTmp().getMin() + "°");
            cloth.setText(MyJson.getWeather(weatherBean).getSuggestion().getDrsg().getBrf());
            condition.setText(MyJson.getWeather(weatherBean).getNow().getCond().getTxt());
            pm.setText(MyJson.getWeather(weatherBean).getAqi().getCity().getPm25());
            hum.setText(MyJson.getWeather(weatherBean).getNow().getHum() + "%");
            wind.setText(MyJson.getWeather(weatherBean).getNow().getWind().getSpd() + "km/h");
            windDir.setText(MyJson.getWeather(weatherBean).getNow().getWind().getDir());
            qlty.setText(MyJson.getWeather(weatherBean).getAqi().getCity().getQlty());
            vis.setText(MyJson.getWeather(weatherBean).getNow().getVis() + "km");
            pres.setText(MyJson.getWeather(weatherBean).getNow().getPres() + "帕");
            uv.setText(MyJson.getWeather(weatherBean).getSuggestion().getUv().getBrf());
            sunrise.setText(MyJson.getWeather(weatherBean).getDaily_forecast().get(0).getAstro().getSr());
            sunset.setText(MyJson.getWeather(weatherBean).getDaily_forecast().get(0).getAstro().getSs());
        } else {
            scrollView.setVisibility(View.GONE);
        }

    }

    @Override
    public void initEvent() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.feedback:
                        break;

                    case R.id.location:
                        break;

                    case R.id.like:
                        break;
                }
                return true;
            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rotateCount[0] % 2 == 0) {
                    Animation animation = new RotateAnimation(0, 180, 50, 50);
                    animation.setDuration(10);
                    animation.setFillAfter(true);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (convertView == null) {
                                convertView = viewStub.inflate();
                                ListView listView = (ListView) convertView.findViewById(R.id.view_spot_list);
                                listView.setEmptyView(findViewById(R.id.empty_view));
                            }
                            convertView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    arrow.startAnimation(animation);
                } else {
                    Animation animation = new RotateAnimation(180, 360, 50, 50);
                    animation.setDuration(10);
                    animation.setFillAfter(true);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            viewStub.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    arrow.startAnimation(animation);
                }
                rotateCount[0]++;
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rotateCount[1] % 2 == 0) {
                    expandView.setVisibility(View.VISIBLE);
                    toDetail.setText("简略");
                    Animation animation = new RotateAnimation(0, 180, 50, 50);
                    animation.setDuration(10);
                    animation.setFillAfter(true);
                    arrowDetail.startAnimation(animation);

                } else {
                    expandView.setVisibility(View.GONE);
                    toDetail.setText("详情");
                    Animation animation = new RotateAnimation(180, 360, 50, 50);
                    animation.setDuration(10);
                    animation.setFillAfter(true);
                    arrowDetail.startAnimation(animation);
                }
                rotateCount[1]++;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
