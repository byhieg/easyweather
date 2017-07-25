package cn.byhieg.betterload.download;

import android.text.TextUtils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import cn.byhieg.betterload.network.NetService;
import cn.byhieg.betterload.utils.FailureMessage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by byhieg on 17/3/13.
 * Contact with byhieg@gmail.com
 */

public class GetFileInfoTask implements Runnable{

    private Call<ResponseBody> call;
    private IGetFileInfoListener listener;
    private FailureMessage failureMessage;

    public GetFileInfoTask(Call<ResponseBody> call, IGetFileInfoListener listener) {
        this.call = call;
        this.listener = listener;
        failureMessage = new FailureMessage();
    }

    @Override
    public void run() {
        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                if (listener != null) {
                    listener.success((!TextUtils.isEmpty(response.headers().get("Content-Range")) &&
                            !TextUtils.isEmpty(response.headers().get("Content-Length"))),
                            response.code() != 206,
                            response.headers().get("Last-Modified"),
                            Long.parseLong(response.headers().get("Content-Range").split("/")[1]));
                }
            }else {
                if (listener != null) {
                    failureMessage.clear();
                    failureMessage.setResultCode(-1);
                    failureMessage.setFailureMessage("response请求失败");
                    listener.onFailure(failureMessage);
                }

            }
        } catch (Exception e){
            if (listener != null) {
                failureMessage.clear();
                failureMessage.setFailureMessage(e.getMessage());
                listener.onFailure(failureMessage);
            }

        }finally {
            if (response != null) {
                if (response.body() != null) {
                    ((ResponseBody) response.body()).close();
                }
            }

        }
    }
}
