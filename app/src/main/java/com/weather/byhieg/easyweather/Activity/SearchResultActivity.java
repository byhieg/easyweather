package com.weather.byhieg.easyweather.Activity;

<<<<<<< HEAD
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.weather.byhieg.easyweather.R;
=======
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
import com.weather.byhieg.easyweather.Bean.CityContext;
import com.weather.byhieg.easyweather.Db.City;
import com.weather.byhieg.easyweather.Db.LoveCity;
import com.weather.byhieg.easyweather.Db.Province;
import com.weather.byhieg.easyweather.Fragment.CityFragment;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Tools.HandleDaoData;
import com.weather.byhieg.easyweather.Tools.NetTool;

import java.util.ArrayList;
import java.util.List;
>>>>>>> 7e55415be6a31b4803128d39eb37797f18228839

import butterknife.Bind;

public class SearchResultActivity extends BaseActivity {

    @Bind(R.id.result_toolbar)
    public Toolbar toolbar;
<<<<<<< HEAD
=======
    @Bind(R.id.result_list)
    public ListView listView;
    @Bind(R.id.search_result)
    public RelativeLayout mainLayout;
    @Bind(R.id.refresh_bar)
    public RelativeLayout refreshBar;
    @Bind(R.id.refresh)
    public ImageView refresh;

    private Handler handler;
    private CityManageActivity.MyHandler myHandler;


    private List<CityContext> cityList = new ArrayList<>();
    private CityListAdapter adapter;

>>>>>>> 7e55415be6a31b4803128d39eb37797f18228839

    @Override
    public int getLayoutId() {

        return R.layout.activity_search_result;
    }

    @Override
    public void initData() {
<<<<<<< HEAD
=======
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
>>>>>>> 7e55415be6a31b4803128d39eb37797f18228839

    }

    @Override
    public void initView() {
        toolbar.setTitle("搜索结果");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
<<<<<<< HEAD
    }

    @Override
    public void initTheme() {

=======

        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.empty_view));
>>>>>>> 7e55415be6a31b4803128d39eb37797f18228839
    }

    @Override
    public void initEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
<<<<<<< HEAD
    }


=======
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

>>>>>>> 7e55415be6a31b4803128d39eb37797f18228839
}
