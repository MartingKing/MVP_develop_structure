package com.dhd.cbmxclient.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dhd.cbmxclient.R;
import com.dhd.cbmxclient.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class HotNewsAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {


    public HotNewsAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(R.layout.item_home_recycler, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Integer item) {
        LogUtils.e(TAG, "convert: item==" + item);
    }
}
