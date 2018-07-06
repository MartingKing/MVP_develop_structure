package com.dhd.cbmxclient.ui.fragment.cb_service;

import com.dhd.cbmxclient.base.BasePresenter;
import com.dhd.cbmxclient.bean.CrowerInfo;
import com.dhd.cbmxclient.http.AppApiManager;
import com.dhd.cbmxclient.http.Callback;
import com.dhd.cbmxclient.http.bean.ApiRes;
import com.dhd.cbmxclient.http.httputils.HttpOnNextListener;

import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */

public class CBServicesPresenterImpl extends BasePresenter<CBServicesPresenter.View> implements CBServicesPresenter.Presenter{


    @Override
    public void getData() {
        HttpOnNextListener listener = new HttpOnNextListener<List<CrowerInfo>>() {
            @Override
            public void onNext(List<CrowerInfo> infos) {
                mView.getCityList(infos);
            }
        };
        listener.setErrorPage(false).setLoadingDialog(false);
        invoke(AppApiManager.getInstence().getService().getCityList(), new Callback<ApiRes<List<CrowerInfo>>>(listener));
    }
}
