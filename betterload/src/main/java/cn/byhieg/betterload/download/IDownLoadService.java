package cn.byhieg.betterload.download;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by byhieg on 17/3/13.
 * Contact with byhieg@gmail.com
 */

public interface IDownLoadService {

    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl, @Header("Range") String range);

    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

    @Streaming
    @GET
    Call<ResponseBody> getHttpHeader(@Url String fileUrl, @Header("Range") String range);
}
