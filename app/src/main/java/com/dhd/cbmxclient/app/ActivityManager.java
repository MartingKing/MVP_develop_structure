package com.dhd.cbmxclient.app;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * Created by dhd on 2016/11/16.
 * 应用程序Activity管理类：用于Activity管理和应用程序退出
 */
public class ActivityManager {
    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    protected ActivityManager() {
    }

    /**
     * 单一实例
     */
    public static ActivityManager getActivityManager() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public static Stack<Activity> getActivityStack() {
        return activityStack;
    }

    public static void setActivityStack(Stack<Activity> activityStack) {
        ActivityManager.activityStack = activityStack;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 从Activity到堆栈中移除
     */
    public void removeActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        if (activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
    }

    /**
     * 判断Activity堆栈种有没有指定Activity
     */
    public boolean isActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        return activityStack.contains(activity);
    }

    /**
     * 判断Activity堆栈种有没有指定Activity
     */
    public boolean isActivity(Class<?> cls) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?>... cls) {
        Activity a = null;
        for (Class<?> cl : cls) {
            for (Activity activity : activityStack) {
                if (activity.getClass().getSimpleName().equals(cl.getSimpleName())) {
                    a = activity;
                    break;
                }
            }
            finishActivity(a);
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }


    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());

        } catch (Exception e) {
        }
    }

    /**
     * 结束指定的Activity
     */
    public void getActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 得到指定类名的Activity
     */
    public Activity getActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * 获取最后一个制定类名的 Activity
     */
    public Activity getLastatActivity(Class<?> cls) {
        if (activityStack != null) {
            int size = activityStack.size();
            for (int i = size - 1; i >= 0; i--) {
                Activity activity = activityStack.get(i);
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        }
        return null;
    }
}
