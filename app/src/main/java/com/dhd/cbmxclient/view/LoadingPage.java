package com.dhd.cbmxclient.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.dhd.cbmxclient.R;
import com.dhd.cbmxclient.http.httputils.NetWorkState;


/**
 * Created by dhd on 2017/9/12.
 */

public abstract class LoadingPage extends FrameLayout {
    private View loadingView;                 // 加载中的界面
    private View errorView;                   // 错误界面
    private View emptyView;                   // 空界面
    public View contentView;                 // 加载成功的界面


    public NetWorkState state = NetWorkState.STATE_UNKNOWN;

    private Context mContext;

    public LoadingPage(Context context) {
        this(context, null);
    }

    public LoadingPage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();//初始化4种界面
    }


    private void init() {
        //把loadingView添加到frameLayout上
        if (loadingView == null) {
            loadingView = createLoadingView();
            this.addView(loadingView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
        //把emptyView添加到frameLayout上
        if (emptyView == null) {
            emptyView = createEmptyView();
            this.addView(emptyView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        }
        //把errorView添加到frameLayout上
        if (errorView == null) {
            errorView = createErrorView();
            this.addView(errorView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        }
        showPage();//根据状态显示界面
    }


    private View createLoadingView() {
        loadingView = LayoutInflater.from(mContext).inflate(R.layout.base_state_loading, null);
        return loadingView;
    }

    private View createEmptyView() {
        emptyView = LayoutInflater.from(mContext).inflate(R.layout.base_state_empty, null);
        return emptyView;
    }

    private View createErrorView() {
        errorView = LayoutInflater.from(mContext).inflate(R.layout.base_state_error, null);
        errorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                state = NetWorkState.STATE_LOADING;
                showPage();
                reLoadData();
            }
        });
        return errorView;
    }


    public void showPage() {
        if (loadingView != null) {
            if (state == NetWorkState.STATE_UNKNOWN || state == NetWorkState.STATE_LOADING) {
                loadingView.setVisibility(View.VISIBLE);
            } else {
                loadingView.setVisibility(View.GONE);
            }
            if (state == NetWorkState.STATE_EMPTY || state == NetWorkState.STATE_ERROR || state == NetWorkState.STATE_SUCCESS) {
                loadingView.setVisibility(View.GONE);
            }
        }

        if (emptyView != null) {
            emptyView.setVisibility(state == NetWorkState.STATE_EMPTY ? View.VISIBLE : View.GONE);
        }

        if (errorView != null) {
            errorView.setVisibility(state == NetWorkState.STATE_ERROR ? View.VISIBLE : View.GONE);
        }

        if (state == NetWorkState.STATE_SUCCESS) {
            if (contentView == null) {
                contentView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
                addView(contentView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                initView();
            }
            contentView.setVisibility(View.VISIBLE);
        } else {
            if (contentView != null) {
                contentView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 3
     * 子类关于View的操作(如setAdapter)都必须在这里面，会因为页面状态不为成功，而binding还没创建就引用而导致空指针。
     */
    protected abstract void initView();

    /**
     * 1
     * 根据网络获取的数据返回状态，每一个子类的获取网络返回的都不一样，所以要交给子类去完成
     */
    protected abstract void reLoadData();


    protected abstract int getLayoutId();
}
