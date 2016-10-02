package com.weather.byhieg.easyweather.Activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.byhieglibrary.Activity.BaseActivity;
import com.example.byhieglibrary.Utils.LogUtils;
import com.weather.byhieg.easyweather.R;

import butterknife.Bind;

public class WikiResultActivity extends BaseActivity {

    @Bind(R.id.web_view)
    public WebView webView;
    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.progress_bar)
    public ProgressBar progressBar;


    private String tag;
    private String link;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wiki_result;
    }

    @Override
    public void initData() {
        tag = getIntent().getBundleExtra("result").getString("keyWord");
        link = getIntent().getBundleExtra("result").getString("url");
        LogUtils.e("tag",tag);
        LogUtils.e("link",link);
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

    }
}
