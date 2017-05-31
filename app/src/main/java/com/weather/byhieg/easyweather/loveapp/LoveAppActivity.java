package com.weather.byhieg.easyweather.loveapp;

import android.annotation.SuppressLint;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.weather.byhieg.easyweather.base.BaseActivity;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoveAppActivity extends BaseActivity {


    @BindView(R.id.web_view)
    public WebView webView;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;



    @Override
    public int getLayoutId() {
        return R.layout.activity_love_app;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);

    }

    @Override
    public void initEvent() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress );
                if(newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    progressBar.setProgress(newProgress );//设置进度值
                }
            }

        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        toolbar.setTitle("喜欢");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        progressBar.setMax(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl("https://github.com/byhieg/easyweather");


    }

    @Override
    public void initTheme() {
        if(MyApplication.nightMode()){
            setTheme(R.style.NightTheme);
        }else {
            setTheme(R.style.DayTheme);
        }
    }

    //设置返回键动作（防止按返回键直接退出程序)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK) {
            if(webView.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                webView.goBack();
                return true;
            }else {//当webview处于第一页面时,直接退出程序
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(MyApplication.nightMode2()){
            initNightView(R.layout.night_mode_overlay);
        }else {
            removeNightView();
        }
    }
}
