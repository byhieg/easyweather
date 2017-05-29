package com.weather.byhieg.easyweather.Activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.example.byhieglibrary.Utils.LogUtils;
import com.weather.byhieg.easyweather.Adapter.CityListAdapter;
import com.weather.byhieg.easyweather.data.bean.CityContext;
import com.weather.byhieg.easyweather.Fragment.CityFragment;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.citymanage.CityManageActivity;
import com.weather.byhieg.easyweather.tools.NetTool;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.result_toolbar)
    public Toolbar toolbar;
    @BindView(R.id.result_list)
    public ListView listView;
    @BindView(R.id.search_result)
    public RelativeLayout mainLayout;
    @BindView(R.id.refresh_bar)
    public RelativeLayout refreshBar;
    @BindView(R.id.refresh)
    public ImageView refresh;

    private Handler handler;
    private CityManageActivity.MyHandler myHandler;


    private List<CityContext> cityList = new ArrayList<>();
    private CityListAdapter adapter;


    @Override
    public int getLayoutId() {

        return R.layout.activity_search_result;
    }

    @Override
    public void initData() {
        handler = new Handler();
        myHandler = new CityManageActivity.MyHandler(new CityManageActivity());

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            List<City> cities = HandleDaoData.getCities(query);
            LogUtils.e("city",cities.size() + "");
            List<Province> provinces = HandleDaoData.getProvince(query);
            LogUtils.e("province",provinces.size() + "");
            for(int i = 0 ;i < cities.size();i++) {
                CityContext context = new CityContext();
                context.setCityName(cities.get(i).getCitynName());
                cityList.add(context);
            }
            for(int i = 0 ;i < provinces.size();i++) {
                CityContext context = new CityContext();
                context.setCityName(provinces.get(i).getProvinceName());
                if(!isInCityList(context.getCityName())){
                    cityList.add(context);
                }
            }
        }
        LogUtils.e("SearchResult",cityList.size() + "");
        adapter = new CityListAdapter(cityList,this);

    }

    @Override
    public void initView() {
        toolbar.setTitle("搜索结果");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.empty_view));
    }

    @Override
    public void initTheme() {
        if(MyApplication.nightMode()){
            setTheme(R.style.NightTheme);
        }else {
            setTheme(R.style.DayTheme);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String cityName = ((CityContext)parent.getItemAtPosition(position)).getCityName();
                int order = HandleDaoData.getLoveCity().size();
                if(!HandleDaoData.isExistInLoveCity(cityName)){
                    LoveCity loveCity = new LoveCity();
                    loveCity.setCitynName(cityName);
                    loveCity.setOrder(++order);
                    HandleDaoData.insertLoveCity(loveCity);
                    Snackbar.make(mainLayout,"添加城市成功",Snackbar.LENGTH_LONG).
                            setAction("点我撤销", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HandleDaoData.deleteCity(cityName);
                                    refreshBar.setVisibility(View.GONE);
                                }
                            }).show();

                    refreshBar.setVisibility(View.VISIBLE);
                    Animation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(1000);
                    animation.setRepeatCount(-1);
                    animation.setInterpolator(new LinearInterpolator());
                    refresh.startAnimation(animation);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                NetTool.doNetWeather(cityName);
                                Thread.sleep(3000);
                                myHandler.sendEmptyMessage(CityFragment.UPDATE_CITY);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        refreshBar.setVisibility(View.GONE);
                                    }
                                });
                            } catch (Exception e) {
                                Snackbar.make(mainLayout, "没有网络 QAQ", Snackbar.LENGTH_LONG).
                                        setAction("点我设置网络", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                                startActivity(intent);
                                            }
                                        }).show();

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        refreshBar.setVisibility(View.GONE);
                                    }
                                });
                            }

                        }
                    }).start();
                }else{
                    Snackbar.make(mainLayout,"该城市已经添加，你忘记了？",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean isInCityList(String name){
        List<String> str = new ArrayList<>();
        for(int i = 0 ;i < cityList.size();i++) {
            str.add(cityList.get(i).getCityName());
        }
        return str.contains(name);
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
