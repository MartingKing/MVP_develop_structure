package com.dhd.cbmxclient.view.smartrefresh;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.dhd.cbmxclient.R;

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/09/22
 * 描述 :
 */

public class RefreshLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.refresh_base_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }

}
