package com.example.byhieglibrary.Net;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by byhieg on 16-4-16.
 */
public  class HttpUtils {
    private static HttpUtils myOkHttp;
    private OkHttpClient okHttpClient;
    private Handler handler;
    private Gson mGson;

    private HttpUtils(){
        okHttpClient = new OkHttpClient();
        this.handler = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    private static HttpUtils getInstance(){
        if (myOkHttp == null) {
            synchronized (HttpUtils.class) {
                if (myOkHttp == null) {
                    myOkHttp = new HttpUtils();
                }
            }
        }
        return myOkHttp;
    }

    /**
     * *****************************************************************
     * 各种内部实现方法
     * *****************************************************************
     */
    private String _urlGenerate(String url, String param, Map<String, String> params) throws UnsupportedEncodingException {
        if(!url.contains("?")){
            url = url + "?";
        }
        if(param != null){
            url = url + param;
        }
        if(params != null){
            Set set = params.entrySet();
            Iterator it = set.iterator();
            int i = 0;
            while (it.hasNext()) {
                Map.Entry me = (Map.Entry)it.next();
                String key = URLEncoder.encode((String)me.getKey(),"utf-8");
                String value = URLEncoder.encode((String)me.getValue(),"utf-8");
                url = url + key + "=" + value;
                if (i != set.size() - 1) {
                    url = url + "&&";
                }
                i++;
            }
        }

        return url;
    }

    /**
     * 构建多种上传类型的request
     */
    private Request _buildMultipartFormRequest(String url, File[] files,
                                             String [] fileKeys, HashMap<String,String> params) {

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (params != null) {
            Set set = params.entrySet();
            Iterator it = set.iterator();
            List<String> key = new ArrayList<>();
            List<String> value = new ArrayList<>();
            while(it.hasNext()){
                Map.Entry me = (Map.Entry)it.next();
                key.add((String)me.getKey());
                value.add((String)me.getValue());
            }
            for(int i = 0;i < key.size();i++) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key.get(i) + "\""),
                        RequestBody.create(null,value.get(i)));
            }
        }

        if(files != null){
            RequestBody fileBody = null;
            for(int i = 0; i < files.length ; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(judgeType(fileName)), file);
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder().url(url).post(requestBody).build();
    }

    /**
     * 根据文件名猜测文件类型
     */
    private String judgeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
    /**
     * 异步get请求
     * param null
     */

    private void _get(String url, ResultCallback resultCallback){
        Request request = new Request.Builder().url(url).build();
        handleRequest(resultCallback,request);
    }

    /**
     * 同步的get请求
     */
    private Response _getAsyn(String url) throws Exception{
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        return response;


    }


    /**
     * 异步post请求
     * 键值对形式
     */

    private void _postMap(String url,ResultCallback resultCallback,HashMap<String,String> params){
        Set set = params.entrySet();
        Iterator it = set.iterator();
        List<String> key = new ArrayList<>();
        List<String> value = new ArrayList<>();
        while(it.hasNext()){
            Map.Entry me = (Map.Entry)it.next();
            key.add((String)me.getKey());
            value.add((String)me.getValue());
        }
        FormBody.Builder builder = new FormBody.Builder();
        for(int i = 0 ; i < key.size() ;i ++){
            builder.add(key.get(i),value.get(i));
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        handleRequest(resultCallback,request);

    }

    /**
     * 异步post请求
     * 单个文件上传
     */
    private void _postFile(String url, ResultCallback resultCallback,File file, String fileKey) {
        Request request = _buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, null);
        handleRequest(resultCallback,request);
    }

    /**
     * 异步下载文件
     */
    private void _download(final String url, final String destFileDir, final ResultCallback callback) {
        final Request request = new Request.Builder().url(url).build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(destFileDir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while((len = is.read(buf)) != -1){
                        fos.write(buf,0,len);
                    }
                    fos.flush();
                    execSuccess(file.getAbsolutePath(),callback);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {

                    }
                    try
                    {
                        if (fos != null) fos.close();
                    } catch (IOException e)
                    {
                    }
                }
            }
        });
    }

    private String getFileName(String path)
    {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }





    private void handleRequest(final ResultCallback resultCallback, final Request request) {
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try
                {
                    final String string = response.body().string();
                    if (resultCallback.mType == String.class)
                    {
                       execSuccess(string, resultCallback);
                    } else
                    {
                        Object o = mGson.fromJson(string, resultCallback.mType);
                        execSuccess(o, resultCallback);
                    }

                } catch (IOException e)
                {
                    execFail(response.request(), e, resultCallback);
                } catch (com.google.gson.JsonParseException e)//Json解析的错误
                {
                    execFail(response.request(), e, resultCallback);
                }
            }
        });
    }

    private void execSuccess(final Object o,final ResultCallback resultCallback) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(resultCallback != null){
                    resultCallback.onResponse(o);
                }
            }
        });
    }
    private void execFail(final Request request, final Exception e, final ResultCallback callback)
    {
        handler.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (callback != null)
                    callback.onError(request, (IOException) e);
            }
        });
    }

    /**
     * ************************************************************************
     * 提供对外接口
     * *************************************************************************
     */
    public static String url(String url, String param, Map<String, String> params) {
        try {
            return getInstance()._urlGenerate(url, param, params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void get(String url, ResultCallback resultCallback){
        getInstance()._get(url,resultCallback);
    }

    public static void postFile(String url, ResultCallback resultCallback,File file, String fileKey){
        getInstance()._postFile(url,resultCallback,file,fileKey);
    }
    public static void postMap(String url,ResultCallback resultCallback,HashMap<String,String> params) {
        getInstance()._postMap(url,resultCallback,params);
    }
    public static void download(final String url, final String destFileDir, final ResultCallback callback) {
        getInstance()._download(url,destFileDir,callback);
    }

    public static Response getAsyn(String url) throws Exception{
       return getInstance()._getAsyn(url);
    }

}
