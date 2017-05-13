package cn.byhieg.betterload.download;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.RandomAccessFile;
import java.net.SocketTimeoutException;

import cn.byhieg.betterload.network.NetService;
import cn.byhieg.betterload.utils.FailureMessage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by byhieg on 17/3/14.
 * Contact with byhieg@gmail.com
 */

public class DownLoadTask implements Runnable {

    private final String TAG = DownLoadTask.class.getSimpleName();
    private String saveFileName;
    private IDownLoadTaskListener listener;
    private Call<ResponseBody> call;
    private long fileSizeDownloaded;
    private long needDownSize;
    private DownLoadEntity entity;
    private long CALL_BACK_LENGTH;
    private FailureMessage failureMessage;


    public DownLoadTask(DownLoadEntity entity, IDownLoadTaskListener listener) {
        this.entity = entity;
        this.listener = listener;
        this.saveFileName = entity.getFileName();
        this.needDownSize = entity.getEnd() - (entity.getStart() + entity.getLoadedData());
        this.CALL_BACK_LENGTH = needDownSize / 100;
        failureMessage = new FailureMessage();
    }


    @Override
    public void run() {
        call = NetService.getInstance().getDownLoadService().downloadFile(entity.getUrl(),
                "bytes=" + entity.getStart() + "-" + entity.getEnd());

        ResponseBody result = null;
        try {
            Response<ResponseBody> response = call.execute();
            result = response.body();
            if (response.isSuccessful()) {
                if (writeToFile(result, entity.getStart(), entity.getLoadedData())) {
                    onCompleted();
                }
            } else {
                onError(new Throwable(response.message()));
            }
        } catch (IOException e) {
            onError(new Throwable(e.getMessage()));
        } finally {
            if (result != null) {
                result.close();
            }
        }
    }

    private boolean writeToFile(ResponseBody body, long startSet, long mDownedSet) {
        try {
            File tmpFile = new File(saveFileName);
            if (!tmpFile.exists()) {
                if (!tmpFile.createNewFile()) {
                    return false;
                }
            }
            RandomAccessFile savedFile = new RandomAccessFile(tmpFile, "rwd");
            savedFile.seek(startSet + mDownedSet);
            InputStream inputStream = null;
            try {
                byte[] fileReader = new byte[4096];

                inputStream = body.byteStream();

                while (fileSizeDownloaded < needDownSize) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    savedFile.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    if (fileSizeDownloaded >= CALL_BACK_LENGTH) {
                        onDownLoading(fileSizeDownloaded);
                        needDownSize -= fileSizeDownloaded;
                        fileSizeDownloaded = 0;
                    } else {
                        if (needDownSize < CALL_BACK_LENGTH) {
                            if (fileSizeDownloaded - 1 == needDownSize) {
                                onDownLoading(fileSizeDownloaded);
                                break;
                            }
                        }
                    }
                }
                return true;
            } finally {

                savedFile.close();

                if (inputStream != null) {
                    inputStream.close();
                }
            }

        } catch (IOException e) {
            if (e instanceof InterruptedIOException && !(e instanceof SocketTimeoutException)) {
                onCancel();
            } else {
                onError(e);
            }
            return false;
        }
    }

    private void onStart() {
        listener.onStart();
    }

    private void onCancel() {
        call.cancel();
        call = null;
        listener.onCancel(entity);
    }

    private void onCompleted() {
        call = null;
        listener.onCompleted(entity);
    }

    private void onError(Throwable throwable) {
        failureMessage.clear();
        failureMessage.setResultCode(-1);
        failureMessage.setFailureMessage(throwable.getMessage());
        listener.onError(entity, failureMessage);
    }

    private void onDownLoading(long downSize) {
        listener.onDownLoading(downSize);
        entity.setLoadedData(entity.getLoadedData() + downSize);
    }


    public static final class Builder {
        private DownLoadEntity entity;
        private IDownLoadTaskListener listener;

        public Builder downLoadEntity(DownLoadEntity entity) {
            this.entity = entity;
            return this;
        }

        public Builder IDownLoadTaskListener(IDownLoadTaskListener downLoadTaskListener) {
            this.listener = downLoadTaskListener;
            return this;
        }

        public DownLoadTask build() {
            if (entity.getUrl().isEmpty()) {
                throw new IllegalStateException("DownLoad URL required.");
            }

            if (listener == null) {
                throw new IllegalStateException("DownLoadTaskListener required.");
            }

            if (entity.getEnd() == 0) {
                throw new IllegalStateException("End required.");
            }
            return new DownLoadTask(entity, listener);
        }
    }

}
