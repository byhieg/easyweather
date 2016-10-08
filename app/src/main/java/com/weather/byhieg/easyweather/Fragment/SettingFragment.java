package com.weather.byhieg.easyweather.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.byhieglibrary.Activity.BaseFragment;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment {

    public static final String TAG = "com.weather.byhieg.easyweather.Fragment.SettingFragment";


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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyApplication.logFilename, getActivity().MODE_PRIVATE);
        boolean checked = sharedPreferences.getBoolean("ischecked", true);
        logSwitch.setChecked(checked);
        switchStatus(logSwitch, MyApplication.logFilename);
    }

    public void switchStatus(Switch aSwitch, final String fileName) {
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences settings = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = settings.edit();
                edit.putBoolean("ischecked", isChecked);
                edit.commit();
//                if (isChecked) {
//                    initNightView(R.layout.night_mode_overlay);
//                } else {
//                    removeNightView();
//                }
            }

        });
    }

}
