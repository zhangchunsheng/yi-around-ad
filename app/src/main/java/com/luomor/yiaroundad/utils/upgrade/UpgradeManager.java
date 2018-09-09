package com.luomor.yiaroundad.utils.upgrade;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.yongche.sso.BuildConfig;
import com.yongche.sso.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by peterzhang on 09/02/2018.
 */

public class UpgradeManager {
    private Context mContext;
    // 提示语
    private String updateMsg = "有最新的软件包哦，亲快下载吧~";
    // 返回的安装包url
    private Dialog noticeDialog;
    private Dialog downloadDialog;
    /* 下载包安装路径 */
    // Environment.getExternalStorageDirectory().toString() + File.separator +
    // Constants.TAG.PACKAGETAG +".apk";
	/* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;
    private int progress;
    private Thread downLoadThread;
    private boolean interceptFlag = false;
    public String apkUrl = null;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    break;
                case DOWN_OVER:
                    installApk((String) msg.obj);
                    break;
                default:
                    break;
            }
        };
    };

    public UpgradeManager(Context context) {
        this.mContext = context;
    }

    public String getPackageName() {
        return BuildConfig.APPLICATION_ID;
    }

    // 外部接口让主Activity调用
    public void checkUpdateInfo(String downloadUrl, Callback callback) {
        this.apkUrl = downloadUrl;
        Log.e("test", "download_url ---->" + downloadUrl);
        showNoticeDialog(callback);
    }

    private void showNoticeDialog(final Callback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件版本更新");
        builder.setMessage(updateMsg);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                callback.download();
                showDownloadDialog();
            }
        });
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                callback.quit();
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void showDownloadDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("软件版本更新");

        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.upgrademanager_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.progress);

        builder.setView(v);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;
                System.exit(1);
            }
        });
        downloadDialog = builder.create();
        downloadDialog.setCancelable(false);
        downloadDialog.show();

        downloadApk();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Log.e("test", "apk_url ---->" + apkUrl);
                URL url = new URL(apkUrl);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(Environment.getExternalStorageDirectory().toString() + File.separator + getPackageName());
                if (!file.exists()) {
                    file.mkdir();
                }
                String apkFile = Environment.getExternalStorageDirectory().toString() + File.separator + getPackageName() + File.separator + getPackageName() + ".apk";
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do {
                    int numread = is.read(buf);
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if (numread <= 0) {
                        // 下载完成通知安装
                        Message msg = new Message();
                        msg.what = DOWN_OVER;
                        msg.obj = apkFile;
                        mHandler.sendMessage(msg);
                        break;
                    }
                    fos.write(buf, 0, numread);
                } while (!interceptFlag);// 点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 下载apk
     *
     */
    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     *
     * @param saveFileName String
     */
    private void installApk(String saveFileName) {
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
        System.exit(1);
    }
}
