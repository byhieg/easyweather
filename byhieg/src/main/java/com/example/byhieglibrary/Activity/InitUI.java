package com.example.byhieglibrary.Activity;

/**
 * Created by shiqifeng on 16-3-22.
 * Contact with byhieg@gmail.com
 */
public interface InitUI {

    /**
     * 这里初始化Activity需要的数据
     */
    void initData() ;

    /**
     * 这里初始化Activity的事件
     */
    void initEvent();

    /**
     * 这里初始化布局内的控件
     */
    void initView();

    void initTheme();

}
