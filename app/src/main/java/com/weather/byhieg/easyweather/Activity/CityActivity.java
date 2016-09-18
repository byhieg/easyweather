package com.weather.byhieg.easyweather.Activity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.weather.byhieg.easyweather.Fragment.ProvinceFragment;
import com.weather.byhieg.easyweather.R;

import butterknife.Bind;

public class CityActivity extends BaseActivity{


    @Bind(R.id.city_list_toolbar)
    public Toolbar toolbar;

    private  FragmentManager fm;

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

        fm = getFragmentManager();
        Fragment fragment = fm.findFragmentByTag(ProvinceFragment.TAG);
        if (fragment == null) {
            fragment = new ProvinceFragment();
        }
        if (!fragment.isAdded()) {
            fm.beginTransaction().add(R.id.fragment_container,fragment,ProvinceFragment.TAG).commit();
        }
        fm.beginTransaction().show(fragment).commit();
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
        final MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        if(searchView == null) return false;

        searchView.setSearchableInfo( searchManager.getSearchableInfo(CityActivity.this.getComponentName()) );
        AutoCompleteTextView textView = (AutoCompleteTextView)searchView.findViewById(R.id.search_src_text);
        if (textView != null){
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                }
                searchView.clearFocus();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

}

