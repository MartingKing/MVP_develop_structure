package com.dhd.cbmxclient.http;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dhd on 2017/12/22.
 * 是否需要缓存
 */

public class CacheInterceptor implements Interceptor {
    private boolean isCache;

    public CacheInterceptor(boolean isCache) {
        this.isCache = isCache;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (isCache) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        if (!isCache) {//直接读取数据
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=0")
                    .removeHeader("Pragma")
                    .build();
        } else {//直接
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
