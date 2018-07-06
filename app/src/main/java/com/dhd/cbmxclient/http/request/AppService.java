package com.dhd.cbmxclient.http.request;


import com.dhd.cbmxclient.bean.CrowerInfo;
import com.dhd.cbmxclient.http.bean.ApiRes;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by dhd on 2017/12/22.
 */

public interface AppService {
    String HOST = "http://192.168.1.237:8080/";
    String CACHETIME = "2592000";


    /**
     * 获取所有城市
     */
    @GET("all")
    Observable<ApiRes<List<CrowerInfo>>> getCityList();
}




































