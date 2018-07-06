package com.dhd.cbmxclient.http;

import com.dhd.cbmxclient.base.ViewModule;
import com.dhd.cbmxclient.http.bean.ApiRes;
import com.dhd.cbmxclient.http.httputils.HttpException;
import com.dhd.cbmxclient.http.httputils.NetWorkState;
import com.dhd.cbmxclient.http.httputils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by quantan.liu on 2017/3/21.
 */

public class HttpUtils {
    public static List<Callback> mCallBacks = new ArrayList<>();

    /**
     * 普通get 请求
     *
     * @param viewModule
     * @param observable
     * @param callback
     * @param <T>
     */
    public static <T> void invoke(final ViewModule viewModule, Observable<T> observable, final Callback<T> callback) {
        /**
         * 如果网络失败直接返回错误
         */
        if (!NetworkUtils.isConnected() && !callback.getListener().isCache()) {
            //自定义错误返回网络错误
            callback.getListener().onError(new HttpException(HttpException.ErrorType.NETWORK_ERROR));
            //根据设置是否显示toast
            if (callback.getListener().isShowToast()) {
                viewModule.showToast("网络连接已断开");
            }
            //根据设置是否错误界面
            if (callback.getListener().isErrorPage() && viewModule != null) {
                viewModule.setState(NetWorkState.STATE_ERROR);
            }
            return;
        }
        mCallBacks.add(callback);
        callback.setViewModule(viewModule);
        callback.setObservable(observable);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        callback.onSubscribe(d);
                    }

                    @Override
                    public void onNext(T t) {
                        if (t instanceof ApiRes) {
                            ApiRes res = (ApiRes) t;
                            if (res.getErrNum() != 2) {//token没有过期移除
                                mCallBacks.remove(callback);
                            } else {//token过期取消所有请求
                                for (Callback call : mCallBacks) {
                                    if (call.getDisposable() != null && !call.getDisposable().isDisposed()) {
                                        call.getDisposable().dispose();
                                    }
                                }
                            }
                        }
                        callback.onNext(t);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mCallBacks.remove(callback);
                        callback.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        callback.onComplete();
                    }
                });

    }

    public static void invokeLoseTokenCallback() {
        for (Callback callback : mCallBacks) {
            invoke(callback.getViewModule(), callback.getObservable(), callback);
        }
        mCallBacks.clear();
    }

    public static void cleanLoseTokenCallback() {
        mCallBacks.clear();
    }

}



