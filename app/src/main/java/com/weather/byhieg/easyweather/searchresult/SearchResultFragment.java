package com.weather.byhieg.easyweather.searchresult;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.TextView;

import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.base.BaseFragment;
import com.weather.byhieg.easyweather.city.adapter.CityListAdapter;
import com.weather.byhieg.easyweather.tools.MessageEvent;
import com.weather.byhieg.easyweather.data.bean.CityContext;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.weather.byhieg.easyweather.city.CityFragment.UPDATE_CITY;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends BaseFragment implements SearchResultContract.View {


    @BindView(R.id.result_list)
    public ListView listView;
    @BindView(R.id.search_result)
    public RelativeLayout mainLayout;
    @BindView(R.id.refresh_bar)
    public RelativeLayout refreshBar;
    @BindView(R.id.refresh)
    public ImageView refresh;
    @BindView(R.id.empty_view)
    public TextView mTextView;

    private String name;
    private SearchResultContract.Presenter mPresenter;
    private CityListAdapter adapter;

    public static SearchResultFragment newInstance() {
        SearchResultFragment fragment = new SearchResultFragment();
        return fragment;
    }

    public SearchResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_result, container, false);
        ButterKnife.bind(this, view);
        name = getArguments().getString("query");
        initEvent();
        return view;
    }

    private void initEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String cityName = ((CityContext) parent.getItemAtPosition(position)).getCityName();
                if (!mPresenter.isExist(cityName)) {
                    mPresenter.insertLoveCity(cityName);
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
                    EventBus.getDefault().post(new MessageEvent(UPDATE_CITY));
                } else {
                    Snackbar.make(mainLayout, "该城市已经添加，你忘记了？", Snackbar.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void setPresenter(SearchResultContract.Presenter presenter) {
        mPresenter = presenter;
    }



    @Override
    public void showNoData() {

    }

    @Override
    public void showQueryData(List<CityContext> data) {
        adapter = new CityListAdapter(data,getActivity());
        listView.setAdapter(adapter);
        listView.setEmptyView(mTextView);
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
    public void onDestroy() {
        super.onDestroy();

    }
}
