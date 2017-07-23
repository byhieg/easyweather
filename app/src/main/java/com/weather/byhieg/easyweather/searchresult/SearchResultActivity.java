package com.weather.byhieg.easyweather.searchresult;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.weather.byhieg.easyweather.base.BaseActivity;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.result_toolbar)
    public Toolbar toolbar;


    private SearchResultPresenter mPresenter;
    private FragmentManager fm;
    String  query;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);

        Intent intent = getIntent();
        query = intent.getStringExtra(SearchManager.QUERY);
        fm = getSupportFragmentManager();
        SearchResultFragment fragment = (SearchResultFragment) fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            Bundle args = new Bundle();
            args.putString("query",query);
            fragment = SearchResultFragment.newInstance();
            fragment.setArguments(args);
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
        mPresenter = new SearchResultPresenter(fragment);
    }

    @Override
    public void initView() {
        toolbar.setTitle("搜索结果");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void initTheme() {
        if(MyApplication.nightMode()){
            setTheme(R.style.NightTheme);
        }else {
            setTheme(R.style.DayTheme);
        }
    }

    @Override
    public void initEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        if(MyApplication.nightMode2()){
            initNightView(R.layout.night_mode_overlay);
        }else {
            removeNightView();
        }
        mPresenter.loadCities(query);
    }
}
