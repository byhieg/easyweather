package com.weather.byhieg.easyweather.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.byhieglibrary.Activity.BaseFragment;
import com.weather.byhieg.easyweather.Activity.SlideMenuActivity;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;


public class LaboratoryFragment extends BaseFragment {

    public static final String TAG = "com.weather.byhieg.easyweather.Fragment.LaboratoryFragment";

    private CardView cardView;
    private TextView textView;
    private ImageView imageView;

    private CheckBox checkBox;

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

        if(MyApplication.nightMode2()){
            initNightView(R.layout.night_mode_overlay);
        }

        return inflater.inflate(R.layout.fragment_laboratory,null);
    }



    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    public void initView(){
        cardView= (CardView) getActivity().findViewById(R.id.item1);
        textView= (TextView) cardView.findViewById(R.id.lab_textview);
        textView.setText(R.string.nightMode1);
        checkBox= (CheckBox) cardView.findViewById(R.id.cb);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyApplication.shareFilename2, getActivity().MODE_PRIVATE);
        boolean checked=sharedPreferences.getBoolean("ischecked",false);
        checkBox.setChecked(checked);
        checkBoxStatus(checkBox,MyApplication.shareFilename2);


        cardView= (CardView) getActivity().findViewById(R.id.item2);
        checkBox= (CheckBox) cardView.findViewById(R.id.cb);
        SharedPreferences share = getActivity().getSharedPreferences(MyApplication.shareFilename1, getActivity().MODE_PRIVATE);
        boolean ischecked=share.getBoolean("ischecked",false);
        checkBox.setChecked(ischecked);
        checkBoxStatus(checkBox,MyApplication.shareFilename1);

    }

    public void checkBoxStatus(CheckBox checkBox, final String fileName){
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences settings = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = settings.edit();
                edit.putBoolean("ischecked",isChecked);
                edit.commit();
                if(fileName.equals(MyApplication.shareFilename1)) {
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), SlideMenuActivity.class);
                    intent.putExtra("itemid", 4);
                    startActivity(intent);
                }else{
                    if(isChecked){
                        initNightView(R.layout.night_mode_overlay);
                    }else {
//                      if(mNightView!=null) {
//                            mWindowManager.removeViewImmediate(mNightView);
//                            mNightView = null;
                            removeNightView();
//                        }
                    }
                }
            }
        });
    }
}
