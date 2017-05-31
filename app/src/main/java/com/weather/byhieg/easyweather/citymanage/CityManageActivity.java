package com.weather.byhieg.easyweather.citymanage;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.weather.byhieg.easyweather.base.BaseActivity;
import com.weather.byhieg.easyweather.city.CityActivity;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityManageActivity extends BaseActivity {

    @BindView(R.id.city_toolbar)
    public Toolbar toolbar;


    private CityManagePresenter mPresenter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_city_manage;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);

        FragmentManager fm = getSupportFragmentManager();
        CityManageFragment fragment = (CityManageFragment) fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = CityManageFragment.newInstance();
            fm.beginTransaction().add(R.id.fragment_container,fragment).commit();
        }
        mPresenter = new CityManagePresenter(fragment);

    }

    @Override
    public void initView() {
        toolbar.setTitle("城市管理");
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
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.add_city:
                        startActivity(CityActivity.class);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        break;
                }
                return true;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_city_manage, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(MyApplication.nightMode2()){
            initNightView(R.layout.night_mode_overlay);
        }else {
            removeNightView();
        }

        mPresenter.start();
    }
}
