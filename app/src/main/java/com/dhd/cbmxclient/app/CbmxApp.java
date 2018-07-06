package com.dhd.cbmxclient.app;

import android.app.Application;
import android.content.Context;

import com.dhd.cbmxclient.R;
import com.dhd.cbmxclient.utils.ContextUtils;
import com.dhd.cbmxclient.view.smartrefresh.NormalRefreshHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

/**
 * Created by Administrator on 2018/2/26.
 */

public class CbmxApp extends Application {

    public static CbmxApp mInstance;

    static {
        //初始化刷新控件 SmartRefreshLayout 构建器
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.white, R.color.tv_normal);//全局设置主题颜色
                layout.setHeaderHeight(60);
                layout.setFooterHeight(40);
                layout.setDragRate(0.8F);
                layout.setEnableLoadmoreWhenContentNotFull(false);
                return new NormalRefreshHeader(context);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ContextUtils.init(this);
    }

    /**
     * 获取类实例对象
     *
     * @return MyApplication
     */
    public static CbmxApp getInstance() {
        if (mInstance != null && mInstance instanceof CbmxApp) {
            return mInstance;
        } else {
            mInstance = new CbmxApp();
            return mInstance;
        }
    }
}
