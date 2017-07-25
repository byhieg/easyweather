package cn.byhieg.betterload.network;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by byhieg on 17/5/11.
 * Contact with byhieg@gmail.com
 */

public interface BaseApiService {

    //非rx的ApiService
    @GET("{url}")
    <T> Call<T> get(@Path("url") String url);

    @GET("{url}")
    <T> Call<T> getWithMaps(@Path("url") String url, @QueryMap Map<String, String> maps);

    @GET("{url}")
    <T> Call<T> getWithId(@Path("url") String url, @Query("id") String id);


    @POST("{url}")
    <T> Call<T> postWithMaps(@Path("url") String url,@QueryMap Map<String, String> maps);


    //rx的ApiService
    @GET("{url}")
    <T> Observable<BaseResponseEntity<T>> rxGet(@Path("url") String url);

    @GET("{url}")
    <T> Observable<T> rxGetWithMaps(@Path("url") String url, @QueryMap Map<String, String> maps);

    @GET("{url}")
    <T> Observable<T> rxGetWithId(@Path("url") String url, @Query("id") String id);

    @POST("{url}")
    <T> Observable<T> rxPostWithMaps(@Path("url") String url,@QueryMap Map<String, String> maps);


}
