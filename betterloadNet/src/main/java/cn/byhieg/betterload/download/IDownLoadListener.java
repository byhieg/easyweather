package cn.byhieg.betterload.download;

import cn.byhieg.betterload.utils.FailureMessage;

/**
 * Created by byhieg on 17/3/13.
 * Contact with byhieg@gmail.com
 */

public interface IDownLoadListener {

    void onStart(double percent);
    void onCancel();
    void onDownloading(double percent);
    void onCompleted();
    void onError(DownLoadEntity downLoadEntity, FailureMessage message);
}
