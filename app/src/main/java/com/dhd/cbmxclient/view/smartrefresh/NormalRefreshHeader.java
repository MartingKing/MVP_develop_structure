package com.dhd.cbmxclient.view.smartrefresh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dhd.cbmxclient.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Author : zhoujiulong
 * Email : 754667445@qq.com
 * Time : 2017/09/22
 * 描述 :
 */

public class NormalRefreshHeader extends RelativeLayout implements RefreshHeader {

    private ImageView mImageViewAni;
    private TextView mTextViewMsg;
    private ValueAnimator mRefreshAnim;
    private ValueAnimator mRefreshAnim2;
    protected int mPaddingTop = 20;
    protected int mPaddingBottom = 20;
    protected int mFinishDuration = 500;
    protected SpinnerStyle mSpinnerStyle = SpinnerStyle.Scale;
    protected RefreshKernel mRefreshKernel;
    protected int mBackgroundColor;

    private int mWidth;

    public NormalRefreshHeader(Context context) {
        this(context, null);
    }

    public NormalRefreshHeader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NormalRefreshHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public NormalRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
    }

    private void initView(Context context, @Nullable AttributeSet attrs) {
        DensityUtil density = new DensityUtil();

        View viewParent = View.inflate(context, R.layout.refresh_normal_header, null);
        mImageViewAni = viewParent.findViewById(R.id.ev_icon);
        mTextViewMsg = viewParent.findViewById(R.id.tv_msg);
        LayoutParams lpHeaderLayout = new LayoutParams(MATCH_PARENT, MATCH_PARENT);
        lpHeaderLayout.addRule(CENTER_IN_PARENT);
        addView(viewParent, lpHeaderLayout);

        initAni();

        TypedArray ta = context.obtainStyledAttributes(attrs, com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader);
        mSpinnerStyle = SpinnerStyle.values()[ta.getInt(com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader_srlClassicsSpinnerStyle, mSpinnerStyle.ordinal())];
        int primaryColor = ta.getColor(com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader_srlPrimaryColor, 0);
        int accentColor = ta.getColor(com.scwang.smartrefresh.layout.R.styleable.ClassicsHeader_srlAccentColor, 0);
        if (primaryColor != 0) {
            if (accentColor != 0) {
                setPrimaryColors(primaryColor, accentColor);
            } else {
                setPrimaryColors(primaryColor);
            }
        } else if (accentColor != 0) {
            setPrimaryColors(0, accentColor);
        }
        ta.recycle();

        if (getPaddingTop() == 0) {
            if (getPaddingBottom() == 0) {
                setPadding(getPaddingLeft(), mPaddingTop = density.dip2px(20), getPaddingRight(), mPaddingBottom = density.dip2px(20));
            } else {
                setPadding(getPaddingLeft(), mPaddingTop = density.dip2px(20), getPaddingRight(), mPaddingBottom = getPaddingBottom());
            }
        } else {
            if (getPaddingBottom() == 0) {
                setPadding(getPaddingLeft(), mPaddingTop = getPaddingTop(), getPaddingRight(), mPaddingBottom = density.dip2px(20));
            } else {
                mPaddingTop = getPaddingTop();
                mPaddingBottom = getPaddingBottom();
            }
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    private void initAni() {
        mRefreshAnim = ObjectAnimator.ofFloat(mImageViewAni, "zhoujiulong", 0.0F, 1.0F).setDuration(400);
        mRefreshAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        mRefreshAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mRefreshAnim2.start();
            }
        });
        mRefreshAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float transValue = (float) animation.getAnimatedValue();
                mImageViewAni.setTranslationX(transValue * mWidth / 4);
                mImageViewAni.setRotation(90F * transValue);
            }
        });
        mRefreshAnim2 = ObjectAnimator.ofFloat(mImageViewAni, "zhoujiulong2", 1.0F, 0.0F).setDuration(800);
        mRefreshAnim2.setInterpolator(new AccelerateDecelerateInterpolator());
        mRefreshAnim2.setRepeatCount(100);
        mRefreshAnim2.setRepeatMode(ValueAnimator.REVERSE);
        mRefreshAnim2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float transValue = (float) animation.getAnimatedValue();
                mImageViewAni.setTranslationX(transValue * mWidth / 2 - mWidth / 4);
                mImageViewAni.setRotation(360F * transValue);
            }
        });
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            setPadding(getPaddingLeft(), 0, getPaddingRight(), 0);
        } else {
            setPadding(getPaddingLeft(), mPaddingTop, getPaddingRight(), mPaddingBottom);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }

    @Override
    public void onPullingDown(float percent, int offset, int headHeight, int extendHeight) {
        if (percent <= 1) {
            mImageViewAni.setScaleX(percent);
            mImageViewAni.setScaleY(percent);
        }
    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {
        if (percent <= 1) {
            mImageViewAni.setScaleX(percent);
            mImageViewAni.setScaleY(percent);
        }
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int extendHeight) {
        if (mRefreshAnim != null && !mRefreshAnim.isRunning()) mRefreshAnim.start();
    }

    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        mTextViewMsg.setText(success ? "刷新成功" : "刷新失败");
        mTextViewMsg.setVisibility(VISIBLE);
        //todo 刷新完成的动画
        mImageViewAni.setImageResource(android.R.drawable.alert_dark_frame);
        mImageViewAni.setVisibility(success ? VISIBLE : INVISIBLE);
        mImageViewAni.setRotation(0F);
        mImageViewAni.setTranslationX(0);
        if (mRefreshAnim != null && mRefreshAnim.isRunning()) mRefreshAnim.cancel();
        if (mRefreshAnim2 != null && mRefreshAnim2.isRunning()) mRefreshAnim2.cancel();
        return mFinishDuration;//延迟500毫秒之后再弹回
    }

    @Override
    @Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
        if (colors.length > 0) {
            if (!(getBackground() instanceof BitmapDrawable)) {
                setPrimaryColor(colors[0]);
            }
            if (colors.length > 1) {
                setAccentColor(colors[1]);
            } else {
                setAccentColor(colors[0] == 0xffffffff ? 0xff666666 : 0xffffffff);
            }
        }
    }


    @Override
    public SpinnerStyle getSpinnerStyle() {
        return mSpinnerStyle;
    }

    public NormalRefreshHeader setSpinnerStyle(SpinnerStyle style) {
        this.mSpinnerStyle = style;
        return this;
    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {
        mRefreshKernel = kernel;
        mRefreshKernel.requestDrawBackgoundForHeader(mBackgroundColor);
    }

    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                mImageViewAni.setVisibility(VISIBLE);
                mImageViewAni.setImageResource(R.mipmap.ic_loading);
                mTextViewMsg.setVisibility(INVISIBLE);
            case PullDownCanceled:
                mImageViewAni.setVisibility(VISIBLE);
                mImageViewAni.setImageResource(R.mipmap.ic_loading);
                mTextViewMsg.setVisibility(INVISIBLE);
                break;
            case Refreshing:
                mImageViewAni.setVisibility(VISIBLE);
                mImageViewAni.setImageResource(R.mipmap.ic_loading);
                mTextViewMsg.setVisibility(INVISIBLE);
                break;
            case ReleaseToRefresh:
                mImageViewAni.setImageResource(R.mipmap.ic_loading);
                mImageViewAni.setVisibility(VISIBLE);
                mTextViewMsg.setText("松手刷新");
                mTextViewMsg.setVisibility(VISIBLE);
                break;
            case Loading:
                mImageViewAni.setVisibility(VISIBLE);
                mImageViewAni.setImageResource(R.mipmap.ic_loading);
                mTextViewMsg.setVisibility(INVISIBLE);
                break;
            default:
                break;
        }
    }

    public NormalRefreshHeader setPrimaryColor(@ColorInt int primaryColor) {
        setBackgroundColor(mBackgroundColor = primaryColor);
        if (mRefreshKernel != null) {
            mRefreshKernel.requestDrawBackgoundForHeader(mBackgroundColor);
        }
        return this;
    }

    public NormalRefreshHeader setAccentColor(@ColorInt int accentColor) {
        mTextViewMsg.setTextColor(accentColor);
        return this;
    }

}














