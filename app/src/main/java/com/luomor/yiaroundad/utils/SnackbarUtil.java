package com.luomor.yiaroundad.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by Peter on 18/6/20 12:18
 * 1097692918@qq.com
 * <p/>
 * 一个简单的SnackBar工具类
 */
public class SnackbarUtil {
    public static void showMessage(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
    }
}
