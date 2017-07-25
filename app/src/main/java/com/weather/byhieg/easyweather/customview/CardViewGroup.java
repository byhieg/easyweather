package com.weather.byhieg.easyweather.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.weather.byhieg.easyweather.tools.ScreenUtil;

/**
 * Created by shiqifeng on 2016/9/12.
 * Mail:byhieg@gmail.com
 */
public class CardViewGroup extends LinearLayout{


    private int screenWidth;
    public CardViewGroup(Context context) {
        super(context);
        init(context);
    }

    public CardViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CardViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        screenWidth = ScreenUtil.getScreenW(context);
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int height = 0;
        int count = getChildCount();
        for(int i = 0;i < count;i++) {
            View child = getChildAt(i);
            MarginLayoutParams params = (MarginLayoutParams)child.getLayoutParams();
            int leftMargin = params.leftMargin;
            int topMargin = params.topMargin;
            int rightMargin = params.rightMargin;
            int bottomMargin = params.bottomMargin;
            height += topMargin;
            child.layout(leftMargin,height,screenWidth - rightMargin,height + child.getMeasuredHeight());
            height += child.getMeasuredHeight() + bottomMargin;
        }
    }

}