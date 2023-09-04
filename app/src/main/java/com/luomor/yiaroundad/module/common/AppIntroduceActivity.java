package com.luomor.yiaroundad.module.common;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.luomor.yiaroundad.base.RxBaseActivity;
import com.luomor.yiaroundad.utils.ShareUtil;
import com.luomor.yiaroundad.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Peter on 18/6/12 10:16
 * 1097692918@qq.com
 * <p/>
 * App介绍界面
 */
public class AppIntroduceActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_version)
    TextView mVersion;
    @BindView(R.id.tv_network_diagnosis)
    TextView mTvNetworkDiagnosis;


    @Override
    public int getLayoutId() {
        return R.layout.activity_app_introduce;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initViews(Bundle savedInstanceState) {
        mVersion.setText("v" + getVersion());
    }


    @Override
    public void initToolBar() {
        mToolbar.setTitle("关于");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private String getVersion() {
        try {
            PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return getString(R.string.about_version);
        }
    }

    @OnClick({R.id.tv_share_app, R.id.tv_feedback})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_share_app:
                //分享app
                ShareUtil.shareLink(getString(R.string.github_url),
                        getString(R.string.share_title), AppIntroduceActivity.this);
                break;
            case R.id.tv_feedback:
                //意见反馈
                new AlertDialog.Builder(AppIntroduceActivity.this)
                        .setTitle(R.string.feedback_titlle)
                        .setMessage(R.string.feedback_message)
                        .setPositiveButton("确定", (dialog, which) -> dialog.dismiss())
                        .show();
                break;
        }
    }
}
