package com.weather.byhieg.easyweather.home;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocationListener;
import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.base.BaseActivity;
import com.weather.byhieg.easyweather.tools.DisplayUtil;
import com.weather.byhieg.easyweather.tools.LogUtils;
import com.weather.byhieg.easyweather.citymanage.CityManageActivity;
import com.weather.byhieg.easyweather.loveapp.LoveAppActivity;
import com.weather.byhieg.easyweather.slidemenu.SlideMenuActivity;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.tools.Constants;
import com.weather.byhieg.easyweather.tools.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.weather.byhieg.easyweather.tools.Constants.UPDATE_SHOW_CITY;

public class MainActivity extends BaseActivity implements ActivityCompat
        .OnRequestPermissionsResultCallback,HomeFragment.Callback {


    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    public NavigationView mNavigationView;

//    public Toolbar toolbar;

    private HomePresenter mHomePresenter;
    private final boolean []isQuit = new boolean[1];

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        FragmentManager fm = getSupportFragmentManager();
        HomeFragment homeFragment = (HomeFragment) fm.findFragmentById(R.id.fragment_container);
        if (homeFragment == null) {
            homeFragment = HomeFragment.newInstance();
            fm.beginTransaction().add(R.id.fragment_container,homeFragment).commit();
        }

        mHomePresenter = new HomePresenter(homeFragment);
        BDLocationListener myListener = new MyLocationListener();
        MyApplication.getmLocationClient().registerLocationListener(myListener);

//
    }

    @Override
    public void initView() {
        toolbar.setTitle("成都");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setItemTextColor(null);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, 0, 0);
        mDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mDrawerToggle);
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(isQuit[0]){
                finish();
            }else{
                showToast("是否要退出");
                isQuit[0] = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isQuit[0] = false;
                    }
                },2000);
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void initEvent() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.feedback:
                        AlertDialog.Builder dialogBuilder;
                        if (MyApplication.nightMode2()){
                           dialogBuilder  = new AlertDialog.Builder(MainActivity.this, R.style.NightDialog);
                        }else{
                            dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        }
                       dialogBuilder.setTitle("反馈").setMessage("在使用过程中，有任何问题均可以发送到邮箱：byhieg@gmail.com").setPositiveButton("恩", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
                        break;

                    case R.id.location:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED||
                                    checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                                    checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                                LogUtils.e("Permissions","还是没有权限啊");
                                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义)
                                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_WIFI_STATE,
                                        Manifest.permission.ACCESS_NETWORK_STATE,
                                        Manifest.permission.CHANGE_WIFI_STATE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, Constants.PERMISSION);
                            }else{
                                mHomePresenter.doBaiduLocation();
                                LogUtils.e("Permissions","已经有权限了");
                            }
                        }else{
                            mHomePresenter.doBaiduLocation();
                        }

                        break;

                    case R.id.like:
                        startActivity(LoveAppActivity.class);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        break;
                    case R.id.add_city:
                        startActivity(CityManageActivity.class);
                        overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                        break;
                }
                return true;
            }
        });

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int position = 0;
                switch (menuItem.getItemId()) {
                    case R.id.trending:
                        break;
                    case R.id.setting:
                        position = 1;
                        break;
                    case R.id.share:
                        position = 2;
                        break;
                    case R.id.help:
                        position = 3;
                        break;
                    case R.id.lab:
                        position = 4;
                        break;
                    case R.id.wiki:
                        position = 5;
                        break;
                    case R.id.more:
                        position = 6;
                        break;
                }
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                Intent intent = new Intent(getApplicationContext(), SlideMenuActivity.class);
                intent.putExtra("itemId", position);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in, R.anim.activity_out);
                return true;
            }
        });

        EventBus.getDefault().register(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onHandleMessageEvent(MessageEvent event){
        if (event.getMessage() == UPDATE_SHOW_CITY){
            toolbar.setTitle(mHomePresenter.getShowCity());
        }
    }




    @Override
    protected void onResume() {
        super.onResume();

        if (MyApplication.nightMode2()) {
            initNightView(R.layout.night_mode_overlay);
        } else {
            removeNightView();
        }
        mHomePresenter.start();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,grantResults);
        switch(requestCode)
        {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case Constants.PERMISSION:
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                MyApplication.getmLocationClient().start();
            }
            else
            {
                // 没有获取到权限，做特殊处理
                showToast("没有权限，定位失败");
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void updateToolBar(String cityName) {
        toolbar.setTitle(cityName);
    }

}
