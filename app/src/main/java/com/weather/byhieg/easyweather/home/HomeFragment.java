package com.weather.byhieg.easyweather.home;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.base.BaseFragment;
import com.weather.byhieg.easyweather.data.bean.WeekWeather;
import com.weather.byhieg.easyweather.data.source.CityDataSource;
import com.weather.byhieg.easyweather.data.source.local.entity.LoveCityEntity;
import com.weather.byhieg.easyweather.tools.DateUtil;
import com.weather.byhieg.easyweather.tools.DisplayUtil;
import com.weather.byhieg.easyweather.tools.ImageUtils;
import com.weather.byhieg.easyweather.tools.LogUtils;
import com.weather.byhieg.easyweather.home.adapter.PopupWindowAdapter;
import com.weather.byhieg.easyweather.data.bean.HoursWeather;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.customview.WeekWeatherView;
import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.tools.MessageEvent;
import com.weather.byhieg.easyweather.tools.ScreenUtil;
import com.weather.byhieg.easyweather.tools.WeatherIcon;
import com.weather.byhieg.easyweather.tools.WeatherJsonConverter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.weather.byhieg.easyweather.tools.Constants.UPDATE_SHOW_CITY;
import static com.weather.byhieg.easyweather.tools.Constants.UPDATE_WEATHER;
import static com.weather.byhieg.easyweather.tools.DisplayUtil.getViewHeight;
import static com.weather.byhieg.easyweather.tools.ImageUtils.BRIEF;
import static com.weather.byhieg.easyweather.tools.Knife.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeContract.View, SwipeRefreshLayout
        .OnRefreshListener {


//    @BindView(R.id.arrow)
//    public ImageView arrow;
    @BindView(R.id.arrow_detail)
    public ImageView arrowDetail;
    @BindView(R.id.expand_view)
    public LinearLayout expandView;
    @BindView(R.id.detail)
    public LinearLayout detail;
    @BindView(R.id.date)
    public TextView date;
    @BindView(R.id.temp)
    public TextView temp;
    @BindView(R.id.tempImage)
    public ImageView tempImage;
    @BindView(R.id.tempHigh)
    public TextView tempHigh;
    @BindView(R.id.tempLow)
    public TextView tempLow;
    @BindView(R.id.cloth)
    public TextView cloth;
    @BindView(R.id.pm)
    public TextView pm;
    @BindView(R.id.hum)
    public TextView hum;
    @BindView(R.id.wind)
    public TextView wind;
    @BindView(R.id.wind_dir)
    public TextView windDir;
    @BindView(R.id.to_detail)
    public TextView toDetail;
    @BindView(R.id.qlty)
    public TextView qlty;
    @BindView(R.id.vis)
    public TextView vis;
    @BindView(R.id.pres)
    public TextView pres;
    @BindView(R.id.uv)
    public TextView uv;
    @BindView(R.id.sunrise)
    public TextView sunrise;
    @BindView(R.id.sunset)
    public TextView sunset;
    @BindView(R.id.condition)
    public TextView condition;
    @BindView(R.id.scrollView)
    public ScrollView scrollView;
    @BindView(R.id.refresh)
    public ImageView refresh;
    @BindView(R.id.swipe_refresh)
    public SwipeRefreshLayout mSwipeLayout;
    @BindView(R.id.updateTime)
    public TextView updateTime;
    @BindView(R.id.cloth_brf)
    public TextView clothBrf;
    @BindView(R.id.cloth_txt)
    public TextView clothTxt;
    @BindView(R.id.sport_brf)
    public TextView sportBrf;
    @BindView(R.id.sport_txt)
    public TextView sportTxt;
    @BindView(R.id.action_bar)
    public LinearLayout action_bar;
    @BindView(R.id.cold_brf)
    public TextView codeBrf;
    @BindView(R.id.cold_txt)
    public TextView coldTxt;
    @BindView(R.id.week_Weather_view)
    public WeekWeatherView weekWeatherView;
    @BindView(R.id.weather_cond)
    public TextView weatherCond;
    @BindView(R.id.update_time_hours)
    public TextView updateHours;
    @BindView(R.id.wind_hours)
    public TextView windHours;
    @BindView(R.id.weather_tmp)
    public TextView weatherTmp;
    @BindView(R.id.item_future)
    public LinearLayout itemFuture;
    @BindView(R.id.more)
    public TextView more;

    @BindView(R.id.root_layout)
    public LinearLayout rootLayout;

    private NetworkChangeReceiver networkChangeReceiver;
    private HomeContract.Presenter mPresenter;
    private Callback mCallback;
    private int[] rotateCount = {0, 0};
    private List<HoursWeather> hoursWeathers = new ArrayList<>();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setCallBack((MainActivity) context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new MessageEvent(UPDATE_WEATHER));

    }

    void setCallBack(Callback callBack) {
        mCallback = callBack;
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        generateTextView(view);
        initView();
        return view;
    }

    private void initView() {
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);

        registerBroadCast();
        initEvent();
    }

    private void initEvent() {
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoData();
                mPresenter.doRefreshInNoData();
            }
        });

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("被点击");
                mPresenter.generateDataInPopView();
            }
        });
    }


    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void updateView(HWeather weather) {
        mSwipeLayout.setVisibility(View.VISIBLE);
        mSwipeLayout.setRefreshing(false);
        refresh.clearAnimation();
        refresh.setVisibility(View.GONE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        date.setText("今天" + simpleDateFormat.format(new Date()));
        Date sqlDate = convertDate(WeatherJsonConverter.getWeather(weather).getBasic()
                .getUpdate().getLoc());
        long time = DateUtil.getDifferenceofDate(new Date(), sqlDate) / (1000 * 60);
        if (time > 1000 * 60 * 60 || time < 0) {
            updateTime.setText("最近更新：" + new SimpleDateFormat("MM-dd HH:mm:ss").format(sqlDate));
        } else {
            updateTime.setText("最近更新：" + time + "分钟之前");
        }
        tempImage.setImageResource(WeatherIcon.getWeatherImage(WeatherJsonConverter.getWeather(weather).getNow().getCond().getCode()));
        mCallback.updateToolBar(WeatherJsonConverter.getWeather(weather).getBasic().getCity());
        //主卡片
        temp.setText(WeatherJsonConverter.getWeather(weather).getNow().getTmp() + "°");
        tempHigh.setText("高 " + WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(0).getTmp().getMax() + "°");
        tempLow.setText("低 " + WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(0).getTmp().getMin() + "°");
        cloth.setText(WeatherJsonConverter.getWeather(weather).getSuggestion().getDrsg().getBrf());
        condition.setText(WeatherJsonConverter.getWeather(weather).getNow().getCond().getTxt());
        if (WeatherJsonConverter.getWeather(weather).getAqi() != null){
            pm.setText(WeatherJsonConverter.getWeather(weather).getAqi().getCity().getPm25());
            qlty.setText(WeatherJsonConverter.getWeather(weather).getAqi().getCity().getQlty());

        }
        hum.setText(WeatherJsonConverter.getWeather(weather).getNow().getHum() + "%");
        wind.setText(WeatherJsonConverter.getWeather(weather).getNow().getWind().getSpd() + "km/h");
        windDir.setText(WeatherJsonConverter.getWeather(weather).getNow().getWind().getDir());
        vis.setText(WeatherJsonConverter.getWeather(weather).getNow().getVis() + "km");
        pres.setText(WeatherJsonConverter.getWeather(weather).getNow().getPres() + "帕");
        uv.setText(WeatherJsonConverter.getWeather(weather).getSuggestion().getUv().getBrf());
        sunrise.setText(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(0).getAstro().getSr());
        sunset.setText(WeatherJsonConverter.getWeather(weather).getDaily_forecast().get(0).getAstro().getSs());

        //穿衣指数
        clothBrf.setText(WeatherJsonConverter.getWeather(weather).getSuggestion().getDrsg().getBrf());
        clothTxt.setText(WeatherJsonConverter.getWeather(weather).getSuggestion().getDrsg().getTxt());

        //运动指数
        sportBrf.setText(WeatherJsonConverter.getWeather(weather).getSuggestion().getSport().getBrf());
        sportTxt.setText(WeatherJsonConverter.getWeather(weather).getSuggestion().getSport().getTxt());

        //感冒指数
        codeBrf.setText(WeatherJsonConverter.getWeather(weather).getSuggestion().getFlu().getBrf());
        coldTxt.setText(WeatherJsonConverter.getWeather(weather).getSuggestion().getFlu().getTxt());

        //未来三小时天气
        if (WeatherJsonConverter.getWeather(weather).getHourly_forecast().size() != 0) {
            weatherCond.setText(WeatherJsonConverter.getWeather(weather).getHourly_forecast().get(0).getPop() + "%");
            updateHours.setText(WeatherJsonConverter.getWeather(weather).getHourly_forecast().get(0).getDate());
            windHours.setText(WeatherJsonConverter.getWeather(weather).getHourly_forecast().get(0).getWind().getDir() + " " +
                    WeatherJsonConverter.getWeather(weather).getHourly_forecast().get(0).getWind().getSc());
            weatherTmp.setText(WeatherJsonConverter.getWeather(weather).getHourly_forecast().get(0).getTmp() + "°");
        } else {
            itemFuture.setVisibility(View.GONE);
        }
        hoursWeathers.clear();
        for (int i = 0; i < WeatherJsonConverter.getWeather(weather).getHourly_forecast().size(); i++) {
            HoursWeather hw = new HoursWeather();
            hw.setTmp(WeatherJsonConverter.getWeather(weather).getHourly_forecast().get(i).getTmp() + "°");
            hw.setHum(WeatherJsonConverter.getWeather(weather).getHourly_forecast().get(i).getHum() + "%");
            hw.setWind_class(WeatherJsonConverter.getWeather(weather).getHourly_forecast().get(i).getWind().getSc());
            hw.setWind_deg(WeatherJsonConverter.getWeather(weather).getHourly_forecast().get(i).getWind().getDeg());
            hw.setWind_speed(WeatherJsonConverter.getWeather(weather).getHourly_forecast().get(i).getWind().getSpd());
            hw.setWind_dir(WeatherJsonConverter.getWeather(weather).getHourly_forecast().get(i).getWind().getDir());
            hw.setPop(WeatherJsonConverter.getWeather(weather).getHourly_forecast().get(i).getPop() + "%");
            hw.setUpdate(WeatherJsonConverter.getWeather(weather).getHourly_forecast().get(i).getDate());
            hoursWeathers.add(hw);
        }
    }

    @Override
    public void showNoData() {
        mSwipeLayout.setVisibility(View.GONE);
        refresh.setVisibility(View.VISIBLE);
        Animation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setRepeatCount(-1);
        animation.setInterpolator(new LinearInterpolator());
        refresh.startAnimation(animation);
        mPresenter.doRefreshInNoData();
    }


    @Override
    public void showDetail() {
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

    @Override
    public void showPopupWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.item_popupwindow, null);
        LinearLayout del = (LinearLayout) contentView.findViewById(R.id.del);
        ListView listView = (ListView) contentView.findViewById(R.id.popup_listview);
        PopupWindowAdapter adapter = new PopupWindowAdapter(hoursWeathers, getActivity());
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
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.color.transparent));
        popupWindow.showAtLocation(rootLayout,Gravity.CENTER ,0, 0);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

            }
        });
    }

    @Override
    public void onResume() {
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
    }

    @Override
    public void setNetWork() {
        Snackbar.make(rootLayout, "还是没有网络 QAQ", Snackbar.LENGTH_LONG).
                setAction("点我设置网络", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                }).show();
    }

    @Override
    public void registerBroadCast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        getActivity().registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        getActivity().unregisterReceiver(networkChangeReceiver);

    }

    public void generateTextView(View v) {
        TextView textView = new TextView(getActivity());
        textView.setText("天气易变，注意天气变化");
        View[] view = {v.findViewById(R.id.toolbar), v.findViewById(R.id.view), v.findViewById(R.id
                .item_cloths), v.findViewById(R.id.item_sports)};
        int totalHeight = 0;
        for (View aView : view) {
            totalHeight += getViewHeight(aView, true) + DisplayUtil.dip2px(getActivity(), 10);
        }
        int pxHeight = ScreenUtil.getScreenHW2(getActivity())[1] - totalHeight;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, pxHeight / 2);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        textView.setLayoutParams(lp);
        action_bar.addView(textView);
    }

    @Override
    public void updateWeeksView(List<WeekWeather> weathers, String[] weeks,List<Integer> lists) {
        weekWeatherView.setData(weathers,weeks,lists);
        Debug.startMethodTracing();
        weekWeatherView.notifyDateChanged();
    }

    @Override
    public void onRefresh() {
        mPresenter.refreshData();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHandleMessageEvent(MessageEvent event){
        if (event.getMessage() == UPDATE_SHOW_CITY){
            mPresenter.getNewShowWeather();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHandleCityName(CityNameMessage message) {

        String cityName = message.getCityName();
        mPresenter.showDialog(cityName,getActivity());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onHandleBackground(MessageEvent event){
        if (event.getMessage() == UPDATE_WEATHER){
            ImageUtils.drawImage(MyApplication.getAppContext(),ImageUtils.BRIEF);
        }
    }

    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) MyApplication
                    .getAppContext()
                    .getSystemService
                            (Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                mPresenter.refreshData();
            }
        }
    }


    interface Callback {

        void updateToolBar(String cityName);
    }

}
