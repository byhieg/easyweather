package com.example.byhieglibrary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.example.byhieglibrary.Activity.InitUI;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements InitUI, View.OnClickListener{

    /**屏幕的宽度*/
    protected int mScreenWidth;
    /**屏幕高度*/
    protected int mScreenHeight;
    /**屏幕密度*/
    protected float mDensity;

    private Toast mToast;

    public int getmScreenWidth() {
        return mScreenWidth;
    }

    public void setmScreenWidth(int mScreenWidth) {
        this.mScreenWidth = mScreenWidth;
    }

    public int getmScreenHeight() {
        return mScreenHeight;
    }

    public void setmScreenHeight(int mScreenHeight) {
        this.mScreenHeight = mScreenHeight;
    }

    public float getmDensity() {
        return mDensity;
    }

    public void setmDensity(float mDensity) {
        this.mDensity = mDensity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setContentView(getLayoutId());
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        setmScreenWidth(metric.widthPixels);
        setmScreenHeight(metric.heightPixels);
        mDensity = metric.density;
        ButterKnife.bind(this);
        initData();
        initView();
        initEvent();

    }

    /**
     * 这里得到Layout的id
     * @return layout布局的id
     */
    public abstract int getLayoutId();

    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }

    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setClass(this, cls);
        startActivity(intent);
    }


//    protected void startActivityForResult(Class<?> cls,int requestCode) {
//        Intent intent=new Intent();
//        intent.setClass(this, cls);
//        super.startActivityForResult(intent, requestCode);
//
//
//    }
//
//    protected void startActivityForResult(Class<?> cls,Bundle bundle,int requestCode) {
//
//        Intent intent=new Intent();
//        intent.putExtras(bundle);
//        intent.setClass(this, cls);
//        super.startActivityForResult(intent, requestCode);
//    }

    public void showToast(String text) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this,text,Toast.LENGTH_SHORT);
        mToast.show();

    }
    @Override
    public void onClick(View v) {

    }
}
