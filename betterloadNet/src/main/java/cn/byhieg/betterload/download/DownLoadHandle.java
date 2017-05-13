package cn.byhieg.betterload.download;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.byhieg.betterload.network.NetService;
import cn.byhieg.betterload.utils.CpuUtils;
import cn.byhieg.betterload.utils.FailureMessage;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by byhieg on 17/3/13.
 * Contact with byhieg@gmail.com
 */

public class DownLoadHandle {


    private ExecutorService getFileService = Executors.
            newFixedThreadPool(CpuUtils.getNumCores() + 1);

    private volatile int downLoadCount;


    public DownLoadEntity queryDownLoadInfo(final DownLoadEntity entity) {
        entity.setLoadedData(0);
        final Call<ResponseBody> call;

        call = NetService.getInstance().getDownLoadService().getHttpHeader(entity.getUrl(),
                "bytes=" + 0 + "-" + 0);

        executeGetFileWork(call,new GetFileInfoListener(call,entity));
        while (!getFileService.isShutdown() && getCount() != 1) {

        }
        return entity;
    }


    private void executeGetFileWork(Call<ResponseBody> call, IGetFileInfoListener listener) {
        GetFileInfoTask task = new GetFileInfoTask(call, listener);
        getFileService.submit(task);
    }


    private synchronized void setCount() {
        this.downLoadCount++;
    }

    private synchronized int getCount() {
        return this.downLoadCount;
    }



    private class GetFileInfoListener implements IGetFileInfoListener {
        private DownLoadEntity entity;
        private Call<ResponseBody> call;
        private int recount = 3;

        public GetFileInfoListener(Call<ResponseBody> call, DownLoadEntity entity){
            this.entity = entity;
            this.call = call;
        }
        @Override
        public void success(boolean isSupportMulti, boolean isNew, String modified, Long fileSize) {
            entity.setTotal(fileSize);
            setCount();
        }

        @Override
        public void onFailure(FailureMessage failureMessage) {
            if (recount <= 0) {
                setCount();
                if (!getFileService.isShutdown()) {
                    getFileService.shutdown();
                }
            }else {
                recount--;
                executeGetFileWork(call,this);
            }
        }
    }

}

