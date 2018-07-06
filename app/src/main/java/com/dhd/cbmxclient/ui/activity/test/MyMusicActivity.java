package com.dhd.cbmxclient.ui.activity.test;

import android.os.Bundle;
import android.widget.TextView;

import com.dhd.cbmxclient.R;
import com.dhd.cbmxclient.base.BaseActivity;
import com.dhd.cbmxclient.http.httputils.NetWorkState;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/2/26.
 */

public class MyMusicActivity extends BaseActivity<TestPresenterImpl> implements TestPresenter.View {

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
        setActionBar("我的音乐");
    }

    @Override
    public void test() {

    }

}
