package com.weather.byhieg.easyweather.slidemenu.future;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.base.BaseFragment;
import com.weather.byhieg.easyweather.slidemenu.future.adapter.FutureListAdapter;
import com.weather.byhieg.easyweather.data.bean.FutureContext;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FutureFragment extends BaseFragment implements FutureContract.View {

    public static final String TAG = "com.weather.byhieg.easyweather.slidemenu.future.FutureFragment";
    private ArrayList<FutureContext> lists = new ArrayList<>();
    private FutureListAdapter adapter;
    private FutureContract.Presenter mPresenter;

    @BindView(R.id.future_recycler_view)
    public RecyclerView recyclerView;

    public FutureFragment() {
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
        View view = inflater.inflate(R.layout.fragment_future, container, false);
        ButterKnife.bind(this, view);
        mPresenter.start();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setPresenter(FutureContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showListView(List<FutureContext> list) {
        adapter = new FutureListAdapter(list);
        Logger.d(list.get(0).getCond());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
