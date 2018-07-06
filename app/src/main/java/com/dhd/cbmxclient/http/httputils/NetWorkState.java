package com.dhd.cbmxclient.http.httputils;

/**
 * Created by dhd on 2017/9/21.
 * 网络加载状态
 */
public enum NetWorkState {
    /**
     * 网络加载前初始状态
     */
    STATE_UNKNOWN,
    /**
     * 网络加载中状态
     */
    STATE_LOADING,
    /**
     * 网络错误状态
     */
    STATE_ERROR,
    /**
     * 加载List数据为空
     */
    STATE_EMPTY,
    /**
     * 加载成功状态
     */
    STATE_SUCCESS
}
