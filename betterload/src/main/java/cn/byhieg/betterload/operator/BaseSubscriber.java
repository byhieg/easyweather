package cn.byhieg.betterload.operator;

import android.util.Log;

import cn.byhieg.betterload.utils.FailureMessage;
import rx.Subscriber;

/**
 * Created by byhieg on 17/5/11.
 * Contact with byhieg@gmail.com
 */

public abstract class BaseSubscriber<T> extends Subscriber<T>{


   private FailureMessage failureMessage = new FailureMessage();

    @Override
    public void onError(Throwable e) {
        failureMessage.clear();
        failureMessage.setFailureMessage(e.getMessage());
        failureMessage.setOther(e.getMessage());
        failureMessage.setResultCode(-1);
        Log.e("失败信息",failureMessage.toString());
    }

}
