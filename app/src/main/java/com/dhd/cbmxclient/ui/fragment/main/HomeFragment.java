package com.dhd.cbmxclient.ui.fragment.main;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.dhd.cbmxclient.R;
import com.dhd.cbmxclient.adapter.HotNewsAdapter;
import com.dhd.cbmxclient.base.BaseFragment;
import com.dhd.cbmxclient.http.httputils.NetWorkState;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/2/26.
 */

public class HomeFragment extends BaseFragment<HomePresenterImpl> implements HomePresenter.View {

    MZBannerView mBanner;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private View mHeaderView;
    private int[] mBannerPics = {
            R.mipmap.banner1, R.mipmap.banner2,
            R.mipmap.banner3, R.mipmap.banner4};

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new HomePresenterImpl();
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
        return R.layout.frag_cb_home;
    }

    @Override
    protected void initView() {
        mHeaderView = View.inflate(getContext(), R.layout.frag_home_headerview, null);
        mBanner = mHeaderView.findViewById(R.id.banner);
        // 设置数据
        List<Integer> pic = new ArrayList<>();
        for (int i = 0; i < mBannerPics.length; i++) {
            pic.add(mBannerPics[i]);
        }
        mBanner.setPages(pic, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Integer> mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add(i);
        }
        HotNewsAdapter adapter = new HotNewsAdapter(R.layout.item_home_recycler, mDatas);
        adapter.addHeaderView(mHeaderView);
        mRecyclerview.setAdapter(adapter);
        adapter.setNewData(mDatas);
    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanner.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.start();//开始轮播
    }
}
