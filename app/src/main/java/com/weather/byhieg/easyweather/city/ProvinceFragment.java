package com.weather.byhieg.easyweather.city;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.base.BaseFragment;
import com.weather.byhieg.easyweather.city.adapter.ProvinceListAdapter;
import com.weather.byhieg.easyweather.data.bean.ProvinceContext;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.data.source.local.entity.ProvinceEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProvinceFragment extends BaseFragment implements CityContract.ProvinceView{

    public static final String TAG = "com.weather.byhieg.easyweather.city.ProvinceFragment";

    private List<ProvinceContext> provinces = new ArrayList<>();
    private ProvinceListAdapter adapter;
    public ListView listView;
    private CityContract.ProvincePresenter mPresenter;

    public ProvinceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        adapter = new ProvinceListAdapter(provinces, MyApplication.getAppContext());
        View view = inflater.inflate(R.layout.fragment_province, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.province_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CityFragment cityFragment = (CityFragment) getActivity().getSupportFragmentManager()
                        .findFragmentByTag(CityFragment.TAG);
                if (cityFragment == null) {
                    cityFragment = new CityFragment();
                }
                CityPresenter cityPresenter = new CityPresenter(cityFragment);
                Bundle bundle = new Bundle();
                bundle.putString("ProvinceName",((ProvinceContext)parent.getItemAtPosition(position)).getName());
                cityFragment.setArguments(bundle);
                cityPresenter.loadCityData(((ProvinceContext)parent.getItemAtPosition(position)).getName());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        cityFragment, CityFragment.TAG).commit();
                getActivity().getSupportFragmentManager().beginTransaction().show(cityFragment).commit();
            }
        });
    }




    @Override
    public void showProvinceData(List<ProvinceEntity> datas) {
        for (ProvinceEntity item : datas) {
            ProvinceContext province = new ProvinceContext();
            province.setName(item.getProvinceName());
            province.setImage(R.mipmap.ic_keyboard_arrow_right_black_24dp);
            provinces.add(province);
        }
    }

    @Override
    public void setPresenter(CityContract.ProvincePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
