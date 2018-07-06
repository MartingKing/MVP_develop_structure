package com.dhd.cbmxclient.ui.activity.test;

import com.dhd.cbmxclient.R;
import com.dhd.cbmxclient.base.BaseActivity;
import com.dhd.cbmxclient.http.httputils.NetWorkState;

/**
 * Created by Administrator on 2018/2/26.
 */

public class MyPostActivity extends BaseActivity<TestPresenterImpl> implements TestPresenter.View {

    @Override
    protected void initPresenter() {
        mPresenter = new TestPresenterImpl();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadData() {
        setState(NetWorkState.STATE_SUCCESS);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        setActionBar("我的帖子");
    }

    @Override
    public void test() {

    }
}
