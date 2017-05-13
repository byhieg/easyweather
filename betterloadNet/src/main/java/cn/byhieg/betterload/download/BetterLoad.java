package cn.byhieg.betterload.download;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by byhieg on 17/3/13.
 * Contact with byhieg@gmail.com
 */

public class BetterLoad {

    private static BetterLoad betterLoad;
    private ExecutorService executorService = Executors.newCachedThreadPool();

    private BetterLoad(){}

    public static BetterLoad getInstance(){
        if (betterLoad == null) {
            synchronized (BetterLoad.class) {
                if (betterLoad == null) {
                    betterLoad = new BetterLoad();
                }
            }
        }
        return betterLoad;
    }

    public void download(final DownLoadEntity entity, final IDownLoadListener listener) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                DownLoadRequest downLoadRequest = new DownLoadRequest(entity,listener);
                downLoadRequest.start();
            }
        });
    }
}
