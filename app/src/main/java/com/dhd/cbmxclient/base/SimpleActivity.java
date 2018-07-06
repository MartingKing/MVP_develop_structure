package com.dhd.cbmxclient.base;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhd.cbmxclient.R;
import com.dhd.cbmxclient.app.ActivityManager;
import com.dhd.cbmxclient.utils.DeviceUtils;
import com.githang.statusbar.StatusBarCompat;

/**
 * Created by zengwendi on 2017/9/21.
 * 所有activity继承的简单activity实现了所有的activity生命周期方法
 * 简单的activity可以做一些初始化的工作
 */

public class SimpleActivity extends AppCompatActivity {
    public Toolbar mToolbar;
    public ImageView mIvBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(this);
        setStatusBarColor(false);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getActivityManager().removeActivity(this);
    }

    /**
     * 设置状态栏颜色默认为白色
     * 如果需要更改不要调用super
     */
    public void setStatusBarColor(boolean lightStatusBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || DeviceUtils.checkMIUI() || DeviceUtils.checkFlyme())
            StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
        else
            StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.color999));
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
            mToolbar = findViewById(R.id.toolbar);
        }
        if (mToolbar != null && mToolbar.getVisibility() != View.VISIBLE) {
            mToolbar.setVisibility(View.VISIBLE);
        }
        mIvBack = findViewById(R.id.back);
        if (mIvBack != null) {
            mIvBack.setVisibility(View.VISIBLE);
            mIvBack.setOnClickListener(new View.OnClickListener() {
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
    protected void onLeftClick() {
        finish();
    }

    /**
     * 设置标题
     */
    public void setTitle(String titleName) {
        if (mToolbar == null) {
            mToolbar = findViewById(R.id.toolbar);
        }
        if (mToolbar != null && mToolbar.getVisibility() != View.VISIBLE) {
            mToolbar.setVisibility(View.VISIBLE);
        }
        TextView title = findViewById(R.id.toolbar_title);
        if (title != null) title.setText(titleName);
    }

    /**
     * 设置特色标题
     */
    public void setDraTitle(int draId) {
        if (mToolbar == null) {
            mToolbar = findViewById(R.id.toolbar);
        }
        if (mToolbar != null && mToolbar.getVisibility() != View.VISIBLE) {
            mToolbar.setVisibility(View.VISIBLE);
        }
        TextView title = findViewById(R.id.toolbar_title);
        if (title != null) {
            title.setBackgroundResource(R.mipmap.img_toolbar_logo_white);
        }
    }

}
