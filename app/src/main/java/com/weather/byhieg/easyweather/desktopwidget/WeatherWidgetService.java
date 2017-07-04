package com.weather.byhieg.easyweather.desktopwidget;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class WeatherWidgetService extends Service {

    public WeatherWidgetService() {
    }

    // 更新 widget 的广播对应的action
    private final String ACTION_UPDATE_ALL = "com.widget.UPDATE_ALL";
    // 周期性更新 widget 的周期
    private static final int UPDATE_TIME = 5000;
    // 周期性更新 widget 的线程
    private UpdateThread mUpdateThread;
    private Context mContext;
    // 更新周期的计数
    private int count=0;

    @Override
    public void onCreate() {

        // 创建并开启线程UpdateThread
        mUpdateThread = new UpdateThread();
        mUpdateThread.start();
        mContext = this.getApplicationContext();
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        // 中断线程，即结束线程。
        if (mUpdateThread != null) {
            mUpdateThread.interrupt();
        }
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /*
     * 服务开始时，即调用startService()时，onStartCommand()被执行。
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    private class UpdateThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                count = 0;
                while (true) {
                    count++;
                    Intent updateIntent=new Intent(ACTION_UPDATE_ALL);
                    mContext.sendBroadcast(updateIntent);
                    Thread.sleep(UPDATE_TIME);
                }
            } catch (InterruptedException e) {
                // 将 InterruptedException 定义在while循环之外，意味着抛出 InterruptedException 异常时，终止线程。
                e.printStackTrace();
            }
        }
    }
}
