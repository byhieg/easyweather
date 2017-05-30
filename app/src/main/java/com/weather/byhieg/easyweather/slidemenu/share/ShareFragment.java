package com.weather.byhieg.easyweather.slidemenu.share;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.byhieglibrary.Activity.BaseFragment;
import com.example.byhieglibrary.Utils.ShareContent;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.tools.ImageUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends BaseFragment {
    public static final String TAG = "com.weather.byhieg.easyweather.slidemenu.share.ShareFragment";


    public ShareFragment() {
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
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_brief);
        imageView.setImageURI(Uri.fromFile(ImageUtils.drawImage(getActivity(),ImageUtils.BRIEF)));

        ImageView imageDetail = (ImageView) view.findViewById(R.id.imageView_detail);
        imageDetail.setImageURI(Uri.fromFile(ImageUtils.drawImage(getActivity(),ImageUtils.DETAIL)));

        ImageView imageFuture = (ImageView) view.findViewById(R.id.imageView_future);
        imageFuture.setImageURI(Uri.fromFile(ImageUtils.drawImage(getActivity(),ImageUtils.FUTURE)));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareContent shareContent = new ShareContent();
                startActivity(Intent.createChooser(
                        shareContent.startShare(
                                getActivity().getClass().getSimpleName(),
                                ImageUtils.drawImage(getActivity(),
                                        ImageUtils.BRIEF)),"分享天气"));
            }
        });

        imageDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareContent shareContent = new ShareContent();
                startActivity(Intent.createChooser(
                        shareContent.startShare(
                                getActivity().getClass().getSimpleName(),
                                ImageUtils.drawImage(getActivity(),
                                        ImageUtils.DETAIL)),"分享天气"));
            }
        });

        imageFuture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareContent shareContent = new ShareContent();
                startActivity(Intent.createChooser(
                        shareContent.startShare(
                                getActivity().getClass().getSimpleName(),
                                ImageUtils.drawImage(getActivity(),
                                        ImageUtils.FUTURE)),"分享天气"));
            }
        });

    }


}
