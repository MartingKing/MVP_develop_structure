package com.dhd.cbmxclient.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dhd.cbmxclient.R;
import com.dhd.cbmxclient.base.BaseActivity;
import com.dhd.cbmxclient.bean.TabEntity;
import com.dhd.cbmxclient.http.httputils.NetWorkState;
import com.dhd.cbmxclient.ui.activity.test.MyMusicActivity;
import com.dhd.cbmxclient.ui.activity.test.MyPostActivity;
import com.dhd.cbmxclient.ui.fragment.cb_cycler.CBCyclerFragment;
import com.dhd.cbmxclient.ui.fragment.cb_service.CBServicesFragment;
import com.dhd.cbmxclient.ui.fragment.main.HomeFragment;
import com.dhd.cbmxclient.utils.LightStatusBarCompat;
import com.dhd.cbmxclient.utils.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenterImpl> implements MainPresenter.View {
    public static final int MAIN_TAB_HOME = 101, MAIN_TAB_MIAOXIANG = 103, MAIN_TAB_SERVICE = 102;

    @BindView(R.id.frame)
    FrameLayout mFrame;
    @BindView(R.id.common_tab)
    CommonTabLayout mCommonTab;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.main_title)
    TextView mMainTitle;
    @BindView(R.id.main_toolbar)
    Toolbar mMainToolbar;
    @BindView(R.id.icon_menu)
    TextView mIconMenu;

    private String[] mTitles = {"首页", "服务", "苗乡"};
    private int[] mIconSelectIds = {
            R.mipmap.ic_cheng_selected, R.mipmap.ic_bu_selected,
            R.mipmap.ic_xian_selected};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_cheng, R.mipmap.ic_bu,
            R.mipmap.ic_xian};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private HomeFragment mHomeFragment;
    private CBCyclerFragment mCycleFragment;
    private CBServicesFragment mCBServicesFragment;
    //当前显示页面
    private int mPosition;

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenterImpl();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadData() {
        setState(NetWorkState.STATE_SUCCESS);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initBottomTab();
        initDrawalayout();
        setStatusBarColor(true);
    }

    private void initDrawalayout() {
        setSupportActionBar(mMainToolbar);
        mMainToolbar.setTitleTextColor(getResources().getColor(android.R.color.transparent));
        mMainTitle.setBackgroundResource(R.mipmap.img_toolbar_logo_white);
        mIconMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(Gravity.START);
            }
        });
        mNavView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MyMusicActivity.class));
            }
        });
        mDrawerLayout.setStatusBarBackground(R.color.invest_icon_blue);
        if (mNavView != null) {
            setupDrawerContent(mNavView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.

                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initBottomTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mCommonTab.setTabData(mTabEntities);
        showFragment(MAIN_TAB_HOME);
        mCommonTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                showFragment(position + MAIN_TAB_HOME);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.list_navigation_menu_item:
                                startActivity(new Intent(MainActivity.this, MyPostActivity.class));
                                break;
                            case R.id.statistics_navigation_menu_item:
                                menuItem.setChecked(true);
                                startActivity(new Intent(MainActivity.this, MyMusicActivity.class));
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //开启跳转功能，从而控制从主页面跳转到指定页面
        if (mFrame == null) return;
        //不能删除，有些手机需要在这里延迟设置，不然状态栏达不到效果，待研究
        mFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mFrame == null) return;
                if (LightStatusBarCompat.isMeizuOrXiaomiOrM()) {
                    LightStatusBarCompat.fullScreen(MainActivity.this);
                    LightStatusBarCompat.setLightStatusBar(getWindow(), true);
                } else {
                    MainActivity.super.setStatusBarColor(true);
                }
            }
        }, 150);
    }

    /**
     * 显示fragment
     */
    private void showFragment(int position) {
        mPosition = position;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        mCommonTab.setCurrentTab(position - MAIN_TAB_HOME);
        switch (position) {
            case MAIN_TAB_HOME:
                setStatusBarColor(true);
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.newInstance();
                    fragmentTransaction.add(R.id.frame, mHomeFragment, String.valueOf(MAIN_TAB_HOME));
                } else {
                    fragmentTransaction.show(mHomeFragment);
                }
                break;
            case MAIN_TAB_MIAOXIANG:
                setStatusBarColor(false);
                if (mCycleFragment == null) {
                    mCycleFragment = CBCyclerFragment.newInstance();
                    fragmentTransaction.add(R.id.frame, mCycleFragment, String.valueOf(MAIN_TAB_MIAOXIANG));

                } else {
                    fragmentTransaction.show(mCycleFragment);
                }
                break;
            case MAIN_TAB_SERVICE:
                setStatusBarColor(false);
                if (mCBServicesFragment == null) {
                    mCBServicesFragment = CBServicesFragment.newInstance();
                    fragmentTransaction.add(R.id.frame, mCBServicesFragment, String.valueOf(MAIN_TAB_SERVICE));
                } else {
                    fragmentTransaction.show(mCBServicesFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    //隐藏fragment
    private void hideFragments(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mCycleFragment != null) {
            transaction.hide(mCycleFragment);
        }
        if (mCBServicesFragment != null) {
            transaction.hide(mCBServicesFragment);
        }
    }

    @Override
    public void onBackPressed() {
        setExit();
    }

    private long exitTime = 0;

    public void setExit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.showShortToast("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setStatusBarColor(boolean lightStatusBar) {
        if (LightStatusBarCompat.isMeizuOrXiaomiOrM()) {
            LightStatusBarCompat.fullScreen(this);
            LightStatusBarCompat.setLightStatusBar(getWindow(), true);
        } else {
            super.setStatusBarColor(lightStatusBar);
        }
    }

    @Override
    public void test() {
        // TODO: 2018/2/26 测试
    }

}
