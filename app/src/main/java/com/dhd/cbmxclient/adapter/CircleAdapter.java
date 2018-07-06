package com.dhd.cbmxclient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dhd.cbmxclient.R;
import com.dhd.cbmxclient.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/12/14 0014.
 */

public class CircleAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private List<Integer> data;

    public CircleAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(R.layout.adapter_circle_item, data);
        this.data = data;
    }

    @Override
    protected void convert(BaseViewHolder holder, Integer item) {
        LogUtils.e(TAG, "convert: item==" + item);
    }
}
