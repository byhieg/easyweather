package com.weather.byhieg.easyweather.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.byhieglibrary.Activity.BaseFragment;
import com.weather.byhieg.easyweather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends BaseFragment {
    public static final String TAG = "com.weather.byhieg.easyweather.Fragment.AboutFragment";


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        return view;
    }



}
