package com.weather.byhieg.easyweather.slidemenu.about;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.UnderlineSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weather.byhieg.easyweather.base.BaseFragment;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends BaseFragment {
    public static final String TAG = "com.weather.byhieg.easyweather.slidemenu.about.AboutFragment";


    public AboutFragment() {
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

        View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView tv = (TextView) view.findViewById(R.id.link);
        String textStr = "https://github.com/byhieg/easyweather";
        tv.setAutoLinkMask(Linkify.WEB_URLS);
        tv.setText(textStr);
        Spannable s = (Spannable) tv.getText();
        s.setSpan(new UnderlineSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(ds.linkColor);
                ds.setUnderlineText(false);
            }
        }, 0, textStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

