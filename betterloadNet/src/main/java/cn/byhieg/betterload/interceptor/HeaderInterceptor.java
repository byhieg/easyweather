package cn.byhieg.betterload.interceptor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by byhieg on 17/5/11.
 * Contact with byhieg@gmail.com
 */

public class HeaderInterceptor implements Interceptor {

    private Map<String, String> headers = new HashMap<>();


    public HeaderInterceptor(Map<String, String> headers) {
        this.headers = headers;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder builder = chain.request()
                .newBuilder();
        if (headers != null && headers.size() > 0) {
            Set<String> keys = headers.keySet();
            for (String headerKey : keys) {
                builder.addHeader(headerKey,headers.get(headerKey)).build();
            }
        }
        return chain.proceed(builder.build());
    }
}
