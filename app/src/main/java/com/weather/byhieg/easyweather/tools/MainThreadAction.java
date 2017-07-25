package com.weather.byhieg.easyweather.tools;

import android.os.Handler;
import android.os.Looper;


/**
 * Created by byhieg on 17/5/22.
 * Contact with byhieg@gmail.com
 */

interface IMainThread{

    void post(Runnable runnable);

    void post(Runnable runnable, long time);


}

public class MainThreadAction implements IMainThread{

    private static MainThreadAction sIntance;
    private static final Object LOCK = new Object();
    private Handler mHandler;


    private MainThreadAction(){
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static MainThreadAction getInstance(){
        if (sIntance == null) {
            synchronized (LOCK) {
                if (sIntance == null) {
                    sIntance = new MainThreadAction();
                }
            }
        }

        return sIntance;
    }


    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    @Override
    public void post(Runnable runnable, long time) {
        mHandler.postDelayed(runnable, time);
    }


}
