package cn.byhieg.betterload.download;

import cn.byhieg.betterload.utils.FailureMessage;

/**
 * Created by byhieg on 17/3/14.
 * Contact with byhieg@gmail.com
 */

public interface IDownLoadTaskListener {

    void onStart();

    void onCancel(DownLoadEntity entity);

    void onDownLoading(long downSize);

    void onCompleted(DownLoadEntity entity);

    void onError(DownLoadEntity entity, FailureMessage message);
}
