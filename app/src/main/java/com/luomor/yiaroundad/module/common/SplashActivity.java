package com.luomor.yiaroundad.module.common;

import android.content.Intent;
import android.os.Bundle;

import com.luomor.yiaroundad.utils.ConstantUtil;
import com.luomor.yiaroundad.utils.PreferenceUtil;
import com.luomor.yiaroundad.utils.SystemUiVisibilityUtil;
import com.luomor.yiaroundad.R;
import com.trello.rxlifecycle.components.RxActivity;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Peter on 18/6/12 10:16
 * 1097692918@qq.com
 * <p/>
 * 启动页界面
 */
public class SplashActivity extends RxActivity {
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bind = ButterKnife.bind(this);
        SystemUiVisibilityUtil.hideStatusBar(getWindow(), true);
        setUpSplash();
    }


    private void setUpSplash() {
        Observable.timer(2000, TimeUnit.MILLISECONDS)
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> finishTask());
    }


    private void finishTask() {
        boolean isLogin = PreferenceUtil.getBoolean(ConstantUtil.KEY, false);
        if (isLogin) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
        SplashActivity.this.finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
