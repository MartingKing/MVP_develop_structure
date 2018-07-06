package com.dhd.cbmxclient.ui.fragment.cb_service;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dhd.cbmxclient.R;
import com.dhd.cbmxclient.adapter.CrowerAdapter;
import com.dhd.cbmxclient.base.BaseFragment;
import com.dhd.cbmxclient.bean.CrowerInfo;
import com.dhd.cbmxclient.http.httputils.NetWorkState;
import com.dhd.cbmxclient.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/2/26.
 */

public class CBServicesFragment extends BaseFragment<CBServicesPresenterImpl> implements CBServicesPresenter.View {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private CrowerAdapter mAdapter;
    private List<CrowerInfo> mDatas;

    public static CBServicesFragment newInstance() {
        return new CBServicesFragment();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new CBServicesPresenterImpl();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadData() {
        setState(NetWorkState.STATE_SUCCESS);
        mPresenter.getData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.footerview_group_detail;
    }

    @Override
    protected void initView() {
        mDatas = new ArrayList<>();
        mAdapter = new CrowerAdapter(R.layout.item_crower, mDatas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void getCityList(List<CrowerInfo> infos) {
        LogUtils.e("getCityList: " + infos);
        mAdapter.setNewData(infos);
    }
}
