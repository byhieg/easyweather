package cn.byhieg.betterload.network;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.byhieg.betterload.download.IDownLoadService;
import cn.byhieg.betterload.interceptor.HeaderInterceptor;
import cn.byhieg.betterload.operator.BaseFlatMapOp;
import cn.byhieg.betterload.utils.FailureMessage;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Function;
import rx.schedulers.Schedulers;

/**
 * Created by byhieg on 17/3/2.
 * Contact with byhieg@gmail.com
 */

public class NetService {


    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;
    private HeaderInterceptor headerInterceptor;

    private NetService() {
        loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }



    private volatile static NetService netService;
    private IDownLoadService downLoadService;

    public static NetService getInstance() {
        if (netService == null) {
            synchronized (NetService.class) {
                if (netService == null) {
                    netService = new NetService();
                }
            }
        }
        return netService;
    }


    public NetService init(String baseUrl) {
        synchronized (this) {
            if (baseUrl.charAt(baseUrl.length() - 1) != '/'){
                baseUrl = baseUrl + "/";
            }
            okHttpClient = new OkHttpClient.Builder().
                    readTimeout(15, TimeUnit.SECONDS).
                    writeTimeout(10, TimeUnit.SECONDS).
                    connectTimeout(10, TimeUnit.SECONDS).
                    addInterceptor(loggingInterceptor).
                    build();

            retrofit = new Retrofit.Builder().
                    addConverterFactory(ScalarsConverterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create()).
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                    baseUrl(baseUrl).
                    client(okHttpClient).
                    build();
            downLoadService = create(IDownLoadService.class);

        }
        return this;
    }

    public NetService init(String baseurl, Map<String, String> headers) {
        synchronized (this) {
            headerInterceptor = new HeaderInterceptor(headers);
            okHttpClient = new OkHttpClient.Builder().
                    readTimeout(15, TimeUnit.SECONDS).
                    writeTimeout(10, TimeUnit.SECONDS).
                    connectTimeout(10, TimeUnit.SECONDS).
                    addInterceptor(loggingInterceptor).
                    addInterceptor(headerInterceptor).
                    build();

            retrofit = new Retrofit.Builder().
                    addConverterFactory(ScalarsConverterFactory.create()).
                    addConverterFactory(GsonConverterFactory.create()).
                    addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                    baseUrl(baseurl).
                    client(okHttpClient).
                    build();
            downLoadService = create(IDownLoadService.class);

        }
        return this;
    }


    public <T> T create(Class<T> clz) {
        return retrofit.create(clz);
    }


    public <T> void asynRequest(final Call<T> requestCall, final IResponseListener<T> resonseListener) {

        final FailureMessage failureMessage = new FailureMessage();
        if (resonseListener == null) {
            return;
        }

        Call<T> call;
        if (requestCall.isExecuted()) {
            call = requestCall.clone();
        } else {
            call = requestCall;
        }


        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                int resultCode = response.code();
                if (response.isSuccessful()) {
                    T result = response.body();
                    if (result == null) {
                        failureMessage.clear();
                        failureMessage.setResultCode(resultCode);
                        failureMessage.setFailureMessage("body为空");
                        resonseListener.onFailure(failureMessage.toString());
                    } else {
                        resonseListener.onSuccess(result);
                    }

                } else {
                    failureMessage.clear();
                    failureMessage.setResultCode(resultCode);
                    failureMessage.setFailureMessage(resultCode + "错误");
                    resonseListener.onFailure(failureMessage.toString());

                }


            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                failureMessage.clear();
                failureMessage.setResultCode(-1);
                failureMessage.setFailureMessage(t.getMessage());
                resonseListener.onFailure(failureMessage.toString());
            }
        });

    }

    public <T> void syncRequest(final Call<T> requestCall,final IResponseListener<T> responseListener){

        final FailureMessage failureMessage = new FailureMessage();
        if (responseListener == null) {
            return;
        }

        Call<T> call;
        if (requestCall.isExecuted()) {
            call = requestCall.clone();
        } else {
            call = requestCall;
        }

        try{
            Response<T> response = call.execute();
            int resultCode = response.code();
            if (response.isSuccessful()) {
                T result = response.body();
                if (result == null) {
                    failureMessage.clear();
                    failureMessage.setResultCode(resultCode);
                    failureMessage.setFailureMessage("body为空");
                    responseListener.onFailure(failureMessage.toString());
                }else {
                    responseListener.onSuccess(result);
                }
            }else {
                failureMessage.clear();
                failureMessage.setResultCode(resultCode);
                failureMessage.setFailureMessage(resultCode + "错误");
                responseListener.onFailure(failureMessage.toString());
            }
        } catch (IOException e) {
            failureMessage.clear();
            failureMessage.setResultCode(-1);
            failureMessage.setFailureMessage(e.getMessage());
            responseListener.onFailure(failureMessage.toString());
        }

    }

    public <T> T syncRequest(final Call<T> requestCall) {
        Call<T> call;
        if (requestCall.isExecuted()) {
            call = requestCall.clone();
        } else {
            call = requestCall;
        }
        try{
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                T result = response.body();
                return result;
            }else {
                return null;
            }
        } catch (IOException e) {
            Logger.e(e.getMessage());
        }
        return null;

    }
//
     public <T,N> void rxRequest(Observable<T> observable, Subscriber<N> subscriber,
                                 BaseFlatMapOp<T,N> baseFlatMapOp){
         observable.subscribeOn(Schedulers.io()).
                 unsubscribeOn(Schedulers.io()).
                 flatMap(baseFlatMapOp).
                 observeOn(AndroidSchedulers.mainThread()).
                 subscribe(subscriber);
     }

     public <T> void rxRequest(Observable<T> observable,Subscriber<T> subscriber) {
         observable.subscribeOn(Schedulers.io()).
                 unsubscribeOn(Schedulers.io()).
                 observeOn(AndroidSchedulers.mainThread()).
                 subscribe(subscriber);
     }

    public IDownLoadService getDownLoadService(){
        return downLoadService;
    }





}
