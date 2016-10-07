package com.weather.byhieg.easyweather.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.byhieglibrary.Activity.BaseFragment;
import com.weather.byhieg.easyweather.Activity.SlideMenuActivity;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Tools.Constants;

import org.jetbrains.annotations.Nullable;


public class LaboratoryFragment extends BaseFragment {

    public static final String TAG = "com.weather.byhieg.easyweather.Fragment.LaboratoryFragment";

    private CardView cardView;
    private TextView textView;

    private Switch aSwitch;
    private View view;

//    private WindowManager mWindowManager = null;
//    private View mNightView = null;
//    private LayoutParams mNightViewParam;

    public LaboratoryFragment() {
    }

    public static LaboratoryFragment getInstance(){
        LaboratoryFragment fragment=new LaboratoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(MyApplication.nightMode()){
            getActivity().setTheme(R.style.NightTheme);
        }else {
            getActivity().setTheme(R.style.DayTheme);
        }

        view = inflater.inflate(R.layout.fragment_laboratory,null);
        initView();
        return view;
    }

    public void initView(){
        cardView= (CardView) view.findViewById(R.id.item1);
        textView= (TextView) view.findViewById(R.id.lab_textview);
        textView.setText(R.string.nightMode1);
        aSwitch= (Switch) cardView.findViewById(R.id.cb);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyApplication.shareFilename2, getActivity().MODE_PRIVATE);
        boolean checked=sharedPreferences.getBoolean("ischecked",false);
        aSwitch.setChecked(checked);
        switchStatus(aSwitch,MyApplication.shareFilename2);


    }

    public void switchStatus(Switch aSwitch, final String fileName){
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences settings = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = settings.edit();
                edit.putBoolean("ischecked",isChecked);
                edit.commit();
                if(fileName.equals(MyApplication.shareFilename1)) {
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), SlideMenuActivity.class);
                    intent.putExtra("itemId", Constants.LAB);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
                }else{
                    if(isChecked){
                        initNightView(R.layout.night_mode_overlay);
                    }else {
                            removeNightView();
                    }
                }
            }
        });
    }
}
