package com.dhd.cbmxclient.base;


import com.dhd.cbmxclient.http.Callback;
import com.dhd.cbmxclient.http.HttpUtils;
import com.dhd.cbmxclient.http.httputils.NetWorkState;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by dhd on 2017/5/10.
 */

public class BasePresenter<T extends ViewModule> {
    protected T mView;//指的是界面，也就是BaseFragment或者BaseActivity

    private Callback callback;

    public void attachView(ViewModule viewModule) {
        this.mView = (T) viewModule;
    }

    protected <T> void invoke(Observable<T> observable, Callback<T> callback) {
        this.callback = callback;
        HttpUtils.invoke(mView, observable, callback);
    }

    /**
     * 给子类检查返回集合是否为空
     *
     * @param list
     */
    public void checkState(List list) {
        if (list.size() == 0) {
            if (mView instanceof ViewModule)
                ((ViewModule) mView).setState(NetWorkState.STATE_EMPTY);
            return;
        }
    }

    public void detachView() {
        if (mView != null)
            mView = null;
        if (callback != null) {
            callback.detachView();
        }

    }

    /**
     * 提供方法取消网络请求进行取消订阅
     */
    public void disposeCallback() {
        if (callback != null && callback.getDisposable() != null && !callback.getDisposable().isDisposed()) {
            callback.getDisposable().dispose();
        }
    }

}
