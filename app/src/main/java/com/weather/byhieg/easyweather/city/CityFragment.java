package com.weather.byhieg.easyweather.city;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.base.BaseFragment;
import com.weather.byhieg.easyweather.city.adapter.CityListAdapter;
import com.weather.byhieg.easyweather.tools.MessageEvent;
import com.weather.byhieg.easyweather.data.bean.CityContext;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.data.source.local.entity.CityEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityFragment extends BaseFragment implements CityContract.CityView {


    public static final String TAG = "com.weather.byhieg.easyweather.city.CityFragment";
    public static final int UPDATE_CITY = 0x103;
    private List<CityContext> cities = new ArrayList<>();
    private CityListAdapter adapter;

    @BindView(R.id.fragment_city)
    public RelativeLayout mainLayout;
    @BindView(R.id.refresh_bar)
    public RelativeLayout refreshBar;
    @BindView(R.id.refresh)
    public ImageView refresh;
    @BindView(R.id.city_list)
    public ListView listView;

    //    private CityManageActivity.MyHandler myHandler;
    private CityContract.CityPresenter mPresenter;

    public CityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        myHandler = new CityManageActivity.MyHandler(new CityManageActivity());
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.city_list);
        mainLayout = (RelativeLayout) view.findViewById(R.id.fragment_city);
        refreshBar = (RelativeLayout) view.findViewById(R.id.refresh_bar);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        adapter = new CityListAdapter(cities, MyApplication.getAppContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String cityName = ((CityContext) parent.getItemAtPosition(position)).getCityName();
                Logger.d(cityName);
                if (!mPresenter.isExist(cityName)) {
                    mPresenter.insertLoveCity(cityName);
                    Logger.d(cityName + "插入成功");
                    Snackbar.make(mainLayout, "添加城市成功", Snackbar.LENGTH_LONG).
                            setAction("点我撤销", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mPresenter.cancelCity(cityName);
                                    cancelRefresh();
                                }
                            }).show();
                    refreshBar.setVisibility(View.VISIBLE);
                    Animation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                    animation.setDuration(1000);
                    animation.setRepeatCount(-1);
                    animation.setInterpolator(new LinearInterpolator());
                    refresh.startAnimation(animation);
                    mPresenter.getCityWeather(cityName);

//                    Message message = Message.obtain();
//                    message.what = UPDATE_CITY;
//                    handler.sendMessage(message) ;
                    EventBus.getDefault().post(new MessageEvent(UPDATE_CITY));

                } else {
                    Snackbar.make(mainLayout, "该城市已经添加，你忘记了？", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showCityData(List<CityEntity> data) {
        for (CityEntity item : data) {
            CityContext cityContext = new CityContext();
            cityContext.setCityName(item.getCityName());
            cities.add(cityContext);
        }
    }

    @Override
    public void cancelRefresh() {
        refreshBar.setVisibility(View.GONE);
    }

    @Override
    public void setNetWork() {
        Snackbar.make(mainLayout, "没有网络 QAQ", Snackbar.LENGTH_LONG).
                setAction("点我设置网络", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        startActivity(intent);
                    }
                }).show();
    }

    @Override
    public void setPresenter(CityContract.CityPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
