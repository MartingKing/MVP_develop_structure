package com.dhd.cbmxclient.ui.fragment.cb_service;

import com.dhd.cbmxclient.base.ViewModule;
import com.dhd.cbmxclient.bean.CrowerInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 */

public class CBServicesPresenter {

    interface View extends ViewModule {
        void getCityList(List<CrowerInfo> infos);
    }

    interface Presenter {
        void getData();
    }
}
