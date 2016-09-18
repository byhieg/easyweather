package com.weather.byhieg.easyweather.Activity;


import android.app.SearchManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.weather.byhieg.easyweather.R;

import butterknife.Bind;

public class CityActivity extends BaseActivity {


    @Bind(R.id.city_list_toolbar)
    public Toolbar toolbar;


    @Override
    public int getLayoutId() {
        return R.layout.activity_city;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        toolbar.setTitle("选择城市");
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

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.search:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_city_list,menu);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if(searchView == null) return false;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()) );
//        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }
}
