package com.dhd.cbmxclient.http;


import com.dhd.cbmxclient.BuildConfig;
import com.dhd.cbmxclient.http.request.AppService;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by dhd on 2017/5/11.
 */

public class ApiManager {

    private static ApiManager mApiManager;
    private AppService mService;


    private ApiManager() {
    }

    public static ApiManager getInstence() {
        if (mApiManager == null) {
            synchronized (ApiManager.class) {
                if (mApiManager == null) {
                    mApiManager = new ApiManager();
                }
            }

        }
        return mApiManager;
    }


    /**
     * 封装配置API
     */
    public AppService getService() {
        if (mService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getClient(new OkHttpClient.Builder()))
                    .baseUrl(AppService.HOST)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mService = retrofit.create(AppService.class);
        }
        return mService;
    }


    private OkHttpClient getClient(OkHttpClient.Builder builder) {
        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        //设置超时
        builder.connectTimeout(300000, TimeUnit.MILLISECONDS);
        builder.writeTimeout(300000, TimeUnit.MILLISECONDS);
        builder.readTimeout(300000, TimeUnit.MILLISECONDS);
        builder.addInterceptor(new HeaderInterceptord());
        if (BuildConfig.DEBUG_MODE) {
            builder.sslSocketFactory(provideSSLSocketFactory(getTrustManager()));
        }
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }


    public X509TrustManager getTrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] chain,
                    String authType) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        };
    }

    public SSLSocketFactory provideSSLSocketFactory(X509TrustManager trustManager) {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException exception) {
        }
        return (SSLSocketFactory) SSLSocketFactory.getDefault();
    }
}
