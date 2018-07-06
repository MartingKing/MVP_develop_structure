package com.dhd.cbmxclient.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dhd.cbmxclient.R;
import com.dhd.cbmxclient.bean.CrowerInfo;

import java.util.List;

/**
 * Created by Administrator on 2018/5/22.
 */

public class CrowerAdapter extends BaseQuickAdapter<CrowerInfo, BaseViewHolder> {


    public CrowerAdapter(int layoutResId, @Nullable List<CrowerInfo> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, CrowerInfo item) {
        holder.setText(R.id.tv_name, item.getCityname());
        holder.setText(R.id.tv_url, item.getCityurl());
    }
}
