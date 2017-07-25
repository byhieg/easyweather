package cn.byhieg.betterload.download;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.byhieg.betterload.utils.CpuUtils;
import cn.byhieg.betterload.utils.FailureMessage;

/**
 * Created by byhieg on 17/3/13.
 * Contact with byhieg@gmail.com
 */

public class DownLoadRequest {

    private DownLoadHandle downLoadHandle;

    private DownLoadEntity entity;
    private FailureMessage failMessage;
    private IDownLoadTaskListener taskListener;
    private IDownLoadListener listener;
    private ExecutorService downloadService;



    public DownLoadRequest(DownLoadEntity entity, IDownLoadListener listener) {
        this.entity = entity;
        this.listener = listener;
        failMessage = new FailureMessage();
        downLoadHandle = new DownLoadHandle();
        downloadService = Executors.newFixedThreadPool(CpuUtils.getNumCores() + 1);
    }


    public void start() {
        entity = downLoadHandle.queryDownLoadInfo(entity);
        long totalFileSize;
        long hasDownSize = 0;
        hasDownSize += entity.getLoadedData();
        if (entity.getTotal() == 0) {
            failMessage.clear();
            failMessage.setFailureMessage("文件读取失败");
            failMessage.setResultCode(-1);
            MainThreadImpl.getMainThread().post(new Runnable() {
                @Override
                public void run() {
                    listener.onError(entity, failMessage);
                }
            });
            return;
        } else {
            totalFileSize = entity.getTotal();
        }

        if (hasDownSize >= totalFileSize) {
            MainThreadImpl.getMainThread().post(new Runnable() {
                @Override
                public void run() {
                    listener.onCompleted();
                }
            });
            return;
        }
        taskListener = new DownLoadTaskListenerImpl(listener, totalFileSize, hasDownSize);
        taskListener.onStart();
        if (entity.getLoadedData() != entity.getTotal()) {
            entity.setEnd(entity.getTotal() - 1);
            entity.setStart(0);
            createDownLoadTask(entity,taskListener);
        }

    }


    private void createDownLoadTask(DownLoadEntity entity,IDownLoadTaskListener downLoadTaskListener) {
        DownLoadTask downLoadTask;
        downLoadTask = new DownLoadTask.Builder().downLoadEntity(entity).IDownLoadTaskListener
                (downLoadTaskListener).build();
        downloadService.submit(downLoadTask);
    }
}
