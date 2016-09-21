package com.weather.byhieg.easyweather.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.weather.byhieg.easyweather.Fragment.LaboratoryFragment;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;

import butterknife.Bind;

public class SlideMenuActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    private int position;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position=getIntent().getIntExtra("itemid",0);
        Toast.makeText(this,position+"",Toast.LENGTH_LONG).show();
        switch (position){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment, LaboratoryFragment.getInstance()).commit();
                break;
            case 5:
                break;
            case 6:
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_laboratory;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initView() {
        toolbar.setTitle(R.string.laboratory);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void initTheme() {

    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(MyApplication.nightMode2()){
//            initNightView(R.layout.night_mode_overlay);
//        }else {
//            removeNightView();
//        }
//    }
}
