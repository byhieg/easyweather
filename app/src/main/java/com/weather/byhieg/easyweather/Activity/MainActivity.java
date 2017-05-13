package com.weather.byhieg.easyweather.Activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClientOption;
import com.example.byhieglibrary.Activity.BaseActivity;
import com.example.byhieglibrary.Utils.DateUtil;
import com.example.byhieglibrary.Utils.DisplayUtil;
import com.example.byhieglibrary.Utils.LogUtils;
import com.weather.byhieg.easyweather.Adapter.DrawerListAdapter;
import com.weather.byhieg.easyweather.Adapter.PopupWindowAdapter;
import com.weather.byhieg.easyweather.Bean.DrawerContext;
import com.weather.byhieg.easyweather.Bean.HoursWeather;
import com.weather.byhieg.easyweather.Bean.WeatherBean;
import com.weather.byhieg.easyweather.Db.LoveCity;
import com.weather.byhieg.easyweather.Interface.MyItemClickListener;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Service.NotificationService;
import com.weather.byhieg.easyweather.Tools.Constants;
import com.weather.byhieg.easyweather.Tools.HandleDaoData;
import com.weather.byhieg.easyweather.Tools.MyJson;
import com.weather.byhieg.easyweather.Tools.MyLocationListener;
import com.weather.byhieg.easyweather.Tools.NetTool;
import com.weather.byhieg.easyweather.Tools.WeatherIcon;
import com.weather.byhieg.easyweather.View.WeekWeatherView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;

import static com.example.byhieglibrary.Utils.DisplayUtil.getViewHeight;
import static com.weather.byhieg.easyweather.R.id.swipe_refresh;

public class MainActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, ActivityCompat.OnRequestPermissionsResultCallback {


    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    @Bind(R.id.recyclerview)
    public RecyclerView recyclerView;
//    @Bind(R.id.view_spot)
//    public ViewStub viewStub;
//    @Bind(R.id.arrow)
//    public ImageView arrow;
    @Bind(R.id.arrow_detail)
    public ImageView arrowDetail;
    @Bind(R.id.expand_view)
    public LinearLayout expandView;
    @Bind(R.id.detail)
    public LinearLayout detail;
    @Bind(R.id.date)
    public TextView date;
    @Bind(R.id.temp)
    public TextView temp;
    @Bind(R.id.tempImage)
    public ImageView tempImage;
    @Bind(R.id.tempHigh)
    public TextView tempHigh;
    @Bind(R.id.tempLow)
    public TextView tempLow;
    @Bind(R.id.cloth)
    public TextView cloth;
    @Bind(R.id.pm)
    public TextView pm;
    @Bind(R.id.hum)
    public TextView hum;
    @Bind(R.id.wind)
    public TextView wind;
    @Bind(R.id.wind_dir)
    public TextView windDir;
    @Bind(R.id.to_detail)
    public TextView toDetail;
    @Bind(R.id.qlty)
    public TextView qlty;
    @Bind(R.id.vis)
    public TextView vis;
    @Bind(R.id.pres)
    public TextView pres;
    @Bind(R.id.uv)
    public TextView uv;
    @Bind(R.id.sunrise)
    public TextView sunrise;
    @Bind(R.id.sunset)
    public TextView sunset;
    @Bind(R.id.condition)
    public TextView condition;
    @Bind(R.id.scrollView)
    public ScrollView scrollView;
    @Bind(R.id.refresh)
    public ImageView refresh;
    @Bind(R.id.main_layout)
    public LinearLayout mainLayout;
    @Bind(swipe_refresh)
    public SwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.updateTime)
    public TextView updateTime;
    @Bind(R.id.cloth_brf)
    public TextView clothBrf;
    @Bind(R.id.cloth_txt)
    public TextView clothTxt;
    @Bind(R.id.sport_brf)
    public TextView sportBrf;
    @Bind(R.id.sport_txt)
    public TextView sportTxt;
    @Bind(R.id.action_bar)
    public LinearLayout action_bar;
    @Bind(R.id.cold_brf)
    public TextView codeBrf;
    @Bind(R.id.cold_txt)
    public TextView coldTxt;
    @Bind(R.id.week_Weather_view)
    public WeekWeatherView weekWeatherView;
    @Bind(R.id.weather_cond)
    public TextView weatherCond;
    @Bind(R.id.update_time_hours)
    public TextView updateHours;
    @Bind(R.id.wind_hours)
    public TextView windHours;
    @Bind(R.id.weather_tmp)
    public TextView weatherTmp;
    @Bind(R.id.item_future)
    public LinearLayout itemFuture;
    @Bind(R.id.more)
    public TextView more;


    public static final int COMPLETE_REFRESH = 0x100;
    public static final int FAILURE_REFRESH = 0x101;
    private DrawerListAdapter drawerListAdapter;
    private ArrayList<DrawerContext> drawerList = new ArrayList<>();
    private WeatherBean weatherBean;
    private int[] rotateCount = {0, 0};
    private List<HoursWeather> hoursWeathers = new ArrayList<>();
    private NetworkChangeReceiver networkChangeReceiver;
    private MyHandler handler = new MyHandler();
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private BDLocationListener myListener;
    public Button button;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        button = new Button(this);
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
        try {
            weatherBean = HandleDaoData.getWeatherBean(HandleDaoData.getShowCity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        myListener = new MyLocationListener(this);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        intentFilter.addAction("com.weather.byhieg.easyweather.Activity.LOCAL_BROADCAST");
        localReceiver = new LocalReceiver();
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
        getHoursData();
        MyApplication.getmLocationClient().registerLocationListener(myListener);
        initLocation();
    }

    @Override
    public void initView() {
        generateTextView();
//        lineChart.invalidate();
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

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        if (weatherBean != null) {
            try {
                updateView(weatherBean);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            doRefreshInNoData();
        }


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
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.feedback:
                        AlertDialog.Builder dialogBuilder;
                        if (MyApplication.nightMode2()){
                           dialogBuilder  = new AlertDialog.Builder(MainActivity.this, R.style.NightDialog);
                        }else{
                            dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        }
                       dialogBuilder.setTitle("反馈").setMessage("在使用过程中，有任何问题均可以发送到邮箱：byhieg@gmail.com").setPositiveButton("恩", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                        break;

                    case R.id.location:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED||
                                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                                    checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                LogUtils.e("Permissions","还是没有权限啊");
                                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_WIFI_STATE,
                                        Manifest.permission.ACCESS_NETWORK_STATE,
                                        Manifest.permission.CHANGE_WIFI_STATE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, Constants.PERMISSION);
                            }else{
                                MyApplication.getmLocationClient().start();
                                LogUtils.e("Permissions","已经有权限了");
                            }
                        }else{
                            MyApplication.getmLocationClient().start();
                        }

                        break;

                    case R.id.like:
                        startActivity(LoveAppActivity.class);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        break;
                    case R.id.add_city:
                        startActivity(CityManageActivity.class);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        break;
                }
                return true;
            }
        });

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rotateCount[1] % 2 == 0) {
                    expandView.setVisibility(View.VISIBLE);
                    toDetail.setText("简略");
                    Animation animation = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(10);
                    animation.setFillAfter(true);
                    arrowDetail.startAnimation(animation);
                    action_bar.setVisibility(View.GONE);

                } else {
                    expandView.setVisibility(View.GONE);
                    toDetail.setText("详情");
                    Animation animation = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(10);
                    animation.setFillAfter(true);
                    arrowDetail.startAnimation(animation);
                    action_bar.setVisibility(View.VISIBLE);
                }
                rotateCount[1]++;
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doRefreshInNoData();
            }
        });

        drawerListAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {

                Intent intent = new Intent(getApplicationContext(), SlideMenuActivity.class);
                intent.putExtra("itemId", postion);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);

            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("button","被点击");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 将天气数据放入视图中
     *
     * @param weatherBean 天气的数据
     */
    @SuppressLint("SimpleDateFormat")
    private void updateView(WeatherBean weatherBean) throws ParseException {
        if(weatherBean == null) return;
        mSwipeLayout.setVisibility(View.VISIBLE);
        refresh.clearAnimation();
        refresh.setVisibility(View.GONE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        date.setText("今天" + simpleDateFormat.format(new Date()));
        weekWeatherView.notifyDateChanged();
        Date sqlDate = HandleDaoData.getCityWeather(HandleDaoData.getShowCity()).getUpdateTime();
        long time = DateUtil.getDifferenceofDate(new Date(), sqlDate) / (1000 * 60);
        if (time > 1000 * 60 * 60 || time < 0) {
            updateTime.setText("最近更新：" + new SimpleDateFormat("MM-dd HH:mm:ss").format(sqlDate));
        } else {
            updateTime.setText("最近更新：" + time + "分钟之前");
        }
        LogUtils.e("code",MyJson.getWeather(weatherBean).getNow().getCond().getCode());
        tempImage.setImageResource(WeatherIcon.getWeatherImage(MyJson.getWeather(weatherBean).getNow().getCond().getCode()));
        toolbar.setTitle(MyJson.getWeather(weatherBean).getBasic().getCity());
        //主卡片
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

        //穿衣指数
        clothBrf.setText(MyJson.getWeather(weatherBean).getSuggestion().getDrsg().getBrf());
        clothTxt.setText(MyJson.getWeather(weatherBean).getSuggestion().getDrsg().getTxt());

        //运动指数
        sportBrf.setText(MyJson.getWeather(weatherBean).getSuggestion().getSport().getBrf());
        sportTxt.setText(MyJson.getWeather(weatherBean).getSuggestion().getSport().getTxt());

        //感冒指数
        codeBrf.setText(MyJson.getWeather(weatherBean).getSuggestion().getFlu().getBrf());
        coldTxt.setText(MyJson.getWeather(weatherBean).getSuggestion().getFlu().getTxt());

        //未来三小时天气
        if (MyJson.getWeather(weatherBean).getHourly_forecast().size() != 0) {
            weatherCond.setText(MyJson.getWeather(weatherBean).getHourly_forecast().get(0).getPop() + "%");
            updateHours.setText(MyJson.getWeather(weatherBean).getHourly_forecast().get(0).getDate());
            windHours.setText(MyJson.getWeather(weatherBean).getHourly_forecast().get(0).getWind().getDir() + " " +
                    MyJson.getWeather(weatherBean).getHourly_forecast().get(0).getWind().getSc());
            weatherTmp.setText(MyJson.getWeather(weatherBean).getHourly_forecast().get(0).getTmp() + "°");
        } else {
            itemFuture.setVisibility(View.GONE);
        }

    }

    /**
     * 没有数据时 刷新
     */

    private void doRefreshInNoData() {
        mSwipeLayout.setVisibility(View.GONE);
        refresh.setVisibility(View.VISIBLE);
        Animation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatCount(-1);
        animation.setInterpolator(new LinearInterpolator());
        refresh.startAnimation(animation);
        showToast("从网络获取天气信息失败");
        new MyAsyncTask().execute(HandleDaoData.getShowCity());
    }

    @Override
    public void onRefresh() {
        final List<LoveCity> cityList = HandleDaoData.getLoveCity();
        Thread[] threads = new Thread[cityList.size()];
        for (int i = 0; i < cityList.size(); i++) {
            final int index = i;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        NetTool.doNetWeather(cityList.get(index).getCitynName());
                    } catch (Exception e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(FAILURE_REFRESH);
                    }

                }
            });
            threads[i].start();
            try {
                threads[i].join();
            } catch (InterruptedException e) {

                LogUtils.e("线程异常!!!", getClass().getSimpleName());
            }
        }
        handler.sendEmptyMessage(COMPLETE_REFRESH);

    }


    class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String city) {
            if (HandleDaoData.isExistInCityWeather(city)) {
                try {
                    updateView(HandleDaoData.getWeatherBean(HandleDaoData.getShowCity()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                setNetWork();
            }

        }

        @Override
        protected String doInBackground(String[] params) {
            try {
                NetTool.doNetWeather(HandleDaoData.getShowCity());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return HandleDaoData.getShowCity();
        }
    }

    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                new MyAsyncTask().execute(HandleDaoData.getShowCity());
            }
        }
    }

    @SuppressLint("HandlerLeak")
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case COMPLETE_REFRESH:
                    mSwipeLayout.setRefreshing(false);
                    scrollView.setVisibility(View.VISIBLE);
                    refresh.clearAnimation();
                    refresh.setVisibility(View.GONE);
                    try {
                        getHoursData();
                        updateView(HandleDaoData.getWeatherBean(HandleDaoData.getShowCity()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startService(new Intent(MainActivity.this, NotificationService.class));
                    break;

                case FAILURE_REFRESH:
                    mSwipeLayout.setRefreshing(false);
                    setNetWork();
                    break;

            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    private void setNetWork() {
        Snackbar.make(mainLayout, "还是没有网络 QAQ", Snackbar.LENGTH_LONG).
                setAction("点我设置网络", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                }).show();
    }

    public void generateTextView() {
        TextView textView = new TextView(this);
        textView.setText("天气易变，注意天气变化");
        View[] view = {findViewById(R.id.toolbar), findViewById(R.id.view), findViewById(R.id.item_cloths), findViewById(R.id.item_sports)};
        int totalHeight = 0;
        for (View aView : view) {
            totalHeight += getViewHeight(aView, true) + DisplayUtil.dip2px(this, 10);
        }
        int pxHeight = getmScreenHeight() - totalHeight;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pxHeight / 2);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        textView.setLayoutParams(lp);
        action_bar.addView(textView);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (scrollView != null) {
            scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (mSwipeLayout != null) {
                        mSwipeLayout.setEnabled(scrollView.getScrollY() == 0);
                    }
                }
            });
        }
        if (MyApplication.nightMode2()) {
            initNightView(R.layout.night_mode_overlay);
        } else {
            removeNightView();
        }
    }


    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        MyApplication.getmLocationClient().setLocOption(option);
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.item_popupwindow, null);
        LinearLayout del = (LinearLayout) contentView.findViewById(R.id.del);
        ListView listView = (ListView) contentView.findViewById(R.id.popup_listview);
        PopupWindowAdapter adapter = new PopupWindowAdapter(hoursWeathers, this);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        final PopupWindow popupWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(this, R.color.transparent));
        popupWindow.showAsDropDown(toolbar, 0, 0);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
    }

    private void getHoursData() {
        hoursWeathers.clear();
        if(weatherBean == null) return;
        try {
            weatherBean = HandleDaoData.getWeatherBean(HandleDaoData.getShowCity());
            for (int i = 0; i < MyJson.getWeather(weatherBean).getHourly_forecast().size(); i++) {
                HoursWeather hw = new HoursWeather();
                hw.setTmp(MyJson.getWeather(weatherBean).getHourly_forecast().get(i).getTmp() + "°");
                hw.setHum(MyJson.getWeather(weatherBean).getHourly_forecast().get(i).getHum() + "%");
                hw.setWind_class(MyJson.getWeather(weatherBean).getHourly_forecast().get(i).getWind().getSc());
                hw.setWind_deg(MyJson.getWeather(weatherBean).getHourly_forecast().get(i).getWind().getDeg());
                hw.setWind_speed(MyJson.getWeather(weatherBean).getHourly_forecast().get(i).getWind().getSpd());
                hw.setWind_dir(MyJson.getWeather(weatherBean).getHourly_forecast().get(i).getWind().getDir());
                hw.setPop(MyJson.getWeather(weatherBean).getHourly_forecast().get(i).getPop() + "%");
                hw.setUpdate(MyJson.getWeather(weatherBean).getHourly_forecast().get(i).getDate());
                hoursWeathers.add(hw);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,grantResults);
        switch(requestCode)
        {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case Constants.PERMISSION:
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                MyApplication.getmLocationClient().start();
            }
            else
            {
                // 没有获取到权限，做特殊处理
                showToast("没有权限，定位失败");
            }
            break;
            default:
                break;
        }
    }


    class LocalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            getHoursData();
            try {
                updateView(HandleDaoData.getWeatherBean(HandleDaoData.getShowCity()));
                startService(new Intent(MainActivity.this, NotificationService.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}
