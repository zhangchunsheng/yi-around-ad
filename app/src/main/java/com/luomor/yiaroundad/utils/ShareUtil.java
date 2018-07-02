package com.luomor.yiaroundad.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Peter on 18/6/25 18:05
 * 1097692918@qq.com
 * <p>
 * 分享工具类
 */
public class ShareUtil {

    /**
     * 分享链接
     */
    public static void shareLink(String url, String title, Context context) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TEXT, "易周围动画安卓客户端,GitHub地址:" + url);
        context.startActivity(Intent.createChooser(intent, title));
    }
}
