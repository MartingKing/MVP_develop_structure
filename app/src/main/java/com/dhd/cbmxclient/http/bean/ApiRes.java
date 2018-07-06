package com.dhd.cbmxclient.http.bean;

/**
 * Created by dhd on 2017/9/27.
 */
public class ApiRes<T> {
    private int errNum;
    private String retMsg;
    private T retData;

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public T getRetData() {
        return retData;
    }

    public void setRetData(T retData) {
        this.retData = retData;
    }
}
