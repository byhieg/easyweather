package com.weather.byhieg.easyweather.Activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.weather.byhieg.easyweather.R;

import butterknife.Bind;

public class SearchResultActivity extends BaseActivity {

    @Bind(R.id.result_toolbar)
    public Toolbar toolbar;

    @Override
    public int getLayoutId() {

        return R.layout.activity_search_result;
    }

    @Override
    public void initData() {

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
    public void initEvent() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
