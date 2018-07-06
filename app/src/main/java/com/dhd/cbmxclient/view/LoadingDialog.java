package com.dhd.cbmxclient.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.dhd.cbmxclient.R;

/**
 * Created by Administrator on 2018/2/26.
 */

public class LoadingDialog extends Dialog {

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.CommonToast);
    }

    public LoadingDialog showLoading(Context context) {
        LoadingDialog loadingDialog = new LoadingDialog(context);
        loadingDialog.show();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        return loadingDialog;
    }
}
