package com.weather.byhieg.easyweather.Fragment;


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
import com.weather.byhieg.easyweather.R;
import com.weather.byhieg.easyweather.Tools.ImageUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends BaseFragment {
    public static final String TAG = "com.weather.byhieg.easyweather.Fragment.ShareFragment";


    public ShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView_brief);
        imageView.setImageURI(Uri.fromFile(ImageUtils.drawImage(getActivity(),ImageUtils.BRIEF)));
        view.setOnClickListener(new View.OnClickListener() {
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
    }


}
