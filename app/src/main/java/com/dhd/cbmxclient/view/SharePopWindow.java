package com.dhd.cbmxclient.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.dhd.cbmxclient.R;
import com.dhd.cbmxclient.i.SharePopWindowCliclLinstener;

/**
 * Created by Administrator on 2018/1/11.
 * 投资页的分享弹窗
 */

public class SharePopWindow extends PopupWindow implements View.OnClickListener {

    private Context mContext;
    private View mPopView;
    private SharePopWindowCliclLinstener mSharePopWindowCliclLinstener;

    public SharePopWindow(Context context, View popView, SharePopWindowCliclLinstener mSharePopWindowCliclLinstener) {
        this.mSharePopWindowCliclLinstener = mSharePopWindowCliclLinstener;
        this.mContext = context;
        this.mPopView = popView;
        initView();
    }

    private void initView() {
        mPopView = View.inflate(mContext, R.layout.share_pop, null);
        setContentView(mPopView);
        LinearLayout wxShare = mPopView.findViewById(R.id.ll_weixin_share);
        LinearLayout wxCycle = mPopView.findViewById(R.id.ll_wexin_cycle);
        LinearLayout QQShare = mPopView.findViewById(R.id.ll_qq_share);
        TextView mTvDissmiss = mPopView.findViewById(R.id.tv_dismiss);
        wxShare.setOnClickListener(this);
        wxCycle.setOnClickListener(this);
        QQShare.setOnClickListener(this);
        mTvDissmiss.setOnClickListener(this);
        //设置弹出窗体的高
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置弹出窗体可点击
        this.setFocusable(true);
        this.setTouchable(true);

        //view添加OnTouchListener监听判断获取触屏位置如果在布局外面则销毁弹出框
        mPopView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mPopView.findViewById(R.id.share_content).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        update();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_weixin_share:
                mSharePopWindowCliclLinstener.onWeixinShare();
                dismiss();
                break;
            case R.id.ll_wexin_cycle:
                mSharePopWindowCliclLinstener.onWeixinCycleShare();
                dismiss();
                break;
            case R.id.ll_qq_share:
                mSharePopWindowCliclLinstener.onQQShare();
                dismiss();
                break;
            case R.id.tv_dismiss:
                dismiss();
                break;

        }
    }
}
