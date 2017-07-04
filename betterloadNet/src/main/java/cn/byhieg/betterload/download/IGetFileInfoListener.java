package cn.byhieg.betterload.download;

import cn.byhieg.betterload.utils.FailureMessage;

/**
 * Created by byhieg on 17/3/13.
 * Contact with byhieg@gmail.com
 */

public interface IGetFileInfoListener {

    void success(boolean isSupportMulti, boolean isNew, String modified, Long fileSize);

    void onFailure(FailureMessage failureMessage);
}
