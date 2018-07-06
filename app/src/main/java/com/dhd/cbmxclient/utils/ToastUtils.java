package com.dhd.cbmxclient.utils;

import android.view.Gravity;
import android.widget.Toast;

/**
 * <pre>
 *     author: Blankj
 *     time  : 2017/7/18
 *     desc  : 吐司相关工具类
 * </pre>
 */
public class ToastUtils {

    private static Toast mToast;

    public static final void showShortToast(CharSequence c) {
        if (mToast == null) {
            mToast = Toast.makeText(ContextUtils.getContext(), c, Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(c);
        }
        mToast.show();
    }

    public static final void showLongToast(CharSequence c) {
        if (mToast == null) {
            mToast = Toast.makeText(ContextUtils.getContext(), c, Toast.LENGTH_LONG);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText(c);
        }
        mToast.show();
    }

    /**
     * 当前网络状况不佳，请稍后重试！
     */
    public static final void showNetErrorToast() {
        if (mToast == null) {
            mToast = Toast.makeText(ContextUtils.getContext(), "当前网络状况不佳，请稍后重试！", Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText("当前网络状况不佳，请稍后重试！");
        }
        mToast.show();
    }

    /**
     * 没有更多的数据
     */
    public static final void showNoneDataToast() {
        if (mToast == null) {
            mToast = Toast.makeText(ContextUtils.getContext(), "没有更多的数据", Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText("没有更多的数据");
        }
        mToast.show();
    }

    /**
     * 数据加载错误，请稍后重试；
     */
    public static final void showErrorToast() {
        if (mToast == null) {
            mToast = Toast.makeText(ContextUtils.getContext(), "数据加载错误，请稍后重试", Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText("数据加载错误，请稍后重试");
        }
        mToast.show();
    }

    /**
     * 服务器异常
     */
    public static final void showErrorServer() {
        if (mToast == null) {
            mToast = Toast.makeText(ContextUtils.getContext(), "服务器异常，请稍后重试", Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            mToast.setText("服务器异常，请稍后重试");
        }
        mToast.show();
    }

}