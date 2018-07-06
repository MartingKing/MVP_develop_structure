package com.dhd.cbmxclient.http;


import com.dhd.cbmxclient.base.ViewModule;
import com.dhd.cbmxclient.http.bean.ApiRes;
import com.dhd.cbmxclient.http.httputils.HttpException;
import com.dhd.cbmxclient.http.httputils.HttpOnNextListener;
import com.dhd.cbmxclient.http.httputils.NetWorkState;
import com.dhd.cbmxclient.http.httputils.NetworkUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by dhd on 2017/5/10.
 * 网络请求回调
 */

public class Callback<T> implements Observer<T> {

    private ViewModule mViewModule;

    private HttpOnNextListener mListener;

    private Observable<T> mObservable;

    private Disposable mDisposable;

    public Disposable getDisposable() {
        return mDisposable;
    }

    public ViewModule getViewModule() {
        return mViewModule;
    }

    public HttpOnNextListener getListener() {
        return mListener;
    }

    public Callback(HttpOnNextListener listener) {
        mListener = listener;
    }

    public void setViewModule(ViewModule module) {
        this.mViewModule = module;
    }


    public Observable<T> getObservable() {
        return mObservable;
    }

    public void setObservable(Observable<T> observable) {
        mObservable = observable;
    }

    public void detachView() {
        if (mViewModule != null) {
            mViewModule = null;
        }
        if (mListener != null) {
            mListener = null;
        }
        if (mObservable != null) {
            mObservable = null;
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mListener != null) {
            mListener.onStart();
        }
        mDisposable = d;
        if (mViewModule != null) {
            mViewModule.bindSubscription(d);
        }
        if (mViewModule != null && mListener != null && mListener.isLoadingDialog()) {//是否显示Loading
            mViewModule.showLoading();
        }
    }

    @Override
    public void onNext(T value) {
        if (value instanceof ApiRes) {
            onResponse((ApiRes<T>) value);
        } else {
            mListener.onError(new HttpException(HttpException.ErrorType.RETURN_ERROR));
            if (mListener.isShowToast() && mViewModule != null) {
                mViewModule.showToast("数据异常");
            }
            if (mListener.isErrorPage() && mViewModule != null) {
                mViewModule.setState(NetWorkState.STATE_ERROR);
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        onfail(e);
        if (mListener != null) {
            mListener.onComplete();
        }
        if (mViewModule != null && mListener != null && mListener.isLoadingDialog()) {//是否隐藏Loading
            mViewModule.hideLoading();
        }
    }

    @Override
    public void onComplete() {
        if (mListener != null) {
            mListener.onComplete();
        }
        if (mViewModule != null && mListener != null && mListener.isLoadingDialog()) {//是否隐藏Loading
            mViewModule.hideLoading();
        }
    }

    /**
     * 统一处理成功回掉
     */
    public void onResponse(ApiRes<T> res) {
        if (res == null) {//未知错误
            onfail(new Throwable());
            mListener.onError(new HttpException(HttpException.ErrorType.ERROR));
            return;
        }
        if (mListener.isCache()) {//缓存数据直接返回结果
            mListener.onCacheNext(res.getRetData());
            return;
        }
        if (res.getErrNum() == 1) {//服务器返回的错误
            if (mViewModule != null && mListener.isShowToast()) {//服务端返回错误并且显示toast直接显示
                mViewModule.showToast(res.getRetMsg());
            }
            if (mViewModule != null && mListener.isErrorPage()) {//是否显示错误界面
                mViewModule.setState(NetWorkState.STATE_ERROR);
            }
            mListener.onError(new HttpException(HttpException.ErrorType.RETURN_ERROR));
            return;
        }
        if (res.getErrNum() == 2) {//token过期
            mListener.onTokenError();
            // TODO: 2018/2/26 重新登录
            return;
        }
        if (res.getErrNum() == 3) {//token过期
            // TODO: 2018/2/26 重新登录
            return;
        }
        if (res.getErrNum() == 0) {//正常返回
            if (mViewModule != null && mListener.isErrorPage())
                mViewModule.setState(NetWorkState.STATE_SUCCESS);
            mListener.onNext(res.getRetData());  //网络数据
        }


    }

    public void onfail(Throwable e) {
        String toastText;
        if (!NetworkUtils.isAvailableByPing()) {
            mListener.onError(new HttpException(HttpException.ErrorType.NETWORK_ERROR));
            toastText = "你连接的网络有问题，请检查网络连接状态";
        } else if (e instanceof retrofit2.HttpException) {
            mListener.onError(new HttpException(HttpException.ErrorType.ERROR));
            toastText = "服务器异常";
        } else {
            mListener.onError(new HttpException(HttpException.ErrorType.ERROR));
            toastText = "数据异常";
        }
        if (mListener.isShowToast() && mViewModule != null) {
            mViewModule.showToast(toastText);
        }
        if (mListener.isErrorPage() && mViewModule != null) {
            mViewModule.setState(NetWorkState.STATE_ERROR);
        }
    }
}
