package com.weather.byhieg.easyweather.slidemenu.wiki;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weather.byhieg.easyweather.base.BaseFragment;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class WikiFragment extends BaseFragment {

    public static final String TAG = "com.weather.byhieg.easyweather.slidemenu.wiki.WikiFragment";
    private String[] tags;
    private String[] links;

    public WikiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTheme(R.style.DayTheme);
        if (MyApplication.nightMode2()) {
            initNightView(R.layout.night_mode_overlay);
        }
        View view = inflater.inflate(R.layout.fragment_wiki, container, false);
        initData();
        initView(view);
        return view;
    }


    private void initView(View view) {
        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        final TagFlowLayout flowLayout = (TagFlowLayout) view.findViewById(R.id.id_flowlayout);
        flowLayout.setAdapter(new TagAdapter<String>(tags) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.item_flow_layout,
                        flowLayout, false);
                tv.setText(s);
                return tv;
            }
        });

        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent){
                Intent intent = new Intent(getActivity(), WikiResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("keyWord",tags[position]);
                bundle.putString("url",links[position]);
                intent.putExtra("result", bundle);
                startActivity(intent);
                return true;
            }
        });
    }

    private void initData() {
        tags = new String[]{
                "晴", "风", "雨", "雷阵雨", "台风", "冰雹", "云", "湿度", "降雨量",
                "紫外线", "能见度", "PM2.5", "PM10", "雪", "摄氏度", "华氏度"};

        links = new String[]{
                "http://baike.baidu.com/subview/42733/8097631.htm#viewPageContent",
                "http://baike.baidu.com/subview/17988/7589816.htm#viewPageContent",
                "http://baike.baidu.com/subview/27432/6630580.htm#viewPageContent",
                "http://baike.baidu.com/item/%E9%9B%B7%E9%98%B5%E9%9B%A8/7602073",
                "http://baike.baidu.com/item/%E5%8F%B0%E9%A3%8E/17003",
                "http://baike.baidu.com/item/%E5%86%B0%E9%9B%B9/2072195",
                "http://baike.baidu.com/subview/24937/9154496.htm#viewPageContent",
                "http://baike.baidu.com/view/286584.htm",
                "http://baike.baidu.com/view/1745.htm",
                "http://baike.baidu.com/subview/598/14136534.htm#viewPageContent",
                "http://baike.baidu.com/view/419927.htm",
                "http://baike.baidu.com/view/4251816.htm?fromtitle=PM2.5&fromid=353332&type=syn",
                "http://baike.baidu.com/view/489133.htm?fromtitle=PM10&fromid=4066123&type=syn",
                "http://baike.baidu.com/item/%E9%9B%AA/2886691",
                "http://baike.baidu.com/view/423347.htm",
                "http://baike.baidu.com/view/562306.htm"};
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}


