package cn.byhieg.betterload.download;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by byhieg on 17/3/14.
 * Contact with byhieg@gmail.com
 */

public class MainThreadImpl implements IMainThread{

    private static final MainThreadImpl mainThread = new MainThreadImpl();
    private Handler handler;

    private MainThreadImpl(){
        handler = new Handler(Looper.getMainLooper());
    }

    public static MainThreadImpl getMainThread(){
        return mainThread;
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
