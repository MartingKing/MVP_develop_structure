package com.dhd.cbmxclient.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.dhd.cbmxclient.R;

/**
 * Created by dhd on 2017/9/21.
 * 所有Fragment都应继承SimpleFragment
 * 简单的Fragment可以做一些初始化的工作如RxBus的注册与销毁
 */

public class SimpleFragment extends Fragment {
    public View mRootView;
    private View mToolbar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 设置标题跟返回键
     */
    public void setActionBar(String titleName) {
        setBack();
        setTitle(titleName);
    }

    /**
     * 设置返回键 返回键的描述
     */
    public void setBack() {
        if (mToolbar == null) {
            mToolbar = mRootView.findViewById(R.id.toolbar);
        }
        if (mToolbar != null && mToolbar.getVisibility() != View.VISIBLE) {
            mToolbar.setVisibility(View.VISIBLE);
        }
        View back = mRootView.findViewById(R.id.back);
        if (back != null) {
            back.setVisibility(View.VISIBLE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLeftClick();
                }
            });
        }
    }

    /**
     * 返回按钮点击事件
     */
    private void onLeftClick() {
        getActivity().finish();
    }

    /**
     * 设置标题
     */
    public void setTitle(String titleName) {
        if (mToolbar == null) {
            mToolbar = mRootView.findViewById(R.id.toolbar);
        }
        if (mToolbar != null && mToolbar.getVisibility() != View.VISIBLE) {
            mToolbar.setVisibility(View.VISIBLE);
        }
        if (mToolbar != null) {
            TextView title = mRootView.findViewById(R.id.toolbar_title);
            if (title != null) title.setText(titleName);
        }
    }
}
