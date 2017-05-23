package com.weather.byhieg.easyweather.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.example.byhieglibrary.Utils.LogUtils;
import com.weather.byhieg.easyweather.Adapter.CityManageAdapter;
import com.weather.byhieg.easyweather.Bean.CityManageContext;
import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.Fragment.CityFragment;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.tools.HandleDaoData;
import com.weather.byhieg.easyweather.tools.WeatherJsonConverter;
import com.weather.byhieg.easyweather.customview.SlideCutListView;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class CityManageActivity extends BaseActivity {

    @BindView(R.id.city_toolbar)
    public Toolbar toolbar;
    @BindView(R.id.card_view_group)
    public SlideCutListView cardViewGroup;


    static List<LoveCity> loveCities = new ArrayList<>();
    private LocalBroadcastManager localBroadcastManager;



    private static CityManageAdapter adapter;
    private static List<CityManageContext> cityManageContexts = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_city_manage;
    }

    @Override
    public void initData() {
        localBroadcastManager = LocalBroadcastManager.getInstance(CityManageActivity.this);
        setData();
    }

    @Override
    public void initView() {
        toolbar.setTitle("城市管理");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        adapter = new CityManageAdapter(cityManageContexts,this);
        cardViewGroup.setAdapter(adapter);

    }


    @Override
    public void initTheme() {
        if(MyApplication.nightMode()){
            setTheme(R.style.NightTheme);
        }else {
            setTheme(R.style.DayTheme);
        }
    }


    private static void setData(){
        loveCities = null;
        loveCities = HandleDaoData.getLoveCity();
        cityManageContexts.clear();
        for(int i = 0 ;i < loveCities.size();i++) {
            CityManageContext context = new CityManageContext();
            String name = loveCities.get(i).getCitynName();
            try {
                WeatherBean weatherBean = HandleDaoData.getWeatherBean(name);
                if (weatherBean != null) {
                    context.setTime(new SimpleDateFormat("HH:mm").
                            format(HandleDaoData.getCityWeather(name).
                                    getUpdateTime()));

                    context.setTmp(WeatherJsonConverter.getWeather(weatherBean).getNow().getTmp());
                    context.setHum(WeatherJsonConverter.getWeather(weatherBean).getNow().getHum());
                    context.setCond(WeatherJsonConverter.getWeather(weatherBean).getNow().getCond().getTxt());
                    context.setCityName(name);
                    context.setCode(WeatherJsonConverter.getWeather(weatherBean).getNow().getCond().getCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            cityManageContexts.add(context);
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
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        break;
                }
                return true;
            }
        });

        cardViewGroup.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String name = ((CityManageContext) parent.getItemAtPosition(position)).getCityName();
                AlertDialog.Builder dialogBuilder;
                if (MyApplication.nightMode2()){
                    dialogBuilder  = new AlertDialog.Builder(CityManageActivity.this, R.style.NightDialog);
                }else{
                    dialogBuilder = new AlertDialog.Builder(CityManageActivity.this);
                }
                dialogBuilder.setMessage("是否将" + name + "设置为首页城市").
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LogUtils.e("name",name);
                                LogUtils.e("show",HandleDaoData.getShowCity());
                                if(name.equals(HandleDaoData.getShowCity())){
                                    showToast("该城市已经是首页城市");
                                }else{
                                    LoveCity loveCity = HandleDaoData.getLoveCity(1);
                                    HandleDaoData.updateCityOrder(loveCity.getCitynName(),HandleDaoData.getLoveCity().size() + 1);
                                    HandleDaoData.updateCityOrder(name,1);
                                    Intent intent = new Intent("com.weather.byhieg.easyweather.Activity.LOCAL_BROADCAST");
                                    localBroadcastManager.sendBroadcast(intent);
                                }
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                return true;
            }
        });

        cardViewGroup.setRemoveListener(new SlideCutListView.RemoveListener() {
            @Override
            public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
                String name = ((CityManageContext)adapter.getItem(position)).getCityName();
                if (name.equals(HandleDaoData.getShowCity())) {
                    AlertDialog.Builder dialogBuilder;
                    if (MyApplication.nightMode2()){
                        dialogBuilder  = new AlertDialog.Builder(CityManageActivity.this, R.style.NightDialog);
                    }else{
                        dialogBuilder = new AlertDialog.Builder(CityManageActivity.this);
                    }
                    dialogBuilder.setMessage("该城市为首页城市，无法删除，如要删除，请指定另一首页城市").
                            setPositiveButton("恩", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else{
                    HandleDaoData.deleteCity(name);
                    setData();
                    adapter.notifyDataSetChanged();
                }
            }
        });
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
                    setData();
                    adapter.notifyDataSetChanged();
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
