package com.dhd.cbmxclient.ui.fragment.cb_cycler;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dhd.cbmxclient.R;
import com.dhd.cbmxclient.adapter.CircleAdapter;
import com.dhd.cbmxclient.base.BaseFragment;
import com.dhd.cbmxclient.http.httputils.NetWorkState;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/2/26.
 */

public class CBCyclerFragment extends BaseFragment<CBCyclerPresenterImpl> implements CBCyclerPresenter.View {

    @BindView(R.id.circle_recyclerview)
    RecyclerView mRecyclerview;
//    @BindView(R.id.fab_add_task)
//    FloatingActionButton mFabAddTask;
    CircleAdapter mCircleAdapter;
    private View mHeaderView;

    public static CBCyclerFragment newInstance() {
        return new CBCyclerFragment();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new CBCyclerPresenterImpl();
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
        return R.layout.frag_cb_cycle;
    }

    @Override
    protected void initView() {
        mHeaderView = View.inflate(getActivity(), R.layout.head_circle, null);
        List<Integer> mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add(i);
        }
        mCircleAdapter = new CircleAdapter(R.layout.adapter_circle_item, mDatas);
        mCircleAdapter.addHeaderView(mHeaderView);
        mRecyclerview.setAdapter(mCircleAdapter);

        mCircleAdapter.setNewData(mDatas);
    }
}
