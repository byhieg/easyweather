package com.weather.byhieg.easyweather.citymanage;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.base.BaseFragment;
import com.weather.byhieg.easyweather.tools.LogUtils;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.tools.MessageEvent;
import com.weather.byhieg.easyweather.citymanage.adapter.CityManageAdapter;
import com.weather.byhieg.easyweather.customview.SlideCutListView;
import com.weather.byhieg.easyweather.data.bean.CityManageContext;
import com.weather.byhieg.easyweather.data.bean.HWeather;
import com.weather.byhieg.easyweather.data.source.local.entity.WeatherEntity;
import com.weather.byhieg.easyweather.tools.WeatherJsonConverter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.weather.byhieg.easyweather.city.CityFragment.UPDATE_CITY;
import static com.weather.byhieg.easyweather.tools.Constants.UPDATE_SHOW_CITY;
import static com.weather.byhieg.easyweather.tools.Constants.UPDATE_WEATHER;
import static com.weather.byhieg.easyweather.tools.Knife.convertObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityManageFragment extends BaseFragment implements CityManageContract.View {

    @BindView(R.id.card_view_group)
    public SlideCutListView cardViewGroup;


    private CityManageContract.Presenter mPresenter;
    private CityManageAdapter mAdapter;
    private List<CityManageContext> mList = new ArrayList<>();

    public CityManageFragment() {
        // Required empty public constructor
//        mHandler = new CityManageHandler(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    public static CityManageFragment newInstance() {
        CityManageFragment fragment = new CityManageFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city_manage, container, false);
        ButterKnife.bind(this, view);
        initView();
        initEvent();
        return view;
    }

    private void initView() {
        mAdapter = new CityManageAdapter(mList,getActivity().getApplicationContext());
        cardViewGroup.setAdapter(mAdapter);
    }

    private void initEvent() {
        cardViewGroup.setRemoveListener(new SlideCutListView.RemoveListener() {
            @Override
            public void removeItem(SlideCutListView.RemoveDirection direction, int position) {
                String name = ((CityManageContext)mAdapter.getItem(position)).getCityName();
                if (name.equals(mPresenter.getShowCity())) {
                    AlertDialog.Builder dialogBuilder;
                    if (MyApplication.nightMode2()){
                        dialogBuilder  = new AlertDialog.Builder(getActivity(), R.style.NightDialog);
                    }else{
                        dialogBuilder = new AlertDialog.Builder(getActivity());
                    }
                    dialogBuilder.setMessage("该城市为首页城市，无法删除，如要删除，请指定另一首页城市").
                            setPositiveButton("恩", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }else{
                    mPresenter.deleteCity(name);
                }

            }
        });

        cardViewGroup.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String name = ((CityManageContext) parent.getItemAtPosition(position)).getCityName();
                AlertDialog.Builder dialogBuilder;
                if (MyApplication.nightMode2()){
                    dialogBuilder  = new AlertDialog.Builder(getActivity(), R.style.NightDialog);
                }else{
                    dialogBuilder = new AlertDialog.Builder(getActivity());
                }
                dialogBuilder.setMessage("是否将" + name + "设置为首页城市").
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LogUtils.e("name",name);
                                LogUtils.e("show",mPresenter.getShowCity());
                                if(name.equals(mPresenter.getShowCity())){
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "该城市已经是首页城市",Toast.LENGTH_SHORT).show();
                                }else{
                                    mPresenter.updateShowCity(name);
                                    EventBus.getDefault().post(new MessageEvent(UPDATE_WEATHER));
                                }
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LogUtils.e("name",name);
                                LogUtils.e("show",mPresenter.getShowCity());
                            }
                        }).show();
                return true;
            }
        });
    }


    @Override
    public void setPresenter(CityManageContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showNoData() {

    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void showCities(List<WeatherEntity> cityEntities) {
        mList.clear();
        if (cityEntities == null || cityEntities.size() == 0) {
            return;
        }
        for (WeatherEntity entity : cityEntities) {
            if (entity == null){
                return;
            }
            HWeather weather = convertObject(entity.getWeather(), HWeather.class);
            CityManageContext context = new CityManageContext();
            context.setTime(new SimpleDateFormat("HH:mm").
                    format(entity.getUpdateTime()));
            context.setTmp(WeatherJsonConverter.getWeather(weather).getNow().getTmp());
            context.setHum(WeatherJsonConverter.getWeather(weather).getNow().getHum());
            context.setCond(WeatherJsonConverter.getWeather(weather).getNow().getCond().getTxt());
            context.setCityName(entity.getCityName());
            context.setCode(WeatherJsonConverter.getWeather(weather).getNow().getCond().getCode());
            mList.add(context);
        }
        mAdapter.notifyDataSetChanged();
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHandleMessageEvent(MessageEvent messageEvent){
        if (messageEvent.getMessage() == UPDATE_CITY){
            mPresenter.loadCities();

        }
    }

}
