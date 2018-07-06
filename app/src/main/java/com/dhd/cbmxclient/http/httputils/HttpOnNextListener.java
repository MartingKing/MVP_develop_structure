package com.dhd.cbmxclient.http.httputils;


import com.dhd.cbmxclient.http.bean.ApiRes;

/**
 * 回调处理
 * Created by dhd on 2017/5/12.
 */
public abstract class HttpOnNextListener<T> {
    /**
     * 是否弹出错误Toast
     */
    private boolean isShowToast = true;
    /**
     * 是否显示错误界面
     */
    private boolean isCache = false;
    /**
     * 是否显示错误界面
     */
    private boolean isErrorPage = true;
    /**
     * 是否显示错误界面
     */
    private boolean isLoadingDialog = false;

    /**
     * 开始网络进行请求或者提交
     */
    public void onStart() {

    }

    /**
     * 成功后回调方法
     *
     * @param t
     */
    public abstract void onNext(T t);

    /**
     * 失败后服务端返回的错误信息只针对失败
     */
    public void getApiRes(ApiRes res) {
    }

    /**
     * 緩存回調結果
     *
     * @param t
     */
    public void onCacheNext(T t) {

    }

    /**
     * 失败或者错误方法包含网络错误服务端返回的错误
     * 主动调用，更加灵活
     */
    public void onError(Throwable e) {

    }

    /**
     * token过期
     */
    public void onTokenError() {

    }

    /**
     * 取消回調
     */
    public void onCancel() {

    }

    /**
     * 完成订阅
     */
    public void onComplete() {

    }

    /**
     * 下载回调进度
     */
    public void onProgress(int progress) {

    }

    /**
     * 是否显示toast
     * @return
     */
    public boolean isShowToast() {
        return isShowToast;
    }

    /**
     * 是否显示错误界面
     * @return
     */
    public boolean isErrorPage() {
        return isErrorPage;
    }

    /**
     * 是否读取缓存
     * @return
     */
    public boolean isCache() {
        return isCache;
    }

    /**
     * 是否显示dialog
     * @return
     */
    public boolean isLoadingDialog() {
        return isLoadingDialog;
    }
    /**
     * 是否缓存数据 默认是false
     *
     * @param cache =true显示错误界面
     */
    public HttpOnNextListener<T> setCache(boolean cache) {
        isCache = cache;
        return this;
    }

    /**
     * 是否弹出错误Toast 默认是true
     *
     * @param showToast =true弹出toast
     */
    public HttpOnNextListener<T> setShowToast(boolean showToast) {
        isShowToast = showToast;
        return this;
    }


    /**
     * 是否弹出显示错误界面 默认是true
     *
     * @param errorPage =true显示错误界面
     */
    public HttpOnNextListener<T> setErrorPage(boolean errorPage) {
        isErrorPage = errorPage;
        return this;
    }
    /**
     * 是否显示dialog 默认是true
     *
     * @param loadingDialog =true显示错误界面
     */
    public HttpOnNextListener<T> setLoadingDialog(boolean loadingDialog) {
        isLoadingDialog = loadingDialog;
        return this;
    }
}
