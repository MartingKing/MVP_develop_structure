package com.dhd.cbmxclient.http;
import com.dhd.cbmxclient.utils.DeviceUtils;
import com.dhd.cbmxclient.utils.SystemUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by dhd on 2016/12/13.
 */

public class HeaderInterceptord implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        Request newRequest = addParam(oldRequest);
        return chain.proceed(newRequest);
    }

    /**
     * 添加公共参数
     *
     * @param oldRequest
     * @return
     */
    private Request addParam(Request oldRequest) {
        String VCUA = new StringBuilder("platform=").append("Android&version=")//app平台
                .append(DeviceUtils.getVersion())//app版本
                .append("&brand=").append(SystemUtil.getDeviceBrand())//手机版本
                .append("&sys_version=").append(SystemUtil.getSystemVersion()).toString();//手机系统版本
        Request.Builder builder = oldRequest.newBuilder()
                .header("VCUA", VCUA);
        return builder.build();
    }
}
