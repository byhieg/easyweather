package com.weather.byhieg.easyweather.slidemenu.help;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weather.byhieg.easyweather.base.BaseFragment;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends BaseFragment {

    public static final String TAG = "com.weather.byhieg.easyweather.slidemenu.help.HelpFragment";

    public HelpFragment() {
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
        return inflater.inflate(R.layout.fragment_help, container, false);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
