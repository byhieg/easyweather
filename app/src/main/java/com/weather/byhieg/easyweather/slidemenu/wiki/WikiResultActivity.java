package com.weather.byhieg.easyweather.slidemenu.wiki;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.weather.byhieg.easyweather.base.BaseActivity;
import com.weather.byhieg.easyweather.MyApplication;
import com.weather.byhieg.easyweather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WikiResultActivity extends BaseActivity {

    @BindView(R.id.web_view)
    public WebView webView;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.progress_bar)
    public ProgressBar progressBar;


    private String tag;
    private String link;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wiki_result;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        tag = getIntent().getBundleExtra("result").getString("keyWord");
        link = getIntent().getBundleExtra("result").getString("url");
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

    @Override
    public void initView() {
        toolbar.setTitle(tag + "的百度百科");
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        progressBar.setMax(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(link);
    }

    @Override
    public void initTheme() {
        if(MyApplication.nightMode()){
            setTheme(R.style.NightTheme);
        }else {
            setTheme(R.style.DayTheme);
        }
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
