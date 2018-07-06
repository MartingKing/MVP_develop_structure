package com.dhd.cbmxclient.http.httputils;


/**
 * Created by dhd on 2017/9/22.
 */

public class HttpException extends Exception {
    /**
     * 错误类型
     */
    public ErrorType mErrorType;

    public HttpException(ErrorType errorType) {
        mErrorType = errorType;
    }

    /**
     * 错误类型String获取
     */
    private String mMsg;

    public String getMsg() {
        if (mErrorType == ErrorType.RETURN_ERROR) {
            return mMsg = "服务器返回的错误";
        } else if (mErrorType == ErrorType.NETWORK_ERROR) {
            return mMsg = "本地网络错误或网络框架错误调用返回异常";
        } else {
            return mMsg = "其他未知错误";
        }
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    @Override
    public void printStackTrace() {
        super.printStackTrace();
    }


    public enum ErrorType {
        /**
         * 服务器返回的错误
         */
        RETURN_ERROR,
        /**
         * 本地网络错误或网络框架错误调用返回异常
         */
        NETWORK_ERROR,
        /**
         * token过期
         */
        TOKEN_ERROR,
        /**
         * 其他未知错误
         */
        ERROR
    }
}
