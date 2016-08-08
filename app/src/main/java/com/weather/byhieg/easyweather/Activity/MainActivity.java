package com.weather.byhieg.easyweather.Activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.weather.byhieg.easyweather.Adapter.DrawerListAdapter;
import com.weather.byhieg.easyweather.Bean.DrawerContext;
import com.weather.byhieg.easyweather.R;

import java.util.ArrayList;

import butterknife.Bind;

public class MainActivity extends BaseActivity {


    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    public DrawerLayout drawerLayout;
    @Bind(R.id.recyclerview)
    public RecyclerView recyclerView;
    @Bind(R.id.view_spot)
    public ViewStub viewStub;
    @Bind(R.id.arrow)
    public ImageView arrow;

    private DrawerListAdapter drawerListAdapter;
    private ArrayList<DrawerContext> drawerList = new ArrayList<>();

    private int rotateCount = 0;
    private View  convertView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        int [] images = { R.mipmap.ic_trending_up_black_24dp,
                          R.mipmap.ic_settings_black_24dp,
                          R.mipmap.ic_share_black_24dp,
                          R.mipmap.ic_help_black_24dp,
                          R.mipmap.ic_hourglass_empty_black_24dp,
                          R.mipmap.ic_wikipedia,
                          R.mipmap.ic_more_black_24dp};

        int[] names = { R.string.future,
                        R.string.setting,
                        R.string.share,
                        R.string.help,
                        R.string.laboratory,
                        R.string.wiki,
                        R.string.about};

        for(int i = 0 ;i < 7;i++) {
            DrawerContext drawerContext = new DrawerContext();
            drawerContext.setImage(images[i]);
            drawerContext.setName(names[i]);
            drawerList.add(drawerContext);
        }

        drawerListAdapter = new DrawerListAdapter(drawerList);

    }

    @Override
    public void initView() {
        toolbar.setTitle("成都");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        mDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mDrawerToggle);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(drawerListAdapter);



    }

    @Override
    public void initEvent() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.feedback:
                        break;

                    case R.id.location:
                        break;

                    case R.id.like:
                        break;
                }
                return true;
            }
        });

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rotateCount % 2 == 0) {
                    Animation animation = new RotateAnimation(0, 180,50,50);
                    animation.setDuration(10);
                    animation.setFillAfter(true);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if(convertView == null){
                                convertView = viewStub.inflate();
                                ListView listView = (ListView) convertView.findViewById(R.id.view_spot_list);
                                listView.setEmptyView(findViewById(R.id.empty_view));
//                                recyclerView.setAdapter(drawerListAdapter);
                            }
                            convertView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    arrow.startAnimation(animation);
                }
                else{
                    Animation animation = new RotateAnimation(180, 360, 50, 50);
                    animation.setDuration(10);
                    animation.setFillAfter(true);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            viewStub.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    arrow.startAnimation(animation);
                }
                rotateCount++;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
