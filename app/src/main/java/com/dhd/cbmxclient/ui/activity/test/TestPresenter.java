package com.dhd.cbmxclient.ui.activity.test;

import com.dhd.cbmxclient.base.ViewModule;

/**
 * Created by Administrator on 2018/2/26.
 */

public interface TestPresenter {

    interface View extends ViewModule {
        void test();
    }

    interface Presenter {
        void test();
    }
}
