package com.dhd.cbmxclient.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by dhd on 2017/12/11.
 */

public class DeviceUtils {
    /**
     * 判断是不是小米设备
     *
     * @return true是否则不是
     */
    public static boolean checkMIUI() {
        boolean isMe = false;
        String manufacturer = Build.MANUFACTURER;
        if (!TextUtils.isEmpty(manufacturer) && manufacturer.equals("Xiaomi")) {
            isMe = true;
        }
        return isMe;
    }

    /**
     * 判断是不是魅族
     *
     * @return true是否则不是
     */
    public static boolean checkFlyme() {
        boolean isMe = false;
        String display = Build.DISPLAY;
        if (!TextUtils.isEmpty(display) && display.equals("Flyme")) {
            isMe = true;
        }
        return isMe;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion() {
        try {
            PackageManager manager = ContextUtils.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(ContextUtils.getContext().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 字符串MD5
     *
     * @param string
     * @return
     */
    public static String getMd5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    //清除缓存
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
