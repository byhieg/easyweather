package com.weather.byhieg.easyweather.slidemenu.setting;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.weather.byhieg.easyweather.base.BaseFragment;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.startweather.NotificationService;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment {

    public static final String TAG = "com.weather.byhieg.easyweather.slidemenu.setting.SettingFragment";


    public SettingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTheme(R.style.DayTheme);
        if (MyApplication.nightMode2()) {
            initNightView(R.layout.night_mode_overlay);
        }
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        Switch logSwitch = (Switch) view.findViewById(R.id.log_switch);
        SharedPreferences log = getActivity().getSharedPreferences(MyApplication.logFilename, getActivity().MODE_PRIVATE);
        boolean logChecked = log.getBoolean("ischecked", true);
        logSwitch.setChecked(logChecked);
        switchStatus(logSwitch, MyApplication.logFilename,1);


        Switch notificationSwitch = (Switch) view.findViewById(R.id.notification_switch);
        SharedPreferences notification = getActivity().getSharedPreferences(MyApplication.notificationname, getActivity().MODE_PRIVATE);
        boolean notificationChecked = notification.getBoolean("ischecked", true);
        notificationSwitch.setChecked(notificationChecked);
        switchStatus(notificationSwitch, MyApplication.notificationname,2);

        Switch widgetSwitch = (Switch) view.findViewById(R.id.widget_switch);
        SharedPreferences widget = getActivity().getSharedPreferences(MyApplication.widgetname, getActivity().MODE_PRIVATE);
        boolean widgetChecked = widget.getBoolean("ischecked", true);
        widgetSwitch.setChecked(widgetChecked);
        switchStatus(widgetSwitch, MyApplication.widgetname,3);

        Switch cacheSwitch = (Switch) view.findViewById(R.id.cache_switch);
        SharedPreferences cache = getActivity().getSharedPreferences(MyApplication.cachename, getActivity().MODE_PRIVATE);
        boolean cacheChecked = cache.getBoolean("ischecked", true);
        cacheSwitch.setChecked(cacheChecked);
        switchStatus(cacheSwitch,MyApplication.cachename,4);
    }

    public void switchStatus(Switch aSwitch, final String fileName, final int flag) {
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences settings = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = settings.edit();
                edit.putBoolean("ischecked", isChecked);
                edit.apply();
                if (isChecked) {
                    if (flag == 2) {
                        getActivity().startService(new Intent(getActivity(), NotificationService.class));
                    }
                }else{
                    if (flag == 2) {
                        getActivity().stopService(new Intent(getActivity(), NotificationService.class));
                    }
                }
            }

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
