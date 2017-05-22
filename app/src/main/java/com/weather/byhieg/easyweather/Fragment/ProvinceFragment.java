package com.weather.byhieg.easyweather.Fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.weather.byhieg.easyweather.Adapter.ProvinceListAdapter;
import com.weather.byhieg.easyweather.Bean.ProvinceContext;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.tools.HandleDaoData;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProvinceFragment extends Fragment {

    public static final String TAG = "com.weather.byhieg.easyweather.Fragment.ProvinceFragment";

    private List<ProvinceContext> provinces = new ArrayList<>();
    private ProvinceListAdapter adapter;
    public static final int CHANGE_FRAGMENT = 0x102;
    public ListView listView;
//    public CityActivity.MyHandler handler = new CityActivity().new MyHandler();

    public ProvinceFragment() {
        // Required empty public constructor
        initData();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        adapter = new ProvinceListAdapter(provinces, getActivity());
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
//                handler.sendEmptyMessage(CHANGE_FRAGMENT);
                Fragment cityFragment = getActivity().getFragmentManager().findFragmentByTag(CityFragment.TAG);
                if (cityFragment == null) {
                    cityFragment = new CityFragment();
                }
                Bundle bundle = new Bundle();
                bundle.putString("ProvinceName",((ProvinceContext)parent.getItemAtPosition(position)).getName());
                cityFragment.setArguments(bundle);
                getActivity().getFragmentManager().beginTransaction().replace(R.id.fragment_container, cityFragment, CityFragment.TAG).commit();
                getActivity().getFragmentManager().beginTransaction().show(cityFragment).commit();
            }
        });
    }

    private void initData() {
        List<Province> provinceArrayList = HandleDaoData.getAllProvince();
        for (Province item : provinceArrayList) {
            ProvinceContext province = new ProvinceContext();
            province.setName(item.getProvinceName());
            province.setImage(R.mipmap.ic_keyboard_arrow_right_black_24dp);
            provinces.add(province);
        }
    }
}
