package com.dhd.cbmxclient.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by zengwendi on 2018/1/3.
 */

public class TextUtils {
    /**
     * 字符串换行处理
     *
     * @param text
     */
    public static String textWrap(String text) {
        return text.replace("\\n", "\n");
    }

    /**
     * 获取整数部分
     *
     * @param newNum
     * @return
     */
    public static String getZhengshu(String newNum) {
        if (newNum.contains("."))
            return (String) newNum.subSequence(0, newNum.indexOf("."));
        else return newNum;
    }

    /**
     * 获取小数部分
     *
     * @param newNum
     * @return
     */
    public static String getXiaoshu(String newNum) {
        if (newNum.contains("."))
            return newNum.substring(newNum.indexOf("."), newNum.length());
        else return "";
    }

    /**
     * 电话号码中间五位用星号表示
     *
     * @param s
     * @return
     */
    public static String setPhoneNoText(String s) {
        if (isEmpty(s)) {
            return "";
        } else {
            return s.substring(0, 3) + "*****" + s.substring(8, s.length());
        }
    }

    /**
     * @param amount 金额
     * @return 对服务器返回金额做非空处理
     */
    public static String getAmout(String amount) {
        if (isEmpty(amount)) {
            return "0";
        } else {
            return amount;
        }
    }

    /**
     * 带自定义默认值
     *
     * @param amount  1
     * @param defaule 1
     * @return
     */
    public static String getDefaultText(String amount, String defaule) {
        if (isEmpty(amount)) {
            return defaule;
        } else {
            return amount;
        }
    }

    /**
     * 保留两位小数
     *
     * @param d 原始shuju
     * @return
     */
    public static String getTwoPoint(double d) {
        return String.format("%.2f", d);
    }


    /**
     * 获取资源文件中的string
     *
     * @param context
     * @param id      资源id
     * @return
     */
    public static String getStringText(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static String getStringTextAppend(Context context, int id, String str) {
        return context.getResources().getString(id) + str;
    }

    public static String getTwoStrAppendText(String str1, String str2) {
        return str1 + str2;
    }

    public static String getThreeAppendText(String str1, String str2, String str3) {
        return str1 + str2 + str3;
    }

    public static boolean isEmpty(String s) {
        return s == null || s.equals("");
    }

    /**
     * 设置输入框输入的小数位数
     *
     * @param editText EditText
     * @param length   小数长度
     */
    public static void setEditTextInputDotLength(final EditText editText, final int length) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = editText.getText().toString().trim();
                if (!android.text.TextUtils.isEmpty(str)) {
                    int dotIndex = str.indexOf(".");
                    if (dotIndex >= 0) {//用户输入的是小数
                        int dotAfterSize = str.length() - 1 - dotIndex;
                        if (dotAfterSize > length) {//用户输入的金额小数点后超过两位
                            editText.setText(str.substring(0, dotIndex + length + 1));
                            editText.setSelection(editText.getText().length());
                        }
                    }
                }
            }
        });
    }
}














