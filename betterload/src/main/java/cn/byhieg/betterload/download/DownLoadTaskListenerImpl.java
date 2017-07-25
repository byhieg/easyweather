package cn.byhieg.betterload.download;

import android.util.Log;

import cn.byhieg.betterload.utils.FailureMessage;

/**
 * Created by byhieg on 17/3/14.
 * Contact with byhieg@gmail.com
 */

public class DownLoadTaskListenerImpl implements IDownLoadTaskListener{


    private IDownLoadListener listener;
    private long totalSize;
    private long hasDownedSize;
    private boolean isReturnStart;
    private boolean isReturnCancel;
    private boolean isReturnError;

    public DownLoadTaskListenerImpl(IDownLoadListener listener, long totalSize, long hasDownedSize) {
        this.listener = listener;
        this.totalSize = totalSize;
        this.hasDownedSize = hasDownedSize;
    }

    @Override
    public void onStart() {
        if (!isReturnStart){
            MainThreadImpl.getMainThread().post(new Runnable() {
                @Override
                public void run() {
                    listener.onStart((double)hasDownedSize / totalSize);
                }
            });
        }
        isReturnStart = true;
    }

    @Override
    public void onCancel(DownLoadEntity entity) {
        if (!isReturnCancel){
            MainThreadImpl.getMainThread().post(new Runnable() {
                @Override
                public void run() {
                    listener.onCancel();
                }
            });
        }

        isReturnCancel = true;
    }

    @Override
    public void onDownLoading(long downSize) {
        hasDownedSize += downSize;
        MainThreadImpl.getMainThread().post(new Runnable() {
            @Override
            public void run() {
                double percent = (double)hasDownedSize / totalSize;
                listener.onDownloading(percent);
                Log.e("hasDownedSize", percent + "");

            }
        });
    }

    @Override
    public void onCompleted(DownLoadEntity entity) {
        MainThreadImpl.getMainThread().post(new Runnable() {
            @Override
            public void run() {
                listener.onCompleted();
            }
        });
    }

    @Override
    public void onError(final DownLoadEntity entity, final FailureMessage message) {
        if (!isReturnError) {
            MainThreadImpl.getMainThread().post(new Runnable() {
                @Override
                public void run() {
                    listener.onError(entity,message);
                }
            });
        }

        isReturnError = true;
    }
}
