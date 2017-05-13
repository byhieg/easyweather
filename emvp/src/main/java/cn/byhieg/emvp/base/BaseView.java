package cn.byhieg.emvp.base;

/**
 * Created by byhieg on 17/5/13.
 * Contact with byhieg@gmail.com
 */

import cn.byhieg.emvp.Utils.FailureMessage;

/**
 * MVP中的View层接口，用来显示基本的View必备信息
 */
public interface BaseView<V> {



    void showDataSuccess(V datas);


    void showDataError(FailureMessage message);

    /**
     * 显示加载进度
     */
    void showProgress();


    /**
     * 隐藏加载进度
     */
    void hideProgress();


    /**
     * 点击重新加载
     */
    void onReload();


    /**
     * 显示加载的View
     */
    void showLoadingView();


    /**
     * 显示无网络的View
     */
    void showNetErrorView();


    /**
     * 显示内容View
     */
    void showContent();


}
