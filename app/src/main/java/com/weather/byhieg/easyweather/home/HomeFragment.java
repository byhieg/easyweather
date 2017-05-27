package com.weather.byhieg.easyweather.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.data.HWeather;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeContract.View{


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {

    }

    @Override
    public void updateView(HWeather weather) {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showErrdata() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showDetail(HWeather weather) {

    }

    @Override
    public void showPopupWindow(HWeather weather) {

    }

    @Override
    public void setNetWork() {

    }

    @Override
    public void registerBroadCast() {

    }
}
