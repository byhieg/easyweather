package com.weather.byhieg.easyweather.Fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
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

import com.weather.byhieg.easyweather.Activity.CityManageActivity;
import com.weather.byhieg.easyweather.Adapter.CityListAdapter;
import com.weather.byhieg.easyweather.Bean.CityContext;
import com.weather.byhieg.easyweather.Db.City;
import com.weather.byhieg.easyweather.Db.LoveCity;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Tools.HandleDaoData;
import com.weather.byhieg.easyweather.Tools.NetTool;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CityFragment extends Fragment {



    public static final String TAG = "com.weather.byhieg.easyweather.Fragment.CityFragment";
    public static final int UPDATE_CITY = 0x103;
    private List<CityContext> cities = new ArrayList<>();
    private CityListAdapter adapter;
    public RelativeLayout mainLayout;
    public RelativeLayout refreshBar;
    public ImageView refresh;
    private Handler handler;
    private CityManageActivity.MyHandler myHandler;

    public CityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        initData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initData();
        handler = new Handler();
        myHandler = new CityManageActivity.MyHandler(new CityManageActivity());
        adapter = new CityListAdapter(cities, getActivity());
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.city_list);
        mainLayout = (RelativeLayout) view.findViewById(R.id.fragment_city);
        refreshBar = (RelativeLayout) view.findViewById(R.id.refresh_bar);
        refresh = (ImageView) view.findViewById(R.id.refresh);
        listView.setAdapter(adapter);
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
                                myHandler.sendEmptyMessage(UPDATE_CITY);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        refreshBar.setVisibility(View.GONE);
                                    }
                                });
                            } catch (Exception e) {
                                myHandler.sendEmptyMessage(UPDATE_CITY);
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

    private void initData() {
        String provinceName = getArguments().getString("ProvinceName");
        List<City> cityList = HandleDaoData.getCityFromProvince(provinceName);
        for (City item : cityList) {
            CityContext cityContext = new CityContext();
            cityContext.setCityName(item.getCitynName());
            cities.add(cityContext);
        }
    }

}
